package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.dodopal.product.business.model.PrdProductYkt;
import com.dodopal.product.business.service.PrdProductYktService;
import com.dodopal.product.business.service.ProductYKTService;
import com.dodopal.product.web.mobileBean.PrdProductYktMobile;
import com.dodopal.product.web.param.ProductListResponse;
import com.dodopal.product.web.param.ProductRequest;

@Controller
@RequestMapping("/productOrder")
public class MobileProductController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(MobileProductController.class);
	@Autowired
	PrdProductYktService prdProductYktService;
	@Autowired
	ProductYKTService productYKTService;
	
	@RequestMapping("/getProductList")
	public @ResponseBody ProductListResponse<PrdProductYktMobile> getProductList(HttpServletRequest request) {
		ProductListResponse<PrdProductYktMobile> response = new ProductListResponse<PrdProductYktMobile>();
		try{
			String jsondata = request.getParameter("jsondata");
			ProductRequest productRequest = convertJsonToRequest(jsondata, ProductRequest.class);
			baseCheck(productRequest);
			checkProductListParam(productRequest);
			signCheck("123", productRequest);
			List<PrdProductYktMobile> mobileList = new ArrayList<PrdProductYktMobile>();
			response.setRespcode(ResponseCode.SUCCESS);
			String versionId= productYKTService.getProversionByCityCode(productRequest.getCitycode());
			if(StringUtils.isNotBlank(versionId)&&!versionId.equals(productRequest.getProversion())){
				List<PrdProductYkt> yktList = prdProductYktService.findAvailableIcdcProductsInCity(productRequest.getCitycode());
				mobileList = changeMobileBean(yktList,productRequest.getCitycode());
			}
			response.setProversion(versionId);
//			sign("123",response);
			response.setList(mobileList);
		}catch(DDPException e){
			response.setRespcode(e.getCode());
		}
		return response;
	}
//	public static void main(String[] args) {
//		ProductRequest productRequest = new ProductRequest();
//		Map map = new HashMap();
//		//map.put("respcode", "000000");
//		map.put("proversion", "1123");
//		map.put("citycode", "1791");
//		map.put("reqdate", DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_YYMMDDHHMMSS_STR));
//		map.put("input_charset", "UTF-8");
//		map.put("sign", SignUtils.sign(SignUtils.createLinkString(map), "123", "UTF-8"));
//		map.put("sign_type", "MD5");
//		System.out.println(JSONObject.fromObject(map));
//		//9762582ccc0ba5835a51747a3500d553
//		//{"sign":"56b7de1e821a52c23e95d710c2653c46","citycode":"1791","sign_type":"MD5","input_charset":"UTF-8","reqdate":"20150929095604","proversion":"1123"}
//		//System.out.println(SignUtils.sign(SignUtils.createLinkString(map), "123", "UTF-8"));
//	}
	
	private void checkProductListParam(ProductRequest productRequest){
		String city = productRequest.getCitycode();
		if (StringUtils.isBlank(city)) {
			throw new DDPException(ResponseCode.PRODUCT_MOBILE_CITY_CODE_NULL);
		}
		String proversion = productRequest.getProversion();
		if (StringUtils.isBlank(proversion)) {
			throw new DDPException(ResponseCode.PRODUCT_MOBILE_PROVERSION_CODE_NULL);
		}
	}
	
	
	private List<PrdProductYktMobile> changeMobileBean(List<PrdProductYkt> yktList,String cityId){
		List<PrdProductYktMobile> mobileList = new ArrayList<PrdProductYktMobile>();
		for(PrdProductYkt pykt : yktList){
			PrdProductYktMobile mobile = new PrdProductYktMobile();
			mobile.setCitycode(cityId);
			mobile.setProcode(pykt.getProCode());
			mobile.setProprice(pykt.getProPrice());
			mobile.setProname(pykt.getProName());	
			mobileList.add(mobile);
		}
		return mobileList;
	}	
	
	
}
