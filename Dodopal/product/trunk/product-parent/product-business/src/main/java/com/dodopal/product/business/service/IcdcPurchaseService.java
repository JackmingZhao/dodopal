package com.dodopal.product.business.service;

import com.dodopal.api.card.dto.CrdSysOrderDTO;
import com.dodopal.api.product.dto.IcdcPurchaseDTO;
import com.dodopal.common.model.DodopalResponse;

public interface IcdcPurchaseService {

    /**
     * 生单接口
     * @param crdDTO
     * @return
     */
    public DodopalResponse<IcdcPurchaseDTO> createIcdcPurchaseOrder(IcdcPurchaseDTO purchaseDto);
    
    
    /**
     * 申请扣款初始化指令
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> checkCard(CrdSysOrderDTO crdDTO);
    
    
    
    /**
     * 申请扣款指令
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> applyDeductInstruction(CrdSysOrderDTO crdDTO);
    
    
    
    /**
     * 上传收单结果
     * @param crdDTO
     * @return
     */
    public DodopalResponse<CrdSysOrderDTO> uploadResult(CrdSysOrderDTO crdDTO);
}
