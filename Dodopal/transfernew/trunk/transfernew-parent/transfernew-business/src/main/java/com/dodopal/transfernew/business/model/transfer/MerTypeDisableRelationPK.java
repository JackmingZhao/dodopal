package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;

/**
 * The primary key class for the MER_TYPE_DISABLE_RELATION database table.
 * 
 */
//@Embeddable
public class MerTypeDisableRelationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

//	@Column(name="MER_TYPE")
	private String merType;

//	@Column(name="DISABLE_RELATION_TYPE")
	private String disableRelationType;

	public MerTypeDisableRelationPK() {
	}
	public String getMerType() {
		return this.merType;
	}
	public void setMerType(String merType) {
		this.merType = merType;
	}
	public String getDisableRelationType() {
		return this.disableRelationType;
	}
	public void setDisableRelationType(String disableRelationType) {
		this.disableRelationType = disableRelationType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MerTypeDisableRelationPK)) {
			return false;
		}
		MerTypeDisableRelationPK castOther = (MerTypeDisableRelationPK)other;
		return 
			this.merType.equals(castOther.merType)
			&& this.disableRelationType.equals(castOther.disableRelationType);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.merType.hashCode();
		hash = hash * prime + this.disableRelationType.hashCode();
		
		return hash;
	}
}