package com.dodopal.payment.business.service.impl;

import com.dodopal.api.account.dto.AccountFundDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.HessianException;
import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.exception.DDPException;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.payment.business.service.AccountManagementService;
import com.dodopal.payment.delegate.AccountManagementDelegate;


@Service
public class AccountManagementServiceImpl implements AccountManagementService{
    private final static Logger log = LoggerFactory.getLogger(AccountManagementServiceImpl.class);

	@Autowired
	AccountManagementDelegate accountManagementDelegate;

	
	public DodopalResponse<String> accountFreeze(String custType,
			String custNum, String tradeNum, long amount,String operId) {
		DodopalResponse<String> dodopalResponse;
		try{
			dodopalResponse = accountManagementDelegate.accountFreeze(custType, custNum, tradeNum, amount,operId);
		}catch(HessianRuntimeException e){
			throw new DDPException(ResponseCode.PAY_ACCOUNT_HESSIAN_ERROR);
		}
		return dodopalResponse;
	}

	@Override
	public DodopalResponse<String> accountUnfreeze(String custType,
			String custNum, String tradeNum, long amount,String operId) {
		DodopalResponse<String> dodopalResponse;
		try{
			dodopalResponse = accountManagementDelegate.accountUnfreeze(custType, custNum, tradeNum, amount,operId);
		}catch(HessianRuntimeException e){
			throw new DDPException(ResponseCode.PAY_ACCOUNT_HESSIAN_ERROR);
		}
		return dodopalResponse;
	}

	@Override
	public DodopalResponse<String> accountDeduct(String custType,
			String custNum, String tradeNum, long amount,String operId) {
		DodopalResponse<String> dodopalResponse;
		try{
			dodopalResponse = accountManagementDelegate.accountDeduct(custType, custNum, tradeNum, amount,operId);
		}catch(HessianRuntimeException e){
			throw new DDPException(ResponseCode.PAY_ACCOUNT_HESSIAN_ERROR);
		}
		return dodopalResponse;
	}

	/**
	 * 账户充值功能实现
	 * @param custType
	 * @param custNum
	 * @param tradeNum
	 * @param amount
	 * @return
	 */
	@Override
	public DodopalResponse<String> accountRecharge(String custType, String custNum, String tradeNum, long amount,String operId) {
		DodopalResponse<String> dodopalResponse;
		try{
			dodopalResponse = accountManagementDelegate.accountRecharge(custType, custNum, tradeNum, amount,operId);
		}catch(HessianRuntimeException e){
			throw new DDPException(ResponseCode.PAY_ACCOUNT_HESSIAN_ERROR);
		}
		return dodopalResponse;
	}

	/**
	 * @descripton  账户可用余额查询
	 * @param custType
	 * @param custNum
	 * @return
	 */
	@Override
	public DodopalResponse<AccountFundDTO> findAccountBalance(String custType, String custNum) {
		DodopalResponse<AccountFundDTO> dodopalResponse;
		try{
			dodopalResponse = accountManagementDelegate.findAccountBalance(custType, custNum);
		}catch(HessianRuntimeException e){
			throw new DDPException(ResponseCode.PAY_ACCOUNT_HESSIAN_ERROR);
		}
		return dodopalResponse;
	}

	@Override
	public DodopalResponse<String> checkRecharge(String custType, String custNum, long amount) {
		DodopalResponse<String> dodopalResponse;
		try{
			dodopalResponse = accountManagementDelegate.checkRecharge(custType, custNum, amount);
		}catch(HessianRuntimeException e){
			throw new DDPException(ResponseCode.PAY_ACCOUNT_HESSIAN_ERROR);
		}
		return dodopalResponse;
	}
}
