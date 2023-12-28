package com.itc.sua.system.pojo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName UserEditReq
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@Data
public class UserEditReq implements Serializable {
    private static final long serialVersionUID = 7997861222542919402L;

    @NotEmpty(message = "id不能为空")
    private Long id;

    private String username;

    private Boolean enabled;

    @Email(message = "邮箱格式错误")
    private String email;

    private String mobile;
}
