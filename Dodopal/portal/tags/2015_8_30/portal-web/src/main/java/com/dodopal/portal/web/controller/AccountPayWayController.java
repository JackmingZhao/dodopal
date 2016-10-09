package com.dodopal.portal.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PayWayKindEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.bean.PayWayBean;
import com.dodopal.portal.business.bean.PayWayCommonBean;
import com.dodopal.portal.business.service.AccountSetService;

@Controller
@RequestMapping("/ddp")
public class AccountPayWayController extends CommonController{
	@Autowired
	private AccountSetService accountSetService;
	
	@RequestMapping("/showAccountPayway")
	public ModelAndView showAccountSetInfo(Model model,HttpServletRequest request){
		return new ModelAndView("ddp/accountPayway");
	}
	
	@RequestMapping("/accountSetPayWay")
	public @ResponseBody DodopalResponse<List<PayWayBean>> accountSetPayWay(HttpServletRequest request){
		DodopalResponse<List<PayWayBean>> response = new DodopalResponse<List<PayWayBean>>();
		boolean ext = false;
		String userCode = "";
		//商户类型
        String merType = getMerType(request.getSession());
        //是否是外接商户(true 是| false 否)
        userCode = getCurrentUserCode(request.getSession());
        if(MerTypeEnum.EXTERNAL.getCode().equals(merType)){
        	ext=true;
        }else{
        	ext=false;
        }
        try {
            response = accountSetService.findCommonPayWay(ext, userCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
	}
	
	@RequestMapping("/accountMorePayWay")
	public @ResponseBody DodopalResponse<List<PayWayBean>> accountMorePayWay(HttpServletRequest request){
		DodopalResponse<List<PayWayBean>> response = new DodopalResponse<List<PayWayBean>>();
		boolean ext = false;
		String merCode = "";
		//商户类型
        String merType = getMerType(request.getSession());
        //是否是外接商户(true 是| false 否)
        merCode = getCurrentMerchantCode(request.getSession());
        if(MerTypeEnum.EXTERNAL.getCode().equals(merType)){
        	ext=true;
        }else{
        	ext=false;
        }
        try {
            response = accountSetService.findMorePayWay(ext, merCode);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
        }
        return response;
	}
	
	@RequestMapping("/accountSetSave")
	public @ResponseBody DodopalResponse<List<Integer>> insertPayWayCommon(HttpServletRequest request,@RequestBody String payWayIds){
		DodopalResponse<List<Integer>> response = new DodopalResponse<List<Integer>>();
		String userCode = getCurrentMerchantCode(request.getSession());
		String merType = getMerType(request.getSession());
		String id = getCurrentUserId(request.getSession());
		String userType = "";
		String payWayKind = "";
		try {
			if(MerTypeEnum.EXTERNAL.equals(merType)){
				userType = MerUserTypeEnum.MERCHANT.getCode();
				payWayKind = PayWayKindEnum.GW_OUT.getCode();
			}else{
				userType = MerUserTypeEnum.PERSONAL.getCode();
				payWayKind = PayWayKindEnum.GW_ALL.getCode();
			}
			List<PayWayCommonBean> list = new ArrayList<PayWayCommonBean>();
				String payIds[] = payWayIds.split(",");
				List<String> payWayIds1 = Arrays.asList(payIds);
				if(payWayIds1.size()>0){
					for(String payWayId : payWayIds1){
						PayWayCommonBean payWayCommonBean = new PayWayCommonBean();
						payWayCommonBean.setUserCode(userCode);
						payWayCommonBean.setUserType(userType);
						payWayCommonBean.setPayWayKind(payWayKind);
						payWayCommonBean.setPayWayId(payWayId);
						payWayCommonBean.setCreateUser(id);
						list.add(payWayCommonBean);
					}
					//清空常用方式
				accountSetService.deletePaywayCommon();
				response = accountSetService.insertPayWayCommon(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
            response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}
}
