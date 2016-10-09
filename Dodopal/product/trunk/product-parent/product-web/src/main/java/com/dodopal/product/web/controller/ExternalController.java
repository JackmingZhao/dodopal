package com.dodopal.product.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.NameValuePair;
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

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.CommonConstants;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.IsShowErrorMsgEnum;
import com.dodopal.common.enums.MerKeyTypeMD5PwdEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.DateUtils;
import com.dodopal.product.business.bean.CardRechargeBean;
import com.dodopal.product.business.bean.MerBusRateBean;
import com.dodopal.product.business.bean.MerRateSupplementBean;
import com.dodopal.product.business.bean.MerchantBean;
import com.dodopal.product.business.bean.MerchantUserBean;
import com.dodopal.product.business.bean.PayWayBean;
import com.dodopal.product.business.constant.ProductConstants;
import com.dodopal.product.business.model.MerKeyType;
import com.dodopal.product.business.model.PrdProductYkt;
import com.dodopal.product.business.model.ProductOrder;
import com.dodopal.product.business.model.ProductYKT;
import com.dodopal.product.business.service.AccountQueryService;
import com.dodopal.product.business.service.CityFindService;
import com.dodopal.product.business.service.LoadOrderService;
import com.dodopal.product.business.service.MerKeyTypeService;
import com.dodopal.product.business.service.MerUserCardBDService;
import com.dodopal.product.business.service.MerchantService;
import com.dodopal.product.business.service.MerchantUserService;
import com.dodopal.product.business.service.PayService;
import com.dodopal.product.business.service.PrdProductYktService;
import com.dodopal.product.business.service.ProductOrderService;
import com.dodopal.product.business.service.ProductYKTService;
import com.dodopal.product.business.util.HttpProtocolHandler;
import com.dodopal.product.business.util.HttpRequest;
import com.dodopal.product.business.util.HttpResponse;
import com.dodopal.product.business.util.HttpResultType;

@Controller
@RequestMapping("/external")
public class ExternalController extends CommonController {
	private final static Logger log = LoggerFactory
			.getLogger(ExternalController.class);
	@Autowired
	MerchantUserService merUserService;
	@Autowired
	ProductOrderService productOrderService;
	@Autowired
	DdicService ddicService;
	@Autowired
	ProductYKTService productYKTService;
	@Autowired
	MerchantService merService;
	@Autowired
	MerKeyTypeService merKeyTypeService;
	
	@Autowired
    PrdProductYktService prdProductYktService;
    @Autowired
    AreaService areaService;
    @Autowired
    LoadOrderService loadOrderService;
    @Autowired 
    AccountQueryService accountQueryService;
    @Autowired
    PayService payService;
    @Autowired
    MerUserCardBDService bdService;
    @Autowired
    CityFindService cityFindService;

