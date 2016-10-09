package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the GROUPINGROUPTB database table.
 * 
 */
//@Embeddable
public class GroupingrouptbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

//	@Column(name="FIRST_GROUPID")
	private long firstGroupid;

//	@Column(name="SECOND_GROUPID")
	private long secondGroupid;

	public GroupingrouptbPK() {
	}
	public long getFirstGroupid() {
		return this.firstGroupid;
	}
	public void setFirstGroupid(long firstGroupid) {
		this.firstGroupid = firstGroupid;
	}
	public long getSecondGroupid() {
		return this.secondGroupid;
	}
	public void setSecondGroupid(long secondGroupid) {
		this.secondGroupid = secondGroupid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GroupingrouptbPK)) {
			return false;
		}
		GroupingrouptbPK castOther = (GroupingrouptbPK)other;
		return 
			(this.firstGroupid == castOther.firstGroupid)
			&& (this.secondGroupid == castOther.secondGroupid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.firstGroupid ^ (this.firstGroupid >>> 32)));
		hash = hash * prime + ((int) (this.secondGroupid ^ (this.secondGroupid >>> 32)));
		
		return hash;
	}
}