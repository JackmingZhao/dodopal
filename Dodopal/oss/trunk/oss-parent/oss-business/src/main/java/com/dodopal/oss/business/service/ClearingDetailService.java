package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.model.AcctReChargeClearing;
import com.dodopal.oss.business.model.AcctReChargeClearingDTO;
import com.dodopal.oss.business.model.CardConsumeClearing;
import com.dodopal.oss.business.model.CardConsumeClearingDTO;
import com.dodopal.oss.business.model.dto.AcctReChargeClearingQuery;
import com.dodopal.oss.business.model.dto.CardConsumeClearingQuery;
import com.dodopal.oss.business.model.dto.CardRechargeQuery;
public interface ClearingDetailService {
    /**
     * 按条件查询账户充值清分产品列表
     * @param icdcPrdQuery
     * @return
     */
   public DodopalResponse<DodopalDataPage<AcctReChargeClearing>> queryAcctRechargeClearingPage(AcctReChargeClearingQuery acctChargeClearingQuery);
    
    /**
     * 根据产品编号查询账户充值产品信息
     * @param proCode
     * @return
     */
    public DodopalResponse<AcctReChargeClearing> queryAcctRechargeClearingDetails(String id);
    /**
     * 按条件查询一卡通消费清分产品列表
     * @param icdcPrdQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<CardConsumeClearing>> queryCardConsumeClearingPage(CardConsumeClearingQuery query);
    /**
     * 根据产品编号查询一卡通消费产品信息
     * @param proCode
     * @return
     */
    public DodopalResponse<CardConsumeClearing> queryCardConsumeClearingDetails(String id);
    
    /**
     * 账户充值异常清分
     * 获取导出集合总数
     * @param query
     * @return
     */
    public int queryAcctRechargeClearingCount(AcctReChargeClearingQuery query);
    
    /**
     * 账户充值异常清分
     * 获取导出数据集合
     * @param query
     * @return
     */
    public List<AcctReChargeClearingDTO> queryAcctRechargeClearingExcel(AcctReChargeClearingQuery query);
    
    /**
     * 一卡通消费异常清分
     * 获取导出集合总数
     * @param query
     * @return
     */
    public int findCardConsumeClearingCount(CardConsumeClearingQuery query);
    
    /**
     * 一卡通消费异常清分
     * 获取导出数据集合
     * @param query
     * @return
     */
    public List<CardConsumeClearingDTO> findCardConsumeClearingExcel(CardConsumeClearingQuery query);
    
}
