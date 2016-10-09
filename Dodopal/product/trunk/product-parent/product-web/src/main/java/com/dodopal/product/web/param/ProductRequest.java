package com.dodopal.product.web.param;

public class ProductRequest extends BaseRequest {
	//城市编码
	private String citycode;
	//产品版本号
	private String proversion;
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getProversion() {
		return proversion;
	}
	public void setProversion(String proversion) {
		this.proversion = proversion;
	}
	
}
