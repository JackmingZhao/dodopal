package com.dodopal.api.product.dto.query;

import com.dodopal.common.model.QueryBase;

public class PrdProductYktQueryDTO extends QueryBase{
    private static final long serialVersionUID = 589485365666174261L;
    /*产品编号*/
    private String proCode;
    /*产品名称*/
    private String proName;
    /*一卡通编号*/
    private String yktCode;
    /*一卡通名称*/
    private String yktName;
    /*业务城市ID*/
    private String cityId;
    /*业务城市名称*/
    private String cityName;
    /*面价*/
    private String proPrice;
    /*状态0：上架、1：下架*/
    private String proStatus;
    /*备注*/
    private String remark;
    /*商户号*/
    private String merCode;
    
    public String getMerCode() {
		return merCode;
	}
	public void setMerCode(String merCode) {
		this.merCode = merCode;
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
    public String getYktCode() {
        return yktCode;
    }
    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }
    public String getYktName() {
        return yktName;
    }
    public void setYktName(String yktName) {
        this.yktName = yktName;
    }
    public String getCityId() {
        return cityId;
    }
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getProPrice() {
        return proPrice;
    }
    public void setProPrice(String proPrice) {
        this.proPrice = proPrice;
    }
    public String getProStatus() {
        return proStatus;
    }
    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
