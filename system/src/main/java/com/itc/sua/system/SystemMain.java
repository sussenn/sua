package com.itc.sua.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName SystemMain
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/15
 */
@SpringBootApplication
@MapperScan({"com.itc.sua.system.mapper"})
public class SystemMain {
    public static void main(String[] args) {
        SpringApplication.run(SystemMain.class, args);
    }
}
