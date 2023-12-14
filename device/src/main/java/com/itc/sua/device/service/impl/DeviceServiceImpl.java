package com.itc.sua.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itc.sua.common.pojo.R;
import com.itc.sua.device.mapper.DeviceMapper;
import com.itc.sua.device.pojo.entity.DeviceDO;
import com.itc.sua.device.service.DeviceService;
import com.itc.sua.feign.api.analysis.CommonDataApi;
import com.itc.sua.feign.pojo.analysis.CommonDataResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName DeviceServiceImpl
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
@Service
@Slf4j
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, DeviceDO> implements DeviceService {

    @Autowired
    private CommonDataApi commonDataApi;

    @Override
    public R<CommonDataResp> getCommonData(String eid) {
        log.info("device module call data-analysis start >>>");
        return commonDataApi.findByEveId(eid);
    }
}
