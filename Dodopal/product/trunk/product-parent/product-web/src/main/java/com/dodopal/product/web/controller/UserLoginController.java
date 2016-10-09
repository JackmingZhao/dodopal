package com.dodopal.product.web.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerDdpInfoDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.MobileUserDTO;
import com.dodopal.api.users.dto.PosDTO;
import com.dodopal.api.users.dto.PosQueryDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.SettlementTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.TrackIdHolder;
import com.dodopal.product.business.service.MerchantService;
import com.dodopal.product.business.service.MerchantUserService;
import com.dodopal.product.business.service.PosService;
import com.dodopal.product.business.service.UserLoginService;
import com.dodopal.product.web.mobileBean.PosModel;
import com.dodopal.product.web.mobileBean.UserInfoModel;
import com.dodopal.product.web.param.BaseResponse;
import com.dodopal.product.web.param.PosRequest;
import com.dodopal.product.web.param.PosResponse;
import com.dodopal.product.web.param.ProxyInfoRequest;
import com.dodopal.product.web.param.ProxyInfoResponse;
import com.dodopal.product.web.param.ProxyUserInfoRequest;
import com.dodopal.product.web.param.ProxyUserInfoResponse;
import com.dodopal.product.web.param.UserLoginRequest;
import com.dodopal.product.web.param.UserLoginResponse;
import com.dodopal.product.web.util.ProductValidateUtils;

/** 
 * @author lifeng@dodopal.com
 */
