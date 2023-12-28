package com.itc.sua.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itc.sua.common.exception.CommonException;
import com.itc.sua.system.convert.SysUserConverts;
import com.itc.sua.system.mapper.SysUserMapper;
import com.itc.sua.system.pojo.dto.AuthUserDTO;
import com.itc.sua.system.pojo.dto.UserAddReq;
import com.itc.sua.system.pojo.dto.UserEditReq;
import com.itc.sua.system.pojo.dto.UserInfoResp;
import com.itc.sua.system.pojo.dto.UserPasswordReq;
import com.itc.sua.system.pojo.dto.UserQueryReq;
import com.itc.sua.system.pojo.entity.SysMenuDO;
import com.itc.sua.system.pojo.entity.SysRoleDO;
import com.itc.sua.system.pojo.entity.SysUserDO;
import com.itc.sua.system.service.SysMenuService;
import com.itc.sua.system.service.SysRoleService;
import com.itc.sua.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName SysUserServiceImpl
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/18
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements SysUserService {

    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysMenuService menuService;
    @Autowired
    PasswordEncoder passwordEncoder;

    private final SysUserConverts userConverts = SysUserConverts.INSTANCE;

    @Override
    public void add(UserAddReq addReq) {
        SysUserDO one = this.getOne(Wrappers.<SysUserDO>lambdaQuery()
                .eq(SysUserDO::getUsername, addReq.getUsername())
                .last("limit 1")
        );
        if (null != one) {
            throw new CommonException("用户名已存在");
        }
        SysUserDO user = userConverts.addReqToUserDO(addReq);
        user.setPassword(passwordEncoder.encode(addReq.getPassword()));
        this.save(user);
    }

    @Override
    public void edit(UserEditReq editReq) {
        this.updateById(userConverts.editReqToUserDO(editReq));
    }

    @Override
    public Boolean updatePassword(UserPasswordReq pswReq) {
        AuthUserDTO authUser = getAuthUserDTO();
        if (null != authUser) {
            SysUserDO sysUser = this.getById(authUser.getSysUser().getId());
            if (passwordEncoder.matches(pswReq.getOldPassword(), sysUser.getPassword())) {
                sysUser.setPassword(passwordEncoder.encode(pswReq.getNewPassword()));
                return this.updateById(sysUser);
            } else {
                throw new CommonException("旧密码输出错误");
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> findPage(UserQueryReq queryReq) {
        Page<SysUserDO> p = new Page<>(queryReq.getPageNum(), queryReq.getPageSize());
        Page<SysUserDO> pages = this.page(p, Wrappers.<SysUserDO>lambdaQuery()
                .like(StrUtil.isNotBlank(queryReq.getName()), SysUserDO::getUsername, queryReq.getName())
                .like(StrUtil.isNotBlank(queryReq.getMobile()), SysUserDO::getMobile, queryReq.getMobile())
                .like(StrUtil.isNotBlank(queryReq.getEmail()), SysUserDO::getEmail, queryReq.getEmail())
        );
        List<UserInfoResp> result = pages.getRecords().stream().map(r -> {
            List<SysRoleDO> roles = roleService.getRoleByUserId(r.getId());
            UserInfoResp resp = new UserInfoResp();
            return resp.setId(r.getId()).setUsername(r.getUsername())
                    .setEmail(r.getEmail())
                    .setMobile(r.getMobile())
                    .setEnabled(r.getEnabled())
                    .setRoleList(roles);
        }).collect(Collectors.toList());
        Map<String, Object> resMap = new HashMap<>(2);
        resMap.put("sysUserList", result);
        resMap.put("total", pages.getTotal());
        return resMap;
    }

    @Override
    public UserInfoResp getUserInfo() {
        AuthUserDTO authUser = getAuthUserDTO();
        if (null != authUser) {
            return userConverts.userDoToUserResp(authUser.getSysUser());
        }
        return null;
    }

    @Override
    public void delBatch(List<Long> ids) {
        this.removeByIds(ids);
        baseMapper.delUserRoleTiesByUserIds(ids);
    }

    @Override
    public SysUserDO getByUsername(String username) {
        return this.getOne(Wrappers.<SysUserDO>lambdaQuery()
                .eq(SysUserDO::getEnabled, true)
                .eq(SysUserDO::getUsername, username)
                .last("limit 1"));
    }

    @Override
    public List<String> getUserAuthInfo(Long userId) {
        StringBuffer authStr = new StringBuffer();
        // 根据userId获取所有角色
        List<SysRoleDO> roles = roleService.getRoleByUserId(userId);
        if (CollectionUtil.isNotEmpty(roles)) {
            String roleKeyStr = roles.stream().map(SysRoleDO::getRoleKey)
                    .distinct()
                    .collect(Collectors.joining(","));
            authStr.append(roleKeyStr);
        }
        // 遍历角色,获取所有菜单权限
        Set<String> menuPermsSet = new HashSet<>();
        for (SysRoleDO role : roles) {
            List<SysMenuDO> menus = menuService.getMenuByRoleId(role.getId());
            for (SysMenuDO menu : menus) {
                String perms = menu.getPerms();
                if (StrUtil.isNotBlank(perms)) {
                    menuPermsSet.add(perms);
                }
            }
        }

        if (CollectionUtil.isNotEmpty(menuPermsSet)) {
            authStr.append(",");
            String menuPermsStr = String.join(",", menuPermsSet);
            authStr.append(menuPermsStr);
        }
        log.warn("[getUserAuthInfo] >>> userId=[{}], authStr=[{}]", userId, authStr);
        return Stream.of(authStr.toString().split(",")).collect(Collectors.toList());
    }

    private static AuthUserDTO getAuthUserDTO() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (AuthUserDTO) auth.getPrincipal();
        } catch (Exception e) {
            log.error("[getAuthUserDTO] >>> get auth user info fail");
            return null;
        }
    }

}