	/**
	 * @author Dingkuiyuan@dodopal.com
	 * @date 创建时间：2015年11月19日 下午3:17:34
	 * @version 1.0
	 * @parameter
	 * @since 外接商户打开dudupal产品页面
	 * @return
	 */
	@RequestMapping("/toCardRecharge")
	public ModelAndView toCardRecharge(
			HttpServletRequest request,
			@RequestParam(value = "input_charset", required = false) String input_charset,
			@RequestParam(value = "sign_type", required = false) String sign_type,
			@RequestParam(value = "sign", required = false) String sign,
			@RequestParam(value = "mercode", required = false) String mercode,
			@RequestParam(value = "businesscode", required = false) String businesscode,
			@RequestParam(value = "trandate", required = false) String trandate,
			@RequestParam(value = "operationcode", required = false) String operationcode) {
		Map<String, String> map = new HashMap<String, String>();
		ModelAndView mv = new ModelAndView();
		Map<String, String> checkParm = new HashMap<String, String>();
		checkParm.put("input_charset", input_charset);
		checkParm.put("sign_type", sign_type);
		checkParm.put("sign", sign);

		checkParm.put("mercode", mercode);
		checkParm.put("businesscode", businesscode);
		checkParm.put("trandate", trandate);
		checkParm.put("operationcode", operationcode);
		StringBuffer msg = checkParamRequired(checkParm);
		if (StringUtils.isNotBlank(msg.toString())) {
			request.getSession().setAttribute("externalMessage",
					"参数不合法" + msg.toString());
			mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
			return mv;
		}
        
		// merService.validateMerchantForIcdcRecharge(merchantNum, userId,
		// posId, providerCode, source)
		// 外接
		String soruce = SourceEnum.MERMACHINE.getCode();
		map.put("input_charset", input_charset);
		map.put("mercode", mercode);
		map.put("businesscode", businesscode);
		map.put("trandate", trandate);
		map.put("operationcode", operationcode);
		DodopalResponse<MerKeyType> md5Result = merKeyTypeService.findMerMD5PayPWDOrBackPayPWD(mercode, MerKeyTypeMD5PwdEnum.MerMD5PayPwd);
		//取商户的验签秘钥
		if(!md5Result.isSuccessCode()){
		    request.getSession().setAttribute("externalMessage", "验签不通过！");
            mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
            return mv;
		}else if(null==md5Result.getResponseEntity()){
		    request.getSession().setAttribute("externalMessage", "验签不通过！");
            mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
            return mv;
		}
		String mySign = SignUtils.sign(SignUtils.createLinkString(map),
		    md5Result.getResponseEntity().getMerMD5PayPwd(), input_charset);
		if (!mySign.equals(sign)) {
			// TODO 验签不通过
			request.getSession().setAttribute("externalMessage", "验签不通过！");
			mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
			return mv;
		}
		request.getSession().setAttribute(ProductConstants.CARD_RECHARGE_CITY,
				"");
		try {
			DodopalResponse<MerchantBean> merResponse = merService
					.findMerchantByCode(mercode);
			if (!ResponseCode.SUCCESS.equals(merResponse.getCode())
					|| null == merResponse.getResponseEntity()) {
				request.getSession().setAttribute("externalMessage",
						merResponse.getResponseEntity());

				mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
			}
			MerchantUserBean userBean = new MerchantUserBean();
			MerchantUserDTO userDTO = merResponse.getResponseEntity()
					.getMerchantUserDTO();
			PropertyUtils.copyProperties(userBean, userDTO);
			System.out.println("session 保存操作");
			/**
			 * 外接商户的操作人id
			 */
			request.getSession().setAttribute(
					ProductConstants.EXTERNAL_OPERATORID, operationcode);
			request.getSession().setAttribute(
					ProductConstants.EXTERNAL_CARD_RECHARGE_USERCODE,
					userBean.getUserCode());
			request.getSession().setAttribute(
					ProductConstants.EXTERNAL_CARD_RECHARGE_USERNAME,
					userBean.getMerUserName());
			request.getSession().setAttribute(ProductConstants.EXTERNAL_CARD_LOGIN_USER,
					userBean);
			if (SourceEnum.MERMACHINE.getCode().equals(soruce)) {
				mv.setViewName("applicationCenter/cardRecharge/cardRechargeWithExternal");
				return mv;
			}
			request.getSession().setAttribute("externalMessage", "来源不合法！");
			mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
			return mv;
		} catch (DDPException e) {
		    //TODO
            request.getSession().setAttribute("externalMessage", "来源不合法！");
            mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
            return mv;
		} catch (Exception e) {
		    //TODO
			request.getSession().setAttribute("externalMessage", "来源不合法！");
			mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
			return mv;
		}
	}

	private StringBuffer checkParamRequired(Map<String, String> checkParm) {
		StringBuffer msg = new StringBuffer();
		for (Entry<String, String> entry : checkParm.entrySet()) {
			if (StringUtils.isBlank(entry.getValue())) {
				msg.append(entry.getKey()).append("为null").append("<br/>");
			}
		}
		return msg;
	}

