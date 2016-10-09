package com.dodopal.transfernew.business.dao;

import com.dodopal.transfernew.business.model.transfer.AccountFund;

public interface AccountFundMapper {

    /**
     * 增加资金授信表记录
     * @param accountFund
     */
    public void addAccountFund(AccountFund accountFund);

}
