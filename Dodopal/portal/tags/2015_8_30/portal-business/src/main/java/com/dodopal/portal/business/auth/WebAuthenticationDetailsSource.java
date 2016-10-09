package com.dodopal.portal.business.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;

public class WebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, DdpWebAuthenticationDetails> {

    @Override
    public DdpWebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new DdpWebAuthenticationDetails(request);
    }

}
