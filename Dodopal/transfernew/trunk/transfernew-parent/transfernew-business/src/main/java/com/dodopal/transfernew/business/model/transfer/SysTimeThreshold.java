package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SYS_TIME_THRESHOLD database table.
 * 
 */
//@Entity
//@Table(name="SYS_TIME_THRESHOLD")
//@NamedQuery(name="SysTimeThreshold.findAll", query="SELECT s FROM SysTimeThreshold s")
public class SysTimeThreshold implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

	private String code;

	private BigDecimal threshold;

	public SysTimeThreshold() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getThreshold() {
		return this.threshold;
	}

	public void setThreshold(BigDecimal threshold) {
		this.threshold = threshold;
	}

}