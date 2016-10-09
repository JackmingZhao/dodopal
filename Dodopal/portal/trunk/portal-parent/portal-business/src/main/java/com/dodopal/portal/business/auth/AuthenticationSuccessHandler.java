package com.dodopal.portal.business.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.portal.business.constant.PortalConstants;

@Component("authenticationSuccessHandler")
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private RequestCache requestCache;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		requestCache.removeRequest(request, response);
		super.setAlwaysUseDefaultTargetUrl(false);
		super.onAuthenticationSuccess(request, response, authentication);
		super.setDefaultTargetUrl("/index");
		super.onAuthenticationSuccess(request, response, authentication);
		AppUserDetails details = (AppUserDetails) authentication.getPrincipal();

		HttpSession session = request.getSession();
		PortalUserDTO user = details.getUser();

		session.setAttribute(PortalConstants.SESSION_USER, user);
		
		List<MerFunctionInfoDTO> functionList = user.getMerFunInfoList();
		List<String> permissions = new ArrayList<String>();
		
		for(MerFunctionInfoDTO f : functionList){
			permissions.add(f.getMerFunCode());
		}
		session.setAttribute(PortalConstants.ALL_FUNCTIONS_IN_SESSION, permissions);
	}
}
