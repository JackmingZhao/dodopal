package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIYKTINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Biyktinfotb.findAll", query="SELECT b FROM Biyktinfotb b")
public class Biyktinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long yktid;

	private String address;

	private BigDecimal areaid;

	private String citycode;

	private BigDecimal cityid;

	private String cityname;

	private String expn;

	private BigDecimal flag;

	private String fullname;

	private String ipaddr;

	private BigDecimal maxlimit;

	private BigDecimal minlimit;

	private BigDecimal perlimit;

	private BigDecimal port;

	private BigDecimal prehndret;

	private BigDecimal provinceid;

	private String recv;

	private String shtname;

	public Biyktinfotb() {
	}

	public long getYktid() {
		return this.yktid;
	}

	public void setYktid(long yktid) {
		this.yktid = yktid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAreaid() {
		return this.areaid;
	}

	public void setAreaid(BigDecimal areaid) {
		this.areaid = areaid;
	}

	public String getCitycode() {
		return this.citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public BigDecimal getCityid() {
		return this.cityid;
	}

	public void setCityid(BigDecimal cityid) {
		this.cityid = cityid;
	}

	public String getCityname() {
		return this.cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getExpn() {
		return this.expn;
	}

	public void setExpn(String expn) {
		this.expn = expn;
	}

	public BigDecimal getFlag() {
		return this.flag;
	}

	public void setFlag(BigDecimal flag) {
		this.flag = flag;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public BigDecimal getMaxlimit() {
		return this.maxlimit;
	}

	public void setMaxlimit(BigDecimal maxlimit) {
		this.maxlimit = maxlimit;
	}

	public BigDecimal getMinlimit() {
		return this.minlimit;
	}

	public void setMinlimit(BigDecimal minlimit) {
		this.minlimit = minlimit;
	}

	public BigDecimal getPerlimit() {
		return this.perlimit;
	}

	public void setPerlimit(BigDecimal perlimit) {
		this.perlimit = perlimit;
	}

	public BigDecimal getPort() {
		return this.port;
	}

	public void setPort(BigDecimal port) {
		this.port = port;
	}

	public BigDecimal getPrehndret() {
		return this.prehndret;
	}

	public void setPrehndret(BigDecimal prehndret) {
		this.prehndret = prehndret;
	}

	public BigDecimal getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(BigDecimal provinceid) {
		this.provinceid = provinceid;
	}

	public String getRecv() {
		return this.recv;
	}

	public void setRecv(String recv) {
		this.recv = recv;
	}

	public String getShtname() {
		return this.shtname;
	}

	public void setShtname(String shtname) {
		this.shtname = shtname;
	}

}