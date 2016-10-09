package com.dodopal.product.business.bean;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

public class MerUserCardBDFindBean extends BaseEntity{
	private static final long serialVersionUID = -3045468204310607256L;
	//绑卡时间开始
    private Date bundLingDateStart;
    //绑卡时间结束
    private Date bundLingDateEnd;
    //解绑时间开始
    private Date unBundLingDateStart;
    //解绑时间结束
    private Date unBundLingDateEnd;
    //用户号
    private String merUserName;
    //卡号
    private String cardCode;
    //状态
    private String bundLingType;
    //解绑人
    private String unBundLingUser;
	public Date getBundLingDateStart() {
		return bundLingDateStart;
	}
	public void setBundLingDateStart(Date bundLingDateStart) {
		this.bundLingDateStart = bundLingDateStart;
	}
	public Date getBundLingDateEnd() {
		return bundLingDateEnd;
	}
	public void setBundLingDateEnd(Date bundLingDateEnd) {
		this.bundLingDateEnd = bundLingDateEnd;
	}
	public Date getUnBundLingDateStart() {
		return unBundLingDateStart;
	}
	public void setUnBundLingDateStart(Date unBundLingDateStart) {
		this.unBundLingDateStart = unBundLingDateStart;
	}
	public Date getUnBundLingDateEnd() {
		return unBundLingDateEnd;
	}
	public void setUnBundLingDateEnd(Date unBundLingDateEnd) {
		this.unBundLingDateEnd = unBundLingDateEnd;
	}
	public String getMerUserName() {
		return merUserName;
	}
	public void setMerUserName(String merUserName) {
		this.merUserName = merUserName;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getBundLingType() {
		return bundLingType;
	}
	public void setBundLingType(String bundLingType) {
		this.bundLingType = bundLingType;
	}
	public String getUnBundLingUser() {
		return unBundLingUser;
	}
	public void setUnBundLingUser(String unBundLingUser) {
		this.unBundLingUser = unBundLingUser;
	}
}
