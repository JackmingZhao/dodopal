package com.dodopal.users.business.service;

import com.dodopal.users.business.model.MerchantUserExtend;

/** 
 * @author lifeng@dodopal.com
 */

public interface MerchantUserExtendService {
	public MerchantUserExtend findByUserCode(String userCode);
}
