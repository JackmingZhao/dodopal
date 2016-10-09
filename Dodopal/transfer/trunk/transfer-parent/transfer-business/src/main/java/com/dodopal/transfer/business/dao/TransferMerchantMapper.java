package com.dodopal.transfer.business.dao;

import org.apache.ibatis.annotations.Param;

import com.dodopal.transfer.business.model.target.TransferMerchant;

public interface TransferMerchantMapper {
    public TransferMerchant findTransferMerchant(@Param("mchnitid") String mchnitid);
    
 
}
