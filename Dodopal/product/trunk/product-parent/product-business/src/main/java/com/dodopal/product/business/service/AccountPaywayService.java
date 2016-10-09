package com.dodopal.product.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalResponse;
import com.dodopal.product.business.bean.PayWayResultBean;

public interface AccountPaywayService {

	
	/**
	 * 常用支付方式
	 * @param ext 是否为外接商户（true 是|false 否）
	 * @param userCode 商户编号  如果是外接商户，商户编号不能为空
	 * @return DodopalResponse<List<PayWayBean>>
	 */
	DodopalResponse<List<PayWayResultBean>> findCommonPayWay(boolean ext,String userCode);
}
