package com.dodopal.product.web.mobileBean;

public class PrdProductYktMobile {
	 /*产品编号*/
    private String procode;
    /*产品名称*/
    private String proname;
    /*面价*/
    private int proprice;
    /*业务城市ID*/
    private String citycode;
    
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	public int getProprice() {
		return proprice;
	}
	public void setProprice(int proprice) {
		this.proprice = proprice;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
    
}
