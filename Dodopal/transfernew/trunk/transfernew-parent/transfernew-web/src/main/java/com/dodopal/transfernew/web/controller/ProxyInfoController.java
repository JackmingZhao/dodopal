package com.dodopal.transfernew.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfernew.business.service.BimchntinfotbService;
import com.dodopal.transfernew.business.service.LogTransferService;
import com.dodopal.transfernew.business.service.MerchantService;
import com.dodopal.transfernew.business.service.MerchantUserService;
import com.dodopal.transfernew.business.service.ProxyInfoTbService;

/**
 * @author Mika
 * @deprecated 个人网点迁移
 */
@Controller
public class ProxyInfoController {
//	private Logger logger = LoggerFactory.getLogger(ProxyInfoController.class);
//	@Autowired
//	BimchntinfotbService bimchntinfotbService;
//	@Autowired
//	LogTransferService logTransferService;
//	@Autowired
//	MerchantService merchantService;
//	@Autowired
//	MerchantUserService merchantUserService;
//	@Autowired
//	ProxyInfoTbService proxyInfoTbService;

//	@RequestMapping("/proxyinfo")
//	public ModelAndView index() {
//		ModelAndView mv = new ModelAndView();
//		mv.setViewName("proxyinfo");
//		return mv;
//	}

//	@RequestMapping("/persProxyInfo")
//	public @ResponseBody DodopalResponse<String> findGRWD(HttpServletRequest request, @RequestBody String citycode) {
//	    return proxyInfoTbService.insertDataByProxyId(citycode); 	// citycode = "100000-1110"
//	}
//	public static void main(String[] args) {
//	    System.out.println("测试一下");
//	    proxyInfoTbService.insertDataByProxyId();
//	}
	
}
