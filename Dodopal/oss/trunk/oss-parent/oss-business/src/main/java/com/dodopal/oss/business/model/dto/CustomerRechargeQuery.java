package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

public class CustomerRechargeQuery extends QueryBase {

	private static final long serialVersionUID = 1L;
	private String merName;//客户名称
	private String cityName;//城市名称
	private String startDate;//交易开始时间
	private String endDate;//交易结束时间
	private String cityCode;//城市编码
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	
}
