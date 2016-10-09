package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.AccountBean;
import com.dodopal.product.business.bean.AccountFundBean;
import com.dodopal.product.business.bean.QueryAccountChangeRequestDTO;
import com.dodopal.product.business.bean.QueryAccountChangeResultDTO;

public interface AccountQueryService {
    /**
     * 查询门户首页 可用余额 资金授信账户信息
     * @param aType 个人or企业
     * @param custNum 用户号 or 商户号
     * @return
     */
    public DodopalResponse<AccountFundBean> findAccountBalance(String aType, String custNum);

    /**
     * 查询 账户资金变更记录（手机端，VC端接入）
     * @param requestDto
     * @return DodopalResponse<List<QueryAccountChangeResultDTO>>
     */
    DodopalResponse<List<QueryAccountChangeResultDTO>> queryAccountChange(QueryAccountChangeRequestDTO queryRequestDto);
    
    /**
     * 查询 可用余额 资金授信账户信息 (手机端，VC端接入)
     * @param custtype 个人or企业
     * @param custcode 用户号 or 商户号
     * @return DodopalResponse<AccountBean>
     */
    public DodopalResponse<AccountBean> queryAccountBalance(String custtype, String custcode);

}
