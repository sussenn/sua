package com.itc.sua.system.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itc.sua.common.enums.ApiErrCode;
import com.itc.sua.common.pojo.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthEntryPointHandler 认证失败处理器
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/21
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEntryPointHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        R<Object> r = R.error(ApiErrCode.AUTH_FAIL);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(objectMapper.writeValueAsString(r));
    }
}
