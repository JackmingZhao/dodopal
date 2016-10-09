package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the GROUPSTAFFINFOTB database table.
 * 
 */
//@Embeddable
public class GroupstaffinfotbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long groupid;

	private String staffid;

	public GroupstaffinfotbPK() {
	}
	public long getGroupid() {
		return this.groupid;
	}
	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}
	public String getStaffid() {
		return this.staffid;
	}
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GroupstaffinfotbPK)) {
			return false;
		}
		GroupstaffinfotbPK castOther = (GroupstaffinfotbPK)other;
		return 
			(this.groupid == castOther.groupid)
			&& this.staffid.equals(castOther.staffid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.groupid ^ (this.groupid >>> 32)));
		hash = hash * prime + this.staffid.hashCode();
		
		return hash;
	}
}