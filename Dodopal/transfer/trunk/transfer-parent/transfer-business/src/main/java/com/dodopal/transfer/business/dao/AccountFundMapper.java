package com.dodopal.transfer.business.dao;

import com.dodopal.transfer.business.model.target.AccountFund;

public interface AccountFundMapper {

    /**
     * 增加资金授信表记录
     * @param accountFund
     */
    public void addAccountFund(AccountFund accountFund);

}
