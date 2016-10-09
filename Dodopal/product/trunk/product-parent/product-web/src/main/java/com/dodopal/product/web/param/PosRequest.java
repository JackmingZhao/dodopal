package com.dodopal.product.web.param;

/**
 * @author lifeng@dodopal.com
 */

public class PosRequest extends BaseRequest {
	private String mercode;
	private String posid;
	private String remarks;

	public String getMercode() {
		return mercode;
	}

	public void setMercode(String mercode) {
		this.mercode = mercode;
	}

	public String getPosid() {
		return posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
