package com.itc.sua.system.convert;

import com.itc.sua.system.pojo.dto.UserAddReq;
import com.itc.sua.system.pojo.dto.UserEditReq;
import com.itc.sua.system.pojo.dto.UserInfoResp;
import com.itc.sua.system.pojo.entity.SysUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @ClassName SysUserConverts
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@Mapper(componentModel = "spring")
public interface SysUserConverts {

    SysUserConverts INSTANCE = Mappers.getMapper(SysUserConverts.class);


    SysUserDO addReqToUserDO(UserAddReq addReq);

    SysUserDO editReqToUserDO(UserEditReq editReq);

    UserInfoResp userDoToUserResp(SysUserDO sysUser);

}
