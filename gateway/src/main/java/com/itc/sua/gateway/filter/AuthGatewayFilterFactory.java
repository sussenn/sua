package com.itc.sua.gateway.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.AntPathMatcher;
import com.itc.sua.common.constants.system.SecurityConstants;
import com.itc.sua.common.exception.CommonException;
import com.itc.sua.common.pojo.auth.AuthLoginUser;
import com.itc.sua.gateway.config.AuthPathsConfig;
import com.itc.sua.gateway.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

    private final RedisTemplate<String, Object> redisTemplate;

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
            List<String> auth = headers.get(SecurityConstants.AuthHeader.TOKEN);
            if (CollectionUtil.isEmpty(auth)) {
                return buildErrVoidMono(exchange, HttpStatus.UNAUTHORIZED);
            }
            String token = auth.get(0);
            log.info("[apply] >>> get token = [{}]", token);

            String userId;
            try {
                // JWTUtil解析token
                userId = JwtUtil.getClaims(token).getSubject();
                log.info("[apply] >>> userId = [{}]", userId);
            } catch (CommonException e) {
                return buildErrVoidMono(exchange, HttpStatus.UNAUTHORIZED);
            }

            // 从redis获取用户url权限
            String userIdKey = SecurityConstants.RedisKey.AUTH_USER + userId;
            AuthLoginUser loginUser = (AuthLoginUser) redisTemplate.opsForValue().get(userIdKey);
            if (null == loginUser) {
                return buildErrVoidMono(exchange, HttpStatus.UNAUTHORIZED);
            }
            List<String> pathList = loginUser.getPathList();
            if (CollectionUtil.isEmpty(pathList)) {
                log.error("[apply] >>> user pathList is null");
                return buildErrVoidMono(exchange, HttpStatus.FORBIDDEN);
            }
            boolean hasPath = pathList.stream().anyMatch(path::startsWith);
            if (!hasPath) {
                log.error("[apply] >>> user not has pathAuth, path=[{}]", path);
                return buildErrVoidMono(exchange, HttpStatus.FORBIDDEN);
            }
            // 传递userId
            ServerWebExchange swe = exchange.mutate()
                    .request(builder -> builder.header(SecurityConstants.AuthHeader.USER_ID, userId))
                    .build();
            return chain.filter(swe);
        }, Ordered.LOWEST_PRECEDENCE - 1);
    }

    @NotNull
    private static Mono<Void> buildErrVoidMono(ServerWebExchange exchange, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
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
