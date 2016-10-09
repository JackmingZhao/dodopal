package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the PROXYOFFLINESALETB database table.
 * 
 */
//@Embeddable
public class ProxyofflinesaletbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long proxyid;

	private long sale;

	private long status;

	public ProxyofflinesaletbPK() {
	}
	public long getProxyid() {
		return this.proxyid;
	}
	public void setProxyid(long proxyid) {
		this.proxyid = proxyid;
	}
	public long getSale() {
		return this.sale;
	}
	public void setSale(long sale) {
		this.sale = sale;
	}
	public long getStatus() {
		return this.status;
	}
	public void setStatus(long status) {
		this.status = status;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProxyofflinesaletbPK)) {
			return false;
		}
		ProxyofflinesaletbPK castOther = (ProxyofflinesaletbPK)other;
		return 
			(this.proxyid == castOther.proxyid)
			&& (this.sale == castOther.sale)
			&& (this.status == castOther.status);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.proxyid ^ (this.proxyid >>> 32)));
		hash = hash * prime + ((int) (this.sale ^ (this.sale >>> 32)));
		hash = hash * prime + ((int) (this.status ^ (this.status >>> 32)));
		
		return hash;
	}
}