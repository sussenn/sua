package com.itc.sua.system.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName LoginParamDTO
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/15
 */
@Data
public class LoginParamDTO implements Serializable {
    private static final long serialVersionUID = -4326393669321148028L;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

    //@NotEmpty(message = "手机号不能为空")
    private String mobile;

    @NotEmpty(message = "图片id不能为空")
    private String imgId;

    @NotEmpty(message = "验证码不能为空")
    private String smsCode;
}
