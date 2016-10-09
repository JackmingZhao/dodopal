package com.dodopal.transfernew.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.transfer.MerchantExtend;

public interface MerchantExtendMapper {

    /**
     * 增加商户扩展表的记录
     * @param merchantExtend
     */
    public int addMerchantExtend(MerchantExtend merchantExtend);

    /**
     * 根据老商户的id查找对应新商户表的上级商户code
     * @param mchnitid 老商户id
     * @param type 老商户类型，集团1，网点2，商户3
     * @return
     */
    public MerchantExtend findMerchantExtend(@Param("mchnitid")String mchnitid, @Param("type")String type);
}
