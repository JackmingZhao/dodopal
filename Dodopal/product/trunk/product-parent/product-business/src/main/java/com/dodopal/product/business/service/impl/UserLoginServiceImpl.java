package com.dodopal.product.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.MobileUserDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.service.UserLoginService;
import com.dodopal.product.delegate.UserLoginDelegate;

/**
 * @author lifeng@dodopal.com
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {
	@Autowired
	private UserLoginDelegate userLoginDelegate;

	@Override
	@Transactional(readOnly = true)
	public DodopalResponse<MobileUserDTO> login(String userName, String password, String source, String loginid, String usertype) {
		DodopalResponse<MobileUserDTO> response = null;
		try {
			response = userLoginDelegate.login(userName, password, source, loginid, usertype);
		} catch (HessianRuntimeException e) {
			throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return response;
	}

}
