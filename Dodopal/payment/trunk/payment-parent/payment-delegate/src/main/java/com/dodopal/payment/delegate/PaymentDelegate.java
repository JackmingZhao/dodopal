package com.dodopal.payment.delegate;

import java.util.Map;

import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.common.model.DodopalResponse;

public interface PaymentDelegate {

	
	DodopalResponse<Map<String, String>> checkMerInfo(String merCode);
	
	DodopalResponse<AccountFundDTO> findAccountBalance(String aType, String custNum);

	/**
	 * @description 获取自动转账标识
	 * @param merCode
	 * @return
	 */
	DodopalResponse<String> getIsAuto(String merCode);

	/**
	 * @description 获取上级商户代码
	 * @param merCode
	 * @return
	 */
	DodopalResponse<String> getParentId(String merCode);

}
