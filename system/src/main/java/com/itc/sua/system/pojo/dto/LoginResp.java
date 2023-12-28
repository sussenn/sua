package com.itc.sua.system.pojo.dto;

import com.itc.sua.system.pojo.entity.SysMenuDO;
import com.itc.sua.system.pojo.entity.SysUserDO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName LoginResp
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/15
 */
@Data
public class LoginResp implements Serializable {
    private static final long serialVersionUID = 4121095640581411276L;

    private SysUserDO userInfo;

    private String token;

    private List<String> permissionList;

    private List<SysMenuDO> menuList;
}
