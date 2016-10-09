package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.users.business.dao.MobileTypeWhiteListMapper;
import com.dodopal.users.business.model.MobileTypeWhiteList;
import com.dodopal.users.business.service.MobileTypeWhiteListService;

/**
 * @author lifeng@dodopal.com
 */
@Service
public class MobileTypeWhiteListServiceImpl implements MobileTypeWhiteListService {
	@Autowired
	MobileTypeWhiteListMapper mobileTypeWhiteListMapper;

	@Override
	public List<MobileTypeWhiteList> findAllWhiteList() {
		return mobileTypeWhiteListMapper.findAllWhiteList();
	}

}
