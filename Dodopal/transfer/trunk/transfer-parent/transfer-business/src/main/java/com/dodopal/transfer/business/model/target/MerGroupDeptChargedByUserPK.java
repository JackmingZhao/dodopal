package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the MER_GROUP_DEPT_CHARGED_BY_USER database table.
 * 
 */
//@Embeddable
public class MerGroupDeptChargedByUserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

//	@Column(name="MER_USER_NAME")
	private String merUserName;

//	@Column(name="MER_GRP_DEP_ID")
	private String merGrpDepId;

	public MerGroupDeptChargedByUserPK() {
	}
	public String getMerUserName() {
		return this.merUserName;
	}
	public void setMerUserName(String merUserName) {
		this.merUserName = merUserName;
	}
	public String getMerGrpDepId() {
		return this.merGrpDepId;
	}
	public void setMerGrpDepId(String merGrpDepId) {
		this.merGrpDepId = merGrpDepId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MerGroupDeptChargedByUserPK)) {
			return false;
		}
		MerGroupDeptChargedByUserPK castOther = (MerGroupDeptChargedByUserPK)other;
		return 
			this.merUserName.equals(castOther.merUserName)
			&& this.merGrpDepId.equals(castOther.merGrpDepId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.merUserName.hashCode();
		hash = hash * prime + this.merGrpDepId.hashCode();
		
		return hash;
	}
}