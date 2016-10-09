package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.CardConsumeClearing;
import com.dodopal.oss.business.model.CardConsumeClearingDTO;
import com.dodopal.oss.business.model.dto.CardConsumeClearingQuery;
import com.dodopal.oss.business.model.dto.CardRechargeQuery;

public interface CardConsumeClearingMapper {
    public List<CardConsumeClearing> queryCardConsumeClearingPage(CardConsumeClearingQuery cardConsumeClearingQuery);

    /** 
     * 查找一卡通消费清分详情
     * @param code
     * @return 
     */
    public CardConsumeClearing queryCardConsumeClearingDetails(String id);

    /**
     * 获取导出集合总数
     * @param query
     * @return
     */
    public int findCardConsumeClearingCount(CardConsumeClearingQuery query);
    
    /**
     * 获取导出数据集合
     * @param query
     * @return
     */
    public List<CardConsumeClearingDTO> findCardConsumeClearingExcel(CardConsumeClearingQuery query);
    
}
