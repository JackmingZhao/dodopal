package com.dodopal.portal.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerFunctionInfoBean;
import com.dodopal.portal.business.bean.MerRoleBean;
import com.dodopal.portal.business.bean.MerRoleQueryBean;
import com.dodopal.portal.business.bean.NodeState;
import com.dodopal.portal.business.bean.TreeNode;
import com.dodopal.portal.business.service.MerRoleService;

@Controller
@RequestMapping("/merchantRole")
public class MerchantRoleController extends CommonController {

	@Autowired
	private MerRoleService service;

	@RequestMapping(value = "/findMerchnatRoles", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DodopalResponse<DodopalDataPage<MerRoleBean>> findMerchnatRoles(HttpServletRequest request, @RequestBody MerRoleQueryBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		bean.setLoginUserId(super.getCurrentUserId(request.getSession()));
		return service.findMerRoleByPage(bean);
	}

	@RequestMapping("/merchantRoleMgmt")
	public ModelAndView verifiedMgmt(HttpServletRequest request) {
		return new ModelAndView("merchant/merchantRoleMgr/merRole");
	}

	@RequestMapping("/findMerchnatRoleByCode")
	public @ResponseBody DodopalResponse<MerRoleBean> findMerchnatRoleById(Model model, HttpServletRequest request, @RequestBody String id) {
		DodopalResponse<MerRoleBean> rep =  service.findMerRoleByMerRoleCode(super.getCurrentMerchantCode(request.getSession()),id );;
		
		Map<String,MerFunctionInfoBean> assignedFunMap = new HashMap<String,MerFunctionInfoBean>();
		if(rep.getResponseEntity()!= null && CollectionUtils.isNotEmpty(rep.getResponseEntity().getMerRoleFunDTOList())){
			List<MerFunctionInfoBean> assignedFuns = rep.getResponseEntity().getMerRoleFunDTOList();
			for(MerFunctionInfoBean f : assignedFuns){
				assignedFunMap.put(f.getMerFunCode(), f);
			}
		}
		
		String userCode = getCurrentUserCode(request.getSession());
		DodopalResponse<List<MerFunctionInfoBean>> allFuns = service.findMerFuncInfoByUserCode(userCode);
		if(ResponseCode.SUCCESS.equals(rep.getCode())){
			List<TreeNode> treeNodes = new ArrayList<TreeNode>();
			for(MerFunctionInfoBean f : allFuns.getResponseEntity()){
				TreeNode t = new TreeNode();
				t.setId(f.getMerFunCode());
				
				if(StringUtils.isEmpty(f.getParentCode()))
					t.setParent("#");
				else
					t.setParent(f.getParentCode());
				
				t.setText(f.getMerFunName());
				NodeState ns = new NodeState();
				
				if(assignedFunMap.keySet().contains(f.getMerFunCode())){
					ns.setChecked(true);
					ns.setSelected(true);
				}else{
					ns.setChecked(false);
					ns.setSelected(false);
				}
				
				ns.setOpened(true);
				t.setState(ns);
				treeNodes.add(t);
			}
			rep.getResponseEntity().setMerRoleFunTreeList(treeNodes);
		}else{
		}
		
		return rep;
	}

	@RequestMapping("/findMerchnatRoleFunctionByCode")
	public @ResponseBody DodopalResponse<List<TreeNode>> findMerchnatRoleFunctionByCode(Model model, HttpServletRequest request) {
		
		String userCode = getCurrentUserCode(request.getSession());
		
		DodopalResponse<List<MerFunctionInfoBean>> functions = service.findMerFuncInfoByUserCode(userCode);
		DodopalResponse<List<TreeNode>> rep = new DodopalResponse<List<TreeNode>>();
		rep.setCode(functions.getCode());
		
		if(ResponseCode.SUCCESS.equals(functions.getCode())){
			List<TreeNode> treeNodes = new ArrayList<TreeNode>();
			for(MerFunctionInfoBean f : functions.getResponseEntity()){
				TreeNode t = new TreeNode();
				t.setId(f.getMerFunCode());
				
				if(StringUtils.isEmpty(f.getParentCode()))
					t.setParent("#");
				else
					t.setParent(f.getParentCode());
				
				t.setText(f.getMerFunName());
				NodeState ns = new NodeState();
				ns.setOpened(true);
				t.setState(ns);
				treeNodes.add(t);
			}
			rep.setResponseEntity(treeNodes);
		}else{
			rep.setMessage(functions.getMessage());
		}
		
		return rep;
	}

	@RequestMapping("/deleteMerchnatRoleByIds")
	public @ResponseBody DodopalResponse<String> deleteMerchnatGroupUserById(Model model, HttpServletRequest request, @RequestBody List<String> ids) {
		return service.batchDelMerRoleByCodes(super.getCurrentMerchantCode(request.getSession()), ids);
	}

	@RequestMapping("/saveMerchnatRole")
	public @ResponseBody DodopalResponse<String> saveMerchnatGroupUser(HttpServletRequest request, @RequestBody MerRoleBean bean) {
		bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));

		if (StringUtils.isBlank(bean.getId())) {
		    bean.setCreateUser(getCurrentUserId(request.getSession()));
			return service.addMerRole(bean);
		} else {
		    bean.setUpdateUser(getCurrentUserId(request.getSession()));
			return service.updateMerRole(bean);
		}
	}

	@RequestMapping("/checkMerRoleNameExist")
	public @ResponseBody DodopalResponse<Boolean> checkMerRoleNameExist(Model model, HttpServletRequest request, @RequestBody MerRoleBean bean) {
	    bean.setMerCode(super.getCurrentMerchantCode(request.getSession()));
		return service.checkMerRoleNameExist(bean);
	}

}
