package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.product.business.bean.MerchantBean;
import com.dodopal.product.business.bean.MerchantUserBean;
import com.dodopal.product.business.bean.PayWayResultBean;
import com.dodopal.product.business.service.AccountPaywayService;
import com.dodopal.product.business.service.MerchantService;
import com.dodopal.product.business.service.MerchantUserService;
import com.dodopal.product.web.param.CityFindRequest;
import com.dodopal.product.web.param.PayWayResponse;


@Controller
@RequestMapping("/payWayFind")
public class PayWayController extends BaseController{
	private final static Logger logger = LoggerFactory.getLogger(PayWayController.class);
	
	@Autowired
	private AccountPaywayService accountPaywayService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private MerchantUserService merchantUserService;
	
	@RequestMapping("/findCommonPayWay")
	public @ResponseBody PayWayResponse findCommonPayWay(HttpServletRequest request){
		PayWayResponse response = new PayWayResponse();
		// TODO:签名验签秘钥如何获取
		String key = "123";
		try {
			// 获取jsondata
            String jsondata = request.getParameter("jsondata");
            if (StringUtils.isBlank(jsondata)) {
                if (logger.isInfoEnabled()) {
                    logger.info("findCommonPayWay:支付方式查询接口:接收到的jsondata参数为空");
                }
                response.setRespcode(ResponseCode.JSON_ERROR);
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("findCommonPayWay:支付方式查询接口:接收到的jsondata参数:" + jsondata);
            }
            // 转换jsondata
            CityFindRequest sendRequest = convertJsonToRequest(jsondata, CityFindRequest.class);
            // 通用参数校验
            baseCheck(sendRequest);
            // 验签
            signCheck(key, sendRequest);
            // 业务参数校验
            queryCheck(sendRequest);
            
            // 业务逻辑
            boolean ext = false;
    		String userCode = sendRequest.getCustcode();
    		DodopalResponse<List<PayWayResultBean>> dodopalResponse = new DodopalResponse<List<PayWayResultBean>>();
    		DodopalResponse<MerchantBean> merchantByCode = new DodopalResponse<MerchantBean>();
    		//商户类型
            String merType = sendRequest.getCusttype();
			if (MerUserTypeEnum.PERSONAL.getCode().equals(merType)) {
				DodopalResponse<MerchantUserBean> merUserRes = merchantUserService.findUserInfoByUserCode(userCode);
				if(!ResponseCode.SUCCESS.equals(merUserRes.getCode())) {
					response.setRespcode(merUserRes.getCode());
					return response;
				}

				if (merUserRes.getResponseEntity() == null) {
					response.setRespcode(ResponseCode.USERS_FIND_USER_ERR);
					return response;
				}
				
				ext = false;
				dodopalResponse = accountPaywayService.findCommonPayWay(ext, userCode);
				response.setRespcode(dodopalResponse.getCode());
				if(!ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
					return response;
				}

				List<PayWayResultBean> resultList = dodopalResponse.getResponseEntity();
        		// TODO:只取账户支付和支付宝,临时代码
        		List<PayWayResultBean> finalList = new ArrayList<PayWayResultBean>();
        		if(CollectionUtils.isNotEmpty(resultList)){
					for (PayWayResultBean temp : resultList) {
						if (PayTypeEnum.PAY_ACT.getCode().equals(temp.getPaytype())) {
							finalList.add(temp);
						} else if (PayTypeEnum.PAY_ONLINE.getCode().equals(temp.getPaytype()) && "支付宝".equals(temp.getPaywayname())) {
							finalList.add(temp);
						}
					}
        			response.setPayWayList(finalList);
        		}
			} else{
            	merchantByCode = merchantService.findMerchantByCode(userCode);
            	
				if (!ResponseCode.SUCCESS.equals(merchantByCode.getCode())) {
					response.setRespcode(merchantByCode.getCode());
					return response;
				}

            	//是否是外接商户(true 是| false 否)
            	String userType = merchantByCode.getResponseEntity().getMerType();
            	if(MerTypeEnum.EXTERNAL.getCode().equals(userType)){
            		ext=true;
            	}else{
            		ext=false;
            	}
            	dodopalResponse = accountPaywayService.findCommonPayWay(ext, userCode);
            	if(ResponseCode.SUCCESS.equals(dodopalResponse.getCode())){
            		List<PayWayResultBean> resultList = dodopalResponse.getResponseEntity();
            		if(CollectionUtils.isNotEmpty(resultList)){
            			response.setPayWayList(resultList);
            		}
            		response.setRespcode(dodopalResponse.getCode());
            	}else{
            		response.setRespcode(dodopalResponse.getCode());
            	}
            }
            // 签名
            if (ResponseCode.SUCCESS.equals(response.getRespcode())) {
                sign(key, response);
            }
		}catch (DDPException e) {
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		if (logger.isInfoEnabled()) {
            logger.info("findCommonPayWay:支付方式查询接口:返回responsecode:" + response.getRespcode());
        }
		return response;
	}
	
	
	private void queryCheck(CityFindRequest sendRequest) {
        
		// 客户类型    String[1]   不为空且（个人用户：设值为0 固定；商户用户：设置为 1 固定）
        String custtype = sendRequest.getCusttype();
        if (!MerUserTypeEnum.contains(custtype)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTTYPE_ERROR);
        }
        // 客户号    String[40]   不为空(个人用户：用户号usercode；商户用户：商户号 mercode)
        String custcode = sendRequest.getCustcode();
        if (!DDPStringUtil.existingWithLength(custcode, 40)) {
            throw new DDPException(ResponseCode.PRODUCT_MOBILE_CUSTCODE_NULL);
        }
    }
}
