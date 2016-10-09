package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the GROUPINPROXYTB database table.
 * 
 */
//@Embeddable
public class GroupinproxytbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long groupid;

	private long proxyid;

	public GroupinproxytbPK() {
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
		if (!(other instanceof GroupinproxytbPK)) {
			return false;
		}
		GroupinproxytbPK castOther = (GroupinproxytbPK)other;
		return 
			(this.groupid == castOther.groupid)
			&& (this.proxyid == castOther.proxyid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.groupid ^ (this.groupid >>> 32)));
		hash = hash * prime + ((int) (this.proxyid ^ (this.proxyid >>> 32)));
		
		return hash;
	}
}