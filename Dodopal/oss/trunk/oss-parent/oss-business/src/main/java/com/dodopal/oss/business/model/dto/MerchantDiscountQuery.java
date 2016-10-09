package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

public class MerchantDiscountQuery extends QueryBase {

	private static final long serialVersionUID = 1L;
	
	private String userDiscount;
	
	private String merCode;//商户号
	
	private String discountThreshold;//折扣阀值
	
	private String merId;
	

	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getDiscountThreshold() {
		return discountThreshold;
	}
	public void setDiscountThreshold(String discountThreshold) {
		this.discountThreshold = discountThreshold;
	}
	public String getMerCode() {
		return merCode;
	}
	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
	public String getUserDiscount() {
		return userDiscount;
	}
	public void setUserDiscount(String userDiscount) {
		this.userDiscount = userDiscount;
	}
	
}
