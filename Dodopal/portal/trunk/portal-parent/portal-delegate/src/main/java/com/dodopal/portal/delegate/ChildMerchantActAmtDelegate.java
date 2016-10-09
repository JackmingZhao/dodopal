package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.account.dto.AccountChildMerchantDTO;
import com.dodopal.api.account.dto.query.AccountChildMerchantQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface ChildMerchantActAmtDelegate {

	
	 /**
     * 根据上级商户 查询其子商户的总余额 及账户信息 （分页） 
     * @param merParentCode 上级商户编号
     * @param merName  商户 名称
     * @return DodopalDataPage<AccountChildMerchantDTO>
     */
	public DodopalResponse<DodopalDataPage<AccountChildMerchantDTO>> findAccountChildMerByPage(AccountChildMerchantQueryDTO queryDTO);
	
	 /**
     * 根据上级商户 查询其子商户的总余额 及账户信息
     * @param merParentCode 上级商户编号
     * @param merName 商户 名称
     * @return List<AccountChildMerchantDTO>
     */
	public DodopalResponse<List<AccountChildMerchantDTO>> findAccountChildMerByList(AccountChildMerchantQueryDTO query);
}
