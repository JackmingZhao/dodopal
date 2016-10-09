package com.dodopal.transfernew.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfernew.business.model.transfer.TransferMerchant;

public interface TransferMerchantMapper {
    public TransferMerchant findTransferMerchant(@Param("mchnitid") String mchnitid);
    
 
}
