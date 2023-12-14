package com.itc.sua.analysis.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itc.sua.analysis.convert.CommonDataConvert;
import com.itc.sua.analysis.mapper.CommonDataMapper;
import com.itc.sua.analysis.pojo.dto.CommonDataAddReq;
import com.itc.sua.analysis.pojo.entity.CommonDataDO;
import com.itc.sua.analysis.service.CommonDataService;
import com.itc.sua.common.enums.ApiErrCode;
import com.itc.sua.common.exception.CommonException;
import com.itc.sua.common.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CommonDataServiceImpl
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
@Service
@Slf4j
public class CommonDataServiceImpl extends ServiceImpl<CommonDataMapper, CommonDataDO> implements CommonDataService {

    @Autowired
    private CommonDataConvert commonDataConvert;

    @Override
    public void add(CommonDataAddReq addReq) {
        this.save(commonDataConvert.addReqToDo(addReq));
    }

    @Override
    public CommonDataDO findByEveId(String eveId) {
        log.info("[findByEveId] eveId = {}, userId = {}", eveId, UserContext.getUser());
        CommonDataDO data = this.getOne(Wrappers.<CommonDataDO>lambdaQuery().eq(CommonDataDO::getEventId, eveId));
        if (null != data) {
            return data;
        }
        throw new CommonException(ApiErrCode.EMPTY);
    }
}
