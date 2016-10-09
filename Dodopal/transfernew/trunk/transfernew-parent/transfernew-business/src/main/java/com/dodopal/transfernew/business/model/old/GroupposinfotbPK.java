package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the GROUPPOSINFOTB database table.
 * 
 */
//@Embeddable
public class GroupposinfotbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long groupid;

	private long posid;

	public GroupposinfotbPK() {
	}
	public long getGroupid() {
		return this.groupid;
	}
	public void setGroupid(long groupid) {
		this.groupid = groupid;
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
		if (!(other instanceof GroupposinfotbPK)) {
			return false;
		}
		GroupposinfotbPK castOther = (GroupposinfotbPK)other;
		return 
			(this.groupid == castOther.groupid)
			&& (this.posid == castOther.posid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.groupid ^ (this.groupid >>> 32)));
		hash = hash * prime + ((int) (this.posid ^ (this.posid >>> 32)));
		
		return hash;
	}
}