package com.dodopal.users.business.dao;

import com.dodopal.users.business.model.MerchantUserExtend;

/** 
 * @author lifeng@dodopal.com
 */

public interface MerchantUserExtendMapper {

	/**
	 * 根据用户编码查询用户扩展信息
	 * @param userCode
	 * @return
	 */
	public MerchantUserExtend findByUserCode(String userCode);
}
