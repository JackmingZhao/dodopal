package com.dodopal.users.delegate;

import com.dodopal.common.model.DodopalResponse;

public interface AccountManagementDelegate {

    /**
     * @description 创建账户
     * @param custType 客户类型:0 个人;1 企业
     * @param custNum  客户号
     * @param accType  账户类型:0 资金;1 授信
     * @param merType  商户类型，个人为99
     * @return 响应码
     */
    DodopalResponse<String> createAccount(String custType,String custNum,String accType, String merType, String userId);

}
