package com.itc.sua.device;

import com.itc.sua.feign.api.analysis.CommonDataApi;
import com.itc.sua.feign.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName DeviceMain
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
@SpringBootApplication
@MapperScan("com.itc.sua.device.mapper")
@EnableFeignClients(defaultConfiguration = DefaultFeignConfig.class,
        clients = {CommonDataApi.class})
// 装载GlobalExceptionHandler
@ComponentScan(basePackages = {"com.itc.sua.*"})
public class DeviceMain {
    public static void main(String[] args) {
        SpringApplication.run(DeviceMain.class, args);
    }
}
