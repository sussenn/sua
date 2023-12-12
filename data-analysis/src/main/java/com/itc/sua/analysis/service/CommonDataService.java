package com.itc.sua.analysis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itc.sua.analysis.pojo.dto.CommonDataAddReq;
import com.itc.sua.analysis.pojo.entity.CommonDataDO;

/**
 * @ClassName CommonDataService
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
public interface CommonDataService extends IService<CommonDataDO> {

    void add(CommonDataAddReq addReq);

    CommonDataDO findByEveId(String eveId);
}
