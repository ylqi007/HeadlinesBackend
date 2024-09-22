package com.atguigu.interceptor;

import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Description: 登录保护拦截器，检查request header中是否包含有效token
 *  有效：放行
 *  无效：返回504
 *
 * @Author: ylqi007
 * @Create: 9/21/24 17:05
 */
@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Get token from headers
        String token = request.getHeader("token");

        // Check validity
        boolean expiration = jwtHelper.isExpiration(token);

        // 无效时返回504
        if(expiration) {
            Result<Object> result = Result.build(null, ResultCodeEnum.NOTLOGIN);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(result);
            response.getWriter().print(json);

            return false;
        }
        return true;
    }
}