	/**
	 * @author Dingkuiyuan@dodopal.com
	 * @date 创建时间：2015年11月19日 下午3:17:02
	 * @version 1.0
	 * @parameter
	 * @since 外接商户调用dudupal进行充值页面跳转
	 * @return
	 */
	@RequestMapping("/toCallRechargeResult")
	public ModelAndView toCallRechargeResult(
			Model model,
			HttpServletRequest request,
			@RequestParam(value = "input_charset", required = false) String _input_charset,
			@RequestParam(value = "sign_type", required = false) String sign_type,
			@RequestParam(value = "sign", required = false) String sign,
			@RequestParam(value = "trandate", required = false) String trandate,
			@RequestParam(value = "ordernum", required = false) String ordernum,
			@RequestParam(value = "extordernum", required = false) String extordernum) {
		ModelAndView mv = new ModelAndView();
		try{
    		Map<String, String> checkParm = new HashMap<String, String>();
    		checkParm.put("input_charset", _input_charset);
    		checkParm.put("sign_type", sign_type);
    		checkParm.put("sign", sign);
    		checkParm.put("extordernum", extordernum);
    		checkParm.put("trandate", trandate);
    		checkParm.put("ordernum", ordernum);
    		StringBuffer msg = checkParamRequired(checkParm);
    		if (StringUtils.isNotBlank(msg.toString())) {
    
    			request.getSession().setAttribute("externalMessage",
    					"参数不合法" + msg.toString());
    			mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
    			return mv;
    		}
    		
    		Map<String, String> map = new HashMap<String, String>();
    
    		map.put("trandate", trandate);
    		map.put("ordernum", ordernum);
    		map.put("extordernum", extordernum);
    		map.put("input_charset", _input_charset);
    
    		//
    		DodopalResponse<ProductOrder> orderPro = productOrderService
    				.getProductOrderByProOrderNum(ordernum);
    		if (ResponseCode.SUCCESS.equals(orderPro.getCode())) {
    		    DodopalResponse<MerKeyType> md5Result = merKeyTypeService.findMerMD5PayPWDOrBackPayPWD(orderPro.getResponseEntity().getMerCode(), MerKeyTypeMD5PwdEnum.MerMD5PayPwd);
    	        
    	        String mySign = SignUtils.sign(SignUtils.createLinkString(map),
    	            md5Result.getResponseEntity().getMerMD5PayPwd(), _input_charset);
    	        if (!mySign.equals(sign)) {
    	            //  验签不通过
    	            mv.setViewName("notFound");
    	            return mv;
    	        }
    			if (null != orderPro.getResponseEntity()) {
    				String cityCode = orderPro.getResponseEntity().getCityCode();
    				if (!PayTypeEnum.PAY_ACT.getCode().equals(
    						orderPro.getResponseEntity().getPayType().toString())) {
    					productOrderService.updateOrderStatusWhenAccountRecharge(
    							ordernum, ResponseCode.SUCCESS);
    				}
    				ProductYKT yktBean = productYKTService
    						.getYktInfoByBusinessCityCode(cityCode);
    				if (null != yktBean) {
    					model.addAttribute("ocxVersion", ddicService
    							.getDdicNameByCodeFormDB(yktBean.getYktCode(), cityCode)
    							.toString());
    					model.addAttribute(
    							"ocxClassId",
    							ddicService.getDdicNameByCodeFormDB(yktBean.getYktCode(),
    									CommonConstants.OCX_CLASS_ID).toString());
    					model.addAttribute("yktCode", yktBean.getYktCode());
    				}
    			    DodopalResponse<MerRateSupplementBean> urlRes = merService
                        .findMerRateSupplementByMerCode(orderPro.getResponseEntity().getMerCode(),
                                RateCodeEnum.YKT_RECHARGE.getCode());
    			    if(urlRes.isSuccessCode()){
    			        if(null!=urlRes.getResponseEntity()){
    			            model.addAttribute("isShowErrorMsg", IsShowErrorMsgEnum.IS_SHOW_ERROR_MSG.getCode().equals(urlRes.getResponseEntity().getIsShowErrorMsg()));
    			        }else{
    			            model.addAttribute("isShowErrorMsg", IsShowErrorMsgEnum.IS_SHOW_ERROR_MSG.getCode());
    			        }
    			    }
    				model.addAttribute("merordernum", extordernum);
    				model.addAttribute("userCode", orderPro.getResponseEntity()
    						.getUserCode());
    				model.addAttribute("orderNum", ordernum);
    				model.addAttribute("befbal", orderPro.getResponseEntity()
    						.getBefbal());
    				// 充值后金额
    				model.addAttribute("blackAmt", orderPro.getResponseEntity()
    						.getBlackAmt());
    				// 实付（收）金额
    				model.addAttribute("receivedPrice", orderPro
    						.getResponseEntity().getReceivedPrice());
    				// 金额
    				model.addAttribute("txnAmt", orderPro.getResponseEntity()
    						.getTxnAmt());
    			}else{
    			    request.getSession().setAttribute("externalMessage", "订单不存在");
    	            mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
    	            return mv;
    			}
    		}
    		return new ModelAndView(
    				"applicationCenter/cardRecharge/cardRechargeWithExternalResultBack");
		}catch(Exception e){
		    request.getSession().setAttribute("externalMessage","操作出错");
		    mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
            return mv;
		}
	}

