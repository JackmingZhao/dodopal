package com.dodopal.account.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.account.business.model.AccountChildMerchant;
import com.dodopal.account.business.model.AccountFund;
import com.dodopal.account.business.model.query.AccountChildMerchantQuery;
import com.dodopal.account.business.model.query.ChildMerFundChangeQuery;
import com.dodopal.common.model.DodopalDataPage;

public interface AccountChildMerchantMapper {

    /**
     * 根据上级商户编号 查询其各个 子商户的 总余额及账户信息
     * @param query 查询条件封装的实体
     * @return List<AccountChildMerchant>
     */
    public List<AccountChildMerchant> findAccountChildMerByPage(AccountChildMerchantQuery query);
    
    /**
     * 根据上级商户编号 查询其各个 子商户的 总余额及账户信息
     * @param query 查询条件封装的实体
     * @return List<AccountChildMerchant>
     */
    public List<AccountChildMerchant> findAccountChildMerByList(AccountChildMerchantQuery query);
  
}
