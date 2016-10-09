package com.dodopal.portal.business.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dodopal.portal.business.constant.PortalConstants;

public class DefaultUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = super.attemptAuthentication(request, response);

        String requestCaptcha = request.getParameter("loginCaptcha");
        String genCaptcha = (String) request.getSession().getAttribute(PortalConstants.SESSION_KAPTCHATYPE_LOGIN);

        logger.info("开始校验验证码，生成的验证码为：" + genCaptcha + " ，输入的验证码为：" + requestCaptcha);

        if (requestCaptcha == null || !requestCaptcha.equalsIgnoreCase(genCaptcha)) {
            throw new BadCredentialsException("验证码不正确");
        }
        return authentication;
    }
}
