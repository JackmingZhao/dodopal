package com.dodopal.users.business.service;

import com.dodopal.users.business.model.MerchantExtend;

/** 
 * @author lifeng@dodopal.com
 */

public interface MerchantExtendService {
	public MerchantExtend findByMerCode(String merCode);
}
