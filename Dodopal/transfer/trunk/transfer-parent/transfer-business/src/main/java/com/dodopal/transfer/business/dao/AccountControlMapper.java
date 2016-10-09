package com.dodopal.transfer.business.dao;

import com.dodopal.transfer.business.model.target.AccountControl;

public interface AccountControlMapper {

    /**
     * 增加风控记录
     * @param accountControl
     */
    public void addAccountControl(AccountControl accountControl);
    

}
