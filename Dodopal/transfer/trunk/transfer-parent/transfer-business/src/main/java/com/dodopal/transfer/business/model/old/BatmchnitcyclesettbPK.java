package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the BATMCHNITCYCLESETTB database table.
 * 
 */
//@Embeddable
public class BatmchnitcyclesettbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String mchnitid;

	private long setdate;

	private long txncode;

	private long yktid;

	public BatmchnitcyclesettbPK() {
	}
	public String getMchnitid() {
		return this.mchnitid;
	}
	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}
	public long getSetdate() {
		return this.setdate;
	}
	public void setSetdate(long setdate) {
		this.setdate = setdate;
	}
	public long getTxncode() {
		return this.txncode;
	}
	public void setTxncode(long txncode) {
		this.txncode = txncode;
	}
	public long getYktid() {
		return this.yktid;
	}
	public void setYktid(long yktid) {
		this.yktid = yktid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BatmchnitcyclesettbPK)) {
			return false;
		}
		BatmchnitcyclesettbPK castOther = (BatmchnitcyclesettbPK)other;
		return 
			this.mchnitid.equals(castOther.mchnitid)
			&& (this.setdate == castOther.setdate)
			&& (this.txncode == castOther.txncode)
			&& (this.yktid == castOther.yktid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.mchnitid.hashCode();
		hash = hash * prime + ((int) (this.setdate ^ (this.setdate >>> 32)));
		hash = hash * prime + ((int) (this.txncode ^ (this.txncode >>> 32)));
		hash = hash * prime + ((int) (this.yktid ^ (this.yktid >>> 32)));
		
		return hash;
	}
}