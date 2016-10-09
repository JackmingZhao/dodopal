package com.dodopal.transfer.business.model.target;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

public class TranDiscount extends BaseEntity {

	private static final long serialVersionUID = -28618853373130047L;
	private Date beginDate;
	private Date endDate;
	private String week;
	private String beginTime;
	private String endTime;
	private String discountThreshold;
	private String setDiscount;
	private String oldSaleId;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDiscountThreshold() {
		return discountThreshold;
	}

	public void setDiscountThreshold(String discountThreshold) {
		this.discountThreshold = discountThreshold;
	}

	public String getSetDiscount() {
		return setDiscount;
	}

	public void setSetDiscount(String setDiscount) {
		this.setDiscount = setDiscount;
	}

	public String getOldSaleId() {
		return oldSaleId;
	}

	public void setOldSaleId(String oldSaleId) {
		this.oldSaleId = oldSaleId;
	}

}
