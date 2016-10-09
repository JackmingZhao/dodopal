package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the GROUPSALETB database table.
 * 
 */
//@Embeddable
public class GroupsaletbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long groupid;

	private long sale;

	private long status;

	public GroupsaletbPK() {
	}
	public long getGroupid() {
		return this.groupid;
	}
	public void setGroupid(long groupid) {
		this.groupid = groupid;
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
		if (!(other instanceof GroupsaletbPK)) {
			return false;
		}
		GroupsaletbPK castOther = (GroupsaletbPK)other;
		return 
			(this.groupid == castOther.groupid)
			&& (this.sale == castOther.sale)
			&& (this.status == castOther.status);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.groupid ^ (this.groupid >>> 32)));
		hash = hash * prime + ((int) (this.sale ^ (this.sale >>> 32)));
		hash = hash * prime + ((int) (this.status ^ (this.status >>> 32)));
		
		return hash;
	}
}