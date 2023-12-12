package com.itc.sua.analysis.convert;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.itc.sua.analysis.pojo.dto.CommonDataAddReq;
import com.itc.sua.analysis.pojo.entity.CommonDataDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @ClassName CommonDataConvert
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
@Mapper(componentModel = "spring",
        imports = {
                IdUtil.class,
                DateUtil.class
        })
public abstract class CommonDataConvert {

    @Mappings({
            @Mapping(target = "eventId", expression = "java(IdUtil.getSnowflakeNextIdStr())")
    })
    public abstract CommonDataDO addReqToDo(CommonDataAddReq addReq);
}
