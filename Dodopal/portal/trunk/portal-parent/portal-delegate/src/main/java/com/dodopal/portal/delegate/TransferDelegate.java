package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.payment.dto.PayTransferDTO;
import com.dodopal.api.users.dto.DirectMerChantDTO;
import com.dodopal.common.model.DodopalResponse;

public interface TransferDelegate {
    /**
     * 根据上级商户 商户名称，查询直营网点
     * @param merParentCode 上级商户编号
     * @param merName 商户名称
     * @return DodopalResponse<List<DirectMerChantDTO>> 
     */
    public DodopalResponse<List<DirectMerChantDTO>> findMerchantByParentCode(String merParentCode, String merName) ;
    
    public DodopalResponse<List<DirectMerChantDTO>> findMerchantByParentCode(String merParentCode, String merName,String businessType) ;
    public DodopalResponse<List<DirectMerChantDTO>> findDirectTransferFilter(String merParentCode, String merName,String businessType) ;
    
    /**
     *  转账（和提取额度）
     * @param payTransferDTOList 
     * @return DodopalResponse<Boolean>
     */
    public DodopalResponse<Boolean> transferAccount(List<PayTransferDTO> payTransferDTOList);
}
