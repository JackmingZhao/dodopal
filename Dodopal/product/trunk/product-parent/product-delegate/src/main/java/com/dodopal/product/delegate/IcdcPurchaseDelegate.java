package com.dodopal.product.delegate;

import java.util.Map;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.common.model.DodopalResponse;

public interface IcdcPurchaseDelegate {

    /**
     * 检验商户合法性
     * @param merchantNum
     * @param userId
     * @param posId
     * @param providerCode
     * @param source
     * @return
     */
    public DodopalResponse<Map<String, String>> validateMerchantForIcdcPurchase(String merchantNum, String userId, String posId, String providerCode,String source);
    
    
    /**
     * 产品库调用卡服务提供的申请扣款初始化指令接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> queryCheckCardConsFun(CrdSysOrderDTO crdDTO);
    
    
    /**
     * 产品库调用卡服务提供的申请扣款指令接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> getDeductOrderConsFun(CrdSysOrderDTO crdDTO);
    
    
    
    /**
     * 产品库调用卡服务提供的上传收单结果接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> uploadResultConsFun(CrdSysOrderDTO crdDTO);
}
