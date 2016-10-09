package com.dodopal.product.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.service.DdicService;
import com.dodopal.product.business.bean.AccountFundBean;
import com.dodopal.product.business.bean.CardRechargeBean;
import com.dodopal.product.business.bean.MerBusRateBean;
import com.dodopal.product.business.bean.MerDiscountBean;
import com.dodopal.product.business.bean.MerchantBean;
import com.dodopal.product.business.bean.MerchantUserBean;
import com.dodopal.product.business.constant.ProductConstants;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.service.AccountQueryService;
import com.dodopal.product.business.service.CityFindService;
import com.dodopal.product.business.service.MerDiscountService;
import com.dodopal.product.business.service.MerUserCardBDService;
import com.dodopal.product.business.service.MerchantService;
import com.dodopal.product.business.service.MerchantUserService;
import com.dodopal.product.business.service.PayService;
import com.dodopal.product.business.service.PrdProductYktService;
import com.dodopal.product.business.service.ProductOrderService;
import com.dodopal.product.business.service.ProductYKTService;

@Controller
@RequestMapping("/cardConsume")
public class CardConsumeController extends CommonController{
	private final static Logger log = LoggerFactory.getLogger(CardConsumeController.class);
    @Autowired
    PrdProductYktService prdProductYktService;
    @Autowired
    AreaService areaService;
    @Autowired
    MerchantUserService merUserService;
    @Autowired
    MerchantService merService;
    @Autowired 
    AccountQueryService accountQueryService;
    @Autowired
    PayService payService;
    @Autowired
    ProductOrderService  productOrderService;
    @Autowired
    MerUserCardBDService bdService;
    @Autowired
    DdicService ddicService;
    @Autowired
    ProductYKTService productYKTService;
    @Autowired
    CityFindService cityFindService;
    @Autowired
    MerDiscountService merDiscountService;	
    
