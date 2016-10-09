/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.product.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.card.dto.BJAccountConsumeDTO;
import com.dodopal.api.card.dto.BJIntegralConsumeDTO;
import com.dodopal.api.card.facade.BJAccIntConsumeDiscountFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.delegate.BJIcdcAcountPointConsumeDelegate;
import com.dodopal.product.delegate.BaseDelegate;
import com.dodopal.product.delegate.constant.DelegateConstant;

@Service
public class BJIcdcAcountPointConsumeDelegateImpl extends BaseDelegate implements BJIcdcAcountPointConsumeDelegate {
  
    /**
     * 通卡账户消费申请
     * @param bjAccountConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> applyYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO) {
        BJAccIntConsumeDiscountFacade facade = getFacade(BJAccIntConsumeDiscountFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.applyAccountConsume(bjAccountConsumeDTO);
    }
    
    /**
     * 通卡账户消费结果上送
     * @param bjAccountConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> uploadYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO) {
        BJAccIntConsumeDiscountFacade facade = getFacade(BJAccIntConsumeDiscountFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.uploadAccountConsume(bjAccountConsumeDTO);
    }
    
    /**
     * 通卡账户消费撤销申请
     * @param bjAccountConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJAccountConsumeDTO> cancelYktAcountConsume(BJAccountConsumeDTO bjAccountConsumeDTO) {
        BJAccIntConsumeDiscountFacade facade = getFacade(BJAccIntConsumeDiscountFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.revokeAccountConsume(bjAccountConsumeDTO);
    }
    
    /**
     * 通卡积分消费申请
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJIntegralConsumeDTO> applyYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO) {
        BJAccIntConsumeDiscountFacade facade = getFacade(BJAccIntConsumeDiscountFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.applyIntegralConsume(bjIntegralConsumeDTO);
    }
    
    /**
     * 通卡积分消费结果上送
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJIntegralConsumeDTO> uploadYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO) {
        BJAccIntConsumeDiscountFacade facade = getFacade(BJAccIntConsumeDiscountFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.uploadIntegralConsume(bjIntegralConsumeDTO);
    }
    
    /**
     * 通卡积分消费撤销申请
     * @param bjIntegralConsumeDTO
     * @return
     */
    @Override
    public DodopalResponse<BJIntegralConsumeDTO> cancelYktPointConsume(BJIntegralConsumeDTO bjIntegralConsumeDTO) {
        BJAccIntConsumeDiscountFacade facade = getFacade(BJAccIntConsumeDiscountFacade.class,DelegateConstant.FACADE_CARD_URL);
        return facade.revokeIntegralConsume(bjIntegralConsumeDTO);
    }
    
}
