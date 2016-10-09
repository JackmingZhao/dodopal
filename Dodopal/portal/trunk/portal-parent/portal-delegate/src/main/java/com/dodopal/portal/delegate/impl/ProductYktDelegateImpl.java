package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.api.product.facade.ProductYktFacade;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.ProductYktDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("productYktDelegate")
public class ProductYktDelegateImpl extends BaseDelegate implements ProductYktDelegate{
	@Override
	public DodopalResponse<ProductYKTDTO> getYktInfoByBusinessCityCode(
			String cityCode) {
		ProductYktFacade facade = getFacade(ProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.getYktInfoByBusinessCityCode(cityCode);
	}

	@Override
	public DodopalResponse<List<ProductYKTDTO>> getAllYktBusinessRateList() {
		ProductYktFacade facade = getFacade(ProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
        return facade.getAllYktBusinessRateList();
	}

}
