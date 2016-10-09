package com.dodopal.portal.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.MerchantUserBean;
import com.dodopal.portal.business.constant.PortalConstants;
import com.dodopal.portal.business.service.MerchantUserService;
import com.dodopal.portal.delegate.MerUserDelegate;
import com.dodopal.portal.delegate.SendDelegate;

@Service
public class MerchantUserServiceImpl implements MerchantUserService {

	@Autowired
	MerUserDelegate merUserDelegate;
	
	@Autowired
	SendDelegate sendDelegate;
	private final static Logger log = LoggerFactory.getLogger(MerchantUserServiceImpl.class);

	/**
	 * 重置用户密码
	 * @param 用户ID
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public DodopalResponse<Map<String, String>> resetPwd(String userId) {
		DodopalResponse<Map<String, String>> rtResponse = new DodopalResponse<Map<String, String>>();
		try {
			//取得用户个人信息
			DodopalResponse<MerchantUserDTO> getResponse = merUserDelegate.findMerUser(userId);
			if (!ResponseCode.SUCCESS.equals(getResponse.getCode())) {
				rtResponse.setCode(getResponse.getCode());
				return rtResponse;
			}
			MerchantUserDTO userDTO = getResponse.getResponseEntity();
			if (!userDTO.getMerchantState().equals(MerStateEnum.THROUGH.getCode())) {
				rtResponse.setCode(ResponseCode.USERS_MER_STATE_ERR);
				return rtResponse;
			}
			DodopalResponse<Map<String, String>> getResSendMsg = sendDelegate.send(userId, MoblieCodeTypeEnum.USER_PWD);
			if (!ResponseCode.SUCCESS.equals(getResSendMsg.getCode())) {
				//		        String dypwd = getResSendMsg.getResponseEntity().get("dypwd");
				//		        String pwdseq = getResSendMsg.getResponseEntity().get("pwdseq");
				Map pwdMap = new HashMap();
				pwdMap.put(PortalConstants.PWDSEQ, getResSendMsg.getResponseEntity().get("pwdseq"));
				pwdMap.put(PortalConstants.PWDRESET, getResSendMsg.getResponseEntity().get("dypwd"));
				rtResponse.setCode(getResSendMsg.getCode());
				rtResponse.setResponseEntity(pwdMap);
				return rtResponse;
			}
		}
		catch (HessianRuntimeException e) {
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.debug("MerUserServiceImpl call error" + e);
			rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
			// throw new DDPException(ResponseCode.SYSTEM_ERROR);
		}
		return rtResponse;
	}

	/* 
	 * 发送验证码
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public DodopalResponse<Map<String, String>> sendAuthCode(String mobile) {
		DodopalResponse<Map<String, String>> rtResponse = new DodopalResponse<Map<String, String>>();
		try {
			String merUserName = merUserDelegate.findMerUserNameByMobile(mobile).getResponseEntity();
			if (null == merUserName) {
				rtResponse.setCode(ResponseCode.USERS_MOB_NOT_EXIST);
			} else {
				DodopalResponse<Map<String, String>> getResSendMsg = sendDelegate.sendNoCheck(mobile);
				if (ResponseCode.SUCCESS.equals(getResSendMsg.getCode())) {
					Map pwdMap = new HashMap();
					pwdMap.put(PortalConstants.PWDSEQ, getResSendMsg.getResponseEntity().get("pwdseq"));
					pwdMap.put(PortalConstants.PWDRESET, getResSendMsg.getResponseEntity().get("dypwd"));
					pwdMap.put(PortalConstants.RESETNAME, merUserName);
					rtResponse.setCode(getResSendMsg.getCode());
					rtResponse.setResponseEntity(pwdMap);
					return rtResponse;
				}
			}
		}
		catch (HessianRuntimeException e) {
			e.printStackTrace();
			rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.debug("MerUserServiceImpl call error" + e);
			rtResponse.setCode(ResponseCode.SYSTEM_ERROR);
			// throw new DDPException(ResponseCode.SYSTEM_ERROR);
		}
		return rtResponse;
	}

	public DodopalResponse<String> checkAuthCode(String mobile, String code, String seq) {
		DodopalResponse<String> rtResponse = new DodopalResponse<String>();
		if (StringUtils.isNotBlank(seq) && StringUtils.isNotBlank(code) && StringUtils.isNotBlank(mobile)) {
			rtResponse = sendDelegate.moblieCodeCheck(mobile, code, seq);
		} else {
			rtResponse.setCode(ResponseCode.USERS_MOB_TEL_NULL);
		}
		return rtResponse;
	}

	@Override
	public DodopalResponse<Boolean> modifyPWD(String mobile, String pwd) {
		DodopalResponse<Boolean> getResponse = merUserDelegate.resetPwdByMobile(mobile, pwd);
		return getResponse;
	}

	@Override
	public DodopalResponse<String> findMerUserNameByMobile(String mobile) {
		DodopalResponse<String> getResponse = merUserDelegate.findMerUserNameByMobile(mobile);
		return getResponse;
	}

	@Override
	public DodopalResponse<MerchantUserBean> findMerUserInfo(MerchantUserBean bean) {
		return null;
	}
    
    public DodopalResponse<MerchantUserBean> findUserInfoByMobileOrUserName(String userNameOrMobile){
        DodopalResponse<MerchantUserDTO> getResponse = merUserDelegate.findUserInfoByUserName(userNameOrMobile);
        DodopalResponse<MerchantUserBean> response = new DodopalResponse<MerchantUserBean>();
        MerchantUserBean bean = new MerchantUserBean();
        if(null!=getResponse.getResponseEntity()){
            try {
                PropertyUtils.copyProperties(bean, getResponse.getResponseEntity());
            }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                response.setCode(ResponseCode.SYSTEM_ERROR);
                e.printStackTrace();
            }
        }
        response.setCode(getResponse.getCode());
        response.setResponseEntity(bean);
        return response;
        
    }

	@Override
	public DodopalResponse<Boolean> updateMerUserBusCity(String cityCode,String userId) {
        DodopalResponse<Boolean> response =  merUserDelegate.updateMerUserBusCity(cityCode, userId);
        return response;
	}
}
