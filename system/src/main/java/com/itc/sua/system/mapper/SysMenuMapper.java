package com.itc.sua.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itc.sua.system.pojo.entity.SysMenuDO;
import com.itc.sua.system.pojo.entity.SysRoleMenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName SysMenuMapper
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/20
 */
public interface SysMenuMapper extends BaseMapper<SysMenuDO> {

    List<String> getPermsByUserId(@Param("userId") Long userId);

    List<SysRoleMenuDO> getRoleMenuByRoleId(@Param("roleId") Long roleId);

    void delRoleMenuByRoleId(@Param("roleId") Long roleId);

    void addRoleMenuBatch(@Param("roleMenuList") List<SysRoleMenuDO> roleMenuList);
}
