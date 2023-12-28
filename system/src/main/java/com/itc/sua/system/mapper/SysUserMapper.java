package com.itc.sua.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itc.sua.system.pojo.entity.SysUserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName SysUserMapper
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/18
 */
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    void delUserRoleTiesByUserIds(@Param("ids") List<Long> ids);

}
