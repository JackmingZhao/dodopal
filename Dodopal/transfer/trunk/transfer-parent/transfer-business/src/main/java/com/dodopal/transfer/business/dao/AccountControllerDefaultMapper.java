package com.dodopal.transfer.business.dao;

import com.dodopal.transfer.business.model.target.AccountControllerDefault;

public interface AccountControllerDefaultMapper {
    /**
     * 查询默认风控表的值
     * @param merType
     * @return
     */
    public AccountControllerDefault queryControlDefault(String merType);
}
