package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the GROUPPOSINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Groupposinfotb.findAll", query="SELECT g FROM Groupposinfotb g")
public class Groupposinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private GroupposinfotbPK id;

	private String adddate;

	private String addtime;

	private BigDecimal isdistribution;

	private String remarks;
	private String groupid;
	private String posid;
	
	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getPosid() {
		return posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

	public Groupposinfotb() {
	}

	public GroupposinfotbPK getId() {
		return this.id;
	}

	public void setId(GroupposinfotbPK id) {
		this.id = id;
	}

	public String getAdddate() {
		return this.adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public BigDecimal getIsdistribution() {
		return this.isdistribution;
	}

	public void setIsdistribution(BigDecimal isdistribution) {
		this.isdistribution = isdistribution;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}