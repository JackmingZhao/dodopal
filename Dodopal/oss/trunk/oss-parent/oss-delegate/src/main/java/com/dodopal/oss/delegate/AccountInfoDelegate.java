package com.dodopal.oss.delegate;

import java.util.List;

import com.dodopal.api.account.dto.AccountInfoDTO;
import com.dodopal.api.account.dto.AccountMainInfoDTO;
import com.dodopal.api.account.dto.FundChangeDTO;
import com.dodopal.api.account.dto.query.AccountInfoListQueryDTO;
import com.dodopal.api.account.dto.query.FundChangeQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface AccountInfoDelegate {
    /**
     * 资金授信账户信息列表查询
     * @param accountInfoListQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<AccountMainInfoDTO>> findAccountInfoListByPage(AccountInfoListQueryDTO accountInfoListQueryDTO);

    /**
     * 详细信息
     * @param acid
     * @return
     */
    public DodopalResponse<AccountInfoDTO> findAccountInfoByCode(String acid);

    /**
     * 禁用/启用账户
     * @param oper
     * @param fundAccountIds
     * @return
     */
    public DodopalResponse<String> operateFundAccountsById(String oper, List<String> fundAccountIds, String userId);
    
    /**
     * 账户资金变动记录
     * @param fundChangeQueryDTO
     * @return
     */
    public DodopalResponse<DodopalDataPage<FundChangeDTO>> findFundsChangeRecordsByPage(FundChangeQueryDTO fundChangeQueryDTO);
    
    /**
     * 导出
     * @param accountInfoListQueryDTO
     * @return
     */
    public DodopalResponse<List<AccountMainInfoDTO>> expAccountInfo(AccountInfoListQueryDTO accountInfoListQueryDTO);
}
