package com.dodopal.product.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.api.users.facade.MerchantUserFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.MerUserDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月29日 上午11:46:05
 */
@Service("merUserDelegate")
public class MerUserDelegateImpl extends BaseDelegate implements MerUserDelegate {
	public DodopalResponse<MerchantUserDTO> findUserInfoByUserName(String userNameOrMobile){
	    MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        return merUserFacade.findUserInfoByUserName(userNameOrMobile);
	}

	@Override
	public DodopalResponse<Boolean> modifyPayInfoFlg(MerchantUserDTO userDTO) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        return merUserFacade.modifyPayInfoFlg(userDTO);
	}

	@Override
	public DodopalResponse<MerchantUserDTO> findUserInfoByUserCode(
			String userCode) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        return merUserFacade.findUserInfoByUserCode(userCode);
	}
	public DodopalResponse<Boolean> updateMerUserBusCity(String cityCode, String userId){
	    MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        return merUserFacade.updateMerUserBusCity(cityCode, userId);
	}

	@Override
	public DodopalResponse<Boolean> resetPWDByMobile(String mobile, String pwd) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
	    return merUserFacade.resetPWDByMobile(mobile, pwd);
	}

 

    @Override
    public DodopalResponse<String> findNickNameById(String id) {
        MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        // TODO Auto-generated method stub
        return merUserFacade.findNickNameByUserId(id);
    }

    @Override
    public DodopalResponse<MerchantUserDTO> findUserInfoById(String id) {
        MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
        return merUserFacade.findUserInfoById(id);
    }

	@Override
	public DodopalResponse<Boolean> updateUserPwdByOldUserId(String userid, String usertype, String userpwd) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merUserFacade.updateUserPwdByOldUserId(userid, usertype, userpwd);
	}

	@Override
	public DodopalResponse<List<MerchantUserDTO>> findMerchantUserList(MerchantUserDTO merUserDTO) {
		MerchantUserFacade merUserFacade = getFacade(MerchantUserFacade.class, DelegateConstant.FACADE_USERS_URL);
		return merUserFacade.findMerchantUserList(merUserDTO);
	}
}
