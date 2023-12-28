package com.itc.sua.system.pojo.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itc.sua.system.pojo.entity.SysUserDO;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName AuthUserDTO
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/20
 */
@Getter
@ToString
public class AuthUserDTO implements UserDetails {
    private static final long serialVersionUID = -5923401185405121905L;

    private final SysUserDO sysUser;

    private final List<String> permissions;

    @JsonIgnore
    private Set<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        if (CollectionUtil.isNotEmpty(authorities)) {
            authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        return authorities;
    }

    public AuthUserDTO(SysUserDO user, List<String> permissions) {
        this.sysUser = user;
        this.permissions = permissions;
    }

    //@Setter
    //private String token;
    //@Setter
    //private String phone;

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
