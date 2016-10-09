package com.dodopal.portal.business.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dodopal.api.product.dto.query.PrdProductYktQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.PrdProductYktBean;
import com.dodopal.portal.business.model.query.PrdProductYktQuery;

public interface ProductManageService {

	/**
	 * 基于城市查询公交卡充值产品(分页)
	 * @param cityId
	 * @return DodopalResponse<DodopalDataPage<PrdProductYktBean>>
	 */
	public DodopalResponse<DodopalDataPage<PrdProductYktBean>> findAvailableIcdcProductsByPage(PrdProductYktQuery query,String userType);
	
	/**
	 * 基于城市查询公交卡充值产品
	 * @param response
	 * @param query
	 * @param userType
	 * @return DodopalResponse<List<PrdProductYktBean>>
	 */
	public DodopalResponse<String> findAvailableIcdcProductsInCity(HttpServletResponse response,PrdProductYktQueryDTO query,String userType);
}
