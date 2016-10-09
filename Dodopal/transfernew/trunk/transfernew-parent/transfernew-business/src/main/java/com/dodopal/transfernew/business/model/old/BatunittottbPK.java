package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the BATUNITTOTTB database table.
 * 
 */
//@Embeddable
public class BatunittottbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long setdate;

	private long batchid;

	private String mchnitid;

	private long txncode;

	private long errcode;

	private long yktid;

	public BatunittottbPK() {
	}
	public long getSetdate() {
		return this.setdate;
	}
	public void setSetdate(long setdate) {
		this.setdate = setdate;
	}
	public long getBatchid() {
		return this.batchid;
	}
	public void setBatchid(long batchid) {
		this.batchid = batchid;
	}
	public String getMchnitid() {
		return this.mchnitid;
	}
	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}
	public long getTxncode() {
		return this.txncode;
	}
	public void setTxncode(long txncode) {
		this.txncode = txncode;
	}
	public long getErrcode() {
		return this.errcode;
	}
	public void setErrcode(long errcode) {
		this.errcode = errcode;
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
		if (!(other instanceof BatunittottbPK)) {
			return false;
		}
		BatunittottbPK castOther = (BatunittottbPK)other;
		return 
			(this.setdate == castOther.setdate)
			&& (this.batchid == castOther.batchid)
			&& this.mchnitid.equals(castOther.mchnitid)
			&& (this.txncode == castOther.txncode)
			&& (this.errcode == castOther.errcode)
			&& (this.yktid == castOther.yktid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.setdate ^ (this.setdate >>> 32)));
		hash = hash * prime + ((int) (this.batchid ^ (this.batchid >>> 32)));
		hash = hash * prime + this.mchnitid.hashCode();
		hash = hash * prime + ((int) (this.txncode ^ (this.txncode >>> 32)));
		hash = hash * prime + ((int) (this.errcode ^ (this.errcode >>> 32)));
		hash = hash * prime + ((int) (this.yktid ^ (this.yktid >>> 32)));
		
		return hash;
	}
}