package com.dodopal.portal.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.portal.business.bean.MerBusRateBean;
import com.dodopal.portal.business.service.MerchantService;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Controller
@RequestMapping("/application")
public class ApplicationCenter extends CommonController{
	    private final static Logger log = LoggerFactory.getLogger(ApplicationCenter.class);
	    @Autowired
	    MerchantService merService;	    
	    
	    
	    @RequestMapping("/toApplicationPage")
	    public String toCardRecharge(HttpServletRequest request,RedirectAttributes attr,@RequestParam String businesscode) {
	    	if(log.isWarnEnabled()){
	    		 log.warn("进入产品库应用跳转,获取的businesscode:{}",businesscode);
	    	}
	        PortalUserDTO dto = super.getLoginUser(request.getSession());
	        Map map = new HashMap();
	        try{
	       
		        String trandate = DateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR );
		        String merCode = "";
		        if(StringUtils.isNotBlank(dto.getMerCode())){
		        	merCode = dto.getMerCode();
		        }
		        map.put("mercode", merCode);
		        map.put("usercode", dto. getUserCode());
		        map.put("businesscode",businesscode);
		        map.put("trandate",trandate);
		        map.put("soruce",SourceEnum.PORTAL.getCode());
		        JSONObject jsonObject = JSONObject.fromObject(map);
		    
		        attr.addAttribute("mercode", merCode);  
		        attr.addAttribute("usercode", dto. getUserCode());  
		        if(null==RateCodeEnum.getRateTypeByCode(businesscode)){
		        	return "redirect:toApplication";
		        }
		        
		        if(MerTypeEnum.PERSONAL.getCode().equals(dto.getMerType())){
		        	//个人
		        	//个人无一卡通消费
		        	if(RateCodeEnum.YKT_PAYMENT.getCode().equals(businesscode)){
		        		return "redirect:toApplication";
		        	}
				        createResponseMap(attr, businesscode, map, trandate);  
				        if(log.isWarnEnabled()){
				        	 log.warn("进入产品库应用跳转,传送的mercode:{};usercode:{};businesscode:{};sign:{};",
						        		merCode,dto. getUserCode(),RateCodeEnum.getRateTypeByCode(businesscode).getCode(),SignUtils.sign(SignUtils.createLinkString(map), "123", CharEncoding.UTF_8));
				        }
				       	String proUrl = DodopalAppVarPropsUtil.getStringProp(DelegateConstant.PRODUCT_URL);
				       	if(log.isInfoEnabled()){
				       		log.info("进入产品库应用跳转链接：{}",proUrl+"/cardRecharge/toCardRecharge");
				       	}
				        return "redirect:"+proUrl+"/cardRecharge/toCardRecharge";
			       
		        }else{
		        	//商户开通的业务检查
		        	DodopalResponse<List<MerBusRateBean>> rateResp = merService.findMerBusRateByMerCode(dto.getMerCode());
	        		if(ResponseCode.SUCCESS.equals(rateResp.getCode())){
	        			if(CollectionUtils.isNotEmpty(rateResp.getResponseEntity())){
	        				//查找费率，并匹配启用的费率
	        				for(MerBusRateBean merRate:rateResp.getResponseEntity()){
	        					if(ActivateEnum.ENABLE.getCode().equals(merRate.getActivate())){
	        						if(businesscode.equals(merRate.getRateCode())){
	        								createResponseMap(attr,
													businesscode, map, trandate);  
	        						        if(log.isWarnEnabled()){
	        						        	 log.warn("进入产品库应用跳转,传送的mercode:{};usercode:{};businesscode:{};sign:{};",
	        								        		merCode,dto. getUserCode(),RateCodeEnum.getRateTypeByCode(businesscode).getCode(),SignUtils.sign(SignUtils.createLinkString(map), "123", CharEncoding.UTF_8));

	        						        }
	        						       	String proUrl = DodopalAppVarPropsUtil.getStringProp(DelegateConstant.PRODUCT_URL);
	        						       	if(log.isInfoEnabled()){
	        						       		log.info("进入产品库应用跳转链接：{}",proUrl+"/cardRecharge/toCardRecharge");
	        						       	}
	        						       	
	        						        return "redirect:"+proUrl+"/cardRecharge/toCardRecharge";
	        						}
	        					}
	        				}
	        			}
	        		}
		        }
		        //返回应用中心页面
        		return "redirect:toApplication";
	        }catch(Exception e){
	        	log.error("ApplicationCenter toCardRecharge call an error",e);
	        	return "redirect:toApplication";
	        }
	    }
		private void createResponseMap(RedirectAttributes attr,
				String businesscode, Map map, String trandate) {
			attr.addAttribute("businesscode",RateCodeEnum.getRateTypeByCode(businesscode).getCode());
			attr.addAttribute("trandate",trandate);
			attr.addAttribute("soruce",SourceEnum.PORTAL.getCode());  
			attr.addAttribute("input_charset", CharEncoding.UTF_8);  
			attr.addAttribute("sign_type", "MD5");  
			attr.addAttribute("sign", SignUtils.sign(SignUtils.createLinkString(map), "123", CharEncoding.UTF_8));
		}
	    @RequestMapping("/toApplication")
		public ModelAndView show(Model model,HttpServletRequest request){
			return new ModelAndView("applicationCenter/applicationCenter");
		}
	    
		 //查询用户绑定的卡片
	    @RequestMapping("checkAppStatus")
	    public @ResponseBody DodopalResponse<Boolean> checkAppStatus(HttpServletRequest request,@RequestBody String businesscode) {
	        DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
	        PortalUserDTO dto = super.getLoginUser(request.getSession());
	        try{
		        if(MerTypeEnum.PERSONAL.getCode().equals(dto.getMerType())){
		        	//个人
		        	//个人无一卡通消费
		        	if(RateCodeEnum.YKT_PAYMENT.getCode().equals(businesscode)){
		        		response.setCode(ResponseCode.PORTAL_APP_NOT_OPPEN_ERR);
					    response.setResponseEntity(false);
					    return response;
		        	}
		        	 if(log.isWarnEnabled()){
			        	 log.warn("应用已经查到，返回页面成功，等待页面进行跳转");
			        }
		        	 response.setCode(ResponseCode.SUCCESS);
				     response.setResponseEntity(true);
				     return response;
		        }else{
			        DodopalResponse<List<MerBusRateBean>> rateResp = merService.findMerBusRateByMerCode(dto.getMerCode());
		    		if(ResponseCode.SUCCESS.equals(rateResp.getCode())){
		    			if(CollectionUtils.isNotEmpty(rateResp.getResponseEntity())){
		    				//查找费率，并匹配启用的费率
		    				for(MerBusRateBean merRate:rateResp.getResponseEntity()){
		    					if(ActivateEnum.ENABLE.getCode().equals(merRate.getActivate())){
		    						if(businesscode.equals(merRate.getRateCode())){
		    						        if(log.isWarnEnabled()){
		    						        	 log.warn("应用已经查到，返回页面成功，等待页面进行跳转");
		    						        }
		    						        response.setCode(ResponseCode.SUCCESS);
		    						        response.setResponseEntity(true);
		    						        return response;
		    						}
		    					}
		    				}
		    			}
		    		}
		        }
	    		response.setCode(ResponseCode.PORTAL_APP_NOT_OPPEN_ERR);
			    response.setResponseEntity(false);
	        }catch(Exception e){
	        	log.error("ApplicationCenter checkAppStatus call an error",e);
	        	response.setCode(ResponseCode.SYSTEM_ERROR);
			    response.setResponseEntity(false);
	        }
	        return response;
	    }
}
