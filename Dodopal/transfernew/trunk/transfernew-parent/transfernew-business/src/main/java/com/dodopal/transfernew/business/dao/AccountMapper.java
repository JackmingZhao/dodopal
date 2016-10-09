package com.dodopal.transfernew.business.dao;

import com.dodopal.transfernew.business.model.transfer.Account;

public interface AccountMapper {

    /**
     * 获取sequence序列的下一个值
     */
    public String getSequenceNextId();

    /**
     * 增加主账户记录
     * @param account
     */
    public void addAccount(Account account);


}
