package com.dodopal.oss.delegate;

import com.dodopal.api.account.dto.AccountAdjustmentApproveListDTO;
import com.dodopal.common.model.DodopalResponse;

public interface AccountManageDelegate {

    /**
     * 账户调帐
     * @description 授权用户登录OSS系统后，对未审批的调账单进行审批，通过后调用此接口。 支持批量。
     * @param approveDTOList
     * @return
     */
    public DodopalResponse<Boolean> accountAdjustment(AccountAdjustmentApproveListDTO approveDTOList);

}
