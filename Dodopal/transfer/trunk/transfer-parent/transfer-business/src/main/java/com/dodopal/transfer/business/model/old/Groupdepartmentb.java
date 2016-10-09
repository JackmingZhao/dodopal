package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the GROUPDEPARTMENTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Groupdepartmentb.findAll", query="SELECT g FROM Groupdepartmentb g")
public class Groupdepartmentb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private GroupdepartmentbPK id;

	private String departmentname;

	private String remarks;

	private BigDecimal status;

	public Groupdepartmentb() {
	}

	public GroupdepartmentbPK getId() {
		return this.id;
	}

	public void setId(GroupdepartmentbPK id) {
		this.id = id;
	}

	public String getDepartmentname() {
		return this.departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

}