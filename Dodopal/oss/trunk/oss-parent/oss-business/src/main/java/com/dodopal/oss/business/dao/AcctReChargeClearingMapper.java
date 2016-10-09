package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.AcctReChargeClearing;
import com.dodopal.oss.business.model.AcctReChargeClearingDTO;
import com.dodopal.oss.business.model.dto.AcctReChargeClearingQuery;
import com.dodopal.oss.business.model.dto.CardRechargeQuery;


public interface AcctReChargeClearingMapper {
    /** 
     * 查找账户充值清分列表
     * @param acctChargeClearingQuery
     * @return 
     */
    
    
    public List<AcctReChargeClearing> queryAcctRechargeClearingPage(AcctReChargeClearingQuery acctChargeClearingQuery);

    /** 
     * 查找账户充值清分详情
     * @param code
     * @return 
     */
    public AcctReChargeClearing queryAcctRechargeClearingDetails(String id);
    
   
    /**
     * 获取导出集合总数
     * @param query
     * @return
     */
    public int queryAcctRechargeClearingCount(AcctReChargeClearingQuery query);
    
    /**
     * 获取导出数据集合
     * @param query
     * @return
     */
    public List<AcctReChargeClearingDTO> queryAcctRechargeClearingExcel(AcctReChargeClearingQuery query);

}
