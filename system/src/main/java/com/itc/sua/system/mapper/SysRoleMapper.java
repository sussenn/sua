package com.itc.sua.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itc.sua.system.pojo.entity.SysRoleDO;
import com.itc.sua.system.pojo.entity.SysUserRoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName SysRoleMapper
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/20
 */
public interface SysRoleMapper extends BaseMapper<SysRoleDO> {

    void delUserRoleTiesByUserId(@Param("userId") Long userId);

    void addUserRoleBatch(@Param("userRoleList") List<SysUserRoleDO> userRoleList);

    void delUserRoleTiesByRoleIds(@Param("ids") List<Long> ids);
}
