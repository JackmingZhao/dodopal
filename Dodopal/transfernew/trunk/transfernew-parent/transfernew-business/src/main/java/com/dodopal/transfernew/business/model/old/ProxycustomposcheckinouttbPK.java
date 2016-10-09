package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the PROXYCUSTOMPOSCHECKINOUTTB database table.
 * 
 */
//@Embeddable
public class ProxycustomposcheckinouttbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long proxyid;

	private long posid;

	private String batchid;

	public ProxycustomposcheckinouttbPK() {
	}
	public long getProxyid() {
		return this.proxyid;
	}
	public void setProxyid(long proxyid) {
		this.proxyid = proxyid;
	}
	public long getPosid() {
		return this.posid;
	}
	public void setPosid(long posid) {
		this.posid = posid;
	}
	public String getBatchid() {
		return this.batchid;
	}
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProxycustomposcheckinouttbPK)) {
			return false;
		}
		ProxycustomposcheckinouttbPK castOther = (ProxycustomposcheckinouttbPK)other;
		return 
			(this.proxyid == castOther.proxyid)
			&& (this.posid == castOther.posid)
			&& this.batchid.equals(castOther.batchid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.proxyid ^ (this.proxyid >>> 32)));
		hash = hash * prime + ((int) (this.posid ^ (this.posid >>> 32)));
		hash = hash * prime + this.batchid.hashCode();
		
		return hash;
	}
}