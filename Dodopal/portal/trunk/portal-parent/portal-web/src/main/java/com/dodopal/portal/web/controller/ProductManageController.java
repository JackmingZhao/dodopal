package com.dodopal.portal.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.api.product.dto.query.PrdProductYktQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.PrdProductYktBean;
import com.dodopal.portal.business.model.query.PrdProductYktQuery;
import com.dodopal.portal.business.service.AccountSetService;
import com.dodopal.portal.business.service.ProductManageService;

@Controller
@RequestMapping("/product")
public class ProductManageController extends CommonController{

	private final static Logger log = LoggerFactory.getLogger(ProductManageController.class);
	
	@Autowired
	private ProductManageService proManageService;
	@Autowired
	private AccountSetService accountSetService;
	
	@RequestMapping("/toProductMge")
	public ModelAndView show(Model model,HttpServletRequest request){
		return new ModelAndView("applicationCenter/productManage");
	}
	
	@RequestMapping("/findProductMage")
	public @ResponseBody DodopalResponse<DodopalDataPage<PrdProductYktBean>> findAvailableIcdcProductsInCity(HttpServletRequest request,@RequestBody PrdProductYktQuery query){
		DodopalResponse<DodopalDataPage<PrdProductYktBean>> response = new DodopalResponse<DodopalDataPage<PrdProductYktBean>>();
		String merType = getMerType(request.getSession());
        String userCode = "";
        String userType = "";
        try {
        	if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
        		userType = MerUserTypeEnum.PERSONAL.getCode();
        	}else {
        		userType = MerUserTypeEnum.MERCHANT.getCode();
        		userCode = getCurrentMerchantCode(request.getSession());
        	}
        	query.setCityId(query.getCityId());
        	query.setMerCode(userCode);
        	response = proManageService.findAvailableIcdcProductsByPage(query, userType);
		} catch (Exception e) {
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}
	
	//导出
    @RequestMapping("/productMgeExport")
    public @ResponseBody DodopalResponse<String>  exportProductManageOrder(HttpServletRequest request,HttpServletResponse response){
    	 DodopalResponse<String> rep = new DodopalResponse<String>();
    	 String userCode = "";
    	 String userType = "";
    	 String cityId = request.getParameter("cityName");
    	 try {
    		 PrdProductYktQueryDTO queryDTO = new PrdProductYktQueryDTO();
    		 String merType = getMerType(request.getSession());
    	        if(MerTypeEnum.PERSONAL.getCode().equals(merType)){
    	            userType = MerUserTypeEnum.PERSONAL.getCode();
    	            userCode = getCurrentUserCode(request.getSession());
    	        }else {
    	            userType = MerUserTypeEnum.MERCHANT.getCode();
    	            userCode = getCurrentMerchantCode(request.getSession());
    	            if(userCode != null && userCode !=  ""){
    	            	queryDTO.setMerCode(userCode);
    	            }
    	        }
    	       //城市Id
        		if(cityId != null && cityId !=  ""){
        			queryDTO.setCityId(cityId);
        		}
    		 rep = proManageService.findAvailableIcdcProductsInCity(response, queryDTO,userType);
		} catch (Exception e) {
			rep.setCode(ResponseCode.SYSTEM_ERROR);
		}
    	return rep;
    } 
    
  //查询业务城市
  	@RequestMapping("/findMerCitys")
  	public @ResponseBody DodopalResponse<List<Area>> findMerCitys(HttpServletRequest request){
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
  		DodopalResponse<List<Area>> merCitys = accountSetService.findYktCitys(custType, merCode);
  		return merCitys;
  	}
}
