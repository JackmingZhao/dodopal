package com.dodopal.transfernew.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.transfer.MerRateSupplement;

public interface MerRateSupplementMapper {

    /**
     * 
     * @param merRateSupplement
     */
    public void addMerRateSupplement(MerRateSupplement merRateSupplement);

    public int batchAddMerRateSpmts(@Param("list") List<MerRateSupplement> list);
}
