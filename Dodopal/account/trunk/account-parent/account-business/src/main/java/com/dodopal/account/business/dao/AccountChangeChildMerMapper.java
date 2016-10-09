package com.dodopal.account.business.dao;

import java.util.List;

import com.dodopal.account.business.model.ChildMerchantAccountChange;
import com.dodopal.account.business.model.query.ChildMerFundChangeQuery;
/**
 * 查询子商户资金变更记录
 * @author xiongzhijing@dodopal.com
 * @version 2015年9月9日
 *
 */
public interface AccountChangeChildMerMapper {

    /**
     * 根据上级商户编号 查询 子商户的资金变更记录(分页)
     * @param query 查询条件封装的实体
     * @return List<AccountFundDTO>
     */
    public  List<ChildMerchantAccountChange> findAccountChangeChildMerByPage(ChildMerFundChangeQuery query);
    
    /**
     * 根据上级商户编号 查询 子商户的资金变更记录
     * @param query 查询条件封装的实体
     * @return List<AccountFundDTO>
     */
    public  List<ChildMerchantAccountChange> findAccountChangeChildMerByList(ChildMerFundChangeQuery query);
}
