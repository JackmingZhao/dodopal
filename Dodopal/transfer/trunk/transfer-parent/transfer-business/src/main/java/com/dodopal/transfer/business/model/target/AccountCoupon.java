package com.dodopal.transfer.business.model.target;

import java.io.Serializable;

public class AccountCoupon implements Serializable{

	private static final long serialVersionUID = 5984805783360510122L;
	private String id;

	private String accountCode;
	
	private String couponAccountCode;
	
	private int couponAccountCount;
	
	private int useAccountCount;
	
	private int surplusAccountCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getCouponAccountCode() {
		return couponAccountCode;
	}

	public void setCouponAccountCode(String couponAccountCode) {
		this.couponAccountCode = couponAccountCode;
	}

	public int getCouponAccountCount() {
		return couponAccountCount;
	}

	public void setCouponAccountCount(int couponAccountCount) {
		this.couponAccountCount = couponAccountCount;
	}

	public int getUseAccountCount() {
		return useAccountCount;
	}

	public void setUseAccountCount(int useAccountCount) {
		this.useAccountCount = useAccountCount;
	}

	public int getSurplusAccountCount() {
		return surplusAccountCount;
	}

	public void setSurplusAccountCount(int surplusAccountCount) {
		this.surplusAccountCount = surplusAccountCount;
	}
	
}
