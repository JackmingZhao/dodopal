package com.dodopal.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.portal.business.constant.PortalConstants;

@Controller
@RequestMapping("/permissions")
public class PermissionController extends CommonController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findAllPermissions", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List<String> findAllPermissions(HttpServletRequest request	) {
		return (List<String>) request.getSession().getAttribute(PortalConstants.ALL_FUNCTIONS_IN_SESSION);
	}

}