	@RequestMapping("/getCardConsumeInfo")
    public @ResponseBody DodopalResponse<CardRechargeBean> getCardConsumeInfo(HttpServletRequest request,@RequestBody String cityCode) {
		DodopalResponse<CardRechargeBean> dodopalResponse = new DodopalResponse<CardRechargeBean>();
    	try{
    		CardRechargeBean cardRechargeBean = new CardRechargeBean();
    		if(StringUtils.isBlank((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE))){
    			dodopalResponse.setCode(ResponseCode.LOGIN_TIME_OUT);
    			return dodopalResponse;
    		}
    		DodopalResponse<MerchantUserBean> userResponse = merUserService.findUserInfoByUserCode((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE));
    		if(!ResponseCode.SUCCESS.equals(userResponse.getCode())&&null==userResponse.getResponseEntity()){
    			//用户未找到
    		    log.error("消费页面获取用户信息未找到："+dodopalResponse.getCode());
    			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
    			throw new DDPException(dodopalResponse.getCode());
    		}
    		MerchantUserBean userBean = userResponse.getResponseEntity();
            log.info("当前登录产品库用户为[{}],用户号/商户号[{}],用户名[{}]",userBean.getMerchantName(),StringUtils.isNotBlank(userBean.getUserCode())?userBean.getUserCode():userBean.getMerCode(),userBean.getMerUserName());
	       // MerchantUserBean userBean= merUserService.findUserInfoByMobileOrUserName((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERNAME)).getResponseEntity();
    		cardRechargeBean.setMerName(userBean.getMerchantName());
    		//用户或商户未设置城市
    		if(StringUtils.isNotBlank((String) request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_CITY))){
    			Area chooseCity = areaService.findCityByCityCode((String) request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_CITY));
    			//根据session里用户选择的城市code填充
    			cardRechargeBean.setCityName(chooseCity.getCityName());
    			cardRechargeBean.setCityCode(chooseCity.getCityCode());
    		}else if(StringUtils.isBlank(userBean.getCityCode())){
    			cardRechargeBean.setCityCode(null);
   		     	cardRechargeBean.setCityName(null);
    		}else{
    			Area area = areaService.findCityByCityCode(userBean.getCityCode());
    			cardRechargeBean.setCityCode(area.getCityCode());
   		     	cardRechargeBean.setCityName(area.getCityName());
    		}
	        cardRechargeBean.setMerCode(userBean.getMerCode());
	        cardRechargeBean.setUserCode(userBean.getUserCode());
	        cardRechargeBean.setMerUserType(userBean.getMerUserType());
	        cardRechargeBean.setUserId(userBean.getId());
	       
//	        List<Integer> prdProductYktList = new ArrayList<Integer>();
//	        DodopalResponse<List<PrdProductYkt>> yktResponse = new DodopalResponse<List<PrdProductYkt>>();
//	        List<PrdProductYkt> proRespone = null;
	        if(MerUserTypeEnum.PERSONAL.getCode().equals(userBean.getMerUserType())){
	        	//个人用户
//	        	Map <String,List<Area>> areaList = areaService.createMapForFirstWord();
//	        	cardRechargeBean.setAllArea(areaList); 
	        	//　需求更改，只显示一卡通所在城市
	        	
	        	cardRechargeBean.setAreaList(cityFindService.getAllBusinessCity());
	        	//查询账户余额
	        	DodopalResponse<AccountFundBean> accountResponse = accountQueryService.findAccountBalance(userBean.getMerUserType(), userBean.getUserCode());
	        	if(ResponseCode.SUCCESS.equals(accountResponse.getCode())){
	        		if(null!=accountResponse.getResponseEntity()){
	        			//设置余额
	        			cardRechargeBean.setAvailableBalance(accountResponse.getResponseEntity().getAvailableBalance());
	        		}
	        	}else{
	        		//查询账户失败直接返回code
	        		dodopalResponse.setCode(accountResponse.getCode());
	        		throw new DDPException(dodopalResponse.getCode());
	        	}
	        	//个人用户城市为空
	        	if(StringUtils.isBlank(cardRechargeBean.getCityCode())){
	        		cardRechargeBean.setAreaList(cityFindService.getAllBusinessCity());
	        		if(CollectionUtils.isNotEmpty(cardRechargeBean.getAreaList())){
	        			cardRechargeBean.setCityCode(cardRechargeBean.getAreaList().get(0).getCityCode());
	        			cardRechargeBean.setCityName(cardRechargeBean.getAreaList().get(0).getCityName());
	        		}
	        		
//	        		cardRechargeBean.setCityName
//	        		cardRechargeBean.setCityCode(CommonConstants.DEFAULT_CITY_CODE);
//	        		cardRechargeBean.setCityName(areaService.findCityByCityCode(CommonConstants.DEFAULT_CITY_CODE).getCityName());
	        	}
	        	//查询城市产品
//	        	proRespone = prdProductYktService.findAvailableIcdcProductsInCity(cardRechargeBean.getCityCode());
	        	//ext  = true 的时候    merCode不能为空 [ True ：外接商户 False：非外接商户]
	        }else{
	        	//商户
	        	if(null!=userBean.getMerCode()){
	        		//业务城市数据构造
	        		DodopalResponse<List<Area>> areaList = merService.findMerCitysPayment(userBean.getMerCode());
	        		if(!ResponseCode.SUCCESS.equals(areaList.getCode())){
	        			//查询商户开通一卡通城市出错
	        			dodopalResponse.setCode(areaList.getCode());
	        			throw new DDPException(dodopalResponse.getCode());
	        		}
	        		cardRechargeBean.setAreaList(areaList.getResponseEntity());
	        		//cardRechargeBean.setAllArea(AreaFirstWordUtils.createAreaMap(areaList.getResponseEntity()));//areaList.getResponseEntity()
//	        		Map <String,List<Area>> areaLista = areaService.createMapForFirstWord();
//		        	cardRechargeBean.setAllArea(areaLista);
		        	//判断商户是否设置了默认城市
	        		if(StringUtils.isBlank(cardRechargeBean.getCityCode())){
	        			if(CollectionUtils.isEmpty(areaList.getResponseEntity())){
	        				cardRechargeBean.setCityName(ProductConstants.CARD_CITY_NULL);
	        			}else{
	        				//给默认城市
	        				cardRechargeBean.setCityCode(areaList.getResponseEntity().get(0).getCityCode());
	        				cardRechargeBean.setCityName(areaList.getResponseEntity().get(0).getCityName());
	        			}
	        		}else{
	        			if(CollectionUtils.isEmpty(areaList.getResponseEntity())){
	        				//没有查到开通一卡通消费的城市
	        				cardRechargeBean.setCityName(ProductConstants.CARD_CITY_NULL);
	        			}else{
	        				//检查设置的默认城市是否是商户开通的业务城市，例如 默认城市是否是一卡通消费的城市
	        				int sameFlag=0;
		        			for(Area tempArea:areaList.getResponseEntity()){
		        				if(cardRechargeBean.getCityCode().equals(tempArea.getCityCode())){
		        					sameFlag++;
		        				}
		        			}
		        			if(sameFlag==0){
		        				cardRechargeBean.setCityCode(areaList.getResponseEntity().get(0).getCityCode());
		        				cardRechargeBean.setCityName(areaList.getResponseEntity().get(0).getCityName());
		        			}
	        			}
	        		}
	    	    }else{
		        	dodopalResponse.setCode(ResponseCode.PRODUCT_PRO_MERCHANTNUM_NULL);
		        	throw new DDPException(dodopalResponse.getCode());
	        	}
	        }
	        if(StringUtils.isBlank(cardRechargeBean.getCityCode())){
                dodopalResponse.setCode(ResponseCode.PRODUCT_RECHARGE_CITY_YKT_ACTIVATE_ERROR);
                throw new DDPException(dodopalResponse.getCode());
	        }
	        //获取通卡公司信息
	        ProductYKT productYKT = productYKTService.getYktInfoByBusinessCityCode(cardRechargeBean.getCityCode());
	        if(null!=productYKT&&ActivateEnum.ENABLE.getCode().equals(productYKT.getActivate())){
	        	cardRechargeBean.setYktName(productYKT.getYktName());
	            cardRechargeBean.setYktCode(productYKT.getYktCode());
        		//2、根据通卡公司code和城市code 取得对应的版本号          
        		cardRechargeBean.setOcxVersion(ddicService.getDdicNameByCodeFormDB(cardRechargeBean.getYktCode(), cardRechargeBean.getCityCode()).toString());
	 	        //3、根据通卡公司code取得 对应的class id
	 	        cardRechargeBean.setOcxClassId(ddicService.getDdicNameByCodeFormDB(cardRechargeBean.getYktCode(), CommonConstants.OCX_CLASS_ID).toString());
	 	        
	 	       //商户的情况下需要配置信息
		 	   if(MerUserTypeEnum.MERCHANT.getCode().equals(userBean.getMerUserType())){ 

		 	        //一卡通消费的费率
		 	       DodopalResponse<List<MerBusRateBean>> rateResp = merService.findMerBusRateByMerCode(userBean.getMerCode());
		       		if(ResponseCode.SUCCESS.equals(rateResp.getCode())){
		       			if(CollectionUtils.isNotEmpty(rateResp.getResponseEntity())){
		       				//查找费率，并匹配启用的费率
		       				for(MerBusRateBean merRate:rateResp.getResponseEntity()){
		       					if(ActivateEnum.ENABLE.getCode().equals(merRate.getActivate())){
		       						if(RateCodeEnum.YKT_PAYMENT.getCode().equals(merRate.getRateCode())){
		       							if(cardRechargeBean.getYktCode().equals(merRate.getProviderCode())){
			       							merRate.setProviderCode("");
			        						merRate.setUniqueIdentification("");
			        						cardRechargeBean.setMerRate(merRate);	        							
		       							}
		       						}
		       					}
		       				}
		       			}else{
		       				log.error("未配置商户费率");
		       	    		dodopalResponse.setCode(ResponseCode.PRODUCT_MER_RATE_NULL);
		       	    		throw new DDPException(dodopalResponse.getCode());
		       			}
		       		}else{
		       			dodopalResponse.setCode(rateResp.getCode());
		       			throw new DDPException(dodopalResponse.getCode());
		       		}
		       		
		       	  DodopalResponse<MerchantBean> merResponse = merService.findMerchantByCode(cardRechargeBean.getMerCode());
		       	  //商户折扣
		    		if(null!=merResponse.getResponseEntity()){
		    			cardRechargeBean.setMerName(merResponse.getResponseEntity().getMerName());
		    			DodopalResponse<List<MerDiscountBean>> merDiscountList = merDiscountService.findMerDiscountByList(cardRechargeBean.getMerCode(), merResponse.getResponseEntity().getMerType());
		    			if(merDiscountList.isSuccessCode()){
		    			    //添加商户折扣
	                        cardRechargeBean.setDiscountList(merDiscountList.getResponseEntity());
		    			}else{
		    			    throw new DDPException(merDiscountList.getCode());
		    			}
		    			List<MerDiscountBean> ddpdiscounts = merDiscountService.findMerDiscountToday(cardRechargeBean.getMerCode());
		    			if(CollectionUtils.isNotEmpty(ddpdiscounts)){
		    			    cardRechargeBean.setDdpDiscount(ddpdiscounts.get(0));
		    			}
		    		}else{
		    			cardRechargeBean.setMerName(cardRechargeBean.getMerCode());
		    		}
		    		
		 	   }
        	}else{
        		//没有通卡公司，或通卡公司被停用
        		dodopalResponse.setCode(ResponseCode.PRODUCT_RECHARGE_CITY_YKT_ACTIVATE_ERROR);
        		dodopalResponse.setResponseEntity(cardRechargeBean);
        		return dodopalResponse;
        	}
//	        if(CollectionUtils.isNotEmpty(proRespone)){
//	        	//放入一卡通公司code
//	        	for(PrdProductYkt bean:proRespone){
//	                prdProductYktList.add(bean.getProPrice()/100);
//	            }
//	        	//ProductYKT yktBean = productYKTService.getProductYktByBusinessCityID(cardRechargeBean.getCityCode());
//	        	//根据城市code查找通卡公司code
//	        }
	      
    		
//	        Collections.sort(prdProductYktList);
	        
//	        cardRechargeBean.setPrdProductYktList(proRespone);
	        cardRechargeBean.setPayWranFlag(userBean.getPayInfoFlg());
	        log.info("当前登录产品库用户为[{}],用户号/商户号[{}],用户名[{}],页面获取的所有参数准备完毕，参数toString:[{}]",userBean.getMerchantName(),StringUtils.isNotBlank(userBean.getUserCode())?userBean.getUserCode():userBean.getMerCode(),userBean.getMerUserName(),cardRechargeBean.toString());

//	        cardRechargeBean.setProPriceList(prdProductYktList);
	        dodopalResponse.setResponseEntity(cardRechargeBean);
	        dodopalResponse.setCode(ResponseCode.SUCCESS);
    	}catch(DDPException e){
    		log.error("消费产品库在页面抓取数据时抛出异常code:"+e.getCode());
			if(!ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
				dodopalResponse.setNewMessage(ResponseCode.PRODUCT_ERROR);
		    }
    	}catch(Exception e){
    		log.error("CardConsumeController call a error:",e);
    		e.printStackTrace();
    		dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
    	}
        return dodopalResponse;
	}
	 
	 @RequestMapping("/openCardConsume")
	    public ModelAndView openCardConsume(HttpServletRequest request) {
	        //PortalUserDTO dto = super.getLoginUser(request.getSession());
	        if(log.isDebugEnabled()){
	            log.debug("匹配当前用户是个人还是商户");
	        }
	        MerchantUserBean userBean= merUserService.findUserInfoByUserCode((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE)).getResponseEntity();
	        request.getSession().setAttribute(ProductConstants.CARD_LOGIN_USER,userBean);
	        if(MerUserTypeEnum.PERSONAL.getCode().equals(userBean.getMerUserType())){
	            return new ModelAndView("applicationCenter/cardRecharge/cardRechargeWithUser");
	        }
	        return new ModelAndView("applicationCenter/cardConsume/cardConsumeWithMer");
	    }
}
