package com.dodopal.payment.delegate.impl;

import com.dodopal.api.account.dto.AccountFundDTO;
import com.dodopal.api.account.facade.AccountQueryFacade;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.facade.AccountManagementFacade;
import com.dodopal.api.payment.facade.PayFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.payment.delegate.AccountManagementDelegate;
import com.dodopal.payment.delegate.BaseDelegate;
import com.dodopal.payment.delegate.constant.DelegateConstant;
@Service("accountManagementDelegate")
public class AccountManagementDelegateImpl extends BaseDelegate implements AccountManagementDelegate{
	public DodopalResponse<String> accountFreeze(String custType, String custNum,String tradeNum, long amount,String operId){
		AccountManagementFacade accountManagementFacade = getFacade(AccountManagementFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		return 	accountManagementFacade.accountFreeze(custType, custNum, tradeNum, amount,operId);
	}
	public DodopalResponse<String> accountUnfreeze(String custType, String custNum,String tradeNum, long amount,String operId){
		AccountManagementFacade accountManagementFacade = getFacade(AccountManagementFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		return 	accountManagementFacade.accountUnfreeze(custType, custNum, tradeNum, amount,operId);
	}
	public DodopalResponse<String> accountDeduct(String custType,String custNum, String tradeNum, long amount,String operId) {
		AccountManagementFacade accountManagementFacade = getFacade(AccountManagementFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		return 	accountManagementFacade.accountDeduct(custType, custNum, tradeNum, amount,operId);
	}

	/**
	 * @description 账户充值
	 * @param custType 客户类型
	 * @param custNum 客户号
	 * @param tradeNum 交易流水号
	 * @param amount 交易金额
	 * @return
	 */
	public DodopalResponse<String> accountRecharge(String custType,String custNum, String tradeNum, long amount,String operId) {
		AccountManagementFacade accountManagementFacade = getFacade(AccountManagementFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		return 	accountManagementFacade.accountRecharge(custType, custNum, tradeNum, amount,operId);
	}
	/**
	 * @description 查询账户余额
	 * @param custType 客户类型
	 * @param custNum 客户号
	 * @return
	 */
	public DodopalResponse<AccountFundDTO> findAccountBalance(String custType,String custNum) {
		AccountQueryFacade accountManagementFacade = getFacade(AccountQueryFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		return 	accountManagementFacade.findAccountBalance(custType,custNum);
	}

	@Override
	public DodopalResponse<String> checkRecharge(String custType, String custNum, long amount) {
		AccountManagementFacade accountManagementFacade = getFacade(AccountManagementFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		return 	accountManagementFacade.checkRecharge(custType, custNum,amount);
	}
	
	
	public DodopalResponse<String> Test() {
		PayFacade accountManagementFacade = getFacade(PayFacade.class, "pay.test.url"); 
		DodopalResponse<String> re = new DodopalResponse<String>();
		System.out.println("11111111");
		return 	re;
	}
}
