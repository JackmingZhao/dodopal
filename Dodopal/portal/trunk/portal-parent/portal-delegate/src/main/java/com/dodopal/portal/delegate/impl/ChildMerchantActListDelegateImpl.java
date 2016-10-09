package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.account.dto.ChildMerchantAccountChangeDTO;
import com.dodopal.api.account.dto.query.ChildMerFundChangeQueryDTO;
import com.dodopal.api.account.facade.AccountChildMerchantFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.ChildMerchantActListDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("childMerchantActListDelegate")
public class ChildMerchantActListDelegateImpl extends BaseDelegate  implements ChildMerchantActListDelegate{

	//根据上级商户编号 查询 子商户的资金变更记录(分页)
	public DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeDTO>> findAccountChangeChildMerByPage(
			ChildMerFundChangeQueryDTO query) {
		AccountChildMerchantFacade facade = getFacade(AccountChildMerchantFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeDTO>> response = facade.findAccountChangeChildMerByPage(query);
		return response;
	}

	//根据上级商户编号 查询 子商户的资金变更记录(导出)
	public DodopalResponse<List<ChildMerchantAccountChangeDTO>> findAccountChangeChildMerByList(
			ChildMerFundChangeQueryDTO query) {
		AccountChildMerchantFacade facade = getFacade(AccountChildMerchantFacade.class, DelegateConstant.FACADE_ACCOUNT_URL);
		DodopalResponse<List<ChildMerchantAccountChangeDTO>> response = facade.findAccountChangeChildMerByList(query);
		return response;
	}

}
