package com.dodopal.product.web.controller;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.md5.SignUtils;
import com.dodopal.common.model.Area;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.service.AreaService;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.DodopalAppVarPropsUtil;
import com.dodopal.product.business.bean.CommonUser;
import com.dodopal.product.business.service.RegisterService;
import com.dodopal.product.business.service.SendService;
import com.dodopal.product.web.param.BaseResponse;
import com.dodopal.product.web.param.CheckPhoneNumRequest;
import com.dodopal.product.web.param.CheckUserNameRequest;
import com.dodopal.product.web.param.MobileCodeCheckRequest;
import com.dodopal.product.web.param.OrderFlagResponse;
import com.dodopal.product.web.param.RegisterComUserResponse;
import com.dodopal.product.web.param.RegisterMerchantRequest;
import com.dodopal.product.web.param.RegisterUserRequest;
import com.dodopal.product.web.param.SendRequest;
import com.dodopal.product.web.param.SendResponse;
import com.dodopal.product.web.util.ProductValidateUtils;

/**
 * @author lifeng@dodopal.com
 */

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(RegisterController.class);
	private final static String PARAM_PWDSEQ = "pwdseq";
	@Autowired
	private SendService sendService;
	@Autowired
	private RegisterService registerService;
	@Autowired
	private AreaService areaService;

	/**
	 * 注册发短信接口(手机端)
	 */
	@RequestMapping("/send")
	public @ResponseBody SendResponse send(HttpServletRequest request) {
		SendResponse response = new SendResponse();
		// TODO:签名验签秘钥如何获取
		String key = "123";
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if(StringUtils.isBlank(jsondata)) {
				if(logger.isInfoEnabled()) {
					logger.info("注册发短信接口:接收到的jsondata参数为空");
				}
				response.setRespcode(ResponseCode.JSON_ERROR);
				return response;
			}
			if(logger.isInfoEnabled()) {
				logger.info("注册发短信接口:接收到的jsondata参数:"+jsondata);
			}
			// 转换jsondata
			SendRequest sendRequest = convertJsonToRequest(jsondata, SendRequest.class);
			// 通用参数校验
			baseCheck(sendRequest);
			// 验签
			signCheck(key, sendRequest);
			// 业务参数校验
			sendCheck(sendRequest);
			// 业务逻辑
			DodopalResponse<Map<String, String>> sendResponse = sendService.send(sendRequest.getMerusermobile(), MoblieCodeTypeEnum.USER_RG);
			if (ResponseCode.SUCCESS.equals(sendResponse.getCode())) {
				// 序号
				String pwdseq = sendResponse.getResponseEntity().get(PARAM_PWDSEQ);
				response.setPwdseq(pwdseq);
			}
			// 响应码
			response.setRespcode(sendResponse.getCode());
			// 签名
			if(ResponseCode.SUCCESS.equals(response.getRespcode())) {
				sign(key, response);
			}
		} catch (DDPException e) {
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		if(logger.isInfoEnabled()) {
			logger.info("注册发短信接口:返回respcode:" + response.getRespcode());
		}
		return response;
	}

	private void sendCheck(SendRequest request) {
		// 手机号
		String merUserMobile = request.getMerusermobile();
		ProductValidateUtils.validateMobile(merUserMobile);
		// 验证码类型
		String codeType = request.getCodetype();
		if (!"1".equals(codeType)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_CODE_TYPE_ERROR);
		}
	}

	/**
	 * 手机验证码验证（手机端）
	 * @param request
	 * @return
	 */
	@RequestMapping("/mobileCodeCheck")
	public @ResponseBody BaseResponse mobileCodeCheck(HttpServletRequest request) {
		BaseResponse response = new BaseResponse();
		// TODO:签名验签秘钥如何获取
		String key = "123";
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if(StringUtils.isBlank(jsondata)) {
				if(logger.isInfoEnabled()) {
					logger.info("手机验证码验证接口:接收到的jsondata参数为空");
				}
				response.setRespcode(ResponseCode.JSON_ERROR);
				return response;
			}
			if(logger.isInfoEnabled()) {
				logger.info("手机验证码验证接口:接收到的jsondata参数:"+jsondata);
			}
			// 转换jsondata
			MobileCodeCheckRequest mobileCodeCheckRequest = convertJsonToRequest(jsondata, MobileCodeCheckRequest.class);
			// 通用参数校验
			baseCheck(mobileCodeCheckRequest);
			// 验签
			signCheck(key, mobileCodeCheckRequest);
			// 业务参数校验
			mobileCodeCheckCheck(mobileCodeCheckRequest);
			// 业务逻辑
			DodopalResponse<String>  checkResponse = sendService.mobileCodeCheck(mobileCodeCheckRequest.getMerusermobile(), mobileCodeCheckRequest.getDypwd(), mobileCodeCheckRequest.getPwdseq());
			// 响应码
			response.setRespcode(checkResponse.getCode());
			// 签名
			if(ResponseCode.SUCCESS.equals(response.getRespcode())) {
				sign(key, response);
			}
		} catch (DDPException e) {
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		if(logger.isInfoEnabled()) {
			logger.info("手机验证码验证接口:返回respcode:" + response.getRespcode());
		}
		return response;
	}

	private void mobileCodeCheckCheck(MobileCodeCheckRequest mobileCodeCheckRequest) {
		// 手机号
		String merUserMobile = mobileCodeCheckRequest.getMerusermobile();
		ProductValidateUtils.validateMobile(merUserMobile);

		// 验证码
		String dypwd = mobileCodeCheckRequest.getDypwd();
		if (StringUtils.isBlank(dypwd)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_DYPWD_NULL);
		}

		// 序号
		String pwdseq = mobileCodeCheckRequest.getPwdseq();
		if (StringUtils.isBlank(pwdseq)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_PWDSEQ_NULL);
		}
	}

	/**
	 * 用户名重复校验接口（手机端）
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkUserNameExist")
	public @ResponseBody BaseResponse checkUserNameExist(HttpServletRequest request) {
		BaseResponse response = new BaseResponse();
		// TODO:签名验签秘钥如何获取
		String key = "123";
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if(StringUtils.isBlank(jsondata)) {
				if(logger.isInfoEnabled()) {
					logger.info("用户名重复校验接口:接收到的jsondata参数为空");
				}
				response.setRespcode(ResponseCode.JSON_ERROR);
				return response;
			}
			if(logger.isInfoEnabled()) {
				logger.info("用户名重复校验接口:接收到的jsondata参数:"+jsondata);
			}
			// 转换jsondata
			CheckUserNameRequest checkUserNameRequest = convertJsonToRequest(jsondata, CheckUserNameRequest.class);
			// 通用参数校验
			baseCheck(checkUserNameRequest);
			// 验签
			signCheck(key, checkUserNameRequest);
			// 业务参数校验
			checkUserNameExist(checkUserNameRequest);
			// 业务逻辑
			DodopalResponse<Boolean>  checkResponse = registerService.checkUserNameExist(checkUserNameRequest.getUsername());
			// 响应码
			response.setRespcode(checkResponse.getCode());
			// 签名
			if(ResponseCode.SUCCESS.equals(response.getRespcode())) {
				sign(key, response);
			}
		} catch (DDPException e) {
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		if(logger.isInfoEnabled()) {
			logger.info("用户名重复校验接口:返回respcode:" + response.getRespcode());
		}
		return response;
	}

	private void checkUserNameExist(CheckUserNameRequest checkUserNameRequest) {
		// 用户名
		String userName = checkUserNameRequest.getUsername();
		ProductValidateUtils.validateMerUserName(userName);
	}

	/**
	 * 注册个人用户接口（手机端）
	 * @param request
	 * @return
	 */
	@RequestMapping("/registerUser")
	public @ResponseBody BaseResponse registerUser(HttpServletRequest request) {
		BaseResponse response = new BaseResponse();
		// TODO:签名验签秘钥如何获取
		String key = "123";
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if(StringUtils.isBlank(jsondata)) {
				if(logger.isInfoEnabled()) {
					logger.info("注册个人用户接口:接收到的jsondata参数为空");
				}
				response.setRespcode(ResponseCode.JSON_ERROR);
				return response;
			}
			if(logger.isInfoEnabled()) {
				logger.info("注册个人用户接口:接收到的jsondata参数:"+jsondata);
			}
			// 转换jsondata
			RegisterUserRequest registerUserRequest = convertJsonToRequest(jsondata, RegisterUserRequest.class);
			// 通用参数校验
			baseCheck(registerUserRequest);
			// 验签
			signCheck(key, registerUserRequest);
			// 业务参数校验
			registerUserCheck(registerUserRequest);
			// 业务逻辑
			DodopalResponse<Boolean>  checkResponse = registerService.registerUser(registerUserRequest.getMerusermobile(), 
																					registerUserRequest.getUsername(), 
																					registerUserRequest.getUserpwd(),
																					registerUserRequest.getSource(),
																					registerUserRequest.getCitycode());
			// 响应码
			response.setRespcode(checkResponse.getCode());
			// 签名
			if(ResponseCode.SUCCESS.equals(response.getRespcode())) {
				sign(key, response);
			}
		} catch (DDPException e) {
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		if(logger.isInfoEnabled()) {
			logger.info("注册个人用户接口:返回respcode:" + response.getRespcode());
		}
		return response;
	}

	private void registerUserCheck(RegisterUserRequest registerUserRequest) {
		// 来源
		String source = registerUserRequest.getSource();
		if(!SourceEnum.isPhone(source)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_SOURCE_ERROR);
		}

		// 手机号
		String merUserMobile = registerUserRequest.getMerusermobile();
		ProductValidateUtils.validateMobile(merUserMobile);

		// 用户名
		String userName = registerUserRequest.getUsername();
		ProductValidateUtils.validateMerUserName(userName);

		// 密码
		String userPwd = registerUserRequest.getUserpwd();
		ProductValidateUtils.validateUserPwd(userPwd);

		// 城市code
		String cityCode = registerUserRequest.getCitycode();
		Area areaCity = areaService.findCityByCityCode(cityCode);
		if (areaCity == null) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_CITY_NOT_FOUND);
		}
	}

	/**
	 * 手机号验证接口（验证手机号是否已注册）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkPhoneNumExist")
	public @ResponseBody
	BaseResponse checkPhoneNumExist(HttpServletRequest request) {
		BaseResponse response = new BaseResponse();
		// TODO:签名验签秘钥如何获取
		String key = "123";
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if (StringUtils.isBlank(jsondata)) {
				if (logger.isInfoEnabled()) {
					logger.info("手机号验证接口:接收到的jsondata参数为空");
				}
				response.setRespcode(ResponseCode.JSON_ERROR);
				return response;
			}
			if (logger.isInfoEnabled()) {
				logger.info("手机号验证接口:接收到的jsondata参数:" + jsondata);
			}
			// 转换jsondata
			CheckPhoneNumRequest checkPhoneNumRequest = convertJsonToRequest(jsondata, CheckPhoneNumRequest.class);
			// 通用参数校验
			baseCheck(checkPhoneNumRequest);
			// 验签
			signCheck(key, checkPhoneNumRequest);
			// 业务参数校验
			checkPhoneNumCheck(checkPhoneNumRequest);

			DodopalResponse<Boolean> checkMobileResponse = registerService.checkMobileExist(checkPhoneNumRequest.getUsermobile());
			// 响应码
			response.setRespcode(checkMobileResponse.getCode());
			// 签名
			if (ResponseCode.SUCCESS.equals(response.getRespcode())) {
				sign(key, response);
			}
		} catch (DDPException e) {
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		if (logger.isInfoEnabled()) {
			logger.info("手机号验证接口:返回respcode:" + response.getRespcode());
		}
		return response;
	}

	private void checkPhoneNumCheck(CheckPhoneNumRequest checkPhoneNumRequest) {
		// 手机号
		String merUserMobile = checkPhoneNumRequest.getUsermobile();
		ProductValidateUtils.validateMobile(merUserMobile);
	}

	
	/**
	 * 通用注册
	 * @param request
	 * @return
	 */
	@RequestMapping("/registerComUser")
	public @ResponseBody RegisterComUserResponse registerComUser(HttpServletRequest request) {
		RegisterComUserResponse response = new RegisterComUserResponse();
		DodopalResponse<String> resTemp = new DodopalResponse<String>();
		try {
			
			String charset = request.getParameter("charset");
			String reqdate = request.getParameter("reqdate");
			String sign = request.getParameter("sign");
			String mobilTel = request.getParameter("mobilTel");
			String wechatId = request.getParameter("wechatId");
			String cardNo = request.getParameter("cardNo");
			String paramKey = request.getParameter("paramKey");
			String source = request.getParameter("source");
			String thirdKey = request.getParameter("thirdKey");
			String reserved  = request.getParameter("reserved");
			if (logger.isInfoEnabled()) {
				logger.info("注册接收参数:" + "charset= " + charset +",reqdate="+reqdate+",sign="+sign+",mobilTel=" +mobilTel
			+",wechatId="+wechatId+",cardNo="+cardNo+",paramKey="+paramKey
			+",source="+source +",thirdKey="+thirdKey+",reserved="+reserved);
			}
			//参数验证----------------------------------------------------------------------------
			// 编码字符集
			if (DDPStringUtil.isNotPopulated(charset)) {
				throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_INPUT_CHARSET_NULL);
			}

			// 签名
			if (DDPStringUtil.isNotPopulated(sign)) {
				throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_SIGN_NULL);
			}
			// 请求时间
			if (DDPStringUtil.isNotPopulated(reqdate)) {
				throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_REQDATE_ERROR);
			}

			if (reqdate.length() != 14) {
				throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_REQDATE_ERROR);
			}
			// 来源			
			if(!SourceEnum.checkCodeExist(source)) {
				throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_SOURCE_ERROR);
			}
			// 手机号
			ProductValidateUtils.validateMobile(mobilTel);
			//卡号
			if(DDPStringUtil.isNotPopulated(cardNo) || (cardNo.length()>20)){
				throw new DDPException(ResponseCode.PRODUCT_CHECK_CARD_ORDER_CARD_ERROR);
			}
			//活动参数
			if(DDPStringUtil.isNotPopulated(paramKey)){				
				//活动参数不合法
				throw new DDPException(ResponseCode.PRODUCT_PARKEY_ERROR);
			}
			String[] strs = paramKey.split("-");
			if(strs.length!=2){
				//活动参数不合法
				throw new DDPException(ResponseCode.PRODUCT_PARKEY_ERROR);
			}		
			
			// 城市code
			String cityCode = strs[0];
			Area areaCity = areaService.findCityByCityCode(cityCode);
			if (areaCity == null) {
				//活动参数不合法
				throw new DDPException(ResponseCode.PRODUCT_PARKEY_ERROR);
			}
			
			//解密键
			if(DDPStringUtil.isNotPopulated(thirdKey) || !"dodo_zg_double".equals(thirdKey)){
				//解密键不合法 
				throw new DDPException(ResponseCode.PRODUCT_THIRDKEY_ERROR);
			}
			//------------------------------------------------------------------------------------
			//根据解密键取得KEY
			String key = DodopalAppVarPropsUtil.getStringProp(thirdKey);
			if(DDPStringUtil.isNotPopulated(key)){				
				throw new DDPException(ResponseCode.KEY_TYPE_PWD_ID_NULL);
			}
			
			wechatId = request.getParameter("wechatId")==null?"":request.getParameter("wechatId");
			
			//特殊签名、验签
			//MD5(密钥+微信号+手机号+活动参数+请求时间)取后10位
			String text = key + wechatId + mobilTel + paramKey + reqdate;
			String newSign = SignUtils.signCom(text, charset);
			logger.info("都都宝平台签名 sign =" +newSign);
			if(!sign.toUpperCase().equals(newSign.substring(newSign.length()-10, newSign.length()).toUpperCase())){
				//验签失败
				response.setRespcode(ResponseCode.PRODUCT_REQ_SIGN_ERROR);
				resTemp.setCode(ResponseCode.PRODUCT_REQ_SIGN_ERROR);
				response.setErrorreason(resTemp.getMessage());
				return response;
			}
						
			CommonUser user = new CommonUser();
			user.setMerUserMobile(mobilTel);
			user.setCityCode(strs[0]);
			user.setParamKey(strs[1]);
			user.setMerUserSource(source);
			user.setCardNo(cardNo);
			user.setWechatId(wechatId);			
						
			// 业务逻辑
			DodopalResponse<Map<String,String>>  regPesponse = registerService.registerComUser(user);
			// 响应码
			response.setRespcode(regPesponse.getCode());
			response.setErrorreason(regPesponse.getMessage());
			if(ResponseCode.SUCCESS.equals(regPesponse.getCode())){
				String id = regPesponse.getResponseEntity().get("userid");
				response.setUserid(id);
			}			
		} catch (DDPException e) {
			resTemp.setCode(e.getCode());
			response.setRespcode(e.getCode());
			response.setErrorreason(resTemp.getMessage());
			
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			resTemp.setCode(ResponseCode.SYSTEM_ERROR);
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
			response.setErrorreason(resTemp.getMessage());
		}
		if(logger.isInfoEnabled()) {
			logger.info("注册个人用户接口:返回respcode:" + response.getRespcode());
		}
		return response;
	}	

	@RequestMapping("/queryOrderFlag")
	public @ResponseBody OrderFlagResponse queryOrderFlag(HttpServletRequest request) {
		OrderFlagResponse response = new OrderFlagResponse();
		DodopalResponse<String> resTemp = new DodopalResponse<String>();
		try {
			
			String charset = request.getParameter("charset");
			String reqdate = request.getParameter("reqdate");
			String orderRecordDate = request.getParameter("orderRecordDate");
			String sign = request.getParameter("sign");
			String cardNo = request.getParameter("cardNo");
			String thirdKey = request.getParameter("thirdKey");
			if (logger.isInfoEnabled()) {
				logger.info("查询交易接收参数:" + "charset=" + charset +",reqdate="+reqdate+",sign="+sign+",cardNo="+cardNo
						+",orderRecordDate="+orderRecordDate+",thirdKey="+thirdKey);
			}
			//参数验证----------------------------------------------------------------------------
			// 编码字符集
			if (DDPStringUtil.isNotPopulated(charset)) {
				throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_INPUT_CHARSET_NULL);
			}

			// 签名
			if (DDPStringUtil.isNotPopulated(sign)) {
				throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_SIGN_NULL);
			}
			//查询日期
			if (DDPStringUtil.isNotPopulated(orderRecordDate) 
					|| orderRecordDate.length()!=8
					|| !DDPStringUtil.isNumberic(orderRecordDate)) {
				throw new DDPException(ResponseCode.PRODUCT_QUEDATE_ERROR);
			}
		   SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-HH-dd");  
		   SimpleDateFormat formatter2=new SimpleDateFormat("yyyyHHdd");
		   String dateTemp=formatter1.format(formatter2.parse(orderRecordDate));  
		   if (!DateUtils.isDate(dateTemp)) {
			   throw new DDPException(ResponseCode.PRODUCT_QUEDATE_ERROR);
		   }
			   
		
			// 请求时间
			if (DDPStringUtil.isNotPopulated(reqdate)) {
				throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_REQDATE_ERROR);
			}

			if (reqdate.length() != 14) {
				throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_REQDATE_ERROR);
			}
			
			//卡号
			if(DDPStringUtil.isNotPopulated(cardNo) || (cardNo.length()>20)){
				throw new DDPException(ResponseCode.PRODUCT_CHECK_CARD_ORDER_CARD_ERROR);
			}
			
			//解密键
			if(DDPStringUtil.isNotPopulated(thirdKey) || !"dodo_zg_double".equals(thirdKey)){
				//解密键不合法 
				throw new DDPException(ResponseCode.PRODUCT_THIRDKEY_ERROR);
			}
			//------------------------------------------------------------------------------------
			//根据解密键取得KEY
			String key = DodopalAppVarPropsUtil.getStringProp(thirdKey);
			if(DDPStringUtil.isNotPopulated(key)){				
				throw new DDPException(ResponseCode.KEY_TYPE_PWD_ID_NULL);
			}
			
			
			//特殊签名、验签
			//MD5(密钥+微信号+手机号+活动参数+请求时间)取后10位
			String text = key + orderRecordDate + cardNo + reqdate;
			String newSign = SignUtils.signCom(text, charset);
			logger.info("都都宝平台签名 sign =" +newSign);
			if(!sign.toUpperCase().equals(newSign.substring(newSign.length()-10, newSign.length()).toUpperCase())){
				//验签失败
				response.setRespcode(ResponseCode.PRODUCT_REQ_SIGN_ERROR);
				resTemp.setCode(ResponseCode.PRODUCT_REQ_SIGN_ERROR);
				response.setErrorreason(resTemp.getMessage());
				return response;
			}
					
						
			// 业务逻辑
			DodopalResponse<Boolean>  regPesponse = registerService.queryOrderFlag(cardNo,orderRecordDate);
			// 响应码
			response.setRespcode(regPesponse.getCode());
			response.setErrorreason(regPesponse.getMessage());
			if(ResponseCode.SUCCESS.equals(regPesponse.getCode())){
				if(regPesponse.getResponseEntity()){
					//有交易
					response.setFlag("1");
				}else{
					//无交易
					response.setFlag("0");
				}
			}
				
		} catch (DDPException e) {
			resTemp.setCode(e.getCode());
			response.setRespcode(e.getCode());
			response.setErrorreason(resTemp.getMessage());
			
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			resTemp.setCode(ResponseCode.SYSTEM_ERROR);
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
			response.setErrorreason(resTemp.getMessage());
		}
		if(logger.isInfoEnabled()) {
			logger.info("交易标识查询:返回respcode:" + response.getRespcode());
		}
		return response;		
	}
	/**
	 * 注册网点商户
	 * @param request
	 * @return
	 */
	@RequestMapping("/registerMerchantForVC")
	public @ResponseBody BaseResponse registerMerchantForVC(HttpServletRequest request) {
		BaseResponse response = new BaseResponse();
		// TODO:签名验签秘钥如何获取
		String key = "123";
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if(StringUtils.isBlank(jsondata)) {
				if(logger.isInfoEnabled()) {
					logger.info("注册网点接口:接收到的jsondata参数为空");
				}
				response.setRespcode(ResponseCode.JSON_ERROR);
				return response;
			}
			if(logger.isInfoEnabled()) {
				logger.info("注册网点接口:接收到的jsondata参数:"+jsondata);
			}
			// 转换jsondata
			RegisterMerchantRequest registerMerchantRequest = convertJsonToRequest(jsondata, RegisterMerchantRequest.class);
			// 通用参数校验
			baseCheck(registerMerchantRequest);
			// 验签
			signCheck(key, registerMerchantRequest);
			// 业务参数校验
			MerchantDTO merchant = new MerchantDTO();
			registerMerchantForVCCheck(registerMerchantRequest, merchant);
			// 业务逻辑
			DodopalResponse<String> checkResponse = registerService.registerMerchantForVC(merchant);
			// 响应码
			response.setRespcode(checkResponse.getCode());
			// 签名
			if(ResponseCode.SUCCESS.equals(response.getRespcode())) {
				sign("", response);
			}
		} catch (DDPException e) {
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		if(logger.isInfoEnabled()) {
			logger.info("注册个人用户接口:返回respcode:" + response.getRespcode());
		}
		return response;
	}

	private void registerMerchantForVCCheck(RegisterMerchantRequest registerMerchantRequest, MerchantDTO merchant) {
		MerchantUserDTO merUser = new MerchantUserDTO();
		merchant.setMerchantUserDTO(merUser);
		PosDTO pos = new PosDTO();
		merchant.setPosDTO(pos);
		// 来源
		String source = registerMerchantRequest.getSource();
		if (!SourceEnum.checkCodeExist(source)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_SOURCE_ERROR);
		}
		merchant.setSource(source);
		merUser.setMerUserSource(source);

		// 网点名称(商户名称)
		String mername = registerMerchantRequest.getMername();
		if (StringUtils.isBlank(mername)) {
			throw new DDPException(ResponseCode.USERS_MER_NAME_NULL);
		}
		merchant.setMerName(mername);

		// 网点地址
		String meradds = registerMerchantRequest.getMeradds();
		if (StringUtils.isBlank(meradds)) {
			throw new DDPException(ResponseCode.USERS_MER_ADDS_NULL);
		}
		merchant.setMerAdds(meradds);
		merUser.setMerUserAdds(meradds);

		// POS编号
		String posid = registerMerchantRequest.getPosid();
		if (StringUtils.isBlank(posid)) {
			throw new DDPException(ResponseCode.USERS_POS_CODE_NULL);
		}
		pos.setCode(posid);

		// 业务城市
		String cityCode = registerMerchantRequest.getCitycode();
		Area areaCity = areaService.findCityByCityCode(cityCode);
		if (areaCity == null) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_CITY_NOT_FOUND);
		}
		merchant.setMerCity(cityCode);
		merchant.setMerPro(areaCity.getParentCode());
		merUser.setMerUserCity(cityCode);
		merUser.setMerUserPro(areaCity.getParentCode());

		// 注册邮箱
		merchant.setMerEmail(registerMerchantRequest.getEmail());
		merUser.setMerUserEmail(registerMerchantRequest.getEmail());
		// 真实姓名
		merchant.setMerLinkUser(registerMerchantRequest.getNickname());
		merUser.setMerUserNickName(registerMerchantRequest.getNickname());

		// 用户名
		String username = registerMerchantRequest.getUsername();
		ProductValidateUtils.validateMerUserName(username);
		merUser.setMerUserName(username);

		// 性别
		merUser.setMerUserSex(registerMerchantRequest.getSex());

		// 密码
		String password = registerMerchantRequest.getPassword();
		ProductValidateUtils.validateUserPwd(password);
		merUser.setMerUserPWD(password);

		// 证件类型
		merUser.setMerUserIdentityType(registerMerchantRequest.getIdentitytype());
		// 证件号码
		merUser.setMerUserIdentityNumber(registerMerchantRequest.getIdentitynumber());

		// 手机号
		String mobile = registerMerchantRequest.getMobile();
		ProductValidateUtils.validateMobile(mobile);
		merchant.setMerLinkUserMobile(mobile);
		merUser.setMerUserMobile(mobile);
	}
}