package com.dodopal.running.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.running.business.model.AccountChange;

public interface AccountChangeMapper {
    
    /**
     * 新增资金变动记录
     * @param accountChange
     */
    public void addAccountChange(AccountChange accountChange);

    /**
     * 根据交易流水号，变动类型，查询资金变动记录
     * @param tranNum
     * @param changeType
     * @return
     */
    public List<AccountChange> getAccountChangeRecordList(@Param("tranNum")String tranNum,@Param("changeType")String changeType);
    
    /**
     * 根据交易流水号，变动类型，资金类别，查询资金变动记录是否已经存在
     * @param tranNum
     * @param changeType
     * @param fundType
     * @return
     */
    public boolean checkAccountChangeRecordExsit(@Param("tranNum")String tranNum,@Param("changeType")String changeType,@Param("fundType")String fundType);

    
}