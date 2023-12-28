package com.itc.sua.system.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName RoleEditReq
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@Data
public class RoleEditReq implements Serializable {
    private static final long serialVersionUID = 4748836504114260548L;

    @NotEmpty(message = "id不能为空")
    private Long id;

    private String name;

    private String roleKey;

    private Boolean enabled;
}
