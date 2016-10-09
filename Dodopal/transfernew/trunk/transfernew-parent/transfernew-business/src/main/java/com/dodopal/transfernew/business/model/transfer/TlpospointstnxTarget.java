package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
import java.math.BigDecimal;

public class TlpospointstnxTarget implements Serializable {

	private static final long serialVersionUID = 5903099913287154823L;
	private String id;
	
	private String ponitsAccountCode;
	
	private String applytime;

	private String orderid;

	private String pointspara;

	private BigDecimal pointtxn;

	private BigDecimal pointvalue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPonitsAccountCode() {
		return ponitsAccountCode;
	}

	public void setPonitsAccountCode(String ponitsAccountCode) {
		this.ponitsAccountCode = ponitsAccountCode;
	}

	public String getApplytime() {
		return applytime;
	}

	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPointspara() {
		return pointspara;
	}

	public void setPointspara(String pointspara) {
		this.pointspara = pointspara;
	}

	public BigDecimal getPointtxn() {
		return pointtxn;
	}

	public void setPointtxn(BigDecimal pointtxn) {
		this.pointtxn = pointtxn;
	}

	public BigDecimal getPointvalue() {
		return pointvalue;
	}

	public void setPointvalue(BigDecimal pointvalue) {
		this.pointvalue = pointvalue;
	}

}
