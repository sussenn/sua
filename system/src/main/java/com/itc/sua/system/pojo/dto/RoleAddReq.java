package com.itc.sua.system.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName RoleAddReq
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@Data
public class RoleAddReq implements Serializable {
    private static final long serialVersionUID = 47601939568069419L;

    @NotEmpty(message = "角色名不能为空")
    private String name;

    @NotEmpty(message = "角色标识不能为空")
    private String roleKey;

    private Boolean enabled;
}
