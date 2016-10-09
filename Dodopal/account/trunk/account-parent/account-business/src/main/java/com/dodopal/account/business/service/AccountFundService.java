package com.dodopal.account.business.service;

import java.util.List;

import com.dodopal.api.account.dto.AccountTransferListDTO;
import com.dodopal.common.model.DodopalResponse;

public interface AccountFundService {
    /**
     * 账户转账
     * @author 袁越
     * @param accountTransferListDTO
     * @return 响应体 true：成功，false：失败。
     */
    public DodopalResponse<Boolean> accountTransfer(AccountTransferListDTO accountTransferListDTO);
    
    /**
     * 禁用/启用账户
     * @param oper
     * @param fundAccountIds
     * @return
     */
    public void operateFundAccountsById(String oper, List<String> fundAccountIds, String userId);
}
