package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the PROXYAUTOADDEDUOPERINFOTB database table.
 * 
 */
//@Embeddable
public class ProxyautoaddeduoperinfotbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String seq;

	private long groupid;

	private long proxyid;

	public ProxyautoaddeduoperinfotbPK() {
	}
	public String getSeq() {
		return this.seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public long getGroupid() {
		return this.groupid;
	}
	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}
	public long getProxyid() {
		return this.proxyid;
	}
	public void setProxyid(long proxyid) {
		this.proxyid = proxyid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProxyautoaddeduoperinfotbPK)) {
			return false;
		}
		ProxyautoaddeduoperinfotbPK castOther = (ProxyautoaddeduoperinfotbPK)other;
		return 
			this.seq.equals(castOther.seq)
			&& (this.groupid == castOther.groupid)
			&& (this.proxyid == castOther.proxyid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.seq.hashCode();
		hash = hash * prime + ((int) (this.groupid ^ (this.groupid >>> 32)));
		hash = hash * prime + ((int) (this.proxyid ^ (this.proxyid >>> 32)));
		
		return hash;
	}
}