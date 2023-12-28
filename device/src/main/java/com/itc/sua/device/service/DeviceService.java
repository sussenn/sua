package com.itc.sua.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itc.sua.common.pojo.R;
import com.itc.sua.device.pojo.entity.DeviceDO;
import com.itc.sua.feign.pojo.analysis.CommonDataResp;

/**
 * @ClassName DeviceService
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
public interface DeviceService extends IService<DeviceDO> {
    R<CommonDataResp> getCommonData(String eid);
}