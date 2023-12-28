package com.itc.sua.system.pojo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName UserAddReq
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@Data
public class UserAddReq implements Serializable {
    private static final long serialVersionUID = 7997861222542919402L;

    @NotEmpty(message = "名称不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

    private Boolean enabled;

    @Email(message = "邮箱格式错误")
    private String email;

    @NotEmpty(message = "手机号不能为空")
    private String mobile;
}
