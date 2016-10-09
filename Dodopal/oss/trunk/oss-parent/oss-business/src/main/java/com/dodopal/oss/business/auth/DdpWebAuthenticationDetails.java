package com.dodopal.oss.business.auth;


import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class DdpWebAuthenticationDetails extends WebAuthenticationDetails {
	private static final long serialVersionUID = 3044725205543389226L;

    /**
     * Records the remote address and will also set the session Id if a session
     * already exists (it won't create one).
     * @param request that the authentication request was received from
     */
	public DdpWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
    }
    
}
