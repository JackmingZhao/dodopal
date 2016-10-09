package com.dodopal.oss.business.model;

import com.dodopal.common.model.BaseEntity;

public class YktPsam extends BaseEntity {

    private static final long serialVersionUID = -5619021026981306059L;
    
    //psam号
    private String samNo;

    //通卡公司code
    private String yktCode;

    //通卡公司名称
    private String yktName;

    //城市code
    private String cityCode;

    //城市名称
    private String cityName;

    //商户号
    private String merCode;

    //商户名称
    private String merName;

    public String getSamNo() {
        return samNo;
    }

    public void setSamNo(String samNo) {
        this.samNo = samNo;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }
  
}
