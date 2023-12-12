package com.itc.sua.analysis.controller;

import com.itc.sua.analysis.pojo.dto.CommonDataAddReq;
import com.itc.sua.analysis.service.CommonDataService;
import com.itc.sua.common.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CommonDataController
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
@RestController
@RequestMapping("/commonData")
public class CommonDataController {

    @Autowired
    private CommonDataService commonDataService;

    @PostMapping("/add")
    public R add(@RequestBody CommonDataAddReq addReq) {
        commonDataService.add(addReq);
        return R.success();
    }

    @GetMapping("/findByEveId/{eveId}")
    public R findByEveId(@PathVariable String eveId) {
        return R.success(commonDataService.findByEveId(eveId));
    }
}
