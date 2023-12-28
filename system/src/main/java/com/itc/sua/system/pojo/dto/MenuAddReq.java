package com.itc.sua.system.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName MenuAddReq
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@Data
public class MenuAddReq implements Serializable {
    private static final long serialVersionUID = 717362574733558299L;

    @NotEmpty(message = "名称不能为空")
    private String menuName;

    private Long parentId;

    /**
     * M目录 C菜单 F按钮
     */
    private String menuType;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 显示顺序
     */
    private Integer sort;

    private Boolean enabled;

    private String perms;

    private String icon;
}
