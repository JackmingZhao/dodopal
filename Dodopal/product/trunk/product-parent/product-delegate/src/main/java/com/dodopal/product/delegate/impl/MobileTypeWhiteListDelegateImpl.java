package com.dodopal.product.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MobileTypeWhiteListDTO;
import com.dodopal.api.users.facade.MobileTypeWhiteListFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.MobileTypeWhiteListDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

/**
 * @author lifeng@dodopal.com
 */
@Service("mobileTypeWhiteListDelegate")
public class MobileTypeWhiteListDelegateImpl extends BaseDelegate implements MobileTypeWhiteListDelegate {

	@Override
	public DodopalResponse<List<MobileTypeWhiteListDTO>> findAllWhiteList() {
		MobileTypeWhiteListFacade facade = getFacade(MobileTypeWhiteListFacade.class, DelegateConstant.FACADE_USERS_URL);
		return facade.findAllWhiteList();
	}

}
