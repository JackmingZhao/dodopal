package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the GROUPDEPARTMENTB database table.
 * 
 */
//@Embeddable
public class GroupdepartmentbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long groupid;

	private long departmentid;

	public GroupdepartmentbPK() {
	}
	public long getGroupid() {
		return this.groupid;
	}
	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}
	public long getDepartmentid() {
		return this.departmentid;
	}
	public void setDepartmentid(long departmentid) {
		this.departmentid = departmentid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GroupdepartmentbPK)) {
			return false;
		}
		GroupdepartmentbPK castOther = (GroupdepartmentbPK)other;
		return 
			(this.groupid == castOther.groupid)
			&& (this.departmentid == castOther.departmentid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.groupid ^ (this.groupid >>> 32)));
		hash = hash * prime + ((int) (this.departmentid ^ (this.departmentid >>> 32)));
		
		return hash;
	}
}