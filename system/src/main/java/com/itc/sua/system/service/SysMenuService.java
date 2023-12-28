package com.itc.sua.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itc.sua.common.pojo.auth.Menu;
import com.itc.sua.system.pojo.dto.MenuAddReq;
import com.itc.sua.system.pojo.dto.MenuEditReq;
import com.itc.sua.system.pojo.entity.SysMenuDO;

import java.util.List;

/**
 * @ClassName SysMenuService
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/20
 */
public interface SysMenuService extends IService<SysMenuDO> {

    List<String> getPermsByUserId(Long userId);

    List<SysMenuDO> getMenuByRoleId(Long roleId);

    /**
     * 获取用户所有菜单
     */
    List<Menu> getUserMenus(Long userId);

    List<Menu> findAll();

    /**
     * 获取角色的权限菜单id集合
     */
    List<Long> getRoleMenu(Long roleId);

    /**
     * 给角色分配菜单
     */
    void grantMenu(Long roleId, List<Long> ids);

    void add(MenuAddReq addReq);

    void edit(MenuEditReq editReq);

    void del(Long id);
}
