package com.itc.sua.system.service.impl;

import com.itc.sua.common.exception.CommonException;
import com.itc.sua.system.pojo.dto.AuthUserDTO;
import com.itc.sua.system.pojo.entity.SysUserDO;
import com.itc.sua.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserDetailsServiceImpl
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/20
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.warn("[loadUserByUsername] start >>> username=[{}]", username);
        SysUserDO sysUser = sysUserService.getByUsername(username);
        if (null == sysUser) {
            throw new CommonException("用户名或密码错误");
        }
        Long userId = sysUser.getId();
        // 数据库查出权限信息,放到userDetails
        List<String> perms = sysUserService.getUserAuthInfo(userId);
        return new AuthUserDTO(sysUser, perms);
    }
}
