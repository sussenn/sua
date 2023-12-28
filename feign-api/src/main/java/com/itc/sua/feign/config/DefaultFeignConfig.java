package com.itc.sua.feign.config;

import com.itc.sua.common.constants.system.SecurityConstants;
import com.itc.sua.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName DefaultFeignConfig
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
public class DefaultFeignConfig {

    @Bean
    public Logger.Level feignLogLevel() {
        return Logger.Level.BASIC;
    }

    /**
     * 服务间调用,不走gateway时,将用户信息携带上
     */
    @Bean
    public RequestInterceptor userInfoInterceptor() {
        return requestTemplate -> requestTemplate
                .header(SecurityConstants.AuthHeader.USER_ID, UserContext.getUser());
    }

}
