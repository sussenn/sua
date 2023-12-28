package com.itc.sua.system.convert;

import com.itc.sua.system.pojo.dto.RoleAddReq;
import com.itc.sua.system.pojo.dto.RoleEditReq;
import com.itc.sua.system.pojo.entity.SysRoleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @ClassName SysRoleConverts
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@Mapper(componentModel = "spring")
public interface SysRoleConverts {

    SysRoleConverts INSTANCE = Mappers.getMapper(SysRoleConverts.class);

    SysRoleDO addReqToRoleDO(RoleAddReq addReq);

    SysRoleDO editReqToRoleDO(RoleEditReq editReq);
}
