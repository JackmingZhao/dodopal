package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the GROUPINPROXYTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Groupinproxytb.findAll", query="SELECT g FROM Groupinproxytb g")
public class Groupinproxytb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	//private GroupinproxytbPK id;
	private String groupid;
	private String proxyid;


	public String getGroupid() {
		return groupid;
	}


	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}


	public String getProxyid() {
		return proxyid;
	}


	public void setProxyid(String proxyid) {
		this.proxyid = proxyid;
	}


	public Groupinproxytb() {
	}

}