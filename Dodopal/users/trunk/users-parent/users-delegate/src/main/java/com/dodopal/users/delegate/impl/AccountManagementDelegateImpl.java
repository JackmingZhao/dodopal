package com.dodopal.users.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.facade.AccountManagementFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.delegate.AccountManagementDelegate;
import com.dodopal.users.delegate.BaseDelegate;
import com.dodopal.users.delegate.constant.DelegateConstant;

@Service("accountManagementDelegate")
public class AccountManagementDelegateImpl extends BaseDelegate implements AccountManagementDelegate {

	/**
	 * @description 创建账户
	 * @param custType
	 *            客户类型:0 个人;1 企业
	 * @param custNum
	 *            客户号
	 * @param accType
	 *            账户类型:0 资金;1 授信
	 * @param merType
	 *            商户类型，个人为99
	 * @param userId
	 *            操作员ID
	 * @return 响应码
	 */
	public DodopalResponse<String> createAccount(String custType, String custNum, String accType, String merType, String userId) {
		AccountManagementFacade facade = getFacade(AccountManagementFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		return facade.createAccount(custType, custNum, accType, merType, userId);
	}

}
