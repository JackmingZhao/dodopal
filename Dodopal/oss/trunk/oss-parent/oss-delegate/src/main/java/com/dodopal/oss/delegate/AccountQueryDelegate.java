package com.dodopal.oss.delegate;

import com.dodopal.api.account.dto.FundAccountInfoDTO;
import com.dodopal.common.model.DodopalResponse;

public interface AccountQueryDelegate {

    /**
     * 资金授信查询 
     * 在OSS调账时，用户在确定客户类型和客户时，需要确定该客户的资金类别，选择需要调账资金或授信。
     * 
     * @param custType(客户类型：个人、商户)
     * @param custNum(类型是商户：商户号；类型是个人：用户编号)
     * @return
     */
    public DodopalResponse<FundAccountInfoDTO> findFundAccountInfo(String custType, String custNum);


}
