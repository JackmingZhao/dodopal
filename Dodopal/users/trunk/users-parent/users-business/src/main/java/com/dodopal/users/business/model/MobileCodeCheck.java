package com.dodopal.users.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

public class MobileCodeCheck extends BaseEntity {    


	/**
	 * 
	 */
	private static final long serialVersionUID = -13487897189804293L;

	//手机号	
    private String  mobilTel;

    //验证码
    private String  dypwd;

    //到期时间
    private Date  expirationTime;
    
    //短信序号
    private String  serialNumber;

	public String getMobilTel() {
		return mobilTel;
	}

	public void setMobilTel(String mobilTel) {
		this.mobilTel = mobilTel;
	}

	public String getDypwd() {
		return dypwd;
	}

	public void setDypwd(String dypwd) {
		this.dypwd = dypwd;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    

    
}