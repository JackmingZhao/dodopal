package com.dodopal.payment.delegate;

import com.dodopal.api.account.dto.AccountTransferListDTO;
import com.dodopal.common.model.DodopalResponse;

public interface PayTraTransactionDelegate {
    public DodopalResponse<Boolean> accountTransfer(AccountTransferListDTO accountTransferDTOList);
}
