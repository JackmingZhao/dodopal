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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerGroupDepartmentBean;
import com.dodopal.portal.business.bean.MerGroupDepartmentQueryBean;
import com.dodopal.portal.business.constant.PortalConstants;
import com.dodopal.portal.business.service.MerGroupDepartmentService;

@Controller
@RequestMapping("/merchantGroupDep")
public class MerGroupDepartmentController extends CommonController {

	@Autowired
	private MerGroupDepartmentService service;

	@RequestMapping(value = "/findMerchnatDeps", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DodopalResponse<DodopalDataPage<MerGroupDepartmentBean>> findMerchnatGroupDep(HttpServletRequest request,@RequestBody MerGroupDepartmentQueryBean queryBean) {
		queryBean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		return service.findMerGroupDepartmentDTOListByPage(queryBean, PortalConstants.FIND_WEB);
	}
	
	@RequestMapping(value = "/findAllMerchnatDeps", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DodopalResponse<List<MerGroupDepartmentBean>> findAllMerchnatGroupDep(HttpServletRequest request) {
		MerGroupDepartmentBean queryBean = new MerGroupDepartmentBean();
		queryBean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		return service.findMerGroupDepartmentDTOList(queryBean);
	}

	@RequestMapping("/merchantDepMgmt")
	public ModelAndView merchantDepMgmt(HttpServletRequest request) {
		return new ModelAndView("merchant/merchantGroupDepMgr/merGroupDepartment");
	}

	@RequestMapping("/findMerchnatDepById")
	public @ResponseBody DodopalResponse<MerGroupDepartmentBean> viewMerGroupDepartment(Model model, HttpServletRequest request, @RequestBody String id) {
		return service.findMerGroupDepartmentById(id);
	}

	@RequestMapping("/deleteMerchnatDepById")
	public @ResponseBody DodopalResponse<String> deleteMerGroupDepartment(Model model, HttpServletRequest request, @RequestBody List<String> ids) {
		return service.deleteMerGroupDepartment(ids);
	}

	@RequestMapping("/saveMerchnatGroupDep")
	public @ResponseBody DodopalResponse<String> saveMerchnatGroupDep(HttpServletRequest request, @RequestBody MerGroupDepartmentBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));

		if(StringUtils.isBlank(bean.getId())){
			bean.setCreateUser(getCurrentUserId(request.getSession()));
			return service.saveMerGroupDepartmentDTOList(bean);
		}else{
			bean.setUpdateUser(getCurrentUserId(request.getSession()));
			return service.updateMerGroupDepartment(bean);
		}
		
	}
	
	@RequestMapping("/checkMerGroupDepNameExist")
	public @ResponseBody DodopalResponse<Boolean> checkMerGroupDepNameExist(Model model, HttpServletRequest request, @RequestBody MerGroupDepartmentBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		return service.checkMerGroupDepartmentDTO(bean);
	}

}
