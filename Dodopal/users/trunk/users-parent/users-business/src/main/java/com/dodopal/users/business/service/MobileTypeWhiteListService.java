package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.users.business.model.MobileTypeWhiteList;

/**
 * 手机型号白名单
 * 
 * @author lifeng@dodopal.com
 */

public interface MobileTypeWhiteListService {
	public List<MobileTypeWhiteList> findAllWhiteList();
}
