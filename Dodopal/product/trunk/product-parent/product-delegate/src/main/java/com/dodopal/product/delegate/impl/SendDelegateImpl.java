package com.dodopal.product.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.facade.SendFacade;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.SendDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

/**
 * @author lifeng@dodopal.com
 */
@Service("sendDelegate")
public class SendDelegateImpl extends BaseDelegate implements SendDelegate {

	@Override
	public DodopalResponse<Map<String, String>> sendNoCheck(String mobileNum) {
		SendFacade sendFacade = getFacade(SendFacade.class, DelegateConstant.FACADE_USERS_URL);
		return sendFacade.sendNoCheck(mobileNum);
	}

	@Override
	public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType) {
		SendFacade sendFacade = getFacade(SendFacade.class, DelegateConstant.FACADE_USERS_URL);
		return sendFacade.send(mobileNum, codeType);
	}

	@Override
	public DodopalResponse<String> mobileCodeCheck(String mobileNum, String dypwd, String serialNumber) {
		SendFacade sendFacade = getFacade(SendFacade.class, DelegateConstant.FACADE_USERS_URL);
		return sendFacade.moblieCodeCheck(mobileNum, dypwd, serialNumber);
	}

}
