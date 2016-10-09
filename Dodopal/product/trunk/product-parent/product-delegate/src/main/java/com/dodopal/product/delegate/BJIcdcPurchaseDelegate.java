package com.dodopal.product.delegate;

import java.util.Map;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.ReslutDataParameter;
import com.dodopal.common.model.DodopalResponse;

public interface BJIcdcPurchaseDelegate {

    /**
     * 根据POS编号查询其绑定商户信息
     * @param posId
     * @return 
     *      返回Map-->key：
     *          merCode(商户编号[String])，
     */
    public DodopalResponse<Map<String,String>> getMerchantByPosCode(String posId);
    
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
     * 卡服务：消费验卡生单接口（V61联机、脱机消费）
     */
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCardConsByV61(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务：消费验卡生单接口（V71联机、脱机消费）
     */
    public DodopalResponse<BJCrdSysOrderDTO> queryCheckCardConsByV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务：消费申请密钥接口（V61联机消费）
     */
    public DodopalResponse<BJCrdSysOrderDTO> getDeductOrderCons(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务：消费结果上传接口（V61联机消费）
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultConsByOnlineV61(BJCrdSysOrderDTO crdDTO);
   
    /**
     * 卡服务：消费结果上传接口（V61脱机消费）
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultConsByOfflineV61(BJCrdSysOrderDTO crdDTO);

    /**
     * 卡服务：消费申请密钥接口（V71联机消费）
     */
    public DodopalResponse<BJCrdSysOrderDTO> getDeductOrderConsByOnlineV71(BJCrdSysOrderDTO crdDTO);

    /**
     * 卡服务：消费结果上传接口（V71联机消费）
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadResultConsByOnlineV71(BJCrdSysOrderDTO crdDTO);
    
    /**
     * 卡服务：V71脱机消费批量上传
     */
    public DodopalResponse<ReslutDataParameter> batchUploadResultByOfflineV71(ReslutDataParameter parameter);
    
}
