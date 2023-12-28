package com.itc.sua.system.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName UserQueryReq
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryReq extends PageDTO implements Serializable {
    private static final long serialVersionUID = -1474608085549710623L;

    private String name;

    private String mobile;

    private String email;
}
