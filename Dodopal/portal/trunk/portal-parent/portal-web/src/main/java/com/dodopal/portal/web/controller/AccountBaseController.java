package com.dodopal.portal.web.controller;

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
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantBean;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.service.AccountSetService;
import com.dodopal.portal.business.service.MerchantService;

@Controller
@RequestMapping("/ddp")
public class AccountBaseController extends CommonController{

	@Autowired
	private AccountSetService accountSetService;
	@Autowired
    MerchantService merchantService;
	
	@RequestMapping("/showAccountSetInfo")
	public ModelAndView showAccountSetInfo(Model model,HttpServletRequest request){
		DodopalResponse<MerchantUserBean> response = accountSetService.findUserInfoById(super.getCurrentUserId(request.getSession()));
		String merCode = "";
		MerUserTypeEnum custType = MerUserTypeEnum.MERCHANT;
		String merType  = getMerType(request.getSession());
		if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
			merCode = getCurrentUserCode(request.getSession());
			custType = MerUserTypeEnum.PERSONAL;
        }else {
            merCode = getCurrentMerchantCode(request.getSession());
            custType = MerUserTypeEnum.MERCHANT;
        }
		//获取修改url后的拼接字符串flag
		String flag = request.getParameter("flag");
		DodopalResponse<List<Area>> merCitys = accountSetService.findYktCitys(custType, merCode);
		List<Area> list = merCitys.getResponseEntity();
//		if(null != list && list.size() >= 0){
//			//判断是否跳转修改页面
//			if("1".equals(flag)){
//				String cityCode = request.getParameter("cityCode");
//				for(Area area :list){
//					if(cityCode.equals(area.getCityCode())){
//						model.addAttribute("merCity",area.getCityName() );
//					}
//				}
//				
//			}else{
//				if(merCitys.getResponseEntity()!=null && merCitys.getResponseEntity().size()>0){
//					System.out.println(merCitys.getResponseEntity().size());
//					model.addAttribute("merCity", merCitys.getResponseEntity().get(0).getCityName());
//				}
//			}
//		}else{
//			model.addAttribute("merCity", "");
//		}
		//账户类型
		String fundType = getFundType(request.getSession());
		//商户类型
		model.addAttribute("account", response.getResponseEntity());
		model.addAttribute("merType", merType);
		model.addAttribute("fundType", fundType);
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
		String merCode = "";
		MerUserTypeEnum custType = MerUserTypeEnum.MERCHANT;
		String merType  = getMerType(request.getSession());
		if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
			custType = MerUserTypeEnum.PERSONAL;
			merCode = getCurrentUserCode(request.getSession());
        }else {
            merCode = getCurrentMerchantCode(request.getSession());
            custType = MerUserTypeEnum.MERCHANT;
        }
		DodopalResponse<List<Area>> merCitys = accountSetService.findYktCitys(custType, merCode);
		//DodopalResponse<List<Area>> merCitys = accountSetService.findMerCitys(merCode);
		return merCitys;
	}
	
	//修改业务城市
	@RequestMapping("/updateMerUserBusCity")
	public @ResponseBody DodopalResponse<Boolean> updateMerUserBusCity(HttpServletRequest request,@RequestBody Area area){
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
		String updateUser = getCurrentUserId(request.getSession());
		response = accountSetService.updateMerUserBusCity(area.getCityCode(), updateUser);
		//String cityName = request.getParameter("cityName");
		setCurrentCityName(request.getSession(), area.getCityName());
		response.setCode(ResponseCode.SUCCESS);
		return response;
	}
	
	//修改用户基本信息
	@RequestMapping("/updateAccountSetInfo")
	public @ResponseBody DodopalResponse<Boolean> updateAccountSetInfo(HttpServletRequest request,@RequestBody MerchantUserBean merchantUserBean){
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
        try {
            if(merchantUserBean!= null){
            	String merCode = getCurrentMerchantCode(request.getSession());
            	if(merCode!=null){
            		DodopalResponse<MerchantBean> merchantBeans = merchantService.findMerchantBeans(merCode);
            		String merUserFlag = merchantBeans.getResponseEntity().getMerchantUserBean().getMerUserFlag();
            		merchantUserBean.setUpdateUser(super.getLoginUser(request.getSession()).getId());
            		if(MerUserFlgEnum.ADMIN.getCode().equals(merUserFlag)){
            			MerchantBean bean = new MerchantBean();
            			bean.setMerCode(getCurrentMerchantCode(request.getSession()));
            			bean.setUpdateUser(super.getLoginUser(request.getSession()).getId());
            			bean.setMerLinkUser(merchantUserBean.getMerUserNickName());
            			bean.setMerEmail(merchantUserBean.getMerUserEmail());
            			bean.setMerchantUserBean(merchantUserBean);
            			DodopalResponse<String> updateMerchantForPortal = merchantService.updateMerchantForPortal(bean);
            		}
            	}
            	response = accountSetService.updateAccountSetInfo(merchantUserBean);
            	setCurrentMerUserNickName(request.getSession(), merchantUserBean.getMerUserNickName());
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
