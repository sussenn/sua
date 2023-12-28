package com.itc.sua.system.convert;

import com.itc.sua.common.pojo.auth.Menu;
import com.itc.sua.system.pojo.dto.MenuAddReq;
import com.itc.sua.system.pojo.dto.MenuEditReq;
import com.itc.sua.system.pojo.entity.SysMenuDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @ClassName SysMenuConverts
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@Mapper(componentModel = "spring")
public interface SysMenuConverts {

    SysMenuConverts INSTANCE = Mappers.getMapper(SysMenuConverts.class);

    SysMenuDO addReqToMenuDO(MenuAddReq addReq);

    SysMenuDO editReqToMenuDO(MenuEditReq editReq);

    @Mappings({
            @Mapping(target = "child", ignore = true)
    })
    List<Menu> doToDTO(List<SysMenuDO> menus);
}
