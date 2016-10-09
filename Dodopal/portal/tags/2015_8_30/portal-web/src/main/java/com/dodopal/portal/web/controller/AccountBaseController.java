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
import org.springframework.web.bind.annotation.RequestParam;
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
public class AccountBaseController extends CommonController{

	@Autowired
	private AccountSetService accountSetService;
	
	@RequestMapping("/showAccountSetInfo")
	public ModelAndView showAccountSetInfo(Model model,HttpServletRequest request){
		DodopalResponse<MerchantUserBean> response = accountSetService.findUserInfoById(super.getCurrentUserId(request.getSession()));
		String merCode = getCurrentMerchantCode(request.getSession());
		String flag = request.getParameter("flag");
		DodopalResponse<List<Area>> merCitys = accountSetService.findMerCitys(merCode);
		List<Area> list = merCitys.getResponseEntity();
		if("1".equals(flag)){
			String cityCode = request.getParameter("cityCode");
			for(Area area :list){
				if(cityCode.equals(area.getCityCode())){
					model.addAttribute("merCity",area.getCityName() );
				}
			}
			
		}else{
			model.addAttribute("merCity", merCitys.getResponseEntity().get(0).getCityName());
		}
		//商户类型
        String merType = getMerType(request.getSession());
		model.addAttribute("account", response.getResponseEntity());
		model.addAttribute("merType", merType);
		return new ModelAndView("ddp/accountBase");
	}
	
	//显示修改 页面
	@RequestMapping("/toUpdateAccountSetInfo")
    public @ResponseBody DodopalResponse<MerchantUserBean> toUpdateAccountSetInfo(HttpServletRequest request,@RequestBody String id) {
        DodopalResponse<MerchantUserBean> response = accountSetService.findUserInfoById(super.getCurrentUserId(request.getSession()));
        return response;
    }
	
	//查询业务城市
	@RequestMapping("/findMerCitys")
	public @ResponseBody DodopalResponse<List<Area>> findMerCitys(HttpServletRequest request){
		String merCode = getCurrentMerchantCode(request.getSession());
		DodopalResponse<List<Area>> merCitys = accountSetService.findMerCitys(merCode);
		return merCitys;
	}
	
	//修改业务城市
	@RequestMapping("/updateMerUserBusCity")
	public @ResponseBody DodopalResponse<Boolean> updateMerUserBusCity(HttpServletRequest request,@RequestParam String cityCode){
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
		String updateUser = getCurrentUserId(request.getSession());
		response = accountSetService.updateMerUserBusCity(cityCode, updateUser);
		response.setCode(ResponseCode.SUCCESS);
		return response;
	}
	
	//修改用户基本信息
	@RequestMapping("/updateAccountSetInfo")
	public @ResponseBody DodopalResponse<Boolean> updateAccountSetInfo(HttpServletRequest request,@RequestBody MerchantUserBean merchantUserBean){
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            if(merchantUserBean!= null){
            	merchantUserBean.setUpdateUser(super.getLoginUser(request.getSession()).getId());
            	response = accountSetService.updateAccountSetInfo(merchantUserBean);
                response.setCode(ResponseCode.SUCCESS);
                }else{
                    response.setCode(ResponseCode.SYSTEM_ERROR); 
                }
           }
           catch (Exception e) {
               e.printStackTrace();
               response.setCode(ResponseCode.UNKNOWN_ERROR);
           }
        
        return response;
	}
	
	
}
