package com.dodopal.users.business.dao;

import java.util.List;

import com.dodopal.users.business.model.MobileTypeWhiteList;

/**
 * @author lifeng@dodopal.com
 */

public interface MobileTypeWhiteListMapper {
	/**
	 * 查询所有白名单手机
	 * 
	 * @return
	 */
	public List<MobileTypeWhiteList> findAllWhiteList();
}
