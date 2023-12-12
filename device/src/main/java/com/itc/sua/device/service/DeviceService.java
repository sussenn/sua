package com.itc.sua.device.service;

import com.itc.sua.common.pojo.R;
import com.itc.sua.device.feign.pojo.CommonDataResp;

/**
 * @ClassName DeviceService
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
public interface DeviceService {
    R<CommonDataResp> getCommonData(String eid);
}