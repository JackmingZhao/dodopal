package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.enums.IsShowErrorMsgEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.PayWarnFlagEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.product.business.bean.AccountFundBean;
import com.dodopal.product.business.bean.CardRechargeBean;
import com.dodopal.product.business.bean.MerBusRateBean;
import com.dodopal.product.business.bean.MerRateSupplementBean;
import com.dodopal.product.business.bean.MerUserCardBDBean;
import com.dodopal.product.business.bean.MerUserCardBDFindBean;
import com.dodopal.product.business.bean.MerchantBean;
import com.dodopal.product.business.bean.MerchantUserBean;
import com.dodopal.product.business.bean.PayWayBean;
import com.dodopal.product.business.constant.ProductConstants;
import com.dodopal.product.business.model.LoadOrder;
import com.dodopal.product.business.model.PrdProductYkt;
import com.dodopal.product.business.model.ProductOrder;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.service.AccountQueryService;
import com.dodopal.product.business.service.CityFindService;
import com.dodopal.product.business.service.LoadOrderService;
import com.dodopal.product.business.service.MerUserCardBDService;
import com.dodopal.product.business.service.MerchantService;
import com.dodopal.product.business.service.MerchantUserService;
import com.dodopal.product.business.service.PayService;
import com.dodopal.product.business.service.PrdProductYktService;
import com.dodopal.product.business.service.ProductOrderService;
import com.dodopal.product.business.service.ProductYKTService;
import com.dodopal.product.delegate.constant.DelegateConstant;
/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月19日 下午7:04:09
 */
@Controller
@RequestMapping("/cardRecharge")
public class CardRechargeController extends CommonController{
    private final static Logger log = LoggerFactory.getLogger(CardRechargeController.class);
    @Autowired
    PrdProductYktService prdProductYktService;
    @Autowired
    AreaService areaService;
    @Autowired
    LoadOrderService loadOrderService;
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
    
    
    
    
    @RequestMapping("/toCardRecharge")
    public ModelAndView toCardRecharge(HttpServletRequest request,
    		@RequestParam(value="input_charset",required=true)String _input_charset,
    		@RequestParam(value="sign_type",required=true)String sign_type,
    		@RequestParam(value="sign",required=true)String sign,
    		@RequestParam(value="mercode",required=true)String mercode,
    		@RequestParam(value="usercode",required=false) String userCode,
    		@RequestParam(value="businesscode",required=true) String businesscode,
    		@RequestParam(value="trandate",required=true) String trandate,
    		@RequestParam(value="soruce",required=false) String soruce) {
        try{
            log.info("进入产品库   into product;参数展示  input_charset:{},sign_type:{},sign:{},mercode:{},usercode:{},"
                + "businesscode:{},trandate:{},soruce{}",_input_charset,sign_type,sign,mercode,userCode,businesscode,trandate,soruce);
            
        	Map<String, String> map = new HashMap<String, String>();
        	ModelAndView mv = new ModelAndView();
        	if(StringUtils.isBlank(soruce)){
        		//外接
        		soruce = SourceEnum.MERMACHINE.getCode();
        	}else{
        	    map.put("soruce",soruce);
        	}
        	if(StringUtils.isNotBlank(userCode)){
        		map.put("usercode", userCode);
        	}
        	map.put("mercode", mercode);
            map.put("businesscode",businesscode);
            map.put("trandate",trandate);
            String mySign = SignUtils.sign(SignUtils.createLinkString(map), CommonConstants.KEY, _input_charset);
            log.info("进行验签 传入签名{};本地签名{}",sign,mySign);
            if(!mySign.equals(sign)){
                log.info("验签不通过");
                return new ModelAndView("notFound");
            }
            log.info("验签通过");
            request.getSession().setAttribute(ProductConstants.CARD_RECHARGE_CITY, "");  
            MerchantUserBean userBean= merUserService.findUserInfoByUserCode(userCode).getResponseEntity();
            request.getSession().setAttribute(ProductConstants.CARD_RECHARGE_USERCODE, userBean.getUserCode());
            request.getSession().setAttribute(ProductConstants.CARD_RECHARGE_USERNAME, userBean.getMerUserName());
            request.getSession().setAttribute(ProductConstants.CARD_LOGIN_USER,userBean);
            mv.setViewName("applicationCenter/urlReturn");
            mv.addObject("bcode", businesscode);
            mv.addObject("soruce", soruce);
            log.info("进行第二次页面跳转");
            return mv;
        }catch(Exception e){
            log.error("在产品库跳转时发生错误  CardRechargeController toCardRecharge call an error: ",e);
            return new ModelAndView("error");
        }
    }
    
    @RequestMapping("/toApplicationPage")
    public ModelAndView toApplicationPage(HttpServletRequest request,@RequestParam(value="bcode")String businesscode,@RequestParam(value="soruce") String soruce) {
        log.info("进入二次重定向方法内 CardRechargeController toApplicationPage  参数businesscode:{};soruce:{}",businesscode,soruce);
        
        ModelAndView mv = new ModelAndView();
        MerchantUserBean userBean = (MerchantUserBean) request.getSession().getAttribute(ProductConstants.CARD_LOGIN_USER);
        if(null==userBean){
            log.info("用户session不存在，或已过期，或直接访问，进行404跳转");
            return new ModelAndView("notFound");
        }
        try{
        if(SourceEnum.MERMACHINE.getCode().equals(soruce)){
            mv.setViewName("applicationCenter/cardRecharge/cardRechargeWithExternal");
            return mv;
        }
        if(MerUserTypeEnum.PERSONAL.getCode().equals(userBean.getMerUserType())){
            //个人用户
            if(RateCodeEnum.YKT_RECHARGE.getCode().equals(businesscode)){
            //一卡通充值
                 mv.setViewName("applicationCenter/cardRecharge/cardRechargeWithUser");
                 return mv;
            } else if (RateCodeEnum.YKT_PAYMENT.getCode().equals(businesscode)){
                //一卡通消费
                 mv.setViewName("applicationCenter/cardConsume/cardConsumeWithUser");
                 return mv;
            }else if (RateCodeEnum.IC_LOAD.getCode().equals(businesscode)){
                mv.setViewName("applicationCenter/loadOrder/loadOrder");
                return mv;
            }
        }else {
            //商户
            if(RateCodeEnum.YKT_RECHARGE.getCode().equals(businesscode)){
                //一卡通充值
                mv.setViewName("applicationCenter/cardRecharge/cardRechargeWithMer");
                return mv;
            }else if (RateCodeEnum.YKT_PAYMENT.getCode().equals(businesscode)){
                //一卡通消费
                mv.setViewName("applicationCenter/cardConsume/cardConsumeWithMer");
                return mv;
            }
        }
            return new ModelAndView("notFound");
        }catch(Exception e){
            mv.setViewName("error");
            return mv;
        }
    }
    
    @RequestMapping("/openCardRecharge")
    public ModelAndView openCardRecharge(HttpServletRequest request) {
        //PortalUserDTO dto = super.getLoginUser(request.getSession());
        if(log.isDebugEnabled()){
            log.debug("匹配当前用户是个人还是商户");
        }
        MerchantUserBean userBean= merUserService.findUserInfoByUserCode((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE)).getResponseEntity();
        request.getSession().setAttribute(ProductConstants.CARD_LOGIN_USER,userBean);
        if(MerUserTypeEnum.PERSONAL.getCode().equals(userBean.getMerUserType())){
            return new ModelAndView("applicationCenter/cardRecharge/cardRechargeWithUser");
        }
        return new ModelAndView("applicationCenter/cardRecharge/cardRechargeWithMer");
    }
    @RequestMapping("/getCardRechargeInfo")
    public @ResponseBody DodopalResponse<CardRechargeBean> getCardRechargeInfo(HttpServletRequest request,@RequestBody String cityCode) {
        log.info("CardRechargeController 进入卡充值时获取页面基本信息，产品 城市等");
        
    	DodopalResponse<CardRechargeBean> dodopalResponse = new DodopalResponse<CardRechargeBean>();
    	try{
    		CardRechargeBean cardRechargeBean =  new CardRechargeBean();
    			
    		if(StringUtils.isBlank((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE))){
    			dodopalResponse.setCode(ResponseCode.LOGIN_TIME_OUT);
    			return dodopalResponse;
    			
    		}
    		DodopalResponse<MerchantUserBean> userResponse = merUserService.findUserInfoByUserCode((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE));
    		
    		if(!ResponseCode.SUCCESS.equals(userResponse.getCode())&&null==userResponse.getResponseEntity()){
    			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
    			throw new DDPException(dodopalResponse.getCode());
    		}
    		MerchantUserBean userBean = userResponse.getResponseEntity();
    	    log.info("当前登录产品库用户为[{}],用户号/商户号[{}],用户名[{}]",userBean.getMerchantName(),StringUtils.isNotBlank(userBean.getUserCode())?userBean.getUserCode():userBean.getMerCode(),userBean.getMerUserName());

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
	       
	        List<Integer> prdProductYktList = new ArrayList<Integer>();
	        DodopalResponse<List<PrdProductYkt>> yktResponse = new DodopalResponse<List<PrdProductYkt>>();
	        List<PrdProductYkt> proRespone = null;
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
	        	}
	        	//查询城市产品
	        	proRespone = prdProductYktService.findAvailableIcdcProductsInCity(cardRechargeBean.getCityCode());
	        	//ext  = true 的时候    merCode不能为空 [ True ：外接商户 False：非外接商户]
	        	DodopalResponse<List<PayWayBean>> payWayResponse = payService.findCommonPayWay(false, userBean.getUserCode());
	        	 if(ResponseCode.SUCCESS.equals(payWayResponse.getCode())){
	        		 cardRechargeBean.setPayCommonWayBean(payWayResponse.getResponseEntity());
	        	 }else{
	        		 dodopalResponse.setCode(payWayResponse.getCode());
	        		 throw new DDPException(dodopalResponse.getCode());
	        	 }
	        }else{
	        	//业务城市数据构造
        		DodopalResponse<List<Area>> areaList = merService.findMerCitys(userBean.getMerCode());
        		if(!ResponseCode.SUCCESS.equals(areaList.getCode())){
        			//查询商户开通一卡通城市出错
        			dodopalResponse.setCode(areaList.getCode());
        			throw new DDPException(dodopalResponse.getCode());
        		}
        		cardRechargeBean.setAreaList(areaList.getResponseEntity());
	        	
	        	
	        	//商户
	        	if(null!=userBean.getMerCode()){
	        		DodopalResponse<List<MerBusRateBean>> rateResp = merService.findMerBusRateByMerCode(userBean.getMerCode());
	        		if(ResponseCode.SUCCESS.equals(rateResp.getCode())){
	        			if(CollectionUtils.isNotEmpty(rateResp.getResponseEntity())){
	        				//查找费率，并匹配启用的费率
	        				for(MerBusRateBean merRate:rateResp.getResponseEntity()){
	        					if(ActivateEnum.ENABLE.getCode().equals(merRate.getActivate())){
	        						if(RateCodeEnum.YKT_RECHARGE.getCode().equals(merRate.getRateCode())){
	        							merRate.setProviderCode("");
		        						merRate.setUniqueIdentification("");
		        						cardRechargeBean.setMerRate(merRate);	        							
	        						}
	        					}
	        				}
	        			}else{
	        				log.error("getCardRechargeInfo 获取商户信息时发现未配置商户费率");
	        	    		dodopalResponse.setCode(ResponseCode.PRODUCT_MER_RATE_NULL);
	        	    		throw new DDPException(dodopalResponse.getCode());
	        			}
	        		}
	        		
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
	        				//没有查到开通一卡通充值的城市
	        				cardRechargeBean.setCityName(ProductConstants.CARD_CITY_NULL);
	        			}else{
	        				//检查设置的默认城市是否是商户开通的业务城市，例如 默认城市是否是一卡通充值的城市
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
	        		//城市code
	        		if(StringUtils.isNotBlank(cardRechargeBean.getCityCode())){
	        			yktResponse = prdProductYktService.findAvailableIcdcProductsForMerchant(userBean.getMerCode(), cardRechargeBean.getCityCode());
	        			if(ResponseCode.SUCCESS.equals(yktResponse.getCode())){
	        				proRespone = yktResponse.getResponseEntity();
	        			}else if(ResponseCode.PRODUCT_YKT_ERR.equals(yktResponse.getCode())){
	        			}else{
	        				log.error("调用产品service查找产品时出错："+yktResponse.getCode());
	        	    		dodopalResponse.setCode(yktResponse.getCode());
	        	    		throw new DDPException(dodopalResponse.getCode());
	        			}
	        		}else{
	        			dodopalResponse.setCode(ResponseCode.PRODUCT_RECHARGE_CITY_YKT_ACTIVATE_ERROR);
	        			throw new DDPException(dodopalResponse.getCode());
	        		}
	    	    }else{
		        	dodopalResponse.setCode(ResponseCode.PRODUCT_PRO_MERCHANTNUM_NULL);
		        	return dodopalResponse;
	        	}
	        	//支付方式 外接与通用支付方式的区别
	        	DodopalResponse<List<PayWayBean>> payWayResponse =  null;//payService.findPayWay(false, userBean.getMerCode());
	        	DodopalResponse<MerchantBean> merResponse = merService.findMerchantByCode(userBean.getMerCode());
	        	if(!ResponseCode.SUCCESS.equals(merResponse.getCode())){
	        		log.error(merResponse.getMessage());
       	    		dodopalResponse.setCode(merResponse.getCode());
       	    		throw new DDPException(dodopalResponse.getCode());
	        	}
	        	if(null!=merResponse.getResponseEntity()){
                    cardRechargeBean.setMerName(merResponse.getResponseEntity().getMerName());
                }else{
                    cardRechargeBean.setMerName(cardRechargeBean.getMerCode());
                }
	        	if(MerTypeEnum.EXTERNAL.getCode().equals(merResponse.getResponseEntity().getMerType())){
	        		if(log.isInfoEnabled()){
	        			log.info("getCardRechargeInfo 外接商户进入查询支付方式");
	        		}
	        		 payWayResponse =  payService.findPayWay(true, userBean.getMerCode());
	        		 
	        	}else{
	        		if(log.isInfoEnabled()){
	        			log.info("getCardRechargeInfo 非外接商户进入查询支付方式");
	        		}
	        		 payWayResponse =  payService.findPayWay(false, userBean.getMerCode());
	        	}
	        	if(ResponseCode.SUCCESS.equals(payWayResponse.getCode())){
	        		//商户拿到账户支付的id
	        		List<PayWayBean> payWaylist = payWayResponse.getResponseEntity();
	        		if(CollectionUtils.isNotEmpty(payWaylist)){
	        		    log.info("查询到商户支付方式的条数为:"+payWaylist.size());
	        			for(PayWayBean tempPay:payWaylist){
		        			if(PayTypeEnum.PAY_ACT.getCode().equals(tempPay.getPayType())){
		        				cardRechargeBean.setMerPayWayId(tempPay.getId());
		        			}
		        		}
	                    log.info("商户账户支付方式id:"+cardRechargeBean.getMerPayWayId());
	        		}else{
	        			dodopalResponse.setCode(ResponseCode.PRODUCT_PAY_WAY_ID_NULL);
	        			throw new DDPException(dodopalResponse.getCode());
	        		}
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
		 	        //商户费率信息查找
		 	        DodopalResponse<List<MerBusRateBean>> rateResp = merService.findMerBusRateByMerCode(userBean.getMerCode());
		       		if(ResponseCode.SUCCESS.equals(rateResp.getCode())){
		       			if(CollectionUtils.isNotEmpty(rateResp.getResponseEntity())){
		       				//查找费率，并匹配启用的费率
		       				for(MerBusRateBean merRate:rateResp.getResponseEntity()){
		       					if(ActivateEnum.ENABLE.getCode().equals(merRate.getActivate())){
		       						if(RateCodeEnum.YKT_RECHARGE.getCode().equals(merRate.getRateCode())){
		       							if(cardRechargeBean.getYktCode().equals(merRate.getProviderCode())){
		       								merRate.setProviderCode("");
			        						merRate.setUniqueIdentification("");
			        						cardRechargeBean.setMerRate(merRate);	        
		       							}
		       						}
		       					}
		       				}
		       			}else{
		       				log.error("getCardRechargeInfo 获取商户信息 未配置商户费率");
		       	    		dodopalResponse.setCode(ResponseCode.PRODUCT_MER_RATE_NULL);
		       	    		throw new DDPException(dodopalResponse.getCode());
		       			}
		       			
		       		}else{
		       			dodopalResponse.setCode(rateResp.getCode());
		       			throw new DDPException(dodopalResponse.getCode());
		       		}
		    		
	 	       }
        	}else{
        		//没有通卡公司，或通卡公司被停用
        		dodopalResponse.setCode(ResponseCode.PRODUCT_RECHARGE_CITY_YKT_ACTIVATE_ERROR);
        		return dodopalResponse;
        	}
	        //判断产品列表是否为空
	        if(CollectionUtils.isNotEmpty(proRespone)){
	        	//放入一卡通公司code
	        	for(PrdProductYkt bean:proRespone){
	                prdProductYktList.add(bean.getProPrice()/100);
	            }
	        }
	        
	        Collections.sort(prdProductYktList);
	        
	        cardRechargeBean.setPrdProductYktList(proRespone);
	        cardRechargeBean.setPayWranFlag(userBean.getPayInfoFlg());
	        cardRechargeBean.setProPriceList(prdProductYktList);
	        log.info("当前登录产品库用户为[{}],用户号/商户号[{}],用户名[{}],页面获取的所有参数准备完毕，参数toString:[{}]",userBean.getMerchantName(),StringUtils.isNotBlank(userBean.getUserCode())?userBean.getUserCode():userBean.getMerCode(),userBean.getMerUserName(),cardRechargeBean.toString());

	        dodopalResponse.setResponseEntity(cardRechargeBean);
	        dodopalResponse.setCode(ResponseCode.SUCCESS);
    	}catch(DDPException e){
    		log.error("充值产品库在页面抓取数据时抛出异常code:"+e.getCode());
			if(!ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
				 dodopalResponse.setNewMessage(ResponseCode.PRODUCT_ERROR);
		    }
    	}catch(Exception e){
    		log.error("CardRechargeController call a error:",e);
    		e.printStackTrace();
    		dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
    	}
        return dodopalResponse;
    }
    
    @RequestMapping("/toStopPayWarn")
    public @ResponseBody DodopalResponse<Boolean> toStopPayWarn(HttpServletRequest request,@RequestBody String flag) {
    	MerchantUserBean userbean = new MerchantUserBean();
    	request.getSession().getAttribute(ProductConstants.CARD_LOGIN_USER);
    	MerchantUserBean sessionUser = (MerchantUserBean)request.getSession().getAttribute(ProductConstants.CARD_LOGIN_USER);
    	userbean.setId(sessionUser.getId());
    	userbean.setPayInfoFlg(PayWarnFlagEnum.OFF_WARN.getCode());
    	DodopalResponse<Boolean> dodopalResponse = merUserService.modifyPayInfoFlg(userbean);
		return dodopalResponse;
    }
    
    @RequestMapping("/getMorPayWay")
    public @ResponseBody DodopalResponse<List<PayWayBean>> getMorPayWay(HttpServletRequest request,@RequestBody String param) {
    	MerchantUserBean sessionUser = (MerchantUserBean)request.getSession().getAttribute(ProductConstants.CARD_LOGIN_USER);
    	DodopalResponse<List<PayWayBean>> dodopalResponse =  payService.findPayWay(false, sessionUser.getUserCode());
		return dodopalResponse;
    }
    @RequestMapping("/toChangeCity")
    public ModelAndView toChangeCity(HttpServletRequest request,@RequestParam String cityCode,@RequestParam String bnscode) {
    	//MerchantUserBean userBean = (MerchantUserBean)request.getSession().getAttribute(ProductConstants.CARD_LOGIN_USER);
    	MerchantUserBean userBean= merUserService.findUserInfoByUserCode((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE)).getResponseEntity();
    	DodopalResponse<Boolean> updateResponse = merUserService.updateMerUserBusCity(cityCode, userBean.getId());
    	if(log.isInfoEnabled()){
    		log.info("CardRechargeController更新的结果为"+updateResponse.getMessage());
    	}
    	request.getSession().setAttribute(ProductConstants.CARD_RECHARGE_CITY, cityCode);  
    	if(RateCodeEnum.YKT_RECHARGE.getCode().equals(bnscode)){
    		//一卡通充值
    		 if(MerUserTypeEnum.PERSONAL.getCode().equals(userBean.getMerUserType())){
    	            return new ModelAndView("applicationCenter/cardRecharge/cardRechargeWithUser");
    	        }
    	        return new ModelAndView("applicationCenter/cardRecharge/cardRechargeWithMer");
		}else if (RateCodeEnum.YKT_PAYMENT.getCode().equals(bnscode)){
			//一卡通消费
        	 return new ModelAndView("applicationCenter/cardConsume/cardConsumeWithMer");
        }else if(RateCodeEnum.IC_LOAD.getCode().equals(bnscode)){
            //一卡通圈存
            return new ModelAndView("applicationCenter/loadOrder/loadOrder");
        }
    	return new ModelAndView("notFound");
    }
    @RequestMapping("/toCallRechargeResult")
    public ModelAndView toCallRechargeResult(Model model, HttpServletRequest request,
    		@RequestParam(value="orderNum",required=true) String orderNum,	
    		@RequestParam(value="sign",required=true)String sign,
    		@RequestParam(value="notify_time",required=true)String notifyTime,
    		@RequestParam(value="businessType",required=false)String businessType) {
        try{
        log.info("toCallRechargeResult 获取的参数为 orderNum:[{}],sign[{}],notify_time[{}],businessType[{}]",orderNum,sign,notifyTime,businessType);
	   	 Map<String, String> signMap = new HashMap<String, String>();
	     signMap.put("orderNum", orderNum);
	     signMap.put("notify_time", notifyTime);
	     String newSign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(signMap)), CommonConstants.KEY, CharEncoding.UTF_8);
	     if(newSign.equals(sign)){
	    	 log.info("验签通过");
	    	 if(StringUtils.isBlank(businessType)){
	    	     log.info("businessType为空");
	    	     return new ModelAndView("notFound");
	    	 }
	    	 String bcode = RateCodeEnum.getRateTypeByCode(businessType).getCode();
	            if(RateCodeEnum.IC_LOAD.getCode().equals(bcode)){ 
	                log.info("进入一卡通圈存处理流程");
	                //一卡通圈存的结果页面
	                DodopalResponse<LoadOrder> loadOrderRes = loadOrderService.updateloadOrderStateAfterAccountDeduct(orderNum);
	                if(ResponseCode.SUCCESS.equals(loadOrderRes.getCode())){
                            log.info("跳转url notFound");
                            model.addAttribute("resultCode",loadOrderRes.getCode());
                            return new ModelAndView("applicationCenter/loadOrder/loadOrderResultBack");
                    }
	               
	            }else if(RateCodeEnum.YKT_RECHARGE.getCode().equals(bcode)){
	                log.info("进入一卡通充值处理流程");
	                //一卡通充值结果页面
	                DodopalResponse<ProductOrder> orderPro  = productOrderService.getProductOrderByProOrderNum(orderNum);
	                log.info("获取原产品库订单返回CODE："+orderPro.getCode());
        	     	if(ResponseCode.SUCCESS.equals(orderPro.getCode())){
        	     		if(null!=orderPro.getResponseEntity()){
        	 		    	if(!PayTypeEnum.PAY_ACT.getCode().equals(orderPro.getResponseEntity().getPayType().toString())){
        	 		    		productOrderService.updateOrderStatusWhenAccountRecharge(orderNum, ResponseCode.SUCCESS);
        	 		    	}
        	 		    	ProductYKT yktBean = productYKTService.getYktInfoByBusinessCityCode(orderPro.getResponseEntity().getCityCode());
        	 	 	        if(null!=yktBean){
        	 		 	        model.addAttribute("ocxVersion",ddicService.getDdicNameByCodeFormDB(yktBean.getYktCode(), orderPro.getResponseEntity().getCityCode()).toString());
        	 		 	        model.addAttribute("ocxClassId",ddicService.getDdicNameByCodeFormDB(yktBean.getYktCode(), CommonConstants.OCX_CLASS_ID).toString());
        	 		 	        model.addAttribute("yktCode",yktBean.getYktCode());
        	 	 	        }
        	 	 	        log.info("userCode"+orderPro.getResponseEntity().getUserCode());
        	 		    	model.addAttribute("userCode",orderPro.getResponseEntity().getUserCode());
        	 				model.addAttribute("orderNum",orderNum);
        	 				model.addAttribute("befbal",orderPro.getResponseEntity().getBefbal());
        	 				// 充值后金额
        	 				model.addAttribute("blackAmt",orderPro.getResponseEntity().getBlackAmt());
        	 				// 实付（收）金额
        	 				model.addAttribute("receivedPrice",orderPro.getResponseEntity().getReceivedPrice());
        	 				//金额
        	 				model.addAttribute("txnAmt",orderPro.getResponseEntity().getTxnAmt());
        	 				 log.info("跳转url applicationCenter/cardRecharge/cardRechargeResultBack");
        	 				return new ModelAndView("applicationCenter/cardRecharge/cardRechargeResultBack");
        	     		}else{
        	     			 log.info("跳转url notFound");
        	     			return new ModelAndView("notFound");
        	     		}
    	     	}
	     	}
	      }
        }catch(Exception e){
            log.error("toCallRechargeResult call an error", e);
            return new ModelAndView("error");
        }
		return new ModelAndView("notFound");
    }
    
    @RequestMapping("/toRechargeBank")
    public ModelAndView toRechargeBank(HttpServletRequest request,@RequestParam String userName,@RequestParam String userType) {
        //PortalUserDTO dto = super.getLoginUser(request.getSession());
        request.getSession().setAttribute(ProductConstants.CARD_RECHARGE_USERNAME, userName);
        MerchantUserBean userBean= merUserService.findUserInfoByUserCode((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE)).getResponseEntity();

        request.getSession().setAttribute(ProductConstants.CARD_LOGIN_USER,userBean);
        if(MerUserTypeEnum.PERSONAL.getCode().equals(userBean.getMerUserType())){
            return new ModelAndView("applicationCenter/cardRecharge/cardRechargeWithUser");
        }
        return new ModelAndView("applicationCenter/cardRecharge/cardRechargeWithMer");
    }
    
  
    
    @RequestMapping("/toBankGate")
    public String toBankGate(HttpServletRequest request,RedirectAttributes attr,@RequestParam String orderCode,@RequestParam String payWayId,@RequestParam String payWranFlag,@RequestParam String isMixed,@RequestParam String cityCode,@RequestParam String businessType){
        if(StringUtils.isBlank(orderCode)||StringUtils.isBlank(payWayId)||StringUtils.isBlank(payWranFlag)){
        	log.info("跳转网关时参数缺失orderCode:{},payWayId:{},payWranFlag:{}",orderCode,payWayId,payWranFlag);
        	return null;
        }
        if(StringUtils.isBlank((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE))){
            log.info("toBankGate时session过期");
        	return null;
        }
        Map<String, String> signMap = new HashMap<String, String>();
        MerchantUserBean userBean= merUserService.findUserInfoByUserCode((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERCODE)).getResponseEntity();

       // MerchantUserBean userBean= merUserService.findUserInfoByMobileOrUserName((String)request.getSession().getAttribute(ProductConstants.CARD_RECHARGE_USERNAME)).getResponseEntity();
        if(ActivateEnum.DISABLE.getCode().equals(userBean.getActivate().toString())){
        	return null;
        }
        
        if(StringUtils.isNotBlank(businessType)){
            //根据业务类型来进行处理跳转网关的订单数据
            if(RateCodeEnum.YKT_RECHARGE.getCode().equals(businessType)){
                DodopalResponse<ProductOrder> orderPro = productOrderService.getProductOrderByProOrderNum(orderCode);
                if(ResponseCode.SUCCESS.equals(orderPro.getCode())){
                      attr.addAttribute("tranMoney", String.valueOf(orderPro.getResponseEntity().getTxnAmt())); //面额
                      attr.addAttribute("commodityName", orderPro.getResponseEntity().getProName()); //商品名称
                      attr.addAttribute("realTranMoney",String.valueOf(orderPro.getResponseEntity().getReceivedPrice())); //实付
                      attr.addAttribute("tranName",orderPro.getResponseEntity().getProName());  //交易名称
                      
                      signMap.put("tranMoney", String.valueOf(orderPro.getResponseEntity().getTxnAmt()));
                      signMap.put("commodityName", orderPro.getResponseEntity().getProName());
                      signMap.put("realTranMoney", String.valueOf(orderPro.getResponseEntity().getReceivedPrice())); 
                      signMap.put("tranName",orderPro.getResponseEntity().getProName());
                }else{
                    return null;
                }
            }else if(RateCodeEnum.IC_LOAD.getCode().equals(businessType)){
                   LoadOrder loadOrder = loadOrderService.getLoadOrderByLoadOrderNum(orderCode);
                   if(null!=loadOrder){
                       attr.addAttribute("tranMoney", String.valueOf(loadOrder.getLoadAmt())); //面额
                       attr.addAttribute("commodityName", loadOrder.getProductName()); //商品名称
                       attr.addAttribute("realTranMoney",String.valueOf(loadOrder.getCustomerPayAmt())); //实付
                       attr.addAttribute("tranName",loadOrder.getProductName());  //交易名称
                       
                       signMap.put("tranMoney", String.valueOf(loadOrder.getLoadAmt()));
                       signMap.put("commodityName", loadOrder.getProductName());
                       signMap.put("realTranMoney", String.valueOf(loadOrder.getCustomerPayAmt())); 
                       signMap.put("tranName",loadOrder.getProductName());
                   }
            }
            attr.addAttribute("businessType",request.getParameter("businessType"));
            signMap.put("businessType", request.getParameter("businessType"));
        
        }else{
            attr.addAttribute("businessType", RateCodeEnum.YKT_RECHARGE.getCode());
            signMap.put("businessType", RateCodeEnum.YKT_RECHARGE.getCode());
        }
        
        if(PayWarnFlagEnum.OFF_WARN.getCode().equals(payWranFlag)&&!payWranFlag.equals(userBean.getPayInfoFlg())){
        	userBean.setPayInfoFlg(PayWarnFlagEnum.OFF_WARN.getCode());
        	merUserService.modifyPayInfoFlg(userBean);
        }
        
        attr.addAttribute("orderNumber",orderCode);  
        attr.addAttribute("customerNo", userBean.getUserCode()); //客户号
        attr.addAttribute("customerType",userBean.getMerUserType());  //客户类型
        attr.addAttribute("isMixed",isMixed);  //0:非联合支付     1:联合支付
        attr.addAttribute("payWayId", payWayId);
        attr.addAttribute("merchantType", false); //商户类型，是否外接
//        if(StringUtils.isNotBlank(request.getParameter("businessType"))){
//            attr.addAttribute("businessType", request.getParameter("businessType"));
//            signMap.put("businessType", request.getParameter("businessType"));
//        }else{
//            attr.addAttribute("businessType", RateCodeEnum.YKT_RECHARGE.getCode());
//            signMap.put("businessType", RateCodeEnum.YKT_RECHARGE.getCode());
//        }
        attr.addAttribute("source", SourceEnum.PORTAL.getCode());
        attr.addAttribute("commonParam", "");//预留

        attr.addAttribute("operateId",userBean.getId()); //操作人
        
        
        signMap.put("orderNumber",orderCode);  
        signMap.put("customerNo", userBean.getUserCode());
        signMap.put("customerType",userBean.getMerUserType());  
        signMap.put("isMixed",isMixed);  
        signMap.put("payWayId", payWayId);
        signMap.put("merchantType","false");
        signMap.put("operateId",userBean.getId());
        
        signMap.put("source", SourceEnum.PORTAL.getCode());
        signMap.put("commonParam", "");
        String proUrl = DodopalAppVarPropsUtil.getStringProp(DelegateConstant.PRODUCT_URL);
        //返回路径
        signMap.put("returnUrl",	proUrl+"/cardRecharge/toCallRechargeResult?cityCode="+cityCode);
        //回调路径
        signMap.put("notifyUrl",	proUrl+"/callBackOrder?cityCode="+cityCode);
 
        String sign = SignUtils.sign(SignUtils.createLinkString(SignUtils.removeNull(signMap)), CommonConstants.KEY, CharEncoding.UTF_8);
        System.out.println("签名值为："+sign);
        attr.addAttribute("sign", sign);
        attr.addAttribute("returnUrl",	proUrl+"/cardRecharge/toCallRechargeResult?cityCode="+cityCode);
        attr.addAttribute("notifyUrl",	proUrl+"/callBackOrder?cityCode="+cityCode);
        if(log.isWarnEnabled()){
        	 log.warn("传给交易支付网关回调链接地址 returnUrl:{}",proUrl+"/cardRecharge/toCallRechargeResult?cityCode="+cityCode);
             log.warn("notifyUrl:{}",proUrl+"/callBackOrder?cityCode="+cityCode);
        }
        String payUrl = DodopalAppVarPropsUtil.getStringProp(DelegateConstant.PAYMENT_URL);
        return "redirect:"+payUrl+"/pay";
    }
    
    @RequestMapping("/userLoadOrderRecharge")
    public ModelAndView userLoadOrderRecharge(Model model, HttpServletRequest request,
    		@RequestParam(value="orderNum",required=true) String orderNum) {
    	
		DodopalResponse<ProductOrder> orderPro  = productOrderService.getProductOrderByProOrderNum(orderNum);
		 if(ResponseCode.SUCCESS.equals(orderPro.getCode())){
	 		if(null!=orderPro.getResponseEntity()){
	 			ProductYKT yktBean = productYKTService.getYktInfoByBusinessCityCode(orderPro.getResponseEntity().getCityCode());
	 	        if(null!=yktBean){
		 	        model.addAttribute("ocxVersion",ddicService.getDdicNameByCodeFormDB(yktBean.getYktCode(), orderPro.getResponseEntity().getCityCode()));
		 	        model.addAttribute("ocxClassId",ddicService.getDdicNameByCodeFormDB(yktBean.getYktCode(), CommonConstants.OCX_CLASS_ID).toString());
		 	        model.addAttribute("yktCode",yktBean.getYktCode());
	 	        }
	 			
		    	model.addAttribute("userCode",orderPro.getResponseEntity().getUserCode());
				model.addAttribute("orderNum",orderNum);
				model.addAttribute("befbal",orderPro.getResponseEntity().getBefbal());
				// 充值后金额
				model.addAttribute("blackAmt",orderPro.getResponseEntity().getBlackAmt());
				// 实付（收）金额
				model.addAttribute("receivedPrice",orderPro.getResponseEntity().getReceivedPrice());
				//金额
				model.addAttribute("txnAmt",orderPro.getResponseEntity().getTxnAmt());
	 		}
	 	}
		 return new ModelAndView("applicationCenter/cardRecharge/cardRechargeResultBack");
    }
    
    
    @RequestMapping("/getCardBindInfo")
    public @ResponseBody DodopalResponse<Integer> getCardBindInfo(HttpServletRequest request,@RequestBody String cardNum) {
    	MerUserCardBDFindBean bdFindBean = new MerUserCardBDFindBean();
    	DodopalResponse<Integer> dodopalResponse = new DodopalResponse<Integer>();
    	try{
    		//设置绑定状态
	    	bdFindBean.setBundLingType(BindEnum.ENABLE.getCode());
	    	//卡号
	    	bdFindBean.setCardCode(cardNum);
	    	//查找绑定状态下的记录
	    	DodopalResponse<List<MerUserCardBDBean>> response = bdService.findMerUserCardBD(bdFindBean);
	    	//成功
	    	if(ResponseCode.SUCCESS.equals(response.getCode())){
	    		//绑定记录为空
	    		if(CollectionUtils.isEmpty(response.getResponseEntity())){
	    			dodopalResponse.setResponseEntity(0);
	    		}else{
	    			dodopalResponse.setResponseEntity(response.getResponseEntity().size());
	    		}
	    	}
	    	dodopalResponse.setCode(response.getCode());
    	}catch(Exception e){
    		dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
    	}
		return dodopalResponse;
    }
    
