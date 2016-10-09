package com.dodopal.oss.business.service;

import com.dodopal.common.model.DodopalResponse;

/**
 * 账户充值异常处理Service
 * 
 * @author shenXiang
 *
 */
public interface AccountRechargeExceptionHandleService {
    
    /**
     * 账户充值异常处理
     * @param tranCode 交易流水号
     * @param operatorId 异常处理人员ID
     * @return
     */
    public DodopalResponse<String> exceptionHandle(String tranCode, String operatorId);

}
