package com.dodopal.account.business.dao;

import java.util.List;

import com.dodopal.account.business.model.AccountControllerDefault;

public interface AccountRiskControllerDefaultMapper {

    public List<AccountControllerDefault> findAccountRiskControllerDefaultItemList();

    public AccountControllerDefault findAccountRiskControllerDefaultById(String id);
    
    public void updateAccountRiskControllerDefaultItem(AccountControllerDefault acctDefault);
    
}
