package com.itc.sua.gateway.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.AntPathMatcher;
import com.itc.sua.common.constants.user.UserConstants;
import com.itc.sua.common.exception.CommonException;
import com.itc.sua.gateway.config.AuthPathsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * @ClassName AuthGatewayFilterFactory
 * 在yaml配置 filters: -Auth  (针对某个模块)
 * 配置在 default-filter: -Auth (所有模块生效)
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/13
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final AuthPathsConfig authPathsConfig;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public GatewayFilter apply(Object config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            log.info("[AuthGatewayFilterFactory] >>> stat");
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if (isExclude(path)) {
                return chain.filter(exchange);
            }

            HttpHeaders headers = request.getHeaders();
            String token = null;
            List<String> auth = headers.get(UserConstants.Auth.TOKEN);
            if (CollectionUtil.isNotEmpty(auth)) {
                token = auth.get(0);
                log.info("get token = [{}]", token);
            }
            String userId;
            // JWTUtil解析token
            try {
                userId = "10086";
                log.info("gateway userId = [{}]", userId);
            } catch (CommonException e) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            // 传递userId
            ServerWebExchange swe = exchange.mutate()
                    .request(builder -> builder.header(UserConstants.Auth.USER_ID, userId))
                    .build();
            return chain.filter(swe);
        }, Ordered.LOWEST_PRECEDENCE - 1);
    }

    private boolean isExclude(String path) {
        for (String pathPattern : authPathsConfig.getExcludePaths()) {
            if (antPathMatcher.match(pathPattern, path)) {
                return true;
            }
        }
        return false;
    }
}
