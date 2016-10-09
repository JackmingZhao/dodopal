package com.dodopal.transfer.business.dao;

import com.dodopal.transfer.business.model.target.Account;

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
