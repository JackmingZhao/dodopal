package com.dodopal.portal.business.service.impl;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianRuntimeException;
import com.dodopal.api.product.dto.ProductYKTDTO;
import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.ProductYKTBean;
import com.dodopal.portal.business.service.ProductYktService;
import com.dodopal.portal.delegate.ProductYktDelegate;

@Service
public class ProductYktServiceImpl implements ProductYktService{
    private final static Logger log = LoggerFactory.getLogger(ProductYktServiceImpl.class);
    @Autowired
    private ProductYktDelegate productDelegate;
	@Override
	public DodopalResponse<ProductYKTBean> getYktInfoByBusinessCityCode(String cityCode) {
		DodopalResponse<ProductYKTBean> dodopalResponse = new DodopalResponse<ProductYKTBean>();
		ProductYKTBean bean = new ProductYKTBean();
		try{
			DodopalResponse<ProductYKTDTO>  dtoResponse= productDelegate.getYktInfoByBusinessCityCode(cityCode);
			if(ResponseCode.SUCCESS.equals(dtoResponse.getCode())){
				if(null!=dtoResponse.getResponseEntity()){
					PropertyUtils.copyProperties(bean, dtoResponse.getResponseEntity());
				}
			}
			dodopalResponse.setCode(dtoResponse.getCode());
			dodopalResponse.setResponseEntity(bean);
		}catch(HessianRuntimeException e){
			dodopalResponse.setCode(ResponseCode.PORTAL_PRODUCT_HESSIAN_ERR);
			log.error("ProductYktServiceImpl getYktInfoByBusinessCityCode call an error",e);
		}catch(Exception e){
			log.error("ProductYktServiceImpl getYktInfoByBusinessCityCode call an error",e);
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}
    
    
}
