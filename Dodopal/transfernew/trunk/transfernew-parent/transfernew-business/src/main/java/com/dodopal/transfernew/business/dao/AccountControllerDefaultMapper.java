package com.dodopal.transfernew.business.dao;

import com.dodopal.transfernew.business.model.transfer.AccountControllerDefault;

public interface AccountControllerDefaultMapper {
    /**
     * 查询默认风控表的值
     * @param merType
     * @return
     */
    public AccountControllerDefault queryControlDefault(String merType);
}
