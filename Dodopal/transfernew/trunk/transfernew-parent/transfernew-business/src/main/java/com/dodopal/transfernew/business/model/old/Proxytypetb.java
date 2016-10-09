package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROXYTYPETB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Proxytypetb.findAll", query="SELECT p FROM Proxytypetb p")
public class Proxytypetb implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal proxytypeid;

	private String proxytypename;

	public Proxytypetb() {
	}

	public BigDecimal getProxytypeid() {
		return this.proxytypeid;
	}

	public void setProxytypeid(BigDecimal proxytypeid) {
		this.proxytypeid = proxytypeid;
	}

	public String getProxytypename() {
		return this.proxytypename;
	}

	public void setProxytypename(String proxytypename) {
		this.proxytypename = proxytypename;
	}

}