package com.itc.sua.common.pojo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName AuthLoginUser
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/26
 */
@Data
public class AuthLoginUser implements Serializable {
    private static final long serialVersionUID = -5798655250314470290L;

    private Long userId;

    private String userName;

    private String mobile;

    private String email;

    private String token;

    private List<Menu> menuList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> permList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> pathList;
}
