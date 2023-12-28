package com.itc.sua.system.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.itc.sua.common.constants.system.SecurityConstants;
import com.itc.sua.common.exception.CommonException;
import com.itc.sua.common.pojo.auth.AuthLoginUser;
import com.itc.sua.common.pojo.auth.Menu;
import com.itc.sua.system.pojo.dto.AuthUserDTO;
import com.itc.sua.system.pojo.dto.LoginParamDTO;
import com.itc.sua.system.pojo.dto.SmsCodeDTO;
import com.itc.sua.system.pojo.entity.SysUserDO;
import com.itc.sua.system.service.LoginService;
import com.itc.sua.system.service.SysMenuService;
import com.itc.sua.system.utils.JwtUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LoginServiceImpl
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/15
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysMenuService menuService;

    private final Captcha captcha = new ArithmeticCaptcha(111, 36);

    @Override
    public Map<String, String> getImgCode() {
        String uuid = IdUtil.simpleUUID();
        String captchaValue = captcha.text();
        // captchaValue有几率为浮点型
        if (captcha.getCharType() == 1 && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        log.info("[getImgCode] >>> imgCode=[{}]", captchaValue);
        // captchaValue保存到redis
        String key = SecurityConstants.RedisKey.IMAGE_CODE_KEY + uuid;
        redisTemplate.opsForValue().set(key, captchaValue);
        redisTemplate.expire(key, 2, TimeUnit.MINUTES);

        return new HashMap<String, String>(2) {{
            put("imgBse64", captcha.toBase64());
            put("imgId", uuid);
        }};
    }

    @Override
    public Boolean getSmsCode(SmsCodeDTO smsCode) {
        this.verifyImageCode(smsCode.getImgId(), smsCode.getImgCode());
        String mobile = smsCode.getMobile();
        boolean isMobile = PhoneUtil.isMobile(mobile);
        if (!isMobile) {
            throw new CommonException("手机号格式错误");
        }
        // 验证短信发送次数
        this.limitVerify(mobile);

        //this.codeErrorLimit(mobile);

        String code = RandomUtil.randomNumbers(6);
        log.info("[getSmsCode] >>> smsCode=[{}]", code);
        // 发送短信
        boolean suc = true;
        if (suc) {
            String smsCodeKey = SecurityConstants.RedisKey.SMS_CODE_KEY + mobile;
            redisTemplate.opsForValue().set(smsCodeKey, code);
            redisTemplate.expire(smsCodeKey, 5, TimeUnit.MINUTES);
            // 限制次数+1
            this.incrLimit(mobile);
        }
        return suc;
    }

    @Override
    public void logout() {
        UsernamePasswordAuthenticationToken authToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        AuthUserDTO authUser = (AuthUserDTO) authToken.getPrincipal();
        // 清除缓存
        redisTemplate.delete(SecurityConstants.RedisKey.AUTH_USER + authUser.getSysUser().getId());
    }

    @Override
    public AuthLoginUser login(LoginParamDTO loginParam) {
        log.warn("[login] start >>>");
        // 验证码校验
        this.verifyImageCode(loginParam.getImgId(), loginParam.getSmsCode());

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginParam.getUsername(), loginParam.getPassword());
        // 验证用户名密码
        Authentication authenticate = authenticationManager.authenticate(authToken);
        if (null == authenticate) {
            log.error("[login] execute fail, authenticate is null");
            throw new CommonException("登录失败");
        }
        // 权限信息在userDetails已经处理了
        AuthUserDTO authUser = (AuthUserDTO) authenticate.getPrincipal();

        SysUserDO sysUser = authUser.getSysUser();
        Long userId = sysUser.getId();

        // 生成token
        String token = JwtUtil.createToken(userId.toString(), authUser.getUsername());

        // 查询出用户菜单权限
        List<Menu> menus = menuService.getUserMenus(userId);
        // 获取用户拥有菜单的path, 放入redis
        List<String> pathList = new ArrayList<>();
        this.getPath(pathList, menus);

        AuthLoginUser loginUser = new AuthLoginUser();
        loginUser.setUserId(userId);
        loginUser.setUserName(sysUser.getUsername());
        loginUser.setEmail(sysUser.getEmail());
        loginUser.setMobile(sysUser.getMobile());
        loginUser.setToken(token);
        loginUser.setPermList(authUser.getPermissions());
        loginUser.setMenuList(menus);
        loginUser.setPathList(pathList);

        String userIdKey = SecurityConstants.RedisKey.AUTH_USER + userId;
        this.setExpVal(userIdKey, loginUser, 30L, TimeUnit.MINUTES);

        loginUser.setPermList(null);
        loginUser.setPathList(null);
        return loginUser;
    }

    private void getPath(List<String> pathList, List<Menu> menus) {
        for (Menu menu : menus) {
            if ("F".equals(menu.getMenuType())) {
                pathList.add(menu.getPath());
            }
            this.getPath(pathList, menu.getChild());
        }
    }

    /**
     * 限制条件: 同一个手机号
     * 1. 1天最多只能发送20条短信
     * 2. 1分钟内最多只能发送1条
     */
    private void limitVerify(String mobile) {
        String dayCountKey = SecurityConstants.RedisKey.DAY_COUNT_KEY + this.getTodayDateStr() + ":" + mobile;
        int dayCount = this.getInt(dayCountKey);
        if (dayCount > SecurityConstants.Limit.SMS_CODE_DAY_LIMIT) {
            throw new CommonException(String.format("超出当日发送%s条短信限制", SecurityConstants.Limit.SMS_CODE_DAY_LIMIT));
        }
        String periodCountKey = SecurityConstants.RedisKey.PERIOD_COUNT_KEY + mobile;
        int periodCount = this.getInt(periodCountKey);
        if (periodCount > SecurityConstants.Limit.SMS_CODE_PERIOD_LIMIT) {
            throw new CommonException("请在1分钟以后再次发送短信");
        }
    }

    private void verifyImageCode(String imgId, String code) {
        String key = SecurityConstants.RedisKey.IMAGE_CODE_KEY + imgId;
        // 从redis取
        String captchaValue = (String) redisTemplate.opsForValue().get(key);
        if (StrUtil.isBlank(captchaValue)) {
            throw new CommonException("图片验证码不存在或已过期");
        }
        if (!StrUtil.equals(code, captchaValue)) {
            throw new CommonException("图片验证码错误");
        }
        // 清除redis
        redisTemplate.delete(key);
    }

    /**
     * 验证码输入错误达上限后, 锁定账户10分钟
     */
    private void codeErrorLimit(String mobile) {
        String loginCountKey = SecurityConstants.RedisKey.LOGIN_COUNT_KEY + ":" + mobile;
        int loginCount = this.getInt(loginCountKey);
        if (loginCount > SecurityConstants.Limit.SMS_CODE_LIMIT_TRY_LOGIN_TIMES) {
            throw new CommonException("验证码错误次数已达上限, 账户被锁定");
        }
    }

    private void setExpVal(String key, Object value, Long expire, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expire, unit);
    }

    private int getInt(String key) {
        Integer i = (Integer) redisTemplate.opsForValue().get(key);
        return null == i ? 0 : i;
    }

    private void incr(String key, Integer i, TimeUnit unit) {
        Long count = redisTemplate.opsForValue().increment(key);
        if (null != count && count == 1) {
            redisTemplate.expire(key, i, unit);
        }
    }

    private void incrLimit(String mobile) {
        // 1天最多发送次数限制
        this.incr(SecurityConstants.RedisKey.DAY_COUNT_KEY + this.getTodayDateStr() + ":" + mobile, 1, TimeUnit.DAYS);

        // 1分钟只能发送1条
        this.incr(SecurityConstants.RedisKey.PERIOD_COUNT_KEY + mobile, SecurityConstants.Limit.SMS_CODE_PERIOD_LIMIT, TimeUnit.MINUTES);
    }

    private String getTodayDateStr() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    }
}
