package com.dodopal.portal.delegate;

import java.util.List;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.RechargeStatisticsYktOrderDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;

public interface RechargeForSupplierDelegate {

	/**
	 * 一卡通充值统计查询
	 * @param query
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>> queryCardRechargeForSupplier(RechargeStatisticsYktQueryDTO query);
	
	/**
	 * 一卡通充值交易详细查询
	 * @param queryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<ProductOrderDTO>> queryCardRechargeDetails(ProductOrderQueryDTO queryDTO);
	
	/**
	 * 启用停用
	 * @param bind
	 * @param codes
	 * @return
	 */
	public DodopalResponse<Integer> startOrStopMerSupplier(String bind, List<String> codes);
}
