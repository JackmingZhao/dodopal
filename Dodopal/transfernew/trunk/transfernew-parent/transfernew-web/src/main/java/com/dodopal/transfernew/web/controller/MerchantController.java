package com.dodopal.transfernew.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.enums.TransferProCtiyCodeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.transfernew.business.model.LogTransfer;
import com.dodopal.transfernew.business.service.BimchntinfotbService;
import com.dodopal.transfernew.business.service.LogTransferService;
import com.dodopal.transfernew.business.service.ProxyInfoTbService;
import com.dodopal.transfernew.business.service.TransferChilrenMerService;
import com.dodopal.transfernew.business.service.TransferGroupinfoService;

@Controller
public class MerchantController {
	private Logger logger = LoggerFactory.getLogger(MerchantController.class);
	@Autowired
	LogTransferService logTransferService;
	@Autowired
	BimchntinfotbService biminfotbService;
	
	@Autowired
	TransferGroupinfoService transferGroupinfoService;
	
	@Autowired
	TransferChilrenMerService transferChilrenMerService;
	
	@Autowired
	ProxyInfoTbService proxyInfoTbService;

	@RequestMapping("/merchantTest")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("merchant");
		return mav;
	}

	@RequestMapping("/findLogTransfer")
	public @ResponseBody
	DodopalResponse<List<LogTransfer>> findLogTransfer(HttpServletRequest request, @RequestBody LogTransfer logTransfer) {
		DodopalResponse<List<LogTransfer>> listRespons = new DodopalResponse<List<LogTransfer>>();
		if(StringUtils.isNotBlank(logTransfer.getBatchId())) {
			listRespons = logTransferService.findLogTransfer(logTransfer);
		}
		return listRespons;
	}

	/**
	 * 商户信息迁移
	 * 
	 * @param request
	 * @param citycode
	 * @return
	 */
	@RequestMapping("/transferMerchant")
	public @ResponseBody
	DodopalResponse<String> transferMerchant(HttpServletRequest request, @RequestBody String citycode) {
		logger.info("商户信息迁移开始，迁移城市编码：" + citycode);
		TransferProCtiyCodeEnum cityEnum = TransferProCtiyCodeEnum.getByCityCode(citycode);
		DodopalResponse<String> listRespons = biminfotbService.findBimchntinfotbs(cityEnum.getProCode(), citycode);
		logger.info("商户信息迁移结束，迁移城市编码：" + citycode);
		return listRespons;
	}

	/**
	 * 集团信息迁移
	 * 
	 * @param request
	 * @param citycode
	 * @return
	 */
	@RequestMapping("/transferGroup")
	public @ResponseBody
	DodopalResponse<String> transferGroup(HttpServletRequest request, @RequestBody String citycode) {
	    DodopalResponse<String> response = new DodopalResponse<String>();
		logger.info("集团信息迁移开始，迁移城市编码：" + citycode);
		response = transferGroupinfoService.transferGroupinfo(citycode);
		logger.info("集团信息迁移结束，迁移城市编码：" + citycode);
		return response;
	}

	/**
	 * 集团下网点迁移
	 * 
	 * @param request
	 * @param citycode
	 * @return
	 */
	@RequestMapping("/transferGroupProxy")
	public @ResponseBody
	DodopalResponse<String> transferGroupProxy(HttpServletRequest request, @RequestBody String citycode) {
	    DodopalResponse<String> response = new DodopalResponse<String>();
	    logger.info("集团下网点迁移开始，迁移城市编码：" + citycode);
	    response = transferChilrenMerService.transferChilrenMerService(citycode);
		logger.info("集团下网点迁移结束，迁移城市编码：" + citycode);
		return response;
	}

	/**
	 * 其他网点迁移
	 * 
	 * @param request
	 * @param citycode
	 * @return
	 */
	@RequestMapping("/transferOtherProxy")
	public @ResponseBody
	DodopalResponse<String> transferOtherProxy(HttpServletRequest request, @RequestBody String citycode) {
		DodopalResponse<String> response = new DodopalResponse<String>();
		logger.info("其他网点迁移开始，迁移城市编码：" + citycode);
		response = proxyInfoTbService.insertDataByProxyId(citycode);
		logger.info("其他网点迁移结束，迁移城市编码：" + citycode);
		return response;
	}
}
