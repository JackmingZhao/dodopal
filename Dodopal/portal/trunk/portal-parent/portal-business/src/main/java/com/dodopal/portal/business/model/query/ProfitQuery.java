package com.dodopal.portal.business.model.query;

import com.dodopal.common.model.QueryBase;

public class ProfitQuery extends QueryBase{

	private static final long serialVersionUID = 3824245781167224229L;

	/**
	 * 开始时间
	 */
	private String startDate;
	
	/**
	 * 结束时间
	 */
	private String endDate;
	
	/**
	 * 商户号
	 */
	private String merCode;
	/**
	 * 汇总日期
	 */
	private String collectDate;
	

	public String getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
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

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
	
}
