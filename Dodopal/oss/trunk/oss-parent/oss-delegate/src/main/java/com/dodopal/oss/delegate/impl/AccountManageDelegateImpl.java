package com.dodopal.oss.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountAdjustmentApproveListDTO;
import com.dodopal.api.account.facade.AccountManagementFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.AccountManageDelegate;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("accountManageDelegate")
public class AccountManageDelegateImpl extends BaseDelegate implements AccountManageDelegate {

    /**
     * 账户调帐
     * @description 授权用户登录OSS系统后，对未审批的调账单进行审批，通过后调用此接口。 支持批量。
     * @param approveDTOList
     * @return
     */
    @Override
    public DodopalResponse<Boolean> accountAdjustment(AccountAdjustmentApproveListDTO approveDTOList) {
        AccountManagementFacade facade = getFacade(AccountManagementFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
        DodopalResponse<Boolean> response = facade.accountAdjustment(approveDTOList);
        return response;
    }

}
