package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the USERSOURCETB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Usersourcetb.findAll", query="SELECT u FROM Usersourcetb u")
public class Usersourcetb implements Serializable {
	private static final long serialVersionUID = 1L;

	private String activeid;

	private String sourceid;

	private String sourcename;

	public Usersourcetb() {
	}

	public String getActiveid() {
		return this.activeid;
	}

	public void setActiveid(String activeid) {
		this.activeid = activeid;
	}

	public String getSourceid() {
		return this.sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getSourcename() {
		return this.sourcename;
	}

	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}

}