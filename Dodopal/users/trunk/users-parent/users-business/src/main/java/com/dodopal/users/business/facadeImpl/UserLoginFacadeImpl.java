package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MobileUserDTO;
import com.dodopal.api.users.dto.PortalUserDTO;
import com.dodopal.api.users.facade.UserLoginFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.facade.AbstractFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DateUtils;
import com.dodopal.common.util.TrackIdHolder;
import com.dodopal.users.business.model.MerDdpInfo;
import com.dodopal.users.business.model.MerFunctionInfo;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.service.MerBusRateService;
import com.dodopal.users.business.service.MerchantUserService;
import com.dodopal.users.business.service.UserLoginService;

/**
 * 类说明 ：
 * 
 * @author lifeng
 */
@Service("userLoginFacade")
public class UserLoginFacadeImpl extends AbstractFacade implements UserLoginFacade {
	private final static Logger logger = LoggerFactory.getLogger(UserLoginFacadeImpl.class);
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private MerchantUserService merchantUserService;
	@Autowired
	private MerBusRateService merBusRateService;
	// 目前可以提供给VC的权限
	private final static Map<String, String> vcFunMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("app.prdmgmt.recharge", null); //一卡通充值
			put("app.product.purchase", null); // 一卡通消费
			put("merchant.pos", null);         // POS管理
		}
	};

	@Override
	public DodopalResponse<PortalUserDTO> login(String userName, String password) {
		DodopalResponse<PortalUserDTO> response = new DodopalResponse<PortalUserDTO>();
		try {
			// 获取追踪号
			String trackId = getTrackId();
			if (DDPStringUtil.isNotPopulated(trackId)) {
				trackId = TrackIdHolder.setDefaultRandomTrackId();
				if (logger.isInfoEnabled()) {
					logger.info("create trackId:" + trackId);
				}
			} else {
				TrackIdHolder.set(trackId);
				if (logger.isInfoEnabled()) {
					logger.info("receive trackId:" + trackId);
				}
			}
			if (StringUtils.isBlank(userName)) {
				if (logger.isInfoEnabled()) {
					logger.info("trackId:" + trackId + ", userName is null");
				}
				response.setCode(ResponseCode.USERS_USER_NAME_OR_PWD_ERR);
				return response;
			}
			if (StringUtils.isBlank(password)) {
				if (logger.isInfoEnabled()) {
					logger.info("trackId:" + trackId + ", userPwd is null");
				}
				response.setCode(ResponseCode.USERS_USER_NAME_OR_PWD_ERR);
				return response;
			}

			DodopalResponse<Merchant> resp = userLoginService.login(userName, password);
			if (resp.getCode().equals(ResponseCode.SUCCESS)) {
				// 转换数据
				PortalUserDTO portUser = convertToPortalUserDTO(resp.getResponseEntity());
				response.setCode(ResponseCode.SUCCESS);
				response.setResponseEntity(portUser);
			} else {
				response.setCode(resp.getCode());
			}

			// 如果不是用户名不存在，则更新用户登录数据
			if (!ResponseCode.USERS_USER_NAME_NO_EXIST.equals(resp.getCode())) {
				try {
					MerchantUser loginUser = new MerchantUser();
					loginUser.setMerUserName(userName);
					boolean isSuccess = true;
					if (!resp.getCode().equals(ResponseCode.SUCCESS)) {
						isSuccess = false;
					}
					int num = merchantUserService.updateLoginDataByUserName(loginUser, isSuccess);
					if (logger.isInfoEnabled()) {
						logger.info("trackId:" + trackId + ", update user login data result:" + num);
					}
				} catch (Exception e) {
					if (logger.isErrorEnabled()) {
						logger.error("trackId:" + trackId + ",更新用户登录数据失败！", e);
					}
				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("trackId:" + TrackIdHolder.get() + "," + e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		if (logger.isInfoEnabled()) {
			logger.info("trackId:" + TrackIdHolder.get() + ",登录用户名：[" + userName + "], 来源(" + SourceEnum.PORTAL.getCode() + ")：" + SourceEnum.PORTAL.getName() + ", resCode:" + response.getCode() + ", resMsg:" + response.getMessage());
		}
		// 如果登录不成功
		if (!ResponseCode.SUCCESS.equals(response.getCode())) {
			// 用户名不存在或密码错误统一提示：用户名或密码错误，其余提示无权限
			if (ResponseCode.USERS_USER_NAME_NO_EXIST.equals(response.getCode()) || ResponseCode.USERS_PASSWORD_ERR.equals(response.getCode())) {
				response.setCode(ResponseCode.USERS_USER_NAME_OR_PWD_ERR);
			} else {
				response.setCode(ResponseCode.USERS_USER_AUTHORITY_NULL);
			}
		}
		return response;
	}

	private PortalUserDTO convertToPortalUserDTO(Merchant mer) {
		MerchantUser merUser = mer.getMerchantUser();
		List<MerFunctionInfo> merFunList = mer.getMerFunInfoList();
		List<MerFunctionInfoDTO> merFunDTOList = new ArrayList<MerFunctionInfoDTO>(merFunList.size());
		for (MerFunctionInfo merFunTemp : merFunList) {
			MerFunctionInfoDTO merFunDTOTemp = new MerFunctionInfoDTO();
			BeanUtils.copyProperties(merFunTemp, merFunDTOTemp);
			merFunDTOList.add(merFunDTOTemp);
		}
		String merCode = mer.getMerCode();
		String merType = mer.getMerType();

		PortalUserDTO portalUser = new PortalUserDTO();
		portalUser.setId(merUser.getId());
		portalUser.setMerFunInfoList(merFunDTOList);
		portalUser.setMerUserLastLoginDate(merUser.getMerUserLastLoginDate());
		portalUser.setMerUserLastLoginIp(merUser.getMerUserLastLoginIp());
		portalUser.setMerUserName(merUser.getMerUserName());
		portalUser.setMerUserNickName(merUser.getMerUserNickName());
		portalUser.setMerUserPWD(merUser.getMerUserPWD());
		portalUser.setUserCode(merUser.getUserCode());
		portalUser.setMerUserMobile(merUser.getMerUserMobile());
		// 用户业务城市(为空取默认值)
		portalUser.setCityName(merUser.getCityName());
		// 商户类型,个人为99
		portalUser.setMerType(mer.getMerType());
		if (StringUtils.isNotBlank(merCode)) {
			portalUser.setMerCode(merCode);
			portalUser.setMerName(mer.getMerName());
			portalUser.setMerClassify(mer.getMerClassify());
		}
		// 一卡通编码
		portalUser.setYktCode(mer.getYktCode());

		// 商户补充信息
		MerDdpInfo merDdpInfo = mer.getMerDdpInfo();
		if (merDdpInfo != null) {
			// 连锁加盟网点
			if (MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType)) {
				// 额度来源
				portalUser.setLimitSource(merDdpInfo.getLimitSource());
			}
		}

		return portalUser;
	}

	@Override
	public DodopalResponse<MobileUserDTO> login(String userName, String password, String source, String loginid, String usertype) {
		DodopalResponse<MobileUserDTO> response = new DodopalResponse<MobileUserDTO>();
		try {
			// 获取追踪号
			String trackId = getTrackId();
			if(DDPStringUtil.isNotPopulated(trackId)) {
				trackId = TrackIdHolder.setDefaultRandomTrackId();
				if(logger.isInfoEnabled()) {
					logger.info("create trackId:" + trackId);
				}
			} else {
				TrackIdHolder.set(trackId);
				if(logger.isInfoEnabled()) {
					logger.info("receive trackId:" + trackId);
				}
			}

			// 如果loginid不为空则为迁移用户，先根据userid、usertype查询用户，如果存在，取出用户名
			if(StringUtils.isNotBlank(loginid)) {
				if (StringUtils.isBlank(usertype)) {
					if (logger.isInfoEnabled()) {
						logger.info("trackId:" + trackId + ", usertype is null");
					}
					response.setCode(ResponseCode.USERS_USER_NAME_OR_PWD_ERR);
					return response;
				}

				// 先根据userid、usertype查询用户，如果存在，取出用户名
				userName = merchantUserService.findUnionLoginUserName(loginid, usertype);
				if (StringUtils.isBlank(userName)) {
					if (logger.isInfoEnabled()) {
						logger.info("trackId:" + trackId + ", userid[" + loginid + "], usertype[" + usertype + "], username not found");
					}
					response.setCode(ResponseCode.USERS_USER_NAME_OR_PWD_ERR);
					return response;
				}
			}

			if (StringUtils.isBlank(userName)) {
				response.setCode(ResponseCode.USERS_USER_NAME_NO_EXIST);
				return response;
			}
			if (StringUtils.isBlank(password)) {
				response.setCode(ResponseCode.USERS_PASSWORD_ERR);
				return response;
			}
			if (StringUtils.isBlank(source)) {
				response.setCode(ResponseCode.USERS_MER_USER_SOURCE_ERROR);
				return response;
			}
			// 来源不是手机端或VC端无权限登录
			if(!SourceEnum.isPhone(source) && !SourceEnum.VC.getCode().equals(source)) {
				response.setCode(ResponseCode.USERS_USER_AUTHORITY_NULL);
			}

			DodopalResponse<Merchant> loginResp = userLoginService.login(userName, password);
			if (ResponseCode.SUCCESS.equals(loginResp.getCode())) {
				response.setCode(ResponseCode.SUCCESS);
				Merchant mer = loginResp.getResponseEntity();
				String merType = mer.getMerType();
				//99：个人用户 只能登录手机端
				//以下商户类型只能登录VC端
				//11：代理商自由网点
				//13：连锁直营网点
				//14：连锁加盟网点
				//15：都都宝自有网点
				if(SourceEnum.isPhone(source)) {
					if(!MerTypeEnum.PERSONAL.getCode().equals(merType)) {
						response.setCode(ResponseCode.USERS_USER_AUTHORITY_NULL);
					}
				} else if(SourceEnum.VC.getCode().equals(source)) {
					if(!MerTypeEnum.AGENT_MER.getCode().equals(merType) 
								&& !MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType) 
								&& !MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType)
								&& !MerTypeEnum.DDP_MER.getCode().equals(merType)) {
						response.setCode(ResponseCode.USERS_USER_AUTHORITY_NULL);
					}

					// 返回给VC端城市编码格式为：100000-1110
					String cityCode = mer.getMerchantUser().getCityCode();
					if (StringUtils.isNotBlank(cityCode)) {
						String yktCode = merBusRateService.findYktCodeByCityCode(cityCode);
						if (StringUtils.isNotBlank(yktCode)) {
							mer.getMerchantUser().setCityCode(yktCode + "-" + cityCode);
						}
					}
				}

				if(ResponseCode.SUCCESS.equals(response.getCode())) {
					// 转换数据
					MobileUserDTO mobileUser = convertToMobileUserDTO(mer);
					response.setResponseEntity(mobileUser);
				}
			} else {
				response.setCode(loginResp.getCode());
			}

			// 如果不是用户名不存在，则更新用户登录数据
			if (!ResponseCode.USERS_USER_NAME_NO_EXIST.equals(loginResp.getCode())) {
				try {
					MerchantUser loginUser = new MerchantUser();
					loginUser.setMerUserName(userName);
					boolean isSuccess = true;
					if (!loginResp.getCode().equals(ResponseCode.SUCCESS)) {
						isSuccess = false;
					}
					merchantUserService.updateLoginDataByUserName(loginUser, isSuccess);
				} catch (Exception e) {
					if (logger.isErrorEnabled()) {
						logger.error("更新用户登录数据失败！", e);
					}
				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("trackId:" + TrackIdHolder.get() + "," + e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		if (logger.isInfoEnabled()) {
			logger.info("trackId:" + TrackIdHolder.get() +",登录用户名：[" + userName + "], 来源(" + source + "):" + SourceEnum.getSourceByCode(source).getName() + ", resCode:" + response.getCode() + ", resMsg:" + response.getMessage());
		}
		// 如果登录不成功
		if (!ResponseCode.SUCCESS.equals(response.getCode())) {
			// 用户名不存在或密码错误统一提示：用户名或密码错误，其余提示无权限
			if (ResponseCode.USERS_USER_NAME_NO_EXIST.equals(response.getCode()) || ResponseCode.USERS_PASSWORD_ERR.equals(response.getCode())) {
				response.setCode(ResponseCode.USERS_USER_NAME_OR_PWD_ERR);
			} else {
				response.setCode(ResponseCode.USERS_USER_AUTHORITY_NULL);
			}
		}
		return response;
	}

	private MobileUserDTO convertToMobileUserDTO(Merchant mer) {
		MobileUserDTO mobileUser = new MobileUserDTO();
		MerchantUser merUser = mer.getMerchantUser();
		String merCode = mer.getMerCode();
		// 用户ID
		mobileUser.setId(merUser.getId());
		// 用户名
		mobileUser.setMerUserName(merUser.getMerUserName());
		// 用户编号
		mobileUser.setUserCode(merUser.getUserCode());
		// 用户手机号
		mobileUser.setMerUserMobile(merUser.getMerUserMobile());
		// 商户类型,个人为99
		mobileUser.setMerType(mer.getMerType());
		// 性别
		mobileUser.setMerUserSex(merUser.getMerUserSex());
		// 业务城市编码
		mobileUser.setCityCode(merUser.getCityCode());
		// 业务城市名称
		mobileUser.setCityName(merUser.getCityName());
		// 上次登录名
		mobileUser.setMerUserLastLoginName("");
		// 上次登录时间
		Date lastLoginDate = merUser.getMerUserLastLoginDate();
		if (lastLoginDate != null) {
			mobileUser.setMerUserLastLoginDate(DateUtils.formatDate(lastLoginDate, DateUtils.DATE_FULL_STR));
		} else {
			mobileUser.setMerUserLastLoginDate("");
		}
		// 可用余额
		mobileUser.setAvailableBalance(mer.getAvailableBalance());
		// VC端获取权限
		if (!MerTypeEnum.PERSONAL.getCode().equals(mer.getMerType())) {
			List<MerFunctionInfo> merFunInfoList = mer.getMerFunInfoList();
			if (CollectionUtils.isNotEmpty(merFunInfoList)) {
				List<String> funCodeList = new ArrayList<String>(merFunInfoList.size());
				for (MerFunctionInfo merFunTemp : merFunInfoList) {
					if (vcFunMap.containsKey(merFunTemp.getMerFunCode())) {
						funCodeList.add(merFunTemp.getMerFunCode());
					}
				}
				mobileUser.setFunCodeList(funCodeList);
			}
		}

		if (StringUtils.isNotBlank(merCode)) {
			// 商户号
			mobileUser.setMerCode(merCode);
			// 商户名称
			mobileUser.setMerName(mer.getMerName());
		}
		return mobileUser;
	}

	@Override
	public DodopalResponse<PortalUserDTO> unionLogin(String loginid, String password, String usertype) {
		DodopalResponse<PortalUserDTO> response = new DodopalResponse<PortalUserDTO>();
		try {
			// 获取追踪号
			String trackId = getTrackId();
			if (DDPStringUtil.isNotPopulated(trackId)) {
				trackId = TrackIdHolder.setDefaultRandomTrackId();
				if (logger.isInfoEnabled()) {
					logger.info("create trackId:" + trackId);
				}
			} else {
				TrackIdHolder.set(trackId);
				if (logger.isInfoEnabled()) {
					logger.info("receive trackId:" + trackId);
				}
			}
			if (StringUtils.isBlank(loginid)) {
				if (logger.isInfoEnabled()) {
					logger.info("trackId:" + trackId + ", loginid is null");
				}
				response.setCode(ResponseCode.USERS_USER_NAME_OR_PWD_ERR);
				return response;
			}
			if (StringUtils.isBlank(password)) {
				if (logger.isInfoEnabled()) {
					logger.info("trackId:" + trackId + ", userPwd is null");
				}
				response.setCode(ResponseCode.USERS_USER_NAME_OR_PWD_ERR);
				return response;
			}
			if (StringUtils.isBlank(usertype)) {
				if (logger.isInfoEnabled()) {
					logger.info("trackId:" + trackId + ", usertype is null");
				}
				response.setCode(ResponseCode.USERS_USER_NAME_OR_PWD_ERR);
				return response;
			}

			// 先根据userid、usertype查询用户，如果存在，取出用户名
			String userName = merchantUserService.findUnionLoginUserName(loginid, usertype);
			if (StringUtils.isBlank(userName)) {
				if (logger.isInfoEnabled()) {
					logger.info("trackId:" + trackId + ", userid[" + loginid + "], usertype[" + usertype + "], username not found");
				}
				response.setCode(ResponseCode.USERS_USER_NAME_OR_PWD_ERR);
				return response;
			}

			DodopalResponse<Merchant> resp = userLoginService.login(userName, password);
			if (resp.getCode().equals(ResponseCode.SUCCESS)) {
				// 转换数据
				PortalUserDTO portUser = convertToPortalUserDTO(resp.getResponseEntity());
				response.setCode(ResponseCode.SUCCESS);
				response.setResponseEntity(portUser);
			} else {
				response.setCode(resp.getCode());
			}

			// 如果不是用户名不存在，则更新用户登录数据
			if (!ResponseCode.USERS_USER_NAME_NO_EXIST.equals(resp.getCode())) {
				try {
					MerchantUser loginUser = new MerchantUser();
					loginUser.setMerUserName(userName);
					boolean isSuccess = true;
					if (!resp.getCode().equals(ResponseCode.SUCCESS)) {
						isSuccess = false;
					}
					int num = merchantUserService.updateLoginDataByUserName(loginUser, isSuccess);
					if (logger.isInfoEnabled()) {
						logger.info("trackId:" + trackId + ", update user login data result:" + num);
					}
				} catch (Exception e) {
					if (logger.isErrorEnabled()) {
						logger.error("trackId:" + trackId + ",更新用户登录数据失败！", e);
					}
				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("trackId:" + TrackIdHolder.get() + "," + e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		if (logger.isInfoEnabled()) {
			logger.info("trackId:" + TrackIdHolder.get() + ",登录用户名：[" + loginid + "], 来源(" + SourceEnum.TRANSFER.getCode() + ")：" + SourceEnum.PORTAL.getName() + ", resCode:" + response.getCode() + ", resMsg:" + response.getMessage());
		}
		// 如果登录不成功
		if (!ResponseCode.SUCCESS.equals(response.getCode())) {
			// 用户名不存在或密码错误统一提示：用户名或密码错误，其余提示无权限
			if (ResponseCode.USERS_USER_NAME_NO_EXIST.equals(response.getCode()) || ResponseCode.USERS_PASSWORD_ERR.equals(response.getCode())) {
				response.setCode(ResponseCode.USERS_USER_NAME_OR_PWD_ERR);
			} else {
				response.setCode(ResponseCode.USERS_USER_AUTHORITY_NULL);
			}
		}
		return response;
	}
}
