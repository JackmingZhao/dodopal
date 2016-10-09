package com.dodopal.account.business.service;

import com.dodopal.common.model.DodopalResponse;

public interface AccountRiskControllerService {
    
    /**
     * 账户转账（转出）风控检查
     * 
     * @param custType 枚举：个人、企业
     * @param custNum 类型是商户：商户号；类型是个人：用户编号
     * @param amount 转出账户的金额，单位为分。必须为正整数。
     * @param operateId 操作员ID
     * @return
     */
    public DodopalResponse<Boolean> allowedToTransfer(String custType, String custNum, long amount, String operateId,int totalCount);
}
