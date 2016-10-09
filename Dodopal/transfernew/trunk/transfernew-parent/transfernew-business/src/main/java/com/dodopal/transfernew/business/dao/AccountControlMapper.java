package com.dodopal.transfernew.business.dao;

import com.dodopal.transfernew.business.model.transfer.AccountControl;

public interface AccountControlMapper {

    /**
     * 增加风控记录
     * @param accountControl
     */
    public void addAccountControl(AccountControl accountControl);
    

}
