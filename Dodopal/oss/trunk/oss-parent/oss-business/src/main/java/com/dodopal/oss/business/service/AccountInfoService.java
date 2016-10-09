package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.AccountInfoBean;
import com.dodopal.oss.business.bean.AccountMainInfoBean;
import com.dodopal.oss.business.bean.FundChangeBean;
import com.dodopal.oss.business.bean.query.AccountInfoListQuery;
import com.dodopal.oss.business.model.dto.FundChangeQuery;

public interface AccountInfoService {
    /**
     * 资金授信账户信息列表查询
     * @param accountInfoListQuery
     * @return
     */
    public DodopalResponse<DodopalDataPage<AccountMainInfoBean>> findAccountInfoListByPage(AccountInfoListQuery accountInfoListQuery);

    /**
     * 详细查询
     * @param acid
     * @return
     */
    public DodopalResponse<AccountInfoBean> findAccountInfoByCode(String acid);

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
    public DodopalResponse<DodopalDataPage<FundChangeBean>> findFundsChangeRecordsByPage(FundChangeQuery fundChangeQuery);
    
    /**
     * 导出
     * @param accountInfoListQuery
     * @return
     */
    public DodopalResponse<List<AccountMainInfoBean>> expAccountInfo(AccountInfoListQuery accountInfoListQuery);
}
