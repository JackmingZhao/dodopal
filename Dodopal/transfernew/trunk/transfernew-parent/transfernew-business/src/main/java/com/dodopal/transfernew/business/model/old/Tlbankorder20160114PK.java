package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the TLBANKORDER20160114 database table.
 * 
 */
//@Embeddable
public class Tlbankorder20160114PK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String orderid;

	private long bankid;

	public Tlbankorder20160114PK() {
	}
	public String getOrderid() {
		return this.orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public long getBankid() {
		return this.bankid;
	}
	public void setBankid(long bankid) {
		this.bankid = bankid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Tlbankorder20160114PK)) {
			return false;
		}
		Tlbankorder20160114PK castOther = (Tlbankorder20160114PK)other;
		return 
			this.orderid.equals(castOther.orderid)
			&& (this.bankid == castOther.bankid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orderid.hashCode();
		hash = hash * prime + ((int) (this.bankid ^ (this.bankid >>> 32)));
		
		return hash;
	}
}