package com.dodopal.account.delegate.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.dodopal.account.delegate.AccountAdjustmentDelegate;
import com.dodopal.account.delegate.BaseDelegate;
import com.dodopal.account.delegate.constant.DelegateConstant;
import com.dodopal.api.users.facade.MerchantFacade;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.DodopalResponse;


@Service("AccountAdjustmentDelegate")
public class AccountAdjustmentDelegateImpl extends BaseDelegate implements AccountAdjustmentDelegate {

    @Override
    public DodopalResponse<Map<String, Object>> validateMerchantForPayment(MerUserTypeEnum userType, String code) {
        MerchantFacade facade =getFacade(MerchantFacade.class,DelegateConstant.FACADE_USERS_URL);
        return facade.validateMerchantForPayment(userType, code);
    }
    
    
}
