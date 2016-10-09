package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.CardRecharge;
import com.dodopal.oss.business.model.CardRechargeDTO;
import com.dodopal.oss.business.model.dto.CardRechargeQuery;

public interface CardRechargeService {

    /**
     * 5.4.1.1  查询 列表显示
     * @param query
     * @return
     */
    DodopalDataPage<CardRecharge> findCardRechargeByPage(CardRechargeQuery query);
    
    /**
     * 5.4.1.2  查看
     * @param orderNo:清分表主键
     * @return
     */
    CardRecharge findCardRecharge(String id);
    
    /**
     * 获取导出集合总数
     * @param query
     * @return
     */
    int findCardRechargeCount(CardRechargeQuery query);
    
    /**
     * 获取导出数据集合
     * @param query
     * @return
     */
    List<CardRechargeDTO> findCardRechargeExcel(CardRechargeQuery query);
}
