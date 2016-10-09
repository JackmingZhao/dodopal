package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the GROUPINGROUPTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Groupingrouptb.findAll", query="SELECT g FROM Groupingrouptb g")
public class Groupingrouptb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private GroupingrouptbPK id;

	public Groupingrouptb() {
	}

	public GroupingrouptbPK getId() {
		return this.id;
	}

	public void setId(GroupingrouptbPK id) {
		this.id = id;
	}

}