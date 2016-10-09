package com.dodopal.product.business.service;

import com.dodopal.api.card.dto.BJCrdSysOrderDTO;
import com.dodopal.api.card.dto.ReslutDataParameter;
import com.dodopal.common.model.DodopalResponse;

/**
 * 北京城市一卡通消费业务流程接口（包含V61，V71）
 * 
 * @author dodopal
 *
 */
public interface BJIcdcPurchaseService {
    
    /**
     * 消费验卡生单（V61、V71联机/脱机消费）
     */
    public DodopalResponse<BJCrdSysOrderDTO> checkCardCreateOrder(BJCrdSysOrderDTO crdDTO, String posType);
    
    
    /******************************  V61  START  北京城市一卡通消费业务流程接口         ******************************/
    
    /**
     * 消费申请消费密钥
     */
    public DodopalResponse<BJCrdSysOrderDTO> applyConsumeByV61(BJCrdSysOrderDTO crdDTO);

    /**
     * 消费结果上传
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadConsumeResultByV61(BJCrdSysOrderDTO crdDTO);
    
    /******************************  V61  END  北京城市一卡通消费业务流程接口         ******************************/
    
    
    /******************************  V71  START  北京城市一卡通消费业务流程接口         ******************************/
    
    /**
     * 消费申请消费密钥（V71联机消费）
     */
    public DodopalResponse<BJCrdSysOrderDTO> applyConsumeByOnlineV71(BJCrdSysOrderDTO crdDTO);

    /**
     * 消费结果上传（V71联机消费）
     */
    public DodopalResponse<BJCrdSysOrderDTO> uploadConsumeResultByOnlineV71(BJCrdSysOrderDTO crdDTO);
        
    /**
     * 消费结果上传（V71脱机消费）
     */
    public DodopalResponse<ReslutDataParameter> uploadConsumeResultByOfflineV71(ReslutDataParameter crdDTO);
    
    /******************************  V71  END  北京城市一卡通消费业务流程接口         ******************************/
}
