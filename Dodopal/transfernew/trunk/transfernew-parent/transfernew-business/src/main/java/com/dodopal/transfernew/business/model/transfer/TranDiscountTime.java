package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TRAN_DISCOUNT_TIME database table.
 * 
 */
//@Entity
//@Table(name="TRAN_DISCOUNT_TIME")
//@NamedQuery(name="TranDiscountTime.findAll", query="SELECT t FROM TranDiscountTime t")
public class TranDiscountTime implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

	private String bak1;

	private String bak10;

	private String bak2;

	private String bak3;

	private String bak4;

	private String bak5;

	private String bak6;

	private String bak7;

	private String bak8;

	private String bak9;

//	@Column(name="BEGIN_TIME")
	private String beginTime;

//	@Column(name="DATE_ID")
	private String dateId;

//	@Column(name="DISCOUNT_THRESHOLD")
	private BigDecimal discountThreshold;

//	@Column(name="END_TIME")
	private String endTime;

//	@Column(name="MER_CODE")
	private String merCode;
	
	private String week;

	private BigDecimal  setDiscount;
	

    public BigDecimal getSetDiscount() {
        return setDiscount;
    }

    public void setSetDiscount(BigDecimal setDiscount) {
        this.setDiscount = setDiscount;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public TranDiscountTime() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBak1() {
		return this.bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak10() {
		return this.bak10;
	}

	public void setBak10(String bak10) {
		this.bak10 = bak10;
	}

	public String getBak2() {
		return this.bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public String getBak3() {
		return this.bak3;
	}

	public void setBak3(String bak3) {
		this.bak3 = bak3;
	}

	public String getBak4() {
		return this.bak4;
	}

	public void setBak4(String bak4) {
		this.bak4 = bak4;
	}

	public String getBak5() {
		return this.bak5;
	}

	public void setBak5(String bak5) {
		this.bak5 = bak5;
	}

	public String getBak6() {
		return this.bak6;
	}

	public void setBak6(String bak6) {
		this.bak6 = bak6;
	}

	public String getBak7() {
		return this.bak7;
	}

	public void setBak7(String bak7) {
		this.bak7 = bak7;
	}

	public String getBak8() {
		return this.bak8;
	}

	public void setBak8(String bak8) {
		this.bak8 = bak8;
	}

	public String getBak9() {
		return this.bak9;
	}

	public void setBak9(String bak9) {
		this.bak9 = bak9;
	}

	public String getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getDateId() {
		return this.dateId;
	}

	public void setDateId(String dateId) {
		this.dateId = dateId;
	}

	public BigDecimal getDiscountThreshold() {
		return this.discountThreshold;
	}

	public void setDiscountThreshold(BigDecimal discountThreshold) {
		this.discountThreshold = discountThreshold;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMerCode() {
		return this.merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

}