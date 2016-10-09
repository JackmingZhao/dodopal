package com.dodopal.product.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.CommonUserDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.api.users.facade.RegisterFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.RegisterDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

/** 
 * @author lifeng@dodopal.com
 */
@Service("registerDelegate")
public class RegisterDelegateImpl extends BaseDelegate implements RegisterDelegate {

	@Override
	public DodopalResponse<Boolean> checkUserNameExist(String userName) {
		RegisterFacade facade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.checkUserNameExist(userName);
	}

	@Override
	public DodopalResponse<Boolean> registerUser(String mobile, String userName, String password, String source, String cityCode) {
		RegisterFacade facade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.registerUserForMobile(mobile, userName, password, source, cityCode);
	}

	@Override
	public DodopalResponse<Boolean> checkMobileExist(String mobile) {
		RegisterFacade facade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.checkMobileExist(mobile);
	}
	
	@Override
	public DodopalResponse<Map<String,String>> registerComUser(CommonUserDTO user) {
		RegisterFacade facade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.registerComUser(user);
	}

	@Override
	public DodopalResponse<String> registerMerchantForVC(MerchantDTO merchant) {
		RegisterFacade facade = getFacade(RegisterFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.registerMerchantForVC(merchant);
	}
}
