package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class ProxyInfoRequest extends BaseRequest {
	// 商户编号（网点编号）
	private String mercode;

	public String getMercode() {
		return mercode;
	}

	public void setMercode(String mercode) {
		this.mercode = mercode;
	}

}
