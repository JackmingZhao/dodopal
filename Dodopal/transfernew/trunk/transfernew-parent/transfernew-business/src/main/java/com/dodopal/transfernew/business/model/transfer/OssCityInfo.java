package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the OSS_CITY_INFO database table.
 * 
 */
//@Entity
//@Table(name="OSS_CITY_INFO")
//@NamedQuery(name="OssCityInfo.findAll", query="SELECT o FROM OssCityInfo o")
public class OssCityInfo implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String citycode;

	private String area;

	private String bzcitycode;

	private String cityabridge;

	private String cityname;

	private String parentcode;

	public OssCityInfo() {
	}

	public String getCitycode() {
		return this.citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBzcitycode() {
		return this.bzcitycode;
	}

	public void setBzcitycode(String bzcitycode) {
		this.bzcitycode = bzcitycode;
	}

	public String getCityabridge() {
		return this.cityabridge;
	}

	public void setCityabridge(String cityabridge) {
		this.cityabridge = cityabridge;
	}

	public String getCityname() {
		return this.cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getParentcode() {
		return this.parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

}