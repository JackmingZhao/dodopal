package com.dodopal.oss.business.model.dto;

import java.io.Serializable;

public class PosUpdateBatch implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5542027624792738297L;

    private String code;

    private String cityCode;

    private String cityName;

    private String provinceCode;

    private String provinceName;

    private String version;

    private long maxTimes;
    
    private String updateUser;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(long maxTimes) {
        this.maxTimes = maxTimes;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}
