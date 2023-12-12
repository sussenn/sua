package com.itc.sua.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName DefaultFeignLogConfig
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
public class DefaultFeignLogConfig {

    @Bean
    public Logger.Level feignLogLevel() {
        return Logger.Level.BASIC;
    }

}