@Controller
@RequestMapping("/login")
public class UserLoginController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(UserLoginController.class);
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private MerchantUserService merchantUserService;
	@Autowired
	private PosService posService;

	@RequestMapping("/login")
	public @ResponseBody UserLoginResponse login(HttpServletRequest request) {
		UserLoginResponse response = new UserLoginResponse();
		// TODO:签名验签秘钥如何获取
		String key = "123";
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if(StringUtils.isBlank(jsondata)) {
				if(logger.isInfoEnabled()) {
					logger.info("手机或VC登录接口:接收到的jsondata参数为空");
				}
				response.setRespcode(ResponseCode.JSON_ERROR);
				return response;
			}

			// 转换jsondata
			UserLoginRequest userLoginRequest = convertJsonToRequest(jsondata, UserLoginRequest.class);
			// 追踪号
			String trackId = TrackIdHolder.setDefaultRandomTrackId();
			if (logger.isInfoEnabled()) {
				logger.info("create trackId:" + trackId + ", jsondata:" + jsondata);
			}
//			String trackId = userLoginRequest.getTrack_id();
//			if (DDPStringUtil.isNotPopulated(trackId)) {
//				trackId = TrackIdHolder.setDefaultRandomTrackId();
//				if (logger.isInfoEnabled()) {
//					logger.info("create trackId:" + trackId + ", jsondata:" + jsondata);
//				}
//			} else {
//				TrackIdHolder.set(trackId);
//				if (logger.isInfoEnabled()) {
//					logger.info("receive trackId:" + trackId + ", jsondata:" + jsondata);
//				}
//			}
			// 通用参数校验
			baseCheck(userLoginRequest);
			// 验签
			signCheck(key, userLoginRequest);
			// 业务参数校验
			loginCheck(userLoginRequest);
			// 业务逻辑
			DodopalResponse<MobileUserDTO>  loginResponse = userLoginService.login(userLoginRequest.getUsername(), 
																					userLoginRequest.getUserpwd(), 
																					userLoginRequest.getSource(),
																					userLoginRequest.getLoginid(),
																					userLoginRequest.getUsertype());
			if (ResponseCode.SUCCESS.equals(loginResponse.getCode())) {
				MobileUserDTO mobileUser = loginResponse.getResponseEntity();
				// 用户名
				response.setUsername(mobileUser.getMerUserName());
				// 用户id
				response.setUserid(mobileUser.getId());
				// 用户号
				response.setUsercode(mobileUser.getUserCode());
				// 手机号
				response.setMerusermobile(mobileUser.getMerUserMobile());
				// 商户号
				response.setMercode(mobileUser.getMerCode());
				// 商户名称
				response.setMername(mobileUser.getMerName());
				// 用户类型
				response.setMerusertype(mobileUser.getMerType());
				// 性别
				response.setSex(mobileUser.getMerUserSex());
				// 业务城市编码
				response.setCityno(mobileUser.getCityCode());
				// 业务城市名称
				response.setCityname(mobileUser.getCityName());
				// 上次登录名
				response.setLastloginname(mobileUser.getMerUserLastLoginName());
				// 上次登录时间
				response.setLastlogintime(mobileUser.getMerUserLastLoginDate());
				// 可用余额
				response.setSurpluslimit(mobileUser.getAvailableBalance());
				// 权限code列表
				response.setModelrules(getStr(mobileUser.getFunCodeList()));
			}
			// 响应码
			response.setRespcode(loginResponse.getCode());
			// 签名
			if(ResponseCode.SUCCESS.equals(response.getRespcode())) {
				sign(key, response);
				if (logger.isDebugEnabled()) {
					logger.debug("trackId:" + trackId + ", sign result:" + response.getSign());
				}
			}
		} catch (DDPException e) {
			if (logger.isErrorEnabled()) {
				logger.error("trackId:" + TrackIdHolder.get() + ", DDPException:code:" + e.getCode());
			}
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("trackId:" + TrackIdHolder.get() + ", error message:" + e.getMessage(), e);
			}
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		if(logger.isInfoEnabled()) {
			logger.info("trackId:" + TrackIdHolder.get() + ", respcode:" + response.getRespcode());
		}
		// 返回track_id TODO:暂不返回，等统一使用此参数后放开
		// response.setTrack_id(TrackIdHolder.get());
		return response;
	}

	private void loginCheck(UserLoginRequest userLoginRequest) {
		String loginid = userLoginRequest.getLoginid();
		// 如果loginid不为空认为是迁移用户，loginid为空则username不能为空
		if(StringUtils.isNotBlank(loginid)) {
			String usertype = userLoginRequest.getUsertype();
			if(StringUtils.isBlank(usertype)) {
				throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_OLD_USER_TYPE_NULL);
			}
		} else {
			String userName = userLoginRequest.getUsername();
			if (StringUtils.isBlank(userName)) {
				throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_USER_NAME_NULL);
			}
