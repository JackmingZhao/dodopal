package com.dodopal.oss.business.model;

import java.util.Date;
import java.util.List;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.BaseEntity;

public class DiscountMerchantInfo extends BaseEntity {

	private static final long serialVersionUID = -8234399083828908386L;

    /*商户编码*/
    private String merCode;
    /*商户名称*/
    private String merName;
    /*商户类型*/
    private String merType;
    /*是否绑定相应的折扣*/
    private String isBoundleDiscount;
    
    private String discountId;
    private Date beginDate; 			// 折扣开始日期
	private Date endDate; 				// 折扣结束日期
	private String week; 				// 星期
	private String beginTime; 			// 折扣开始时间
	private String endTime; 			// 折扣结束时间
	private Integer discountThreshold; 	// 折扣阀值
    
	public String getMerCode() {
		return merCode;
	}
	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getMerType() {
		return MerTypeEnum.getNameByCode(merType);
	}
	public void setMerType(String merType) {
		this.merType = merType;
	}
	public String getIsBoundleDiscount() {
		return isBoundleDiscount;
	}
	public void setIsBoundleDiscount(String isBoundleDiscount) {
		this.isBoundleDiscount = isBoundleDiscount;
	}
	public String getDiscountId() {
		return discountId;
	}
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
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
	public Integer getDiscountThreshold() {
		return discountThreshold;
	}
	public void setDiscountThreshold(Integer discountThreshold) {
		this.discountThreshold = discountThreshold;
	}
    
    
}
