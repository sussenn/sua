package com.itc.sua.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName GatewayMain
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/13
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.itc.sua.*"})
public class GatewayMain {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMain.class, args);
    }
}
