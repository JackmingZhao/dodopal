package com.dodopal.account.delegate;

import java.util.Map;

import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.DodopalResponse;


public interface AccountAdjustmentDelegate {
    
    /**
     * 用户或商户状态合法性
     * @param userType
     * @param code
     * @return
     */
    public DodopalResponse<Map<String,Object>> validateMerchantForPayment(MerUserTypeEnum userType, String code);
    
}
