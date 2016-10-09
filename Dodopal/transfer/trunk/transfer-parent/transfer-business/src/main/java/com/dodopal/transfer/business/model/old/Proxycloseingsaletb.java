package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the PROXYCLOSEINGSALETB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Proxycloseingsaletb.findAll", query="SELECT p FROM Proxycloseingsaletb p")
public class Proxycloseingsaletb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private ProxycloseingsaletbPK id;

	private String createdatetime;

	private String groupsaleseq;

	private String seq;

	public Proxycloseingsaletb() {
	}

	public ProxycloseingsaletbPK getId() {
		return this.id;
	}

	public void setId(ProxycloseingsaletbPK id) {
		this.id = id;
	}

	public String getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(String createdatetime) {
		this.createdatetime = createdatetime;
	}

	public String getGroupsaleseq() {
		return this.groupsaleseq;
	}

	public void setGroupsaleseq(String groupsaleseq) {
		this.groupsaleseq = groupsaleseq;
	}

	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

}