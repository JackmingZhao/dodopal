package com.dodopal.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerGroupDepartmentBean;
import com.dodopal.portal.business.bean.MerGroupUserBean;
import com.dodopal.portal.business.bean.MerGroupUserQueryBean;
import com.dodopal.portal.business.service.MerGroupUserService;

@Controller
@RequestMapping("/merchantGroupUser")
public class MerGroupUserController extends CommonController{

	@Autowired
	private MerGroupUserService service;

	
	@RequestMapping(value = "/findMerchnatGroupUsers", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DodopalResponse<DodopalDataPage<MerGroupUserBean>> findMerchnatGroupUser(HttpServletRequest request,@RequestBody MerGroupUserQueryBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		return service.findMerGpUsersByPage(bean, SourceEnum.PORTAL);
	}
	
	@RequestMapping("/merchantGroupUserMgmt")
	public ModelAndView verifiedMgmt(HttpServletRequest request) {
		return new ModelAndView("merchant/merchantGroupUserMgr/merGroupUser");
	}
	
	@RequestMapping("/findMerchnatGroupUserById")
	public @ResponseBody DodopalResponse<MerGroupUserBean> findMerchnatGroupUserById(Model model, HttpServletRequest request, @RequestBody String id) {
		return service.findMerGroupUserById(id);
	}

	@RequestMapping("/deleteMerchnatGroupUserById")
	public @ResponseBody DodopalResponse<String> deleteMerchnatGroupUserById(Model model, HttpServletRequest request, @RequestBody String id) {
			return service.deleteMerGroupUser(id);
	}

	@RequestMapping("/saveMerchnatGroupUser")
	public @ResponseBody DodopalResponse<String> saveMerchnatGroupUser(HttpServletRequest request, @RequestBody MerGroupUserBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		
		if(StringUtils.isBlank(bean.getId())){
			bean.setCreateUser(super.getCurrentUserId(request.getSession()));
			return service.saveMerGroupUserDTOList(bean);
		}else{
		    bean.setUpdateUser(super.getCurrentUserId(request.getSession()));
			return service.updateMerGroupUser(bean);
		}
	}
	
	@RequestMapping("/checkMerGroupUserCardExist")
	public @ResponseBody DodopalResponse<String> checkMerGroupUserCardExist(Model model, HttpServletRequest request, @RequestBody MerGroupUserBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		return service.checkCardCode(bean.getMerCode(), bean.getCardCode(), bean.getId());
	} 
	
	@RequestMapping(value = "/importGroupUser")
	public @ResponseBody DodopalResponse<String> importGroupUser(@RequestParam("groupUserFile") CommonsMultipartFile file, HttpServletRequest request) {
		DodopalResponse<String> response = null;
		try {
			return service.importMerGpUsers(file,super.getCurrentMerchantCode(request.getSession()));
		}
		catch (DDPException ddp) {
			ddp.printStackTrace();
			response = new DodopalResponse<String>();
			response.setCode(ResponseCode.SYSTEM_ERROR);
			response.setMessage(ddp.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			response = new DodopalResponse<String>();
			response.setCode(ResponseCode.UNKNOWN_ERROR);
		}
		return response;
	}
}
