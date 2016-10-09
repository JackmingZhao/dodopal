package com.dodopal.oss.business.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.alibaba.fastjson.JSON;
import com.dodopal.common.constant.ResponseCode;

@SuppressWarnings("deprecation")
public class AuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            Map<String, Object> error = new HashMap<String, Object>();
            error.put("", false);
            error.put("code", ResponseCode.SESSION_TIME_OUT);
            error.put("message", "与服务器的回话已经超时");
            error.put("data", "");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JSON.toJSONString(error));
            printWriter.flush();
            printWriter.close();
        } else {
            super.commence(request, response, authException);
        }
    }
}
