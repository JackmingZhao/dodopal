package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the GROUPSTAFFINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Groupstaffinfotb.findAll", query="SELECT g FROM Groupstaffinfotb g")
public class Groupstaffinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private GroupstaffinfotbPK id;

	private String cardkahao;

	private BigDecimal chargecardtype;

	private BigDecimal czmoney;

	private BigDecimal departmentid;

	private String entrytime;

	private String iccard;

	private String mobile;

	private String monthcardtype;

	private String remarks;

	private String staffidentityid;

	private String staffname;

	private BigDecimal status;

	private String tel;

	private String zh;

	public Groupstaffinfotb() {
	}

	public GroupstaffinfotbPK getId() {
		return this.id;
	}

	public void setId(GroupstaffinfotbPK id) {
		this.id = id;
	}

	public String getCardkahao() {
		return this.cardkahao;
	}

	public void setCardkahao(String cardkahao) {
		this.cardkahao = cardkahao;
	}

	public BigDecimal getChargecardtype() {
		return this.chargecardtype;
	}

	public void setChargecardtype(BigDecimal chargecardtype) {
		this.chargecardtype = chargecardtype;
	}

	public BigDecimal getCzmoney() {
		return this.czmoney;
	}

	public void setCzmoney(BigDecimal czmoney) {
		this.czmoney = czmoney;
	}

	public BigDecimal getDepartmentid() {
		return this.departmentid;
	}

	public void setDepartmentid(BigDecimal departmentid) {
		this.departmentid = departmentid;
	}

	public String getEntrytime() {
		return this.entrytime;
	}

	public void setEntrytime(String entrytime) {
		this.entrytime = entrytime;
	}

	public String getIccard() {
		return this.iccard;
	}

	public void setIccard(String iccard) {
		this.iccard = iccard;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMonthcardtype() {
		return this.monthcardtype;
	}

	public void setMonthcardtype(String monthcardtype) {
		this.monthcardtype = monthcardtype;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStaffidentityid() {
		return this.staffidentityid;
	}

	public void setStaffidentityid(String staffidentityid) {
		this.staffidentityid = staffidentityid;
	}

	public String getStaffname() {
		return this.staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getZh() {
		return this.zh;
	}

	public void setZh(String zh) {
		this.zh = zh;
	}

}