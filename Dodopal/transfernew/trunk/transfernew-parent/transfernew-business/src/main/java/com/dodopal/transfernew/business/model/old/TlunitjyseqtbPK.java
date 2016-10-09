package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the TLUNITJYSEQTB database table.
 * 
 */
//@Embeddable
public class TlunitjyseqtbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String orderno;

	private String mchnitid;

	private long censeq;

	public TlunitjyseqtbPK() {
	}
	public String getOrderno() {
		return this.orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getMchnitid() {
		return this.mchnitid;
	}
	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}
	public long getCenseq() {
		return this.censeq;
	}
	public void setCenseq(long censeq) {
		this.censeq = censeq;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TlunitjyseqtbPK)) {
			return false;
		}
		TlunitjyseqtbPK castOther = (TlunitjyseqtbPK)other;
		return 
			this.orderno.equals(castOther.orderno)
			&& this.mchnitid.equals(castOther.mchnitid)
			&& (this.censeq == castOther.censeq);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orderno.hashCode();
		hash = hash * prime + this.mchnitid.hashCode();
		hash = hash * prime + ((int) (this.censeq ^ (this.censeq >>> 32)));
		
		return hash;
	}
}