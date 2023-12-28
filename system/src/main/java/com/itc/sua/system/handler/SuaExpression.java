package com.itc.sua.system.handler;

import com.itc.sua.system.pojo.dto.AuthUserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName SuaExpression
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/24
 */
@Component("sua")
public class SuaExpression {

    /**
     * 检查用户是否有权限
     */
    public Boolean check(String... permissions) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDTO authUser = (AuthUserDTO) auth.getPrincipal();
        // 获取当前用户的所有权限
        List<String> perms = authUser.getPermissions();
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return perms.contains("admin") || Arrays.stream(permissions).anyMatch(perms::contains);
    }
}
