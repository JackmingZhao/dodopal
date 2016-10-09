package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.RechargeStatisticsYktOrderDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.RechargeStatisticsYktQueryDTO;
import com.dodopal.api.product.facade.ManagementForSupplierFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.RechargeForSupplierDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("rechargeForSupplierDelegate")
public class RechargeForSupplierDelegateImpl extends BaseDelegate implements RechargeForSupplierDelegate{

	/**
	 * 一卡通充值统计查询
	 * @param query
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>> queryCardRechargeForSupplier(
			RechargeStatisticsYktQueryDTO query) {
		ManagementForSupplierFacade facade = getFacade(ManagementForSupplierFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
		DodopalResponse<DodopalDataPage<RechargeStatisticsYktOrderDTO>> response = facade.queryCardRechargeForSupplier(query);
		return response;
	}

	/**
	 * 一卡通充值交易详细查询
	 * @param queryDTO
	 * @return
	 */
	public DodopalResponse<DodopalDataPage<ProductOrderDTO>> queryCardRechargeDetails(
			ProductOrderQueryDTO queryDTO) {
		ManagementForSupplierFacade facade = getFacade(ManagementForSupplierFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
		DodopalResponse<DodopalDataPage<ProductOrderDTO>> response = facade.queryCardRechargeDetails(queryDTO);
		return response;
	}

	/**
	 * 启用停用
	 * @param bind
	 * @param codes
	 * @return
	 */
	public DodopalResponse<Integer> startOrStopMerSupplier(String bind,List<String> codes) {
		ManagementForSupplierFacade facade = getFacade(ManagementForSupplierFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
		DodopalResponse<Integer> response = facade.startOrStopMerSupplier(bind, codes);
		return response;
	}

}
