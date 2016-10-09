package com.dodopal.portal.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.DirectMerChantBean;
import com.dodopal.portal.business.model.PortalTransfer;

public interface TransferService {
    /**
     * 根据上级商户编码（连锁商户），查询其处于启动状态的 直营网点
     * @param merParentCode 直营网点的上级商户编码（连锁商户）
     * @param merName （直营网点的 ）商户名称
     * @return DodopalResponse<List<MerchantBean>>
     */
    DodopalResponse<List<DirectMerChantBean>> findMerchantByParentCode(String merParentCode,String merName);
    
    /**
     * 根据上级商户编码（连锁商户），查询其处于启动状态的 直营网点或者加盟网点
     * @param merParentCode 直营网点的上级商户编码（连锁商户）
     * @param merName （直营网点的 ）商户名称
     * @param businessType(网店类型) 13 直营网点;14加盟网点
     * @return DodopalResponse<List<MerchantBean>>
     */
    DodopalResponse<List<DirectMerChantBean>> findMerchantByParentCode(String merParentCode,String merName,String businessType);
    DodopalResponse<List<DirectMerChantBean>> findDirectTransferFilter(String merParentCode,String merName,String businessType);
    
    
    
    /**
     * 门户  转账  
     * @param portalTransfer 
     * @return
     */
    DodopalResponse<Boolean> accountTransfer(PortalTransfer portalTransfer);
}
