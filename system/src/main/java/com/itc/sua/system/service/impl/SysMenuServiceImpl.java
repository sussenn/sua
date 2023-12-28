package com.itc.sua.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itc.sua.common.exception.CommonException;
import com.itc.sua.common.pojo.auth.Menu;
import com.itc.sua.system.convert.SysMenuConverts;
import com.itc.sua.system.mapper.SysMenuMapper;
import com.itc.sua.system.pojo.dto.MenuAddReq;
import com.itc.sua.system.pojo.dto.MenuEditReq;
import com.itc.sua.system.pojo.entity.SysMenuDO;
import com.itc.sua.system.pojo.entity.SysRoleDO;
import com.itc.sua.system.pojo.entity.SysRoleMenuDO;
import com.itc.sua.system.service.SysMenuService;
import com.itc.sua.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName SysMenuServiceImpl
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/20
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuDO> implements SysMenuService {

    @Autowired
    private SysRoleService roleService;

    private final SysMenuConverts menuConverts = SysMenuConverts.INSTANCE;

    @Override
    public List<String> getPermsByUserId(Long userId) {
        return baseMapper.getPermsByUserId(userId);
    }

    @Override
    public List<SysMenuDO> getMenuByRoleId(Long roleId) {
        return this.list(Wrappers.<SysMenuDO>lambdaQuery()
                .eq(SysMenuDO::getEnabled, true)
                .inSql(SysMenuDO::getId, "select menu_id from sys_role_menu where role_id = " + roleId));
    }

    @Override
    public List<Menu> getUserMenus(Long userId) {
        List<SysRoleDO> roles = roleService.getRoleByUserId(userId);
        Set<SysMenuDO> menuSet = new HashSet<>();
        for (SysRoleDO role : roles) {
            List<SysMenuDO> ml = this.getMenuByRoleId(role.getId());
            menuSet.addAll(ml);
        }
        List<SysMenuDO> menuList = new ArrayList<>(menuSet);
        menuList.sort(Comparator.comparing(SysMenuDO::getSort));
        List<Menu> menus = menuConverts.doToDTO(menuList);
        return this.buildTreeMenu(menus);
    }

    @Override
    public List<Menu> findAll() {
        List<SysMenuDO> menus = this.list(Wrappers.<SysMenuDO>lambdaQuery().orderByAsc(SysMenuDO::getSort));
        List<Menu> list = menuConverts.doToDTO(menus);
        return this.buildTreeMenu(list);
    }

    @Override
    public List<Long> getRoleMenu(Long roleId) {
        List<SysRoleMenuDO> roleMenuList = baseMapper.getRoleMenuByRoleId(roleId);
        return roleMenuList.stream()
                .map(SysRoleMenuDO::getMenuId)
                .collect(Collectors.toList());
    }

    @Override
    public void grantMenu(Long roleId, List<Long> ids) {
        baseMapper.delRoleMenuByRoleId(roleId);
        ArrayList<SysRoleMenuDO> roleMenuList = new ArrayList<>();
        ids.forEach(mid -> {
            SysRoleMenuDO rm = new SysRoleMenuDO();
            rm.setRoleId(roleId).setMenuId(mid);
            roleMenuList.add(rm);
        });
        baseMapper.addRoleMenuBatch(roleMenuList);
    }

    @Override
    public void add(MenuAddReq addReq) {
        this.save(menuConverts.addReqToMenuDO(addReq));
    }

    @Override
    public void edit(MenuEditReq editReq) {
        this.updateById(menuConverts.editReqToMenuDO(editReq));
    }

    @Override
    public void del(Long id) {
        long count = this.count(Wrappers.<SysMenuDO>lambdaQuery().eq(SysMenuDO::getParentId, id));
        if (count > 0) {
            throw new CommonException("请先删除子菜单");
        }
        this.removeById(id);
    }

    private List<Menu> buildTreeMenu(List<Menu> menus) {
        List<Menu> result = new ArrayList<>();
        for (Menu menu : menus) {
            for (Menu m : menus) {
                // 寻找子节点
                if (m.getParentId().equals(menu.getId())) {
                    menu.getChild().add(m);
                }
            }
            // 父节点
            if (menu.getParentId() == 0L) {
                result.add(menu);
            }
        }
        return result;
    }

}
