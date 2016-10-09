package com.dodopal.product.business.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.enums.MoblieCodeTypeEnum;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.service.SendService;
import com.dodopal.product.delegate.SendDelegate;

/** 
 * @author lifeng@dodopal.com
 */
@Service("sendService")
public class SendServiceImpl implements SendService {
	@Autowired
	private SendDelegate sendDelegate;

	@Override
	@Transactional(readOnly = true)
	public DodopalResponse<Map<String, String>> sendNoCheck(String mobileNum) {
		DodopalResponse<Map<String, String>> response = null;
		try {
			response = sendDelegate.sendNoCheck(mobileNum);
		} catch (HessianRuntimeException e) {
			throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public DodopalResponse<Map<String, String>> send(String mobileNum, MoblieCodeTypeEnum codeType) {
		DodopalResponse<Map<String, String>> response = null;
		try {
			response = sendDelegate.send(mobileNum, codeType);
		} catch (HessianRuntimeException e) {
			throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public DodopalResponse<String> mobileCodeCheck(String mobileNum, String dypwd, String serialNumber) {
		DodopalResponse<String> response = null;
		try {
			response = sendDelegate.mobileCodeCheck(mobileNum, dypwd, serialNumber);
		} catch (Exception e) {
			throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return response;
	}

}
