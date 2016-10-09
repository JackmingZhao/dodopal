package com.dodopal.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.MerchantUserQueryBean;
import com.dodopal.portal.business.service.MerUserService;
import com.dodopal.portal.business.service.MerchantUserService;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月29日 下午3:05:16
 */

@Controller
@RequestMapping("/merchantUser")
public class MerchantUserController extends CommonController {

	private final static Logger log = LoggerFactory.getLogger(MerchantUserController.class);

	@Autowired
	MerchantUserService merUserService;

	@Autowired
	MerUserService service;
	
	@RequestMapping(value = "/findMerchnatUsers", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DodopalResponse<DodopalDataPage<MerchantUserBean>> findMerchnatUser(HttpServletRequest request,@RequestBody MerchantUserQueryBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		bean.setLoginUserId(super.getCurrentUserId(request.getSession()));
		log.info("Inside findMerchnatUsers bean " + bean);
		return service.findMerOperatorByPage(bean);
	}

	@RequestMapping("/merchantUserMgmt")
	public ModelAndView merchantUserMgmt(HttpServletRequest request) {
		return new ModelAndView("merchant/merchantUser/merchantUser");
	}
	
	@RequestMapping("/saveMerchantUser")
	public @ResponseBody DodopalResponse<String> saveMerchnatUser(HttpServletRequest request, @RequestBody MerchantUserBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		bean.setUpdateUser(super.getCurrentUserId(request.getSession()));
		
		if(StringUtils.isBlank(bean.getId())){
			bean.setCreateUser(super.getCurrentUserId(request.getSession()));
			return service.addMerOperator(bean);
		}else{
			return service.updateMerOperator(bean);
		}
	}
	
	@RequestMapping("/batchStartMerOperator")
	public @ResponseBody DodopalResponse<String> startMerchnatUser(HttpServletRequest request, @RequestBody List<String> userCodes) {
		return service.batchActivateMerOperator(super.getCurrentMerchantCode(request.getSession()), super.getCurrentUserId(request.getSession()), ActivateEnum.ENABLE, userCodes);
	}
	
	@RequestMapping("/batchStopMerOperator")
	public @ResponseBody DodopalResponse<String> stopMerchnatUser(HttpServletRequest request, @RequestBody List<String> userCodes) {
		return service.batchActivateMerOperator(super.getCurrentMerchantCode(request.getSession()), super.getCurrentUserId(request.getSession()), ActivateEnum.DISABLE, userCodes);
	}
	
	@RequestMapping("/findMerchnatUserByCode")
	public @ResponseBody DodopalResponse<MerchantUserBean> findMerchnatUserByCode(Model model, HttpServletRequest request, @RequestBody String id) {
 		return service.findMerOperatorByUserCode(super.getCurrentMerchantCode(request.getSession()), id);
	}
	
	@RequestMapping("/resetMerchnatUserPwd")
	public @ResponseBody DodopalResponse<String> resetMerchnatUserPwd(Model model, HttpServletRequest request, @RequestBody MerchantUserBean bean) {
		return service.resetOperatorPwd(super.getCurrentMerchantCode(request.getSession()), bean.getId(), bean.getMerUserPWD(), super.getCurrentUserId(request.getSession()));
	}
	
	@RequestMapping("/configMerOperatorRole")
	public @ResponseBody DodopalResponse<String> configMerOperatorRole(Model model, HttpServletRequest request, @RequestBody MerchantUserBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		bean.setUpdateUser(super.getCurrentUserId(request.getSession()));
		return service.configMerOperatorRole(bean);
	}
}
