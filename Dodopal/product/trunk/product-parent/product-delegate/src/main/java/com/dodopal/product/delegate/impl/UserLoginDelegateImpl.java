package com.dodopal.product.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MobileUserDTO;
import com.dodopal.api.users.facade.UserLoginFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.common.util.TrackIdHolder;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.UserLoginDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

/** 
 * @author lifeng@dodopal.com
 */
@Service("userLoginDelegate")
public class UserLoginDelegateImpl extends BaseDelegate implements UserLoginDelegate {

	@Override
	public DodopalResponse<MobileUserDTO> login(String userName, String password, String source, String loginid, String usertype) {
		UserLoginFacade facade = getFacade(UserLoginFacade.class, DelegateConstant.FACADE_USERS_URL);
		facade.setTrackId(TrackIdHolder.get());
		return facade.login(userName, password, source, loginid, usertype);
	}

}
