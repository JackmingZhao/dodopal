package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the MER_DISCOUNT_REFER database table.
 * 
 */
//@Entity
//@Table(name="MER_DISCOUNT_REFER")
//@NamedQuery(name="MerDiscountRefer.findAll", query="SELECT m FROM MerDiscountRefer m")
public class MerDiscountRefer implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Column(name="MER_CODE")
	private String merCode;

//	@Column(name="MER_DISCOUNT_ID")
	private String merDiscountId;

	public MerDiscountRefer() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerCode() {
		return this.merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getMerDiscountId() {
		return this.merDiscountId;
	}

	public void setMerDiscountId(String merDiscountId) {
		this.merDiscountId = merDiscountId;
	}

}