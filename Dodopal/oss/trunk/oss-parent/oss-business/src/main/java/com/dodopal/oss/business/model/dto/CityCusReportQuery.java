package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

/**
 * @author Mika
 */
public class CityCusReportQuery extends QueryBase {

	private static final long serialVersionUID = 609083284408871864L;
	private String queryDate;
	private String cityCode;		// 城市编号
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	
	
	
}
