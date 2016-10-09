package com.dodopal.product.delegate;

import java.util.List;

import com.dodopal.api.payment.dto.PayWayDTO;
import com.dodopal.common.model.DodopalResponse;

public interface AccountPaywayDelegate {

	
	/**
	 * 常用支付
	 * @param ext 是否为外接商户（true 是|false 否）
	 * @param userCode 商户编号  如果是外接商户，商户编号不能为空
	 * @return DodopalResponse<List<PayWayDTO>>
	 */
	public DodopalResponse<List<PayWayDTO>> findCommonPayWay(boolean ext,String custcode);
}
