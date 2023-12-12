package com.itc.sua.feign.api.analysis;

import com.itc.sua.common.pojo.R;
import com.itc.sua.feign.pojo.analysis.CommonDataResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName CommonDataApi
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
@FeignClient(name = "data-analysis", contextId = "commonData")
public interface CommonDataApi {

    @GetMapping("/commonData/findByEveId/{eveId}")
    R<CommonDataResp> findByEveId(@PathVariable String eveId);
}
