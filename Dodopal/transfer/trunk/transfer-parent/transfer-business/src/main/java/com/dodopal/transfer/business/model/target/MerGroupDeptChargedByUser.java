package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the MER_GROUP_DEPT_CHARGED_BY_USER database table.
 * 
 */
//@Entity
//@Table(name="MER_GROUP_DEPT_CHARGED_BY_USER")
//@NamedQuery(name="MerGroupDeptChargedByUser.findAll", query="SELECT m FROM MerGroupDeptChargedByUser m")
public class MerGroupDeptChargedByUser implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private MerGroupDeptChargedByUserPK id;

	public MerGroupDeptChargedByUser() {
	}

	public MerGroupDeptChargedByUserPK getId() {
		return this.id;
	}

	public void setId(MerGroupDeptChargedByUserPK id) {
		this.id = id;
	}

}