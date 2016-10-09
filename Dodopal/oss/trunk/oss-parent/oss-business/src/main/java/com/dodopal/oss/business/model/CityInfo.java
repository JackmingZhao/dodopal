package com.dodopal.oss.business.model;

import java.io.Serializable;

public class CityInfo implements Serializable {

	private static final long serialVersionUID = 4531843553745309270L;

	private String cityCode;
	private String cityName;
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
}
