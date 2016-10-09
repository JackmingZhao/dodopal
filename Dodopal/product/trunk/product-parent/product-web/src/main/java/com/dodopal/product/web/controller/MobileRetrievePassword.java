package com.dodopal.product.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.product.business.service.MerchantUserService;
import com.dodopal.product.web.param.BaseResponse;
import com.dodopal.product.web.param.MobileRetrieveRequest;
import com.dodopal.product.web.param.MobileRetrieveResponse;
import com.dodopal.product.web.param.MobileUnionRetrieveRequest;
import com.dodopal.product.web.param.ProductRequest;

@Controller
@RequestMapping("/retrievePassword")
public class MobileRetrievePassword extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(MobileRetrievePassword.class);
	@Autowired
	MerchantUserService merchantUserService;
	@RequestMapping("/toRetrievePassword")
	public @ResponseBody MobileRetrieveResponse toRetrievePassword(HttpServletRequest request) {
		MobileRetrieveResponse response = new MobileRetrieveResponse();
		try{
			String jsondata = request.getParameter("jsondata");
			MobileRetrieveRequest mobileRetrieveRequest = convertJsonToRequest(jsondata, MobileRetrieveRequest.class);
			baseCheck(mobileRetrieveRequest);
			
//			checkProductListParam(productRequest);
			signCheck("123", mobileRetrieveRequest);
			DodopalResponse<Boolean> dodopalRes = merchantUserService.resetPWDByMobile(mobileRetrieveRequest.getMerusermobile(),mobileRetrieveRequest.getUserpwd());
			response.setRespcode(dodopalRes.getCode());
			sign("123",response);
		}catch(DDPException e){
			response.setRespcode(e.getCode());
		}
		return response;
	}

	@RequestMapping("/unionRetrievePassword")
	public @ResponseBody BaseResponse unionRetrievePassword(HttpServletRequest request) {
		BaseResponse response = new BaseResponse();
		String key = "123";
		try {
			String jsondata = request.getParameter("jsondata");
			MobileUnionRetrieveRequest unionRetrieveRequest = convertJsonToRequest(jsondata, MobileUnionRetrieveRequest.class);
			// 通用参数校验
			baseCheck(unionRetrieveRequest);
			// 验签
			signCheck(key, unionRetrieveRequest);
			// 业务参数校验
			unionRetrieveCheck(unionRetrieveRequest);

			DodopalResponse<Boolean> updateResponse = merchantUserService.updateUserPwdByOldUserId(unionRetrieveRequest.getUserid(), unionRetrieveRequest.getUsertype(), unionRetrieveRequest.getUserpwd());
			response.setRespcode(updateResponse.getCode());

			// 签名
			if (ResponseCode.SUCCESS.equals(response.getRespcode())) {
				sign(key, response);
			}
		} catch(DDPException e){
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	private void unionRetrieveCheck(MobileUnionRetrieveRequest unionRetrieveRequest) {
		String userid = unionRetrieveRequest.getUserid();
		if (StringUtils.isBlank(userid)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_OLD_USER_ID_NULL);
		}

		String usertype = unionRetrieveRequest.getUsertype();
		if (StringUtils.isBlank(usertype)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_OLD_USER_TYPE_NULL);
		}

		String userpwd = unionRetrieveRequest.getUserpwd();
		if (StringUtils.isBlank(userpwd)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_PASSWORD_ERROR);
		}
	}
	
	public static void main(String[] args) {
		ProductRequest productRequest = new ProductRequest();
		Map map = new HashMap();
		//map.put("respcode", "000000");
		map.put("userpwd", "af26610db5e49dd014584b03cd4d3599");
		map.put("merusermobile", "15037617232");
		map.put("reqdate", DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
		map.put("input_charset", "UTF-8");
		map.put("sign", SignUtils.sign(SignUtils.createLinkString(map), "123", "UTF-8"));
		map.put("sign_type", "MD5");
		System.out.println(JSONObject.fromObject(map));
		//9762582ccc0ba5835a51747a3500d553
		//{"sign":"56b7de1e821a52c23e95d710c2653c46","citycode":"1791","sign_type":"MD5","input_charset":"UTF-8","reqdate":"20150929095604","proversion":"1123"}
		//System.out.println(SignUtils.sign(SignUtils.createLinkString(map), "123", "UTF-8"));
	}
	
}
