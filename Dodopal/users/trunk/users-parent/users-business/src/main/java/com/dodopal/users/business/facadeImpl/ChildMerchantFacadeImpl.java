package com.dodopal.users.business.facadeImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MerAutoAmtDTO;
import com.dodopal.api.users.dto.MerBusRateDTO;
import com.dodopal.api.users.dto.MerDdpInfoDTO;
import com.dodopal.api.users.dto.MerFunctionInfoDTO;
import com.dodopal.api.users.dto.MerKeyTypeDTO;
import com.dodopal.api.users.dto.MerRateSupplementDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.dto.MerchantQueryDTO;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.facade.ChildMerchantFacade;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.DelFlgEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.SexEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.interceptor.PageParameter;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.DDPStringUtil;
import com.dodopal.common.util.DodopalDataPageUtil;
import com.dodopal.users.business.constant.UsersConstants;
import com.dodopal.users.business.model.MerAutoAmt;
import com.dodopal.users.business.model.MerBusRate;
import com.dodopal.users.business.model.MerDdpInfo;
import com.dodopal.users.business.model.MerFunctionInfo;
import com.dodopal.users.business.model.MerKeyType;
import com.dodopal.users.business.model.MerRateSupplement;
import com.dodopal.users.business.model.Merchant;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.query.MerchantQuery;
import com.dodopal.users.business.service.MerBusRateService;
import com.dodopal.users.business.service.MerchantService;
import com.dodopal.users.business.service.MerchantUserService;
import com.dodopal.users.business.util.ResponseMsgUtil;

/**
 * 类说明 ：
 * 
 * @author lifeng
 */
@Service("childMerchantFacade")
public class ChildMerchantFacadeImpl implements ChildMerchantFacade {
	private final static Logger logger = LoggerFactory.getLogger(ChildMerchantFacadeImpl.class);
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private MerchantUserService merchantUserService;
	@Autowired
	private MerBusRateService merBusRateService;

	private static final String[] IGNORE_FIELDS = { "merchantUserDTO", "merchantUser", "merBusRateList", "merDefineFunList", "merRateSpmtList", "merKeyType", "merKeyTypeDTO", "merDdpInfo", "merAutoAmt", "merAutoAmtDTO"};

