package com.dodopal.product.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.api.users.dto.MobileTypeWhiteListDTO;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.service.MobileService;
import com.dodopal.product.delegate.MobileTypeWhiteListDelegate;

/** 
 * @author lifeng@dodopal.com
 */
@Service
public class MobileServiceImpl implements MobileService {
	@Autowired
	MobileTypeWhiteListDelegate mobileTypeWhiteListDelegate;

	@Override
	public DodopalResponse<List<MobileTypeWhiteListDTO>> findAllWhiteList() {
		return mobileTypeWhiteListDelegate.findAllWhiteList();
	}

}
