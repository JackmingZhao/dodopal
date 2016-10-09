package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the PROXYOFFLINESALETB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Proxyofflinesaletb.findAll", query="SELECT p FROM Proxyofflinesaletb p")
public class Proxyofflinesaletb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private ProxyofflinesaletbPK id;

	private String createdatetime;

	private String deletedatetime;

	private String groupsaleseq;

	private String seq;

	public Proxyofflinesaletb() {
	}

	public ProxyofflinesaletbPK getId() {
		return this.id;
	}

	public void setId(ProxyofflinesaletbPK id) {
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