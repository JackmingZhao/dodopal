package com.dodopal.product.delegate;

import com.dodopal.api.card.dto.BJAccountConsumeDTO;
import com.dodopal.api.card.dto.BJIntegralConsumeDTO;
import com.dodopal.common.model.DodopalResponse;

public interface BJIcdcAcountPointConsumeDelegate {  
    
    /******************************  北京通卡“账户消费”业务流程接口 start   ******************************/
    
    /**
     * 通卡账户消费申请
     * @param bjAccountConsumeDTO
     * @return
     */
    public DodopalResponse<BJAccountConsumeDTO> applyYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO);
    
    /**
     * 通卡账户消费结果上送
     * @param bjAccountConsumeDTO
     * @return
     */
    public DodopalResponse<BJAccountConsumeDTO> uploadYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO);
    
    /**
     * 通卡账户消费撤销申请
     * @param bjAccountConsumeDTO
     * @return
     */
    public DodopalResponse<BJAccountConsumeDTO> cancelYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO);
    
    
    /******************************  北京通卡“账户消费”业务流程接口 end   ******************************/
    
    
    /******************************  北京通卡“积分消费”业务流程接口 start   ******************************/
    
    /**
     * 通卡积分消费申请
     * @param bjIntegralConsumeDTO
     * @return
     */
    public DodopalResponse<BJIntegralConsumeDTO> applyYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO);
    
    /**
     * 通卡积分消费结果上送
     * @param bjIntegralConsumeDTO
     * @return
     */
    public DodopalResponse<BJIntegralConsumeDTO> uploadYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO);
    
    /**
     * 通卡积分消费撤销申请
     * @param bjIntegralConsumeDTO
     * @return
     */
    public DodopalResponse<BJIntegralConsumeDTO> cancelYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO);
    
    /******************************  北京通卡“积分消费”业务流程接口 end   ******************************/}
