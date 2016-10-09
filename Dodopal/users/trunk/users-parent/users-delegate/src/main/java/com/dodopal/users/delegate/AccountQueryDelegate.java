package com.dodopal.users.delegate;

import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.common.model.DodopalResponse;

/** 
 * @author lifeng@dodopal.com
 */

public interface AccountQueryDelegate {
	/**
	 * 查询门户首页 可用余额 资金授信账户信息
	 * @param aType 个人or企业
	 * @param custNum 用户号 or 商户号
	 * @return
	 */
	public DodopalResponse<AccountFundDTO> findAccountBalance(String aType,String custNum);
}
