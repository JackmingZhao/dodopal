package com.dodopal.oss.business.service;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.ExceptionOrderHandleDto;
import com.dodopal.oss.business.model.ProductOrder;

/**
 * 一卡通充值订单异常处理Service
 * 
 * @author shenXiang
 *
 */
public interface RechargeOrderExceptionHandleService {
    
    /**
     * 一卡通充值订单异常处理
     * @param ExceptionOrderHandleDto 
     * @param operatorName
     * @return
     */
    public DodopalResponse<String> exceptionHandle(ExceptionOrderHandleDto orderHandleDto, String operatorName);
    
    /**
     * 账户加值异常处理（网银支付时，用户账户未加值成功：订单状态：已付款_账户充值失败_网银支付成功）
     * @param orderNum 
     * @param operatorName
     * @return
     */
    public DodopalResponse<String> accountRechargeHandle(ProductOrder productOrder, String operatorName);
    
    /**
     * 资金解冻流程
     * @param orderNum
     * @param operatorName
     * @return
     */
    public DodopalResponse<String> accountUnfreezeHandle(ProductOrder productOrder, String operatorName);
    
    /**
     * 资金扣款流程
     * @param orderNum
     * @param operatorName
     * @return
     */
    public DodopalResponse<String> accountDeductHandle(ProductOrder productOrder, String operatorName);

}
