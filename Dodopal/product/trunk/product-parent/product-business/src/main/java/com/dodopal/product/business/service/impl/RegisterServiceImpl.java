package com.dodopal.product.business.service.impl;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.users.dto.CommonUserDTO;
import com.dodopal.api.users.dto.MerchantDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.CommonUser;
import com.dodopal.product.business.dao.ProductPurchaseOrderMapper;
import com.dodopal.product.business.service.RegisterService;
import com.dodopal.product.delegate.RegisterDelegate;

/** 
 * @author lifeng@dodopal.com
 */
@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private RegisterDelegate registerDelegate;
	
	@Autowired
	private ProductPurchaseOrderMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public DodopalResponse<Boolean> checkUserNameExist(String userName) {
		DodopalResponse<Boolean> response = null;
		try {
			response = registerDelegate.checkUserNameExist(userName);
		} catch (HessianRuntimeException e) {
			throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public DodopalResponse<Boolean> registerUser(String mobile, String userName, String password, String source, String cityCode) {
		DodopalResponse<Boolean> response = null;
		try {
			response = registerDelegate.registerUser(mobile, userName, password, source, cityCode);
		} catch (HessianRuntimeException e) {
			throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return response;
	}

	
	@Override
	public DodopalResponse<Map<String,String>> registerComUser(CommonUser user) {
		DodopalResponse<Map<String,String>> response = null;
		try {
			CommonUserDTO userDTO = new CommonUserDTO();
            if (user != null) {
                PropertyUtils.copyProperties(userDTO, user);
            }
			response = registerDelegate.registerComUser(userDTO);
		} catch (HessianRuntimeException e) {
			throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}catch (Exception e) {
			 response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}
	
	@Override
	public DodopalResponse<Boolean> queryOrderFlag(String cardno,String queryDate) {
		DodopalResponse<Boolean> response = new DodopalResponse<Boolean>();
		response.setCode(ResponseCode.SUCCESS);
		try {
		
			int count = mapper.queryOrderFlag(cardno,queryDate);
			if(count>0){
				response.setResponseEntity(true);
			}else{
				response.setResponseEntity(false);
			}
		}catch (Exception e) {
			 response.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return response;
	}

	@Override
	public DodopalResponse<Boolean> checkMobileExist(String mobile) {
		DodopalResponse<Boolean> response = null;
		try {
			response = registerDelegate.checkMobileExist(mobile);
		} catch (HessianRuntimeException e) {
			throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return response;
	}

	@Override
	public DodopalResponse<String> registerMerchantForVC(MerchantDTO merchant) {
		DodopalResponse<String> response = null;
		try {
			response = registerDelegate.registerMerchantForVC(merchant);
		} catch (HessianRuntimeException e) {
			throw new DDPException(ResponseCode.PRODUCT_CALL_USERS_ERROR);
		}
		return response;
	}

}