//			char firstChar = userName.charAt(0);
//			if (Character.isDigit(firstChar)) {
//				if (userName.length() == 11) {
//					ProductValidateUtils.validateMobile(userName);
//				}
//			} else {
//				ProductValidateUtils.validateMerUserName(userName);
//			}
		}

		String userPwd = userLoginRequest.getUserpwd();
		ProductValidateUtils.validateUserPwd(userPwd);

		String source = userLoginRequest.getSource();
		if (!SourceEnum.isPhone(source) && !SourceEnum.VC.getCode().equals(source)) {
			throw new DDPException(ResponseCode.PRODUCT_REQ_PARAM_SOURCE_ERROR);
		}

	}

	private String getStr(List<String> list) {
		StringBuilder sb = new StringBuilder();
		if (CollectionUtils.isNotEmpty(list)) {
			for (String temp : list) {
				sb.append("," + temp);
			}
			return sb.toString().substring(1);
		}
		return "";
	}

	/**
	 * 获取网点信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getProxyInfo")
	public @ResponseBody BaseResponse getProxyInfo(HttpServletRequest request) {
		ProxyInfoResponse response = new ProxyInfoResponse();
		// TODO:签名验签秘钥如何获取
		String key = "123";
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if(StringUtils.isBlank(jsondata)) {
				if(logger.isInfoEnabled()) {
					logger.info("网点信息接口:接收到的jsondata参数为空");
				}
				response.setRespcode(ResponseCode.JSON_ERROR);
				return response;
			}

			// 转换jsondata
			ProxyInfoRequest proxyInfoRequest = convertJsonToRequest(jsondata, ProxyInfoRequest.class);
			// 通用参数校验
			baseCheck(proxyInfoRequest);
			// 验签
			signCheck(key, proxyInfoRequest);
			// 业务参数校验
			getProxyInfoCheck(proxyInfoRequest);
			// 业务逻辑
			DodopalResponse<MerchantDTO>  merchantResponse = merchantService.findMerchantInfoByMerCode(proxyInfoRequest.getMercode());
			if (ResponseCode.SUCCESS.equals(merchantResponse.getCode())) {
				MerchantDTO merchantDTO = merchantResponse.getResponseEntity();
				// 商户名称(proxyname)
				response.setMername(merchantDTO.getMerName());
				// 所在城市(cityno)
				response.setCitycode(merchantDTO.getMerCity());
				// 网点类型(proxytype)
				response.setMertype(merchantDTO.getMerType());
				// 网点地址(proxyaddress)
				response.setMeraddress(merchantDTO.getMerAdds());
				// 网点电话(proxytel)
				response.setMertel(merchantDTO.getMerTelephone());
				// 网点传真(proxyfax)
				response.setMerfax(merchantDTO.getMerFax());
				// 网点邮政编码(proxyzipcode)
				response.setMerzip(merchantDTO.getMerZip());
				// 网点注册时间(proxyregisttime)
				Date regDate = merchantDTO.getMerRegisterDate();
				if(regDate != null) {
					response.setMerregtime(DateUtils.formatDate(regDate, DateUtils.DATE_FULL_STR));
				}
				// 网点状态(proxystatus)
				response.setMerstate(merchantDTO.getMerState());
				// 网点是否为定制状态: 0：停用1：启用-1:取状态出错(dzstatus)
				response.setMeractivate(merchantDTO.getActivate());
				// 是否线下收单标示0否，1是
				response.setPayflag("0");
				// 充值费率
				response.setRateamt("0.0");
				// 是否线下收单标示0否，1是
				response.setPayflag("0");
				List<MerBusRateDTO> merBusRateList = merchantDTO.getMerBusRateList();
				if(CollectionUtils.isNotEmpty(merBusRateList)) {
					for(MerBusRateDTO merBusRateTemp : merBusRateList) {
						if(RateCodeEnum.YKT_RECHARGE.getCode().equals(merBusRateTemp.getRateCode())) {
							// 充值费率
							response.setRateamt(Double.toString(merBusRateTemp.getRate()));
						} else if(RateCodeEnum.YKT_PAYMENT.getCode().equals(merBusRateTemp.getRateCode())) {
							// 是否线下收单标示0否，1是
							response.setPayflag("1");
						}
					}
				}
				// 结算周期(例如：30天)
				MerDdpInfoDTO merDdpInfoDTO = merchantDTO.getMerDdpInfo();
				if(merDdpInfoDTO != null 
						&& merDdpInfoDTO.getSettlementType() != null 
						&& SettlementTypeEnum.DAYS_NUMBER.getCode().equals(merDdpInfoDTO.getSettlementType())
						&& merDdpInfoDTO.getSettlementThreshold() != null) {
					response.setCycletype(merDdpInfoDTO.getSettlementThreshold().toString());
				}
				// 费率(含义未知)
				response.setAmtfee("0.0");
			}
			// 响应码
			response.setRespcode(merchantResponse.getCode());
			// 签名
			if(ResponseCode.SUCCESS.equals(response.getRespcode())) {
				sign(key, response);
			}
		} catch (DDPException e) {
			if (logger.isErrorEnabled()) {
				logger.error("DDPException:code:" + e.getCode());
			}
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		if(logger.isInfoEnabled()) {
			logger.info("respcode:" + response.getRespcode());
		}
		return response;
	}

	private void getProxyInfoCheck(ProxyInfoRequest proxyInfoRequest) {
		String merCode = proxyInfoRequest.getMercode();
		if(StringUtils.isBlank(merCode)) {
			throw new DDPException(ResponseCode.PRODUCT_PRO_MERCHANTNUM_NULL);
		}
	}

	@RequestMapping("/getProxyUserInfo")
	public @ResponseBody BaseResponse getProxyUserInfo(HttpServletRequest request) {
		ProxyUserInfoResponse response = new ProxyUserInfoResponse();
		// TODO:签名验签秘钥如何获取
		String key = "123";
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if(StringUtils.isBlank(jsondata)) {
				if(logger.isInfoEnabled()) {
					logger.info("网点用户信息接口:接收到的jsondata参数为空");
				}
				response.setRespcode(ResponseCode.JSON_ERROR);
				return response;
			}

			// 转换jsondata
			ProxyUserInfoRequest proxyUserInfoRequest = convertJsonToRequest(jsondata, ProxyUserInfoRequest.class);
			// 通用参数校验
			baseCheck(proxyUserInfoRequest);
			// 验签
			signCheck(key, proxyUserInfoRequest);
			// 业务参数校验
			getProxyUserInfoCheck(proxyUserInfoRequest);
			// 业务逻辑
			MerchantUserDTO merUserDTO = new MerchantUserDTO();
			merUserDTO.setMerCode(proxyUserInfoRequest.getMercode());
			if (StringUtils.isNotBlank(proxyUserInfoRequest.getUsercode())) {
				merUserDTO.setUserCode(proxyUserInfoRequest.getUsercode());
			}
			DodopalResponse<List<MerchantUserDTO>> merchantUserResponse = merchantUserService.findMerchantUserList(merUserDTO);
			if (ResponseCode.SUCCESS.equals(merchantUserResponse.getCode())) {
				List<MerchantUserDTO> userDTOList = merchantUserResponse.getResponseEntity();
				if(CollectionUtils.isNotEmpty(userDTOList)) {
					List<UserInfoModel> userlist = new ArrayList<UserInfoModel>();
					for(MerchantUserDTO userDTOTemp : userDTOList) {
						UserInfoModel userInfoTemp = new UserInfoModel();
						// 用户id
						userInfoTemp.setUserid(userDTOTemp.getId());
						// 用户名
						userInfoTemp.setUsername(userDTOTemp.getMerUserName());
						// 真实姓名
						userInfoTemp.setUsernickname(userDTOTemp.getMerUserNickName());
						// 证件类型
						userInfoTemp.setIdentitytype(userDTOTemp.getMerUserIdentityType());
						// 证件号码
						userInfoTemp.setIdentitynumber(userDTOTemp.getMerUserIdentityNumber());
						// 性别
						userInfoTemp.setSex(userDTOTemp.getMerUserSex());
						// 邮箱
						userInfoTemp.setEmail(userDTOTemp.getMerUserEmail());
						// 移动电话
						userInfoTemp.setMobile(userDTOTemp.getMerUserMobile());
						// 固定电话
						userInfoTemp.setTelephone(userDTOTemp.getMerUserTelephone());
						// 省份
						userInfoTemp.setProvince(userDTOTemp.getMerUserPro());
						// 城市
						userInfoTemp.setCity(userDTOTemp.getMerUserCity());
						// 地址
						userInfoTemp.setAddress(userDTOTemp.getMerUserAdds());
						// 邮编
						userInfoTemp.setZip(null);
						// 备注
						userInfoTemp.setRemark(userDTOTemp.getMerUserRemark());
						// 状态
						userInfoTemp.setStatus(userDTOTemp.getMerUserState());
						userlist.add(userInfoTemp);
					}
					response.setUserlist(userlist);
				}
			}
			// 响应码
			response.setRespcode(merchantUserResponse.getCode());
			// 签名
			if(ResponseCode.SUCCESS.equals(response.getRespcode())) {
				sign(key, response);
			}
		} catch (DDPException e) {
			if (logger.isErrorEnabled()) {
				logger.error("DDPException:code:" + e.getCode());
			}
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		if(logger.isInfoEnabled()) {
			logger.info("respcode:" + response.getRespcode());
		}
		return response;
	}

	private void getProxyUserInfoCheck(ProxyUserInfoRequest proxyUserInfoRequest) {
		String merCode = proxyUserInfoRequest.getMercode();
		if (StringUtils.isBlank(merCode)) {
			throw new DDPException(ResponseCode.PRODUCT_PRO_MERCHANTNUM_NULL);
		}
	}

	@RequestMapping("/getPosInfo")
	public @ResponseBody BaseResponse getPosInfo(HttpServletRequest request) {
		PosResponse response = new PosResponse();
		// TODO:签名验签秘钥如何获取
		String key = "123";
		try {
			// 获取jsondata
			String jsondata = request.getParameter("jsondata");
			if(StringUtils.isBlank(jsondata)) {
				if(logger.isInfoEnabled()) {
					logger.info("网点POS信息接口:接收到的jsondata参数为空");
				}
				response.setRespcode(ResponseCode.JSON_ERROR);
				return response;
			}

			// 转换jsondata
			PosRequest posRequest = convertJsonToRequest(jsondata, PosRequest.class);
			// 通用参数校验
			baseCheck(posRequest);
			// 验签
			signCheck(key, posRequest);
			// 业务参数校验
			getPosInfoCheck(posRequest);
			// 业务逻辑
			PosQueryDTO posQueryDTO = new PosQueryDTO();
			posQueryDTO.setMerchantCode(posRequest.getMercode());
			if(StringUtils.isNotBlank(posRequest.getPosid())) {
				posQueryDTO.setCode(posRequest.getPosid());
			}
			if(StringUtils.isNotBlank(posRequest.getRemarks())) {
				posQueryDTO.setComments(posRequest.getRemarks());
			}
			// TODO
			posQueryDTO.setPage(new PageParameter(1, 1000));
			DodopalResponse<DodopalDataPage<PosDTO>> posResponse = posService.findPosListPage(posQueryDTO);
			if (ResponseCode.SUCCESS.equals(posResponse.getCode()) && posResponse.getResponseEntity() != null) {
				List<PosDTO> posDTOList = posResponse.getResponseEntity().getRecords();
				if (CollectionUtils.isNotEmpty(posDTOList)) {
					List<PosModel> posModelList = new ArrayList<PosModel>(posDTOList.size());
					for (PosDTO posTemp : posDTOList) {
						PosModel posModelTemp = new PosModel();
						posModelTemp.setPosid(posTemp.getCode());
						posModelTemp.setAddtime(DateUtils.formatDate(posTemp.getBundlingDate(), DateUtils.DATE_FULL_STR));
						posModelTemp.setRemarks(posTemp.getComments());
						posModelList.add(posModelTemp);
					}
					response.setPoslist(posModelList);
				}
			}
			// 响应码
			response.setRespcode(posResponse.getCode());
			// 签名
			if(ResponseCode.SUCCESS.equals(response.getRespcode())) {
				// sign(key, response);
			}
		} catch (DDPException e) {
			if (logger.isErrorEnabled()) {
				logger.error("DDPException:code:" + e.getCode());
			}
			response.setRespcode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setRespcode(ResponseCode.SYSTEM_ERROR);
		}
		if(logger.isInfoEnabled()) {
			logger.info("respcode:" + response.getRespcode());
		}
		return response;
	}

	private void getPosInfoCheck(PosRequest posRequest) {
		String merCode = posRequest.getMercode();
		if (StringUtils.isBlank(merCode)) {
			throw new DDPException(ResponseCode.PRODUCT_PRO_MERCHANTNUM_NULL);
		}
	}
}
