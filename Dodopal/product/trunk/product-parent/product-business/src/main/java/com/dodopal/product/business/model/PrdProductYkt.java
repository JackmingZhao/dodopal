package com.dodopal.product.business.model;

import com.dodopal.common.model.BaseEntity;

public class PrdProductYkt extends BaseEntity{

    private static final long serialVersionUID = 1238898021680366675L;
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
    private int proPrice;
    /*状态0：上架、1：下架*/
    private String proStatus;
    /*备注*/
    private String remark;
    /*费率*/
    private Float proRate;
    /*成本价*/
    private int proPayPrice;
    /*类别0：标准;1：自定义*/
    private String proType;
    
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
    public int getProPrice() {
        return proPrice;
    }
    public void setProPrice(int proPrice) {
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
    public Float getProRate() {
        return proRate;
    }
    public void setProRate(Float proRate) {
        this.proRate = proRate;
    }
    public int getProPayPrice() {
        return proPayPrice;
    }
    public void setProPayPrice(int proPayPrice) {
        this.proPayPrice = proPayPrice;
    }
    public String getProType() {
        return proType;
    }
    public void setProType(String proType) {
        this.proType = proType;
    }
    
}
