package com.itc.sua.analysis;

import com.itc.sua.feign.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName DataAnalysisMain
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/2
 */
@SpringBootApplication
@MapperScan("com.itc.sua.analysis.mapper")
@EnableFeignClients(defaultConfiguration = DefaultFeignConfig.class)
public class DataAnalysisMain {
    // TLog日志
    //static {
    //    AspectLogEnhance.enhance();
    //}

    public static void main(String[] args) {
        SpringApplication.run(DataAnalysisMain.class, args);
    }
}