//    @RequestMapping("/getRealMoney")
//    public @ResponseBody DodopalResponse<Long> getRealMoney(HttpServletRequest request,@RequestBody String orderCode) {
//    	DodopalResponse<Long> dodopalResponse = new DodopalResponse<Long>();
//    	if(StringUtils.isBlank(orderCode)){
//    		dodopalResponse.setCode(ResponseCode.PAY_ORDER_CODE_NULL);
//    		return dodopalResponse;
//    	}
//    	try{
//    		DodopalResponse<ProductOrder> orderPro  = productOrderService.getProductOrderByProOrderNum(orderCode);
//        	if(ResponseCode.SUCCESS.equals(orderPro.getCode())){
//        		dodopalResponse.setResponseEntity(orderPro.getResponseEntity().getReceivedPrice());
//        	}
//        	dodopalResponse.setCode(orderPro.getCode());
//    	}catch(Exception e){
//    		dodopalResponse.setResponseEntity(0l);
//    		dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
//    	}
//		return dodopalResponse;
//    }
//    
    
    @RequestMapping("/toBindCard")
    public @ResponseBody DodopalResponse<Boolean> toBindCard(HttpServletRequest request,@RequestBody Map<String,String> map) {
    	MerUserCardBDBean bdbean = new MerUserCardBDBean();
    	DodopalResponse<Boolean> dodopalResponse = new DodopalResponse<Boolean>();

    	String cardCode = (String) map.get("cardCode");//卡号
    	String remarks = (String) map.get("remarks");//备注
    	String cardType = (String) map.get("cardType");//卡类型
    	String cardCityName = (String) map.get("cardCityName");//卡所属城市
    	if(StringUtils.isBlank(cardCode)){
    		dodopalResponse.setCode(ResponseCode.USERS_CARD_CODE_NOT_EMPTY);
    		return dodopalResponse;
    	}
    	try{
    		MerchantUserBean sessionUser = (MerchantUserBean)request.getSession().getAttribute(ProductConstants.CARD_LOGIN_USER);
        	bdbean.setMerUserName(sessionUser.getMerUserName());
        	bdbean.setMerUserNameName(sessionUser.getMerUserNickName());
        	bdbean.setSource(SourceEnum.PORTAL.getCode());
	    	bdbean.setBundLingType(BindEnum.ENABLE.getCode());
	    	bdbean.setCardCode(cardCode);
	    	bdbean.setCardType(cardType);
	    	bdbean.setCardCityName(cardCityName);
	    	bdbean.setCreateUser(sessionUser.getId());
	    	bdbean.setUpdateUser(sessionUser.getId());
	    	bdbean.setRemark(remarks);
	    	DodopalResponse<MerUserCardBDBean> response = bdService.saveMerUserCardBD(bdbean);
	    	if(ResponseCode.SUCCESS.equals(response.getCode())){
	    		dodopalResponse.setResponseEntity(true);
	    	}
	    	dodopalResponse.setCode(response.getCode());
    	}catch(Exception e){
    		dodopalResponse.setResponseEntity(false);
    		dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
    	}
		return dodopalResponse;
    }
    
}