package com.dodopal.portal.business.service;

import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.users.dto.query.ProfitQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.ProfitCollectBean;
import com.dodopal.portal.business.bean.ProfitDetailsBean;
import com.dodopal.portal.business.model.query.ProfitQuery;

public interface ProfitService {

	/**
	 * 查询
	 * @param query
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<ProfitCollectBean>> findProfitCollectListByPage(ProfitQuery query);
	/**
	 * 详情
	 * @param query
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<ProfitDetailsBean>> findProfitDetailsListByPage(ProfitQuery query);
	
	/**
	 * 导出
	 * @param response
	 * @param query
	 * @return
	 */
	public DodopalResponse<String> exportProfit(HttpServletResponse response,ProfitQueryDTO query);
}
