package com.dodopal.account.business.service;

import java.util.List;

import com.dodopal.account.business.model.ChildMerchantAccountChange;
import com.dodopal.account.business.model.AccountChildMerchant;
import com.dodopal.account.business.model.query.AccountChildMerchantQuery;
import com.dodopal.account.business.model.query.ChildMerFundChangeQuery;
import com.dodopal.common.model.DodopalDataPage;

/**
 * 查询子商户 账户信息
 * @author xiongzhijing@dodopal.com
 * @version 2015年9月8日
 *
 */
public interface AccountChildMerchantService {
    /**
     * 根据上级商户 查询其子商户的总余额 及账户信息 （分页） 
     * @param query 查询条件封装的实体
     * @return DodopalDataPage<AccountChildMerchant>
     */
    public DodopalDataPage<AccountChildMerchant> findAccountChildMerByPage(AccountChildMerchantQuery query);
    
    /**
     * 根据上级商户 查询其子商户的总余额 及账户信息
     * @param query 查询条件封装的实体
     * @return List<AccountChildMerchant>
     */
    public List<AccountChildMerchant> findAccountChildMerByList(AccountChildMerchantQuery query);
    
    /**
     * 根据上级商户编号 查询 子商户的资金变更记录(分页)
     * @param query 查询条件封装的实体
     * @return DodopalDataPage<AccountFundDTO>
     */
    public DodopalDataPage<ChildMerchantAccountChange>  findAccountChangeChildMerByPage(ChildMerFundChangeQuery query);
   
    /**
     * 根据上级商户编号 查询 子商户的资金变更记录(导出)
     * @param query 查询条件封装的实体
     * @return List<AccountFundDTO>
     */
    public List<ChildMerchantAccountChange> findAccountChangeChildMerByList(ChildMerFundChangeQuery query);
    
    
    
}
