package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the PROXYPOSTB database table.
 * 
 */
//@Embeddable
public class ProxypostbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long proxyid;

	private long posid;

	public ProxypostbPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProxypostbPK)) {
			return false;
		}
		ProxypostbPK castOther = (ProxypostbPK)other;
		return 
			(this.proxyid == castOther.proxyid)
			&& (this.posid == castOther.posid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.proxyid ^ (this.proxyid >>> 32)));
		hash = hash * prime + ((int) (this.posid ^ (this.posid >>> 32)));
		
		return hash;
	}
}