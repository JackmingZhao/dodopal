package com.dodopal.api.users.dto;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class PosQueryDTO extends QueryBase {

	private static final long serialVersionUID = -2824492854137246295L;

	  /**商户编号*/
	private String merchantCode;
	  /**商户名称*/
    private String merchantName;
	  
	/** POS号 */
	private String code;
	
	/** 备注 */
	private String comments;

	/** 绑定开始时间 */
	private Date bundlingDateStart;
	
	/** 绑定结束时间 */
	private Date bundlingDateEnd;
	
	/**城市名称*/
	private String cityName;

	
	public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}	

	public String getComments() {
		return comments;
	}


    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getBundlingDateStart() {
		return bundlingDateStart;
	}

	public void setBundlingDateStart(Date bundlingDateStart) {
		this.bundlingDateStart = bundlingDateStart;
	}

	public Date getBundlingDateEnd() {
		return bundlingDateEnd;
	}

	public void setBundlingDateEnd(Date bundlingDateEnd) {
		this.bundlingDateEnd = bundlingDateEnd;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

}