package com.dodopal.account.delegate;

import com.dodopal.common.model.DodopalResponse;

public interface AccountDelegate {
    //创建账户
    DodopalResponse createAccount(String aType,String custNum,String accType,String merType,String operateId);
    //账户充值
    DodopalResponse accountRecharge(String custType, String custNum,String tradeNum, long amount,String operateId);
    //冻结
    DodopalResponse accountFreeze(String custType, String custNum,String tradeNum, long amount,String operateId);
    //解冻
    DodopalResponse accountUnfreeze(String custType, String custNum,String tradeNum, long amount,String operateId);
    //扣款
    DodopalResponse accountDeduct(String custType, String custNum,String tradeNum, long amount,String operateId);
}
