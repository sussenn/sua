package com.itc.sua.system.filter;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.itc.sua.common.constants.system.SecurityConstants;
import com.itc.sua.common.pojo.auth.AuthLoginUser;
import com.itc.sua.system.pojo.dto.AuthUserDTO;
import com.itc.sua.system.pojo.entity.SysUserDO;
import com.itc.sua.system.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TokenFilter
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/20
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 登录完成拥有token后执行的过滤器
        log.warn("[Token.doFilterInternal] execute >>>");
        String token = request.getHeader(SecurityConstants.AuthHeader.TOKEN);
        if (StringUtils.hasText(token)) {
            log.warn("[doFilterInternal] start. token = [{}]", token);
            // 解析token
            String userId = JwtUtil.getClaims(token).getSubject();
            String key = SecurityConstants.RedisKey.AUTH_USER + userId;
            // 从redis取出用户信息
            AuthLoginUser loginUser = (AuthLoginUser) redisTemplate.opsForValue().get(key);
            if (null != loginUser) {
                // loginUser转换成AuthUserDO
                AuthUserDTO authUser = this.loginUserConvert(loginUser);
                // 将权限信息放到authToken
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authUser, token, authUser.getAuthorities());
                // 放入contextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
                // token续期
                this.tokenRenewal(userId);
            }
        }
        filterChain.doFilter(request, response);
    }

    private AuthUserDTO loginUserConvert(AuthLoginUser loginUser) {
        SysUserDO sysUser = new SysUserDO();
        sysUser.setId(loginUser.getUserId())
                .setUsername(loginUser.getUserName())
                .setMobile(loginUser.getMobile())
                .setEmail(loginUser.getEmail());
        return new AuthUserDTO(sysUser, loginUser.getPermList());
    }

    private void tokenRenewal(String userId) {
        // 判断是否续期token,计算token的过期时间
        String key = SecurityConstants.RedisKey.AUTH_USER + userId;
        long time = redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
        // 判断当前时间与过期时间的时间差
        long differ = expireDate.getTime() - System.currentTimeMillis();
        // 如果在续期检查的范围内(30分钟)则续期
        long exp = SecurityConstants.Limit.TOKEN_RENEWAL_MILL;
        if (differ <= exp) {
            // 续期30分钟
            long renew = time + exp;
            redisTemplate.expire(key, renew, TimeUnit.MILLISECONDS);
        }
    }
}
