package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the GROUPPROXYCLOSEINGSALETB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Groupproxycloseingsaletb.findAll", query="SELECT g FROM Groupproxycloseingsaletb g")
public class Groupproxycloseingsaletb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private GroupproxycloseingsaletbPK id;

	private String createdatetime;

	private String seq;

	public Groupproxycloseingsaletb() {
	}

	public GroupproxycloseingsaletbPK getId() {
		return this.id;
	}

	public void setId(GroupproxycloseingsaletbPK id) {
		this.id = id;
	}

	public String getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(String createdatetime) {
		this.createdatetime = createdatetime;
	}

	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

}