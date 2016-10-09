package com.dodopal.product.delegate;

import com.dodopal.common.model.DodopalResponse;

/**
 * @author lifeng@dodopal.com
 */

public interface PayRefundDelegate {
	/**
	 * 手机验证码验证
	 * 
	 * @param tranCode   交易流水号
	 * @param source     来源
	 * @param userid     用户号
	 * @return
	 */
	public DodopalResponse<String> sendRefund(String tranCode, String source, String userid);
}
