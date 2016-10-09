package com.dodopal.portal.delegate.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.dto.query.MerchantUserQueryDTO;
import com.dodopal.api.users.facade.MerchantUserFacade;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.MerUserDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月29日 上午11:46:05
 */
@Service("merUserDelegate")
public class MerUserDelegateImpl extends BaseDelegate implements MerUserDelegate {

	@Override
	public DodopalResponse<List<MerchantUserDTO>> findMerUserList(MerchantUserDTO userDTO) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merUserFacade.findUserInfoList(userDTO);
	}

	@Override
	public DodopalResponse<MerchantUserDTO> findMerUser(String userID) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merUserFacade.findUserInfoById(userID);
	}

	@Override
	public DodopalResponse<Boolean> resetPwdByMobile(String mobile, String pwd) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merUserFacade.resetPWDByMobile(mobile, pwd);
	}


	@Override
	public DodopalResponse<Boolean> updateUser(MerchantUserDTO userDTO) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merUserFacade.updateUser(userDTO);
	}

	@Override
	public DodopalResponse<Map<String, String>> startOrStop(List<String> idList, ActivateEnum activate,String updateUser) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merUserFacade.toStopOrStartUser(activate, idList, updateUser);
	}

	@Override
	public DodopalResponse<DodopalDataPage<MerchantUserDTO>> findMerUsersByPage(MerchantUserQueryDTO dto) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merUserFacade.findUserInfoListByPage(dto);
	}

	@Override
	public DodopalResponse<String> findMerUserNameByMobile(String mobile) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merUserFacade.findMerUserNameByMobile(mobile);
	}
	public DodopalResponse<MerchantUserDTO> findUserInfoByUserName(String userNameOrMobile){
	    MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        return merUserFacade.findUserInfoByUserName(userNameOrMobile);
	}
	
	public DodopalResponse<Boolean> updateMerUserBusCity(String cityCode, String userId){
	    MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        return merUserFacade.updateMerUserBusCity(cityCode, userId);
	}
}
