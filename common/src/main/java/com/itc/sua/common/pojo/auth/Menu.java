package com.itc.sua.common.pojo.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Menu
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/26
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 6590229375093257708L;

    private Long id;

    private String menuName;

    private Long parentId;

    private String menuType;

    private String path;

    private String component;

    private Integer sort;

    private Boolean enabled;

    private String perms;

    private String icon;

    private List<Menu> child = new ArrayList<>();
}
