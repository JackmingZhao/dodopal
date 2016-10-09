package com.dodopal.portal.business.bean;

import com.dodopal.common.model.BaseEntity;

public class ProductBean extends BaseEntity{
    private static final long serialVersionUID = 5093420866111127809L;
	private String proCode;
    private String proName;
    private String rateCode;
    private String rateName;
    /*数值*/
    private double rate;
    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }
    private String rateType;
    private boolean checked;
    private char index;
    public String getRateType() {
        return rateType;
    }
    public void setRateType(String rateType) {
        this.rateType = rateType;
    }
    public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getRateCode() {
		return rateCode;
	}
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	public String getRateName() {
		return rateName;
	}
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public char getIndex() {
        return index;
    }
    public void setIndex(char index) {
        this.index = index;
    }

}
