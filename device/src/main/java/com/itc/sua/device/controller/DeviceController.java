package com.itc.sua.device.controller;

import com.itc.sua.common.pojo.R;
import com.itc.sua.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DeviceController
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/getCommonData/{eid}")
    public R getCommonData(@PathVariable String eid) {
        return R.success(deviceService.getCommonData(eid).getData());
    }

}
