package com.dodopal.users.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerchantUserExtendMapper;
import com.dodopal.users.business.model.MerchantUserExtend;
import com.dodopal.users.business.service.MerchantUserExtendService;

/**
 * @author lifeng@dodopal.com
 */
@Service
public class MerchantUserExtendServiceImpl implements MerchantUserExtendService {
	@Autowired
	private MerchantUserExtendMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public MerchantUserExtend findByUserCode(String userCode) {
		return mapper.findByUserCode(userCode);
	}

}
