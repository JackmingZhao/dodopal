package com.dodopal.product.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.product.business.model.YktConsumeRecord;

public interface YktConsumeRecordMapper {
    
    /**
     * (账户消费：Z ;积分消费：J)+ 20150428222211 +五位数据库cycle sequence（循环使用）
     * @return
     */
    String getOrderNumSeq();
    
    YktConsumeRecord selectByOrderNum(@Param("orderNum")String orderNum);
    
    int addOrderRecord(YktConsumeRecord record);

    int updateByOrderNum(YktConsumeRecord record);

}