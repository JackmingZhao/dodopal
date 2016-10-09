package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
import java.math.BigDecimal;

public class Tlpospoints implements Serializable{

	private static final long serialVersionUID = 6406912728846014950L;

	private String id;
	//主帐号编号
	private String accountCode;
	//积分主帐号编号
	private String ponitsAccountCode;
	//积分值
	private BigDecimal totalPoints;
	//最近一次变动积分
	private BigDecimal lastChangePoints;
	//变动前积分
	private BigDecimal beforeChangePoints;
	//清空年度，默认为0，没有清空过
	private BigDecimal clearyear;
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
	public String getPonitsAccountCode() {
		return ponitsAccountCode;
	}
	public void setPonitsAccountCode(String ponitsAccountCode) {
		this.ponitsAccountCode = ponitsAccountCode;
	}
	public BigDecimal getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(BigDecimal totalPoints) {
		this.totalPoints = totalPoints;
	}
	public BigDecimal getLastChangePoints() {
		return lastChangePoints;
	}
	public void setLastChangePoints(BigDecimal lastChangePoints) {
		this.lastChangePoints = lastChangePoints;
	}
	public BigDecimal getBeforeChangePoints() {
		return beforeChangePoints;
	}
	public void setBeforeChangePoints(BigDecimal beforeChangePoints) {
		this.beforeChangePoints = beforeChangePoints;
	}
	public BigDecimal getClearyear() {
		return clearyear;
	}
	public void setClearyear(BigDecimal clearyear) {
		this.clearyear = clearyear;
	}
	
	
}