	@Override
	public DodopalResponse<DodopalDataPage<MerchantDTO>> findChildMerchantByPage(MerchantQueryDTO merQueryDTO) {
		DodopalResponse<DodopalDataPage<MerchantDTO>> response = new DodopalResponse<DodopalDataPage<MerchantDTO>>();
		response.setCode(ResponseCode.SUCCESS);
		try {
			String resCode = findChildMerchantByPageCheck(merQueryDTO);
			if (ResponseCode.SUCCESS.equals(resCode)) {
				MerchantQuery merQuery = new MerchantQuery();
				BeanUtils.copyProperties(merQueryDTO, merQuery);

				if (merQuery.getPage() == null) {
					merQuery.setPage(new PageParameter());
				}

				DodopalDataPage<Merchant> ddpResult = merchantService.findMerchantByPage(merQuery);
				if (ddpResult != null) {
					List<Merchant> resultList = ddpResult.getRecords();
					List<MerchantDTO> resResultList = null;
					if (resultList != null && resultList.size() > 0) {
						resResultList = new ArrayList<MerchantDTO>(resultList.size());
						for (Merchant mer : resultList) {
							MerchantDTO merDTO = new MerchantDTO();
							BeanUtils.copyProperties(mer, merDTO, IGNORE_FIELDS);
							resResultList.add(merDTO);
						}
					}
					PageParameter page = DodopalDataPageUtil.convertPageInfo(ddpResult);
					DodopalDataPage<MerchantDTO> ddpDTOResult = new DodopalDataPage<MerchantDTO>(page, resResultList);
					response.setResponseEntity(ddpDTOResult);
				}
			} else {
				response.setCode(resCode);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	@Override
	public DodopalResponse<String> saveChildMerchant(MerchantDTO merchantDTO) {
		DodopalResponse<String> response = new DodopalResponse<String>();
		response.setCode(ResponseCode.SUCCESS);
		try {
			List<String> msg = new ArrayList<String>();
			String resCode = saveChildMerchantCheck(merchantDTO, msg);
			if (ResponseCode.SUCCESS.equals(resCode)) {
				Merchant merchant = buildMerchant(merchantDTO);
				AtomicReference<String> randomPWD = new AtomicReference<String>();
				DodopalResponse<String> merResponse = merchantService.register(merchant, randomPWD);
				if (ResponseCode.SUCCESS.equals(merResponse.getCode())) {
					response.setResponseEntity(merResponse.getResponseEntity());
				} else {
					response.setCode(merResponse.getCode());
				}
			} else {
				response.setCode(resCode);
				if (ResponseCode.USERS_MER_RATE_OVER_PARENT.equals(resCode) || ResponseCode.USERS_PARENT_RATE_NOT_FOUND.equals(resCode)) {
					response.setMessage(ResponseMsgUtil.formatMerBusRateMsg(msg));
				}
			}
		} catch (HessianRuntimeException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.HESSIAN_ERROR);
		} catch (DDPException e) {
			response.setCode(e.getCode());
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	@Override
	public DodopalResponse<MerchantDTO> findChildMerchantByMerCode(String merCode, String merParentCode) {
		DodopalResponse<MerchantDTO> response = new DodopalResponse<MerchantDTO>();
		response.setCode(ResponseCode.SUCCESS);
		if (StringUtils.isBlank(merCode)) {
			response.setCode(ResponseCode.USERS_FIND_MER_CODE_NULL);
			return response;
		}

		if (StringUtils.isBlank(merParentCode)) {
			response.setCode(ResponseCode.USERS_MER_PARENT_CODE_NULL);
			return response;
		}
		try {
			Merchant merchant = merchantService.findMerchantByMerCode(merCode);
			if (merchant != null && StringUtils.isNotBlank(merchant.getMerParentCode()) && merParentCode.equals(merchant.getMerParentCode())) {
				MerchantDTO merchantDTO = convertToMerchantDTO(merchant);
				response.setResponseEntity(merchantDTO);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	@Override
	public DodopalResponse<String> upChildMerchant(MerchantDTO merchantDTO) {
		DodopalResponse<String> response = new DodopalResponse<String>();
		response.setCode(ResponseCode.SUCCESS);
		try {
			List<String> msg = new ArrayList<String>();
			String resCode = upChildMerchantCheck(merchantDTO, msg);
			if (ResponseCode.SUCCESS.equals(resCode)) {
				Merchant merchant = buildMerchant(merchantDTO);
				if (MerStateEnum.THROUGH.getCode().equals(merchant.getMerState())) {
					merchantService.updateThroughMerchant(merchant);
				} else if (MerStateEnum.REJECT.getCode().equals(merchant.getMerState())) {
					// 审核状态改为未审核
					merchant.setMerState(MerStateEnum.NO_AUDIT.getCode());
					merchantService.updateRejectMerchant(merchant);
				}
			} else {
				response.setCode(resCode);
				if (ResponseCode.USERS_MER_RATE_OVER_PARENT.equals(resCode) || ResponseCode.USERS_PARENT_RATE_NOT_FOUND.equals(resCode)) {
					response.setMessage(ResponseMsgUtil.formatMerBusRateMsg(msg));
				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	@Override
	public DodopalResponse<Integer> upChildMerchantActivate(String[] merCodes, String activate, String merParentCode, String updateUser) {
		DodopalResponse<Integer> response = new DodopalResponse<Integer>();
		response.setCode(ResponseCode.SUCCESS);
		if (StringUtils.isBlank(activate) || ActivateEnum.getActivateByCode(activate) == null) {
			response.setCode(ResponseCode.ACTIVATE_ERROR);
			return response;
		}
		if (StringUtils.isBlank(merParentCode)) {
			response.setCode(ResponseCode.USERS_MER_PARENT_CODE_NULL);
			return response;
		}
		if (merCodes == null || merCodes.length == 0) {
			return response;
		}
		List<String> msg = new ArrayList<String>();
		try {
			Merchant merchant = new Merchant();
			merchant.setActivate(activate);
			merchant.setMerParentCode(merParentCode);
			// int updateNum = merchantService.batchUpdateMerchant(merchant,
			// Arrays.asList(merCodes));
			int updateNum = merchantService.batchUpdateMerchantActivate(activate, Arrays.asList(merCodes), msg, updateUser);
			response.setResponseEntity(updateNum);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		if (msg.size() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("商户");
			for (String msgStr : msg) {
				sb.append(msgStr);
			}
			sb.append("无法启用，请先启用相应上级商户");
			response.setCode(ResponseCode.USERS_PARENT_MERCHANT_DISABLE);
			response.setMessage(sb.toString());
		}
		return response;
	}

	@Override
	public DodopalResponse<Integer> batchDelChildMerchant(String[] merCodes, String merParentCode) {
		DodopalResponse<Integer> response = new DodopalResponse<Integer>();
		response.setCode(ResponseCode.SUCCESS);
		try {
			String resCode = batchDelChildMerchantCheck(merCodes, merParentCode);
			if (ResponseCode.SUCCESS.equals(resCode)) {
				int updateNum = merchantService.batchDelMerchantByMerCodes(Arrays.asList(merCodes));
				response.setResponseEntity(updateNum);
			} else {
				response.setCode(resCode);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	// ---------------------------------------------接口参数校验
	private String findChildMerchantByPageCheck(MerchantQueryDTO merQueryDTO) {
		// 上级商户号
		String parentCode = merQueryDTO.getMerParentCode();
		if (StringUtils.isBlank(parentCode)) {
			return ResponseCode.USERS_MER_PARENT_CODE_NULL;
		}

		return ResponseCode.SUCCESS;
	}

	private String saveChildMerchantCheck(MerchantDTO merchantDTO, List<String> msg) {
		// 上级商户号
		String merParentCode = merchantDTO.getMerParentCode();
		if (StringUtils.isBlank(merParentCode)) {
			return ResponseCode.USERS_MER_PARENT_CODE_NULL;
		}
		Merchant merchantParent = merchantService.findMerchantByMerCode(merParentCode);
		if (merchantParent == null) {
			return ResponseCode.USERS_FIND_PARENT_MERCHANT_ERR;
		}

		// 商户名称
		String merName = merchantDTO.getMerName();
		if (StringUtils.isBlank(merName)) {
			return ResponseCode.USERS_MER_NAME_NULL;
		}
		Merchant checkMerName = new Merchant();
		checkMerName.setMerName(merName);
		if (merchantService.checkExist(checkMerName)) {
			return ResponseCode.USERS_MER_NAME_EXIST;
		}

		// 商户类型
		String merType = merchantDTO.getMerType();
		String merParentType = merchantParent.getMerType();
		if (MerTypeEnum.AGENT.getCode().equals(merParentType)) {
			// 代理商户可以创建代理商户、连锁商户、代理商自有网点
			if (!(MerTypeEnum.AGENT.getCode().equals(merType) || MerTypeEnum.CHAIN.getCode().equals(merType) || MerTypeEnum.AGENT_MER.getCode().equals(merType))) {
				// return ResponseCode.USERS_MER_TYPE_ERR;
			}
		} else if (MerTypeEnum.CHAIN.getCode().equals(merParentType)) {
			if (StringUtils.isBlank(merType)) {
				return ResponseCode.USERS_MER_TYPE_ERR;
			}
			// 连锁商户可以创建连锁直营网点、连锁加盟网点
			if (!(MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType))) {
				return ResponseCode.USERS_MER_TYPE_ERR;
			}
		} else {
			return ResponseCode.USERS_MERCHANT_NOT_ALLOWED_CREATE;
		}

		// 启用标识
		String activate = merchantDTO.getActivate();
		if (StringUtils.isBlank(activate)) {
			return ResponseCode.ACTIVATE_ERROR;
		}

		// 商户分类
		merchantDTO.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());
		// 商户属性
		merchantDTO.setMerProperty(MerPropertyEnum.STANDARD.getCode());

		// 联系人
		String merLinkUser = merchantDTO.getMerLinkUser();
		if (StringUtils.isBlank(merLinkUser)) {
			return ResponseCode.USERS_MER_LINK_USER_NULL;
		}

		// 手机号码
		String merLinkUserMobile = merchantDTO.getMerLinkUserMobile();
		if (StringUtils.isBlank(merLinkUserMobile)) {
			return ResponseCode.USERS_MOB_TEL_NULL;
		}
		if (!DDPStringUtil.isMobile(merLinkUserMobile)) {
			return ResponseCode.USERS_MOB_TEL_ERR;
		}
		MerchantUser checkUserMobile = new MerchantUser();
		checkUserMobile.setMerUserMobile(merLinkUserMobile);
		if (merchantUserService.checkExist(checkUserMobile)) {
			return ResponseCode.USERS_MOB_EXIST;
		}

		// 省份、城市、地址
		String merPro = merchantDTO.getMerPro();
		String merCity = merchantDTO.getMerCity();
		String merAdds = merchantDTO.getMerAdds();
		if (StringUtils.isBlank(merPro)) {
			return ResponseCode.USERS_MER_PRO_NULL;
		}
		if (StringUtils.isBlank(merCity)) {
			return ResponseCode.USERS_MER_CITY_NULL;
		}
		if (StringUtils.isBlank(merAdds)) {
			return ResponseCode.USERS_MER_ADDS_NULL;
		}

		// 用户名
		MerchantUserDTO merUserDTO = merchantDTO.getMerchantUserDTO();
		if (merUserDTO == null) {
			return ResponseCode.USERS_MER_INFO_NULL;
		}
		String merUserName = merUserDTO.getMerUserName();
		if (StringUtils.isBlank(merUserName)) {
			return ResponseCode.USERS_FIND_USER_NAME_NULL;
		}
		String reg = UsersConstants.REG_USER_NAME;
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(merUserName);
		if (!matcher.matches()) {
			return ResponseCode.USERS_USER_NAME_ERR;
		}
		MerchantUser checkUserName = new MerchantUser();
		checkUserName.setMerUserName(merUserName);
		if (merchantUserService.checkExist(checkUserName)) {
			return ResponseCode.USERS_MER_USER_NAME_EXIST;
		}

		// 创建人
		String createUser = merchantDTO.getCreateUser();
		if (StringUtils.isBlank(createUser)) {
			return ResponseCode.CREATE_USER_NULL;
		}
		if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
			// 审核人
			merchantDTO.setMerStateUser(createUser);
			merchantDTO.setMerStateDate(new Date());
		}

		// 用户注册来源
		merUserDTO.setMerUserSource(SourceEnum.PORTAL.getCode());
		// 用户启用标识
		merUserDTO.setActivate(activate);
		// 删除标志(默认正常)
		merchantDTO.setDelFlg(DelFlgEnum.NORMAL.getCode());
		// 商户注册来源
		merchantDTO.setSource(SourceEnum.PORTAL.getCode());
		// 真实姓名
		merUserDTO.setMerUserNickName(merLinkUser);
		// 性别
		String merUserSex = merUserDTO.getMerUserSex();
		if (StringUtils.isBlank(merUserSex)) {
			merUserDTO.setMerUserSex(SexEnum.MALE.getCode());
		}

		// 连锁直营网点不需要判断费率，使用时直接取上级费率
		if (!MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
			// 费率信息
			List<MerBusRateDTO> merBusRateDTOList = merchantDTO.getMerBusRateList();
			if (CollectionUtils.isNotEmpty(merBusRateDTOList)) {
				List<MerBusRate> parentMerBusRateList = merBusRateService.findMerBusRateByMerCode(merParentCode);
				List<String> overMsg = new ArrayList<String>();
				List<String> notFoundMsg = new ArrayList<String>();
				for (MerBusRateDTO merBusRateNew : merBusRateDTOList) {
					String providerCode = merBusRateNew.getProviderCode();
					String rateCode = merBusRateNew.getRateCode();
					String rateType = merBusRateNew.getRateType();
					if (StringUtils.isBlank(providerCode)) {
						return ResponseCode.USERS_PROVIDER_CODE_NULL;
					}
					if (StringUtils.isBlank(rateCode)) {
						return ResponseCode.USERS_RATE_CODE_NULL;
					}
					if (StringUtils.isBlank(rateType)) {
						return ResponseCode.USERS_RATE_TYPE_NULL;
					}
					if(RateCodeEnum.YKT_PAYMENT.getCode().equals(rateCode)) {
						continue;
					}
					boolean isFound = false;
					for (MerBusRate merBusRateOld : parentMerBusRateList) {
						String rateCodeOld = merBusRateOld.getRateCode();
						if(RateCodeEnum.YKT_PAYMENT.getCode().equals(rateCodeOld)) {
							continue;
						}
						if (rateCode.equals(merBusRateOld.getRateCode()) && providerCode.equals(merBusRateOld.getProviderCode()) && rateType.equals(merBusRateOld.getRateType())) {
							isFound = true;
							double rateNew = merBusRateNew.getRate();
							double rateOld = merBusRateOld.getRate();
							if (rateNew > rateOld) {
								overMsg.add(merBusRateNew.getProviderCode() + "," + merBusRateNew.getRateCode() + "," + merBusRateNew.getRateType());
								// return
								// ResponseCode.USERS_MER_RATE_OVER_PARENT;
							}
							break;
						}
					}
					if (!isFound) {
						notFoundMsg.add(merBusRateNew.getProviderCode() + "," + merBusRateNew.getRateCode() + "," + merBusRateNew.getRateType());
						// return ResponseCode.USERS_PARENT_RATE_NOT_FOUND;
					}

					if (overMsg.size() > 0) {
						msg.addAll(overMsg);
						return ResponseCode.USERS_MER_RATE_OVER_PARENT;
					}
					if (notFoundMsg.size() > 0) {
						msg.addAll(notFoundMsg);
						return ResponseCode.USERS_PARENT_RATE_NOT_FOUND;
					}
				}
			}
		}

		return ResponseCode.SUCCESS;
	}

	private String upChildMerchantCheck(MerchantDTO merchantDTO, List<String> msg) {
		// TODO：子商户更新校验
		// 商户号
		String merCode = merchantDTO.getMerCode();
		if (StringUtils.isBlank(merCode)) {
			return ResponseCode.USERS_FIND_MER_CODE_NULL;
		}

		Merchant merchantOld = merchantService.findMerchantByMerCode(merCode);
		if (merchantOld == null) {
			return ResponseCode.USERS_FIND_MERCHANT_ERR;
		}
		MerchantUser merUserOld = merchantOld.getMerchantUser();
		if (merUserOld == null) {
			return ResponseCode.USERS_FIND_USER_ERR;
		}

		// 上级商户号
		String merParentCode = merchantDTO.getMerParentCode();
		if (StringUtils.isBlank(merParentCode)) {
			return ResponseCode.USERS_MER_PARENT_CODE_NULL;
		}
		if (!merParentCode.equals(merchantOld.getMerParentCode())) {
			return ResponseCode.USERS_CHILD_PARENT_INCONFORMITY;
		}
		Merchant merchantParent = merchantService.findMerchantByMerCode(merParentCode);
		if (merchantParent == null) {
			return ResponseCode.USERS_FIND_PARENT_MERCHANT_ERR;
		}

		// 用户名
		MerchantUserDTO merUserDTO = merchantDTO.getMerchantUserDTO();
		if (merUserDTO == null) {
			return ResponseCode.USERS_MER_INFO_NULL;
		}
		String merUserName = merUserDTO.getMerUserName();
		if (StringUtils.isBlank(merUserName)) {
			return ResponseCode.USERS_USER_NAME_ERR;
		}

		// 原审核状态
		String merStateOld = merchantOld.getMerState();
		merchantDTO.setMerState(merStateOld);
		// 审核通过
		if (MerStateEnum.THROUGH.getCode().equals(merStateOld)) {

		} else if (MerStateEnum.REJECT.getCode().equals(merStateOld)) {
			// 商户类型
			String merType = merchantDTO.getMerType();
			String merParentType = merchantParent.getMerType();
			if (MerTypeEnum.AGENT.getCode().equals(merParentType)) {
				// 代理商户可以创建代理商户、连锁商户、代理商自有网点
				if (!(MerTypeEnum.AGENT.getCode().equals(merType) || MerTypeEnum.CHAIN.getCode().equals(merType) || MerTypeEnum.AGENT_MER.getCode().equals(merType))) {
					// return ResponseCode.USERS_MER_TYPE_ERR;
				}
			} else if (MerTypeEnum.CHAIN.getCode().equals(merParentType)) {
				// 商户类型
				if (StringUtils.isBlank(merType)) {
					return ResponseCode.USERS_MER_TYPE_ERR;
				}
				// 连锁商户可以创建连锁直营网点、连锁加盟网点
				if (!(MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merType))) {
					return ResponseCode.USERS_MER_TYPE_ERR;
				}
			} else {
				return ResponseCode.USERS_MERCHANT_NOT_ALLOWED_CREATE;
			}

			// 商户名称
			String merName = merchantDTO.getMerName();
			if (StringUtils.isBlank(merName)) {
				return ResponseCode.USERS_MER_NAME_NULL;
			}
			if (!merchantOld.getMerName().equals(merName)) {
				Merchant checkMerName = new Merchant();
				checkMerName.setMerName(merName);
				boolean merNameExist = merchantService.checkExist(checkMerName);
				if (merNameExist) {
					return ResponseCode.USERS_MER_NAME_EXIST;
				}
			}

			// 联系人
			String merLinkUser = merchantDTO.getMerLinkUser();
			if (StringUtils.isBlank(merLinkUser)) {
				return ResponseCode.USERS_MER_LINK_USER_NULL;
			}

			// 手机号
			String merLinkUserMobile = merchantDTO.getMerLinkUserMobile();
			if (StringUtils.isBlank(merLinkUserMobile)) {
				return ResponseCode.USERS_MOB_TEL_NULL;
			}
			if (!DDPStringUtil.isMobile(merLinkUserMobile)) {
				return ResponseCode.USERS_MOB_TEL_ERR;
			}
			MerchantUser checkUserMobile = new MerchantUser();
			checkUserMobile.setMerUserMobile(merLinkUserMobile);
			if (merchantUserService.checkExist(checkUserMobile)) {
				return ResponseCode.USERS_MOB_EXIST;
			}

			String merPro = merchantDTO.getMerPro();
			String merCity = merchantDTO.getMerCity();
			String merAdds = merchantDTO.getMerAdds();
			if (StringUtils.isBlank(merPro)) {
				return ResponseCode.USERS_MER_PRO_NULL;
			}
			if (StringUtils.isBlank(merCity)) {
				return ResponseCode.USERS_MER_CITY_NULL;
			}
			if (StringUtils.isBlank(merAdds)) {
				return ResponseCode.USERS_MER_ADDS_NULL;
			}

			// 启用标志
			String activate = merchantDTO.getActivate();
			if (StringUtils.isBlank(activate)) {
				return ResponseCode.ACTIVATE_ERROR;
			}
			merUserDTO.setActivate(activate);

			// 用户名
			if (!merUserOld.getMerUserName().equals(merUserName)) {
				MerchantUser checkUserName = new MerchantUser();
				checkUserName.setMerUserName(merUserName);
				if (merchantUserService.checkExist(checkUserName)) {
					return ResponseCode.USERS_MER_USER_NAME_EXIST;
				}
			}

			// 密码
			String merUserPWD = merUserDTO.getMerUserPWD();
			if (StringUtils.isBlank(merUserPWD)) {
				// return ResponseCode.USERS_FIND_USER_PWD_NULL;
			}

			// 用户编码
			/*
			 * String userCode = merUserDTO.getUserCode(); if
			 * (StringUtils.isBlank(userCode)) { return
			 * ResponseCode.USERS_USER_CODE_NULL; }
			 */
			merUserDTO.setUserCode(merUserOld.getUserCode());

			// 连锁直营网点不需要判断费率，使用时直接取上级费率
			if (!MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merType)) {
				// 费率信息
				List<MerBusRateDTO> merBusRateDTOList = merchantDTO.getMerBusRateList();
				if (CollectionUtils.isNotEmpty(merBusRateDTOList)) {
					if (StringUtils.isNotBlank(merParentCode)) {
						List<MerBusRate> parentMerBusRateList = merBusRateService.findMerBusRateByMerCode(merParentCode);
						List<String> overMsg = new ArrayList<String>();
						List<String> notFoundMsg = new ArrayList<String>();
						for (MerBusRateDTO merBusRateNew : merBusRateDTOList) {
							String providerCode = merBusRateNew.getProviderCode();
							String rateCode = merBusRateNew.getRateCode();
							String rateType = merBusRateNew.getRateType();
							if (StringUtils.isBlank(providerCode)) {
								return ResponseCode.USERS_PROVIDER_CODE_NULL;
							}
							if (StringUtils.isBlank(rateCode)) {
								return ResponseCode.USERS_RATE_CODE_NULL;
							}
							if (StringUtils.isBlank(rateType)) {
								return ResponseCode.USERS_RATE_TYPE_NULL;
							}
							if(RateCodeEnum.YKT_PAYMENT.getCode().equals(rateCode)) {
								continue;
							}

							boolean isFound = false;
							for (MerBusRate merBusRateOld : parentMerBusRateList) {
								String rateCodeOld = merBusRateOld.getRateCode();
								if(RateCodeEnum.YKT_PAYMENT.getCode().equals(rateCodeOld)) {
									continue;
								}
								if (providerCode.equals(merBusRateOld.getProviderCode()) && rateCode.equals(merBusRateOld.getRateCode()) && rateType.equals(merBusRateOld.getRateType())) {
									isFound = true;
									double rateNew = merBusRateNew.getRate();
									double rateOld = merBusRateOld.getRate();
									if (rateNew > rateOld) {
										overMsg.add(merBusRateNew.getProviderCode() + "," + merBusRateNew.getRateCode() + "," + merBusRateNew.getRateType());
										// return
										// ResponseCode.USERS_MER_RATE_OVER_PARENT;
									}
									break;
								}
							}
							if (!isFound) {
								notFoundMsg.add(merBusRateNew.getProviderCode() + "," + merBusRateNew.getRateCode() + "," + merBusRateNew.getRateType());
								// return
								// ResponseCode.USERS_PARENT_RATE_NOT_FOUND;
							}

							if (overMsg.size() > 0) {
								msg.addAll(overMsg);
								return ResponseCode.USERS_MER_RATE_OVER_PARENT;
							}
							if (notFoundMsg.size() > 0) {
								msg.addAll(notFoundMsg);
								return ResponseCode.USERS_PARENT_RATE_NOT_FOUND;
							}
						}
					}
				}
			}

			// 商户分类
			merchantDTO.setMerClassify(MerClassifyEnum.OFFICIAL.getCode());
			// 商户属性
			merchantDTO.setMerProperty(MerPropertyEnum.STANDARD.getCode());
			// 删除标志(默认正常)
			merchantDTO.setDelFlg(DelFlgEnum.NORMAL.getCode());
			// 真实姓名
			merUserDTO.setMerUserNickName(merLinkUser);
		} else {
			return ResponseCode.USERS_STATE_NOT_ALLOWED_UPDATE;
		}

		// 更新人
		String updateUser = merchantDTO.getUpdateUser();
		if (StringUtils.isBlank(updateUser)) {
			return ResponseCode.UPDATE_USER_NULL;
		}

		return ResponseCode.SUCCESS;
	}

	private String batchDelChildMerchantCheck(String[] merCodes, String merParentCode) {
		if (StringUtils.isBlank(merParentCode)) {
			return ResponseCode.USERS_MER_PARENT_CODE_NULL;
		}

		if (merCodes == null || merCodes.length == 0) {
			return ResponseCode.USERS_FIND_MER_CODE_NULL;
		}

		// TODO:判断删除的商户都是传入的商户的子商户

		return ResponseCode.SUCCESS;
	}

	private MerchantDTO convertToMerchantDTO(Merchant merchant) {
		MerchantDTO merchantDTO = new MerchantDTO();
		BeanUtils.copyProperties(merchant, merchantDTO, IGNORE_FIELDS);

		MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
		MerchantUser merchantUser = merchant.getMerchantUser();
		if (merchantUser != null) {
			BeanUtils.copyProperties(merchantUser, merchantUserDTO);
			merchantDTO.setMerchantUserDTO(merchantUserDTO);
		}

		MerDdpInfoDTO merDdpInfoDTO = new MerDdpInfoDTO();
		MerDdpInfo merDdpInfo = merchant.getMerDdpInfo();
		if (merDdpInfo != null) {
			BeanUtils.copyProperties(merDdpInfo, merDdpInfoDTO);
			merchantDTO.setMerDdpInfo(merDdpInfoDTO);
		}

		List<MerBusRate> merBusRateList = merchant.getMerBusRateList();
		if (merBusRateList != null && merBusRateList.size() > 0) {
			List<MerBusRateDTO> merBusRateDTOList = new ArrayList<MerBusRateDTO>();
			for (MerBusRate merBusRateTemp : merBusRateList) {
				MerBusRateDTO merBusRateDTOTemp = new MerBusRateDTO();
				BeanUtils.copyProperties(merBusRateTemp, merBusRateDTOTemp);
				merBusRateDTOList.add(merBusRateDTOTemp);
			}
			merchantDTO.setMerBusRateList(merBusRateDTOList);
		}

		if (MerPropertyEnum.CUSTOM.getCode().equals(merchant.getMerProperty())) {
			List<MerFunctionInfo> merFunctionInfoList = merchant.getMerDefineFunList();
			if (merFunctionInfoList != null && merFunctionInfoList.size() > 0) {
				List<MerFunctionInfoDTO> merFunctionInfoDTOList = new ArrayList<MerFunctionInfoDTO>();
				for (MerFunctionInfo merFunctionInfoTemp : merFunctionInfoList) {
					MerFunctionInfoDTO merFunctionInfoDTOTemp = new MerFunctionInfoDTO();
					BeanUtils.copyProperties(merFunctionInfoTemp, merFunctionInfoDTOTemp);
					merFunctionInfoDTOList.add(merFunctionInfoDTOTemp);
				}
				merchantDTO.setMerDefineFunList(merFunctionInfoDTOList);
			}
		}

		if (MerTypeEnum.EXTERNAL.getCode().equals(merchant.getMerType())) {
			MerKeyType merKeyType = merchant.getMerKeyType();
			if (merKeyType != null) {
				MerKeyTypeDTO merKeyTypeDTO = new MerKeyTypeDTO();
				BeanUtils.copyProperties(merKeyType, merKeyTypeDTO);
				merchantDTO.setMerKeyTypeDTO(merKeyTypeDTO);
			}
		}

		List<MerRateSupplement> merRateSpmtList = merchant.getMerRateSpmtList();
		if (!CollectionUtils.isEmpty(merRateSpmtList)) {
			List<MerRateSupplementDTO> merRateSpmtDTOList = new ArrayList<MerRateSupplementDTO>();
			for (MerRateSupplement merFunctionInfoTemp : merRateSpmtList) {
				MerRateSupplementDTO merRateSpmtDTO = new MerRateSupplementDTO();
				BeanUtils.copyProperties(merFunctionInfoTemp, merRateSpmtDTO);
				merRateSpmtDTOList.add(merRateSpmtDTO);
			}
			merchantDTO.setMerRateSpmtList(merRateSpmtDTOList);
		}

		if (MerTypeEnum.CHAIN_STORE_MER.getCode().equals(merchant.getMerType()) || MerTypeEnum.CHAIN_JOIN_MER.getCode().equals(merchant.getMerType())) {
			MerAutoAmt merAutoAmt = merchant.getMerAutoAmt();
			if (merAutoAmt != null) {
				MerAutoAmtDTO merAutoAmtDTO = new MerAutoAmtDTO();
				BeanUtils.copyProperties(merAutoAmt, merAutoAmtDTO);
				merchantDTO.setMerAutoAmtDTO(merAutoAmtDTO);
			}
		}

		return merchantDTO;
	}

	private Merchant buildMerchant(MerchantDTO merchantDTO) {
		Merchant merchant = new Merchant();
		BeanUtils.copyProperties(merchantDTO, merchant, IGNORE_FIELDS);

		MerchantUserDTO merUserDTO = merchantDTO.getMerchantUserDTO();
		MerchantUser merUser = new MerchantUser();
		BeanUtils.copyProperties(merUserDTO, merUser);
		merUser.setMerUserFlag(MerUserFlgEnum.ADMIN.getCode());
		merchant.setMerchantUser(merUser);

		MerDdpInfoDTO merDdpInfoDTO = merchantDTO.getMerDdpInfo();
		if (merDdpInfoDTO != null) {
			MerDdpInfo merDdpInfo = new MerDdpInfo();
			BeanUtils.copyProperties(merDdpInfoDTO, merDdpInfo);
			merchant.setMerDdpInfo(merDdpInfo);
		}

		List<MerBusRateDTO> merBusRateDTOList = merchantDTO.getMerBusRateList();
		if (merBusRateDTOList != null && merBusRateDTOList.size() > 0) {
			List<MerBusRate> merBusRateList = new ArrayList<MerBusRate>();
			for (MerBusRateDTO merBusRateDTOTemp : merBusRateDTOList) {
				MerBusRate merBusRateTemp = new MerBusRate();
				BeanUtils.copyProperties(merBusRateDTOTemp, merBusRateTemp);
				merBusRateList.add(merBusRateTemp);
			}
			merchant.setMerBusRateList(merBusRateList);
		}

		// 自定义商户
		if (MerPropertyEnum.CUSTOM.getCode().equals(merchantDTO.getMerProperty())) {
			List<MerFunctionInfoDTO> merFunctionInfoDTOList = merchantDTO.getMerDefineFunList();
			if (merFunctionInfoDTOList != null && merFunctionInfoDTOList.size() > 0) {
				List<MerFunctionInfo> merFunctionInfoList = new ArrayList<MerFunctionInfo>();
				for (MerFunctionInfoDTO merFunctionInfoDTOTemp : merFunctionInfoDTOList) {
					MerFunctionInfo merFunctionInfoTemp = new MerFunctionInfo();
					BeanUtils.copyProperties(merFunctionInfoDTOTemp, merFunctionInfoTemp);
					merFunctionInfoList.add(merFunctionInfoTemp);
				}
				merchant.setMerDefineFunList(merFunctionInfoList);
			}
		}

		MerKeyTypeDTO merKeyTypeDTO = merchantDTO.getMerKeyTypeDTO();
		if (merKeyTypeDTO != null) {
			MerKeyType merKeyType = new MerKeyType();
			BeanUtils.copyProperties(merKeyTypeDTO, merKeyType);
			merchant.setMerKeyType(merKeyType);
		}

		// 商户业务信息
		List<MerRateSupplementDTO> merRateSpmtDTOList = merchantDTO.getMerRateSpmtList();
		if (!CollectionUtils.isEmpty(merRateSpmtDTOList)) {
			List<MerRateSupplement> merRateSpmtList = new ArrayList<MerRateSupplement>();
			for (MerRateSupplementDTO merFunctionInfoDTOTemp : merRateSpmtDTOList) {
				MerRateSupplement merRateSpmt = new MerRateSupplement();
				BeanUtils.copyProperties(merFunctionInfoDTOTemp, merRateSpmt);
				merRateSpmtList.add(merRateSpmt);
			}
			merchant.setMerRateSpmtList(merRateSpmtList);
		}

		MerAutoAmtDTO merAutoAmtDTO = merchantDTO.getMerAutoAmtDTO();
		if (merAutoAmtDTO != null) {
			MerAutoAmt merAutoAmt = new MerAutoAmt();
			BeanUtils.copyProperties(merAutoAmtDTO, merAutoAmt);
			merchant.setMerAutoAmt(merAutoAmt);
		}

		return merchant;
	}

}
