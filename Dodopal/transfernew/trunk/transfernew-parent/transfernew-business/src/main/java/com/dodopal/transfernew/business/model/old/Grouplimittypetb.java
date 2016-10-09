package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the GROUPLIMITTYPETB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Grouplimittypetb.findAll", query="SELECT g FROM Grouplimittypetb g")
public class Grouplimittypetb implements Serializable {
	private static final long serialVersionUID = 1L;

	private String remarks;

	private BigDecimal status;

	private BigDecimal typeid;

	private String typename;

	public Grouplimittypetb() {
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public BigDecimal getTypeid() {
		return this.typeid;
	}

	public void setTypeid(BigDecimal typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

}