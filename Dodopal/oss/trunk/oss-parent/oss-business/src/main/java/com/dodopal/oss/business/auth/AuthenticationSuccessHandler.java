package com.dodopal.oss.business.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import com.dodopal.oss.business.constant.OSSConstants;
import com.dodopal.oss.business.model.User;
import com.dodopal.oss.business.service.PermissionService;

@Component("authenticationSuccessHandler")
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private PermissionService permissionService;

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
		User user = details.getUser();
		//更新登录IP
		try {
			String loginIp = getIp(request);
			System.out.println("loginIP:" +loginIp);
			permissionService.updateUserLoginIPAndDate(user.getLoginName(), loginIp);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute(OSSConstants.SESSION_USER, user);
		session.setAttribute(OSSConstants.ALL_FUNCTIONS_IN_SESSION, permissionService.findAllPermissons(user));
	}

	public String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		
		System.out.println("X-Forwarded-For:" + request.getHeader("X-Forwarded-For"));
		
		System.out.println("X-Real-IP:" + request.getHeader("X-Real-IP"));
		
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
