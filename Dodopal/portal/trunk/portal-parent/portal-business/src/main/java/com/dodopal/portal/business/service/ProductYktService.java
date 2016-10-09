package com.dodopal.portal.business.service;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.portal.business.bean.ProductYKTBean;

public interface ProductYktService {
	public DodopalResponse<ProductYKTBean> getYktInfoByBusinessCityCode(String cityCode);

}
