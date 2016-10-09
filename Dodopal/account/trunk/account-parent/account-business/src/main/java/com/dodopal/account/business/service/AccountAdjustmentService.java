package com.dodopal.account.business.service;

import com.dodopal.api.account.dto.AccountAdjustmentApproveListDTO;
import com.dodopal.common.model.DodopalResponse;

public interface AccountAdjustmentService {
    
    /**
     * 9    账户调帐
     * @param approveListDTO
     * @return
     */
    public DodopalResponse<Boolean> accountAdjustment(AccountAdjustmentApproveListDTO approveListDTO);
    
}
