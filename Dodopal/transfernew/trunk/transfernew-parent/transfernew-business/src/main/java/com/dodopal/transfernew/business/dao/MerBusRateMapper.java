package com.dodopal.transfernew.business.dao;

import java.util.List;

import com.dodopal.transfernew.business.model.transfer.MerBusRate;

public interface MerBusRateMapper {

    /**
     * 
     * @param merBusRate
     */
    public void addMerBusRate(MerBusRate merBusRate);
    /**
     * 添加费率
     * @param list
     * @return
     */
    public int addMerBusRateBatch(List<MerBusRate> list);

}
