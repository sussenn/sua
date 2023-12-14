package com.itc.sua.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName AuthPathsConfig
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/13
 */
@Data
@Component
@ConfigurationProperties(prefix = "sua.auth")
public class AuthPathsConfig {

    private List<String> includePaths;

    private List<String> excludePaths;
}
