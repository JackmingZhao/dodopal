package com.dodopal.portal.delegate.impl;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductOrderDTO;
import com.dodopal.api.product.dto.YktCardConsumTradeDetailDTO;
import com.dodopal.api.product.dto.query.ProductOrderQueryDTO;
import com.dodopal.api.product.dto.query.YktCardConsumTradeDetailQueryDTO;
import com.dodopal.api.product.facade.ManagementForSupplierFacade;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.ManagementForSupplierDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service
public class ManagementForSupplierDelegateImpl extends BaseDelegate implements ManagementForSupplierDelegate {

	
	@Override
	public DodopalResponse<DodopalDataPage<ProductOrderDTO>> queryCardRechargeDetails(ProductOrderQueryDTO queryDTO) {
		ManagementForSupplierFacade proFacade = getFacade(ManagementForSupplierFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
		return proFacade.queryCardRechargeDetails(queryDTO);
	}

	@Override
	public DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> queryCardConsumDetails(
			YktCardConsumTradeDetailQueryDTO query) {
		ManagementForSupplierFacade proFacade = getFacade(ManagementForSupplierFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
		return proFacade.queryCardConsumDetails(query);
	}

	
	/**
     * 一卡通消费业务订单汇总详细查询
     * @param queryDTO
     * @return
     */
	public DodopalResponse<DodopalDataPage<YktCardConsumTradeDetailDTO>> queryCardConsumCollectDetailsByPage(
			YktCardConsumTradeDetailQueryDTO query) {
		ManagementForSupplierFacade proFacade = getFacade(ManagementForSupplierFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
		return proFacade.queryCardConsumCollectDetailsByPage(query);
	}
}
