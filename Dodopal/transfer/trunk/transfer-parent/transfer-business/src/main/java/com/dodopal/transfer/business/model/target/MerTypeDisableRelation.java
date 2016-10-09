package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the MER_TYPE_DISABLE_RELATION database table.
 * 
 */
//@Entity
//@Table(name="MER_TYPE_DISABLE_RELATION")
//@NamedQuery(name="MerTypeDisableRelation.findAll", query="SELECT m FROM MerTypeDisableRelation m")
public class MerTypeDisableRelation implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private MerTypeDisableRelationPK id;

	public MerTypeDisableRelation() {
	}

	public MerTypeDisableRelationPK getId() {
		return this.id;
	}

	public void setId(MerTypeDisableRelationPK id) {
		this.id = id;
	}

}