package com.dodopal.transfer.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfer.business.service.TransferGroupinfoService;

/**
 * @author lifeng@dodopal.com
 */
@Controller
public class TransferGroupinfoController extends CommonController {
	@Autowired
	private TransferGroupinfoService transferGroupinfoService;

	@RequestMapping("/findAllGroupinfo")
	public @ResponseBody
	DodopalResponse<String> findAllGroupinfo(HttpServletRequest request) {
		return transferGroupinfoService.transferGroupinfo();
	}

}
