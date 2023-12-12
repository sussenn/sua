package com.itc.sua.device;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName DeviceMain
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
@SpringBootApplication
@MapperScan("com.itc.sua.device.mapper")
@EnableFeignClients
public class DeviceMain {
    public static void main(String[] args) {
        SpringApplication.run(DeviceMain.class,args);
    }
}
