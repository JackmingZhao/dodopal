package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.account.dto.ChildMerchantAccountChangeDTO;
import com.dodopal.api.account.dto.query.ChildMerFundChangeQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ChildMerchantActListDelegate {

	 /**
     * 根据上级商户编号 查询 子商户的资金变更记录(分页)
     * @param query 查询条件封装的实体
     * @return DodopalResponse<DodopalDataPage<AccountFundDTO>>
     */
    public DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeDTO>>  findAccountChangeChildMerByPage(ChildMerFundChangeQueryDTO query);
    
    /**
     * 根据上级商户编号 查询 子商户的资金变更记录(导出)
     * @param query 查询条件封装的实体
     * @return DodopalResponse<List<AccountFundDTO>>
     */
    public DodopalResponse<List<ChildMerchantAccountChangeDTO>> findAccountChangeChildMerByList(ChildMerFundChangeQueryDTO query);
}
