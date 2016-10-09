package com.dodopal.oss.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.AccountInfoDTO;
import com.dodopal.api.account.dto.AccountMainInfoDTO;
import com.dodopal.api.account.dto.FundChangeDTO;
import com.dodopal.api.account.dto.query.AccountInfoListQueryDTO;
import com.dodopal.api.account.dto.query.FundChangeQueryDTO;
import com.dodopal.api.account.facade.AccountManagementFacade;
import com.dodopal.api.account.facade.AccountQueryFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.delegate.AccountInfoDelegate;
import com.dodopal.oss.delegate.BaseDelegate;
import com.dodopal.oss.delegate.constant.DelegateConstant;

@Service("AccountInfoDelegate")
public class AccountInfoDelegateImpl extends BaseDelegate implements AccountInfoDelegate {

    /**
     * 资金授信账户信息列表查询
     * @param accountInfoListQueryDTO
     * @return
     */
    @Override
    public DodopalResponse<DodopalDataPage<AccountMainInfoDTO>> findAccountInfoListByPage(AccountInfoListQueryDTO accountInfoListQueryDTO) {
        AccountQueryFacade facade = getFacade(AccountQueryFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
        return facade.findAccountInfoListByPage(accountInfoListQueryDTO);
    }

    /**
     * 详细信息
     * @param acid
     * @return
     */
    @Override
    public DodopalResponse<AccountInfoDTO> findAccountInfoByCode(String acid) {
        AccountQueryFacade facade = getFacade(AccountQueryFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
        return facade.findAccountInfo(acid);
    }

    /**
     * 禁用/启用账户
     * @param oper
     * @param fundAccountIds
     * @return
     */
    @Override
    public DodopalResponse<String> operateFundAccountsById(String oper, List<String> fundAccountIds, String userId) {
        AccountManagementFacade facade = getFacade(AccountManagementFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
        return facade.operateFundAccountsById(oper, fundAccountIds, userId);
    }

    /**
     * 账户资金变动记录
     * @param fundChangeQueryDTO
     * @return
     */
    @Override
    public DodopalResponse<DodopalDataPage<FundChangeDTO>> findFundsChangeRecordsByPage(FundChangeQueryDTO fundChangeQueryDTO) {
        AccountQueryFacade facade = getFacade(AccountQueryFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
        return facade.findFundsChangeRecordsByPage(fundChangeQueryDTO);
    }

    /**
     * 导出
     */
    @Override
    public DodopalResponse<List<AccountMainInfoDTO>> expAccountInfo(AccountInfoListQueryDTO accountInfoListQueryDTO) {
        AccountQueryFacade facade = getFacade(AccountQueryFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
        return facade.expAccountInfo(accountInfoListQueryDTO);
    }

}
