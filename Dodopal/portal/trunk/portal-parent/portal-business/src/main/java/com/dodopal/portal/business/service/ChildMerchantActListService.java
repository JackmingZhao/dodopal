package com.dodopal.portal.business.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.account.dto.query.ChildMerFundChangeQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.ChildMerchantAccountChangeBean;
import com.dodopal.portal.business.bean.query.ChildMerFundChangeQuery;

public interface ChildMerchantActListService {

	/**
	 * 根据上级商户编号 查询 子商户的资金变更记录(分页)
	 * @param query
	 * @return DodopalDataPage<ChildMerchantAccountChangeDTO>
	 */
	DodopalResponse<DodopalDataPage<ChildMerchantAccountChangeBean>>  findAccountChangeChildMerByPage(ChildMerFundChangeQuery query);
	
	/**
	 * 根据上级商户编号 查询 子商户的资金变更记录(导出)
	 * @param query
	 * @return List<ChildMerchantAccountChangeDTO>
	 */
	DodopalResponse<String> findAccountChangeChildMerByList(HttpServletResponse response,ChildMerFundChangeQueryDTO query);
}
