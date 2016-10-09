package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the GROUPSALETB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Groupsaletb.findAll", query="SELECT g FROM Groupsaletb g")
public class Groupsaletb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private GroupsaletbPK id;

	private String createdatetime;

	private String deletedatetime;

	private String seq;

	public Groupsaletb() {
	}

	public GroupsaletbPK getId() {
		return this.id;
	}

	public void setId(GroupsaletbPK id) {
		this.id = id;
	}

	public String getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(String createdatetime) {
		this.createdatetime = createdatetime;
	}

	public String getDeletedatetime() {
		return this.deletedatetime;
	}

	public void setDeletedatetime(String deletedatetime) {
		this.deletedatetime = deletedatetime;
	}

	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

}