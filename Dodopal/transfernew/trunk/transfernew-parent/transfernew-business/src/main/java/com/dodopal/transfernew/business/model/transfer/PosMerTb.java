package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the POS_MER_TB database table.
 * 
 */
//@Entity
//@Table(name="POS_MER_TB")
//@NamedQuery(name="PosMerTb.findAll", query="SELECT p FROM PosMerTb p")
public class PosMerTb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

	private String code;

	private String comments;

//	@Column(name="MER_CODE")
	private String merCode;

	public PosMerTb() {
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

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMerCode() {
		return this.merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

}