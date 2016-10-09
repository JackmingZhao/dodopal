package com.dodopal.users.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.users.business.model.DirectMerChant;

public interface DirectMerChantMapper {
    public List<DirectMerChant> findMerchantByParentCode(@Param("merParentCode")String merParentCode, @Param("merName")String merName);
    
    public List<DirectMerChant> findMerchantByParentCodeType(@Param("merParentCode")String merParentCode, @Param("merName")String merName,@Param("businessType")String businessType);
    public List<DirectMerChant> findDirectTransferFilter(@Param("merParentCode")String merParentCode, @Param("merName")String merName,@Param("businessType")String businessType);

}
