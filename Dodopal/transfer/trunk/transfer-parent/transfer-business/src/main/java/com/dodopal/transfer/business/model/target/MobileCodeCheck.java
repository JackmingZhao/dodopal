package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the MOBILE_CODE_CHECK database table.
 * 
 */
//@Entity
//@Table(name="MOBILE_CODE_CHECK")
//@NamedQuery(name="MobileCodeCheck.findAll", query="SELECT m FROM MobileCodeCheck m")
public class MobileCodeCheck implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

	private String dypwd;

//	@Temporal(TemporalType.DATE)
//	@Column(name="EXPIRATION_TIME")
	private Date expirationTime;

	private String mobiltel;

//	@Column(name="SERIAL_NUMBER")
	private String serialNumber;

	public MobileCodeCheck() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDypwd() {
		return this.dypwd;
	}

	public void setDypwd(String dypwd) {
		this.dypwd = dypwd;
	}

	public Date getExpirationTime() {
		return this.expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getMobiltel() {
		return this.mobiltel;
	}

	public void setMobiltel(String mobiltel) {
		this.mobiltel = mobiltel;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

}