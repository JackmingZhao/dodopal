package com.dodopal.users.business.dao;

import com.dodopal.users.business.model.MerchantExtend;

/** 
 * @author lifeng@dodopal.com
 */

public interface MerchantExtendMapper {
	public MerchantExtend findByMerCode(String merCode);
}
