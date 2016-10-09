package com.dodopal.users.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerchantExtendMapper;
import com.dodopal.users.business.model.MerchantExtend;
import com.dodopal.users.business.service.MerchantExtendService;

/** 
 * @author lifeng@dodopal.com
 */
@Service
public class MerchantExtendServiceImpl implements MerchantExtendService {
	@Autowired
	private MerchantExtendMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public MerchantExtend findByMerCode(String merCode) {
		return mapper.findByMerCode(merCode);
	}

}
