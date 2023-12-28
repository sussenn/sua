package com.itc.sua.system.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName UserPasswordReq
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@Data
public class UserPasswordReq implements Serializable {
    private static final long serialVersionUID = 5083385555769704759L;

    //@NotEmpty(message = "id不能为空")
    //private Long id;

    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;

}
