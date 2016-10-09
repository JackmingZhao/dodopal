package com.dodopal.portal.delegate.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dodopal.api.product.dto.PrdProductYktDTO;
import com.dodopal.api.product.dto.query.PrdProductYktQueryDTO;
import com.dodopal.api.product.facade.PrdProductYktFacade;
import com.dodopal.api.product.facade.PrdRateFacade;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.MerUserTypeEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.delegate.BaseDelegate;
import com.dodopal.portal.delegate.ProductManageDelegate;
import com.dodopal.portal.delegate.constant.DelegateConstant;

@Service("productManageDelegate")
public class ProductManageDelegateImpl extends BaseDelegate implements ProductManageDelegate{

	//基于城市查询公交卡充值产品(分页)
	public DodopalResponse<DodopalDataPage<PrdProductYktDTO>> findAvailableIcdcProductsByPage(PrdProductYktQueryDTO query,String userType) {
		PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
		DodopalResponse<DodopalDataPage<PrdProductYktDTO>> response = new DodopalResponse<DodopalDataPage<PrdProductYktDTO>>();
		if(MerUserTypeEnum.PERSONAL.getCode().equals(userType)){
			//个人
			response = facade.findAvailableIcdcProductsInCityByPage(query);
        }else {
        	//商户
        	response = facade.findAvailableIcdcProductsForMerchantByPage(query);
        }
		return response;
	}

	//基于城市查询公交卡充值产品
	public DodopalResponse<List<PrdProductYktDTO>> findAvailableIcdcProductsInCity(PrdProductYktQueryDTO query,String userType) {
		PrdProductYktFacade facade = getFacade(PrdProductYktFacade.class, DelegateConstant.FACADE_PRODUCT_URL);
		DodopalResponse<List<PrdProductYktDTO>> response = new DodopalResponse<List<PrdProductYktDTO>>();
		if(MerUserTypeEnum.PERSONAL.getCode().equals(userType)){
			//个人
			response = facade.findAvailableIcdcProductsInCity(query.getCityId());
        }else {
        	//商户
        	response = facade.findAvailableIcdcProductsForMerchant(query.getMerCode(), query.getCityId());
        }
		return response;
	}

}
