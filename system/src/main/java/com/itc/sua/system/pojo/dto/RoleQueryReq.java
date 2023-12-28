package com.itc.sua.system.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName RoleQueryReq
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQueryReq extends PageDTO implements Serializable {
    private static final long serialVersionUID = 3771636001875400025L;

    private String name;
}