	/**
	 * @author Dingkuiyuan@dodopal.com
	 * @date 创建时间：2015年11月19日 下午3:16:43
	 * @version 1.0
	 * @parameter
	 * @since 验卡回调外接商户
	 * @return
	 */
	@RequestMapping("/toCardRechargeRUL")
	public String toCardRechargeRUL(
			HttpServletRequest request,
			RedirectAttributes attr,
			@RequestParam(value = "orderNum", required = false) String orderNum,
			@RequestParam(value = "code", required = true) String code) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank((String) request.getSession().getAttribute(
				ProductConstants.EXTERNAL_CARD_RECHARGE_USERCODE))) {
			attr.addAttribute("msg", "登录失效");
			return "redirect:errorMsgPage";
		}
		MerchantUserBean userBean = merUserService.findUserInfoByUserCode(
				(String) request.getSession().getAttribute(
						ProductConstants.EXTERNAL_CARD_RECHARGE_USERCODE))
				.getResponseEntity();
		DodopalResponse<MerRateSupplementBean> urlRes = merService
				.findMerRateSupplementByMerCode(userBean.getMerCode(),
						RateCodeEnum.YKT_RECHARGE.getCode());
		String url = null;
		if (ResponseCode.SUCCESS.equals(urlRes.getCode())) {
			// 服务器回调通知外接商户 （验卡）的链接是否为空
			if (null != urlRes.getResponseEntity()
					&& null != urlRes.getResponseEntity()
							.getPageCheckCallbackUrl()) {
				// pageCheckCallbackUrl
				url = urlRes.getResponseEntity().getPageCheckCallbackUrl();
			} else {	
				attr.addAttribute("msg", "商户未配置验卡返回链接！");
				return "redirect:errorMsgPage";
			}
		} else {
			log.info("订单号：" + orderNum + "查询外接商户的回调配置返回的错误信息为："
					+ urlRes.getMessage());
			attr.addAttribute("msg", "错误码:"+urlRes.getCode());
			return "redirect:errorMsgPage";
		}
		DodopalResponse<MerKeyType> md5Result = merKeyTypeService.findMerMD5PayPWDOrBackPayPWD(userBean.getMerCode(), MerKeyTypeMD5PwdEnum.MerMD5BackPayPWD);

		// 订单号为空，则为验卡之前阶段
		if (StringUtils.isBlank(orderNum)) {
			attr.addAttribute("input_charset", CharEncoding.UTF_8);
			attr.addAttribute("sign_type", "MD5");
			

			attr.addAttribute("sign", SignUtils.sign(
					SignUtils.createLinkString(map), md5Result.getResponseEntity().getMerMD5BackPayPWD(),
					CharEncoding.UTF_8));
			attr.addAttribute("rescode", code);
			log.info("发送参数为：" + attr.toString());
			return "redirect:" + url;
		} else {

			// if(ResponseCode.SUCCESS.equa-ls(urlRes.getCode())){
			// if(null != urlRes.getResponseEntity()){
			// String url = urlRes.getResponseEntity().getPageCallbackUrl();
			DodopalResponse<ProductOrder> orderPro = productOrderService
					.getProductOrderByProOrderNum(orderNum);
			if (ResponseCode.SUCCESS.equals(orderPro.getCode())) {
				ProductOrder productOrder = orderPro.getResponseEntity();
				
				attr.addAttribute("prdordernum", orderNum);
				attr.addAttribute("orderdate", DateUtils.dateToString(
						productOrder.getProOrderDate(),
						DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
				attr.addAttribute("productcode", productOrder.getProCode());
				attr.addAttribute("productname", productOrder.getProName());
				attr.addAttribute("tradecard", productOrder.getOrderCardno());
				attr.addAttribute("posid", productOrder.getPosCode());
				attr.addAttribute("befbal", productOrder.getBefbal());
				attr.addAttribute("tranamt", productOrder.getTxnAmt());

				map.put("input_charset", CharEncoding.UTF_8);
				map.put("prdordernum", orderNum);
				map.put("orderdate", DateUtils.dateToString(
						productOrder.getProOrderDate(),
						DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
				map.put("productcode", productOrder.getProCode());
				map.put("productname", productOrder.getProName());
				map.put("tradecard", productOrder.getOrderCardno());
				map.put("posid", productOrder.getPosCode());
				map.put("befbal", String.valueOf(productOrder.getBefbal()));
				map.put("tranamt", String.valueOf(productOrder.getTxnAmt()));
			} else {
				log.info("订单号：" + orderNum + "查询订单的结果错误信息为："
						+ urlRes.getMessage());
				attr.addAttribute("msg", "订单错误码:"+orderPro.getCode());
				return "redirect:errorMsgPage";
			}
			attr.addAttribute("input_charset", CharEncoding.UTF_8);
			attr.addAttribute("sign_type", "MD5");
			attr.addAttribute("sign", SignUtils.sign(
					SignUtils.createLinkString(map), md5Result.getResponseEntity().getMerMD5BackPayPWD(), CharEncoding.UTF_8));
			attr.addAttribute("rescode", code);
			log.info("发送参数为：" + attr.toString());
			log.info("验卡回调外接商户url:" + url);
			return "redirect:" + url;
		}
	}

	@RequestMapping("/errorMsgPage")
	public ModelAndView errorMsgPage(Model model, HttpServletRequest request,
			@RequestParam(value = "msg", required = false) String msg) {
		ModelAndView mv = new ModelAndView();
		request.getSession().setAttribute("externalMessage", msg.toString());
		mv.setViewName("applicationCenter/cardRecharge/externalReturnMessage");
		return mv;
	}

	/**
	 * @author Dingkuiyuan@dodopal.com
	 * @date 创建时间：2015年11月19日 下午3:15:54
	 * @version 1.0
	 * @parameter
	 * @since 通知充值结果页面回调外接商户
	 * @return
	 */
	@RequestMapping("/toCallRechargeResultPageReturn")
	public String toCallRechargeResultPageReturn(
			HttpServletRequest request,
			RedirectAttributes attr,
			@RequestParam(value = "orderNum", required = false) String orderNum,
			@RequestParam(value = "resCode", required = false) String resCode) {
		Map<String, String> map = new HashMap<String, String>();
		
		DodopalResponse<ProductOrder> orderPro = productOrderService
				.getProductOrderByProOrderNum(orderNum);
		if (ResponseCode.SUCCESS.equals(orderPro.getCode())) {
			ProductOrder productOrder = orderPro.getResponseEntity();
			String cardBefbal = "";
			if(ResponseCode.SUCCESS.equals(resCode)){
				cardBefbal = String.valueOf(productOrder.getBefbal()+productOrder.getTxnAmt());
			}else{
				cardBefbal = String.valueOf(productOrder.getBefbal());
			}
			
			String url = null;
			DodopalResponse<MerRateSupplementBean> urlRes = merService
					.findMerRateSupplementByMerCode(productOrder.getMerCode(),
							RateCodeEnum.YKT_RECHARGE.getCode());
		    DodopalResponse<MerKeyType> md5Result = merKeyTypeService.findMerMD5PayPWDOrBackPayPWD(productOrder.getMerCode(), MerKeyTypeMD5PwdEnum.MerMD5BackPayPWD);

			if (ResponseCode.SUCCESS.equals(orderPro.getCode())) {
				// if(null != urlRes.getResponseEntity()){
				// String url = urlRes.getResponseEntity().getPageCallbackUrl();

				if (ResponseCode.SUCCESS.equals(urlRes.getCode())) {
					// 服务器回调通知外接商户 （验卡）的链接是否为空
					if (null != urlRes.getResponseEntity()
							&& null != urlRes.getResponseEntity()
									.getPageCallbackUrl()) {
						// pageCheckCallbackUrl
						url = urlRes.getResponseEntity().getPageCallbackUrl();
					} else {
						attr.addAttribute("msg", "通知充值结果页面回调链接未配置");
						return "redirect:errorMsgPage";
					}
				} else {
					log.info("订单号：" + orderNum
							+ "查询外接商户的通知充值结果页面回调外接商户url时的错误信息为："
							+ urlRes.getMessage());
					attr.addAttribute("msg", "错误码："+urlRes.getCode());
					return "redirect:errorMsgPage";
				}
				attr.addAttribute("prdordernum", orderNum);
				attr.addAttribute("extordernum", productOrder.getMerOrderNum());
				attr.addAttribute("returndate", DateUtils.dateToString(
						productOrder.getProOrderDate(),
						DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
				attr.addAttribute("productcode", productOrder.getProCode());
				attr.addAttribute("productname", productOrder.getProName());
				attr.addAttribute("tradecard", productOrder.getOrderCardno());
				attr.addAttribute("posid", productOrder.getPosCode());
				
				attr.addAttribute("befbal", cardBefbal);
				attr.addAttribute("tranamt", productOrder.getTxnAmt());

				map.put("prdordernum", orderNum);
				map.put("input_charset", CharEncoding.UTF_8);
				map.put("extordernum", productOrder.getMerOrderNum());
				map.put("returndate", DateUtils.dateToString(
						productOrder.getProOrderDate(),
						DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
				map.put("productcode", productOrder.getProCode());
				map.put("productname", productOrder.getProName());
				map.put("tradecard", productOrder.getOrderCardno());
				map.put("posid", productOrder.getPosCode());
				map.put("befbal", cardBefbal);
				map.put("tranamt", String.valueOf(productOrder.getTxnAmt()));

				attr.addAttribute("input_charset", CharEncoding.UTF_8);
				attr.addAttribute("sign_type", "MD5");
				attr.addAttribute("sign", SignUtils.sign(
						SignUtils.createLinkString(map), md5Result.getResponseEntity().getMerMD5BackPayPWD(),
						CharEncoding.UTF_8));
				attr.addAttribute("rescode", resCode);
				log.info("页面回调" + url);
				return "redirect:" + url;// +url;http://192.168.1.122:8089/thirdly-web/toReturnResult
			} else {
				log.info("toCallRechargeResultPageReturn 订单查询失败"
						+ orderPro.getMessage() + "订单号为:" + orderNum);
			}
		} else {
			attr.addAttribute("msg", "错误码:"+orderPro.getCode());
			return "redirect:errorMsgPage";
		}
		return "";
	}

	 @RequestMapping("/toChangeCity")
	public @ResponseBody ModelAndView toChangeCity(HttpServletRequest request,
			@RequestParam String cityCode, @RequestParam String bnscode) {
		try {
//			DodopalResponse<CardRechargeBean> dodopalResponse = new DodopalResponse<CardRechargeBean>();
			// MerchantUserBean userBean =
			// (MerchantUserBean)request.getSession().getAttribute(ProductConstants.CARD_LOGIN_USER);
			MerchantUserBean userBean = merUserService.findUserInfoByUserCode(
					(String) request.getSession().getAttribute(
							ProductConstants.EXTERNAL_CARD_RECHARGE_USERCODE))
					.getResponseEntity();
			DodopalResponse<Boolean> updateResponse = merUserService
					.updateMerUserBusCity(cityCode, userBean.getId());
			if (log.isInfoEnabled()) {
				log.info("CardRechargeController更新的结果为"
						+ updateResponse.getMessage());
			}
			request.getSession().setAttribute(
					ProductConstants.CARD_RECHARGE_CITY, cityCode);
			return new ModelAndView(
					"applicationCenter/cardRecharge/cardRechargeWithExternal");
		} catch (Exception e) {
			request.getSession().setAttribute("externalMessage", "系统错误");
			return new ModelAndView(
					"applicationCenter/cardRecharge/externalReturnMessage");
		}
	}

	/**
	 * @author Dingkuiyuan@dodopal.com
	 * @date 创建时间：2015年11月19日 下午3:16:20
	 * @version 1.0
	 * @parameter
	 * @since 充值结果服务器端回调外接商户
	 * @return
	 */
	@RequestMapping("/notifyRechargeResult")
	public ModelAndView notifyRechargeResult(HttpServletRequest request,
			@RequestBody Map<String, String> resMap) {
		log.info("回调外接商户开始");
		String orderNum = resMap.get("orderNum");
		String resCode = resMap.get("resCode");
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler
				.getInstance();
		Map<String, String> reqParams = new HashMap<String, String>();
		HttpRequest req = new HttpRequest(HttpResultType.BYTES);
		req.setCharset("UTF-8");// 字符编码
		DodopalResponse<ProductOrder> orderPro = productOrderService
				.getProductOrderByProOrderNum(orderNum);
		if (ResponseCode.SUCCESS.equals(orderPro.getCode())) {
			DodopalResponse<MerRateSupplementBean> urlRes = merService
					.findMerRateSupplementByMerCode(orderPro
							.getResponseEntity().getMerCode(),
							RateCodeEnum.YKT_RECHARGE.getCode());
			String url = null;
			if (ResponseCode.SUCCESS.equals(urlRes.getCode())) {
				// 服务器回调通知外接商户 （验卡）的链接是否为空
				if (null != urlRes.getResponseEntity()
						&& null != urlRes.getResponseEntity()
								.getPageCheckCallbackUrl()) {
					// pageCheckCallbackUrl
					url = urlRes.getResponseEntity().getServiceNoticeUrl();
				} else {
					return null;
				}
			} else {
				log.info("订单号：" + orderNum + "查询充值结果服务器端回调外接商户url返回的错误信息为："
						+ urlRes.getMessage());
				return null;
			}
			req.setUrl(url);// url

			ProductOrder productOrder = orderPro.getResponseEntity();
			DodopalResponse<MerKeyType> md5Result = merKeyTypeService.findMerMD5PayPWDOrBackPayPWD(productOrder.getMerCode(), MerKeyTypeMD5PwdEnum.MerMD5BackPayPWD);

			Map<String, String> map = new HashMap<String, String>();
			map.put("prdordernum", orderNum);
			map.put("input_charset", CharEncoding.UTF_8);
			map.put("extordernum", productOrder.getMerOrderNum());
			map.put("returndate", DateUtils.dateToString(
					productOrder.getProOrderDate(),
					DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
			map.put("productcode", productOrder.getProCode());
			map.put("productname", productOrder.getProName());
			map.put("tradecard", productOrder.getOrderCardno());
			map.put("posid", productOrder.getPosCode());
			map.put("befbal", String.valueOf(productOrder.getShowBlackAmt()));
			map.put("tranamt", String.valueOf(productOrder.getTxnAmt()));

			map.put("input_charset", CharEncoding.UTF_8);
			map.put("sign_type", "MD5");
			map.put("sign", SignUtils.sign(SignUtils.createLinkString(map),
			    md5Result.getResponseEntity().getMerMD5BackPayPWD(), CharEncoding.UTF_8));

			map.put("rescode", resCode);// RechargeOrderStatesEnum.RECHARGE_SUCCESS
			
			reqParams = map;
		}
		
		req.setParameters(generatNameValuePair(reqParams));
		HttpResponse res = httpProtocolHandler.execute(req);
		log.info("通知商户结束url:" + req.getUrl());
		try {
			String strResult = res.getStringResult();
			log.info("通知商户结束响应码:" + strResult);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	private static NameValuePair[] generatNameValuePair(
			Map<String, String> properties) {
		NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			nameValuePair[(i++)] = new NameValuePair(entry.getKey(),
					entry.getValue());
		}
		return nameValuePair;
	}
	
	@RequestMapping("/getCardRechargeInfo")
    public @ResponseBody DodopalResponse<CardRechargeBean> getCardRechargeInfo(HttpServletRequest request,@RequestBody String cityCode) {
        log.info("ExternalController 进入卡充值时获取页面基本信息，产品 城市等");
        
        DodopalResponse<CardRechargeBean> dodopalResponse = new DodopalResponse<CardRechargeBean>();
        try{
            CardRechargeBean cardRechargeBean =  new CardRechargeBean();
                
            if(StringUtils.isBlank((String)request.getSession().getAttribute(ProductConstants.EXTERNAL_CARD_RECHARGE_USERCODE))){
                dodopalResponse.setCode(ResponseCode.LOGIN_TIME_OUT);
                return dodopalResponse;
                
            }
            DodopalResponse<MerchantUserBean> userResponse = merUserService.findUserInfoByUserCode((String)request.getSession().getAttribute(ProductConstants.EXTERNAL_CARD_RECHARGE_USERCODE));
            
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
//                  Map <String,List<Area>> areaLista = areaService.createMapForFirstWord();
//                  cardRechargeBean.setAllArea(areaLista);
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
                     /**
                      * 外接商户查询 外接回调页面及错误提示信息是否展示字段
                      */
                     DodopalResponse<MerRateSupplementBean> urlRes = merService
                            .findMerRateSupplementByMerCode(userBean.getMerCode(),
                                    RateCodeEnum.YKT_RECHARGE.getCode());
                     if(urlRes.isSuccessCode()){
                         if(null!=urlRes.getResponseEntity()){
                             cardRechargeBean.setIsShowErrorMsg(urlRes.getResponseEntity().getIsShowErrorMsg());
                         }else{
                             cardRechargeBean.setIsShowErrorMsg(IsShowErrorMsgEnum.IS_SHOW_ERROR_MSG.getCode());
                         }
                     }
                     
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
}
