package com.dodopal.payment.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountTransferListDTO;
import com.dodopal.api.account.facade.AccountManagementFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.payment.delegate.BaseDelegate;
import com.dodopal.payment.delegate.PayTraTransactionDelegate;
import com.dodopal.payment.delegate.constant.DelegateConstant;

@Service("PayTraTransactionDelegate")
public class PayTraTransactionDelegateImpl extends BaseDelegate implements PayTraTransactionDelegate {

    @Override
    public DodopalResponse<Boolean> accountTransfer(AccountTransferListDTO accountTransferDTOList){
        AccountManagementFacade facade = getFacade(AccountManagementFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
        return facade.accountTransfer(accountTransferDTOList);
    }
}
