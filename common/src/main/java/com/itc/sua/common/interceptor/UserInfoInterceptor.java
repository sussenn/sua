package com.itc.sua.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.itc.sua.common.constants.user.UserConstants;
import com.itc.sua.common.utils.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UserInfoInterceptor
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/13
 */
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader(UserConstants.Auth.USER_ID);
        if (StrUtil.isNotBlank(userId)) {
            UserContext.setUser(userId);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}
