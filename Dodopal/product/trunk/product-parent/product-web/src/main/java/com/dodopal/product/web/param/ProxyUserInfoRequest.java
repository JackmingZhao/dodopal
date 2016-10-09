package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class ProxyUserInfoRequest extends BaseRequest {
	private String mercode;
	private String usercode;

	public String getMercode() {
		return mercode;
	}

	public void setMercode(String mercode) {
		this.mercode = mercode;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

}
