/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.account.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.account.business.model.AccountChange;
import com.dodopal.account.business.model.query.AccountChangeQuery;
import com.dodopal.account.business.model.query.FundChangeQuery;

/**
 * Created by lenovo on 2015/8/21.
 */
public interface AccountChangeMapper {
    /**
     * @description 新增资金变动记录
     *@param accountChange
     * @param accountChange
     */
    public void addAccountChange(AccountChange accountChange);

    
    public List<AccountChange> queryAccountChange(@Param("tranNum")String tranNum,@Param("changeType")String changeType);
    
    /**
     * 查询资金变更
     * @param query
     * @return
     */
    public List<AccountChange> findFundsChangeRecordsByPage(FundChangeQuery query);
    
    /**
     * 查询所有资金变更 
     * @return List<AccountChange>
     */
    public List<AccountChange> findFundsChangeRecordsAll(FundChangeQuery query);

    /**
     * 根据资金账户号，订单号，变动类型查询资金变动记录是否已经存在
     * @return
     */
    public boolean checkAccountChangeRecordExsit(@Param("tranNum")String tranNum,@Param("changeType")String changeType,@Param("fundType")String fundType);
    
    
    /**
     * 查询资金变更记录（手机、VC端接入）
     * @param queryRequest
     * @return List<AccountChange>
     */
    public List<AccountChange> queryAccountChangeByPhone(AccountChangeQuery queryRequest);
    
}