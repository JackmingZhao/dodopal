package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

public class PosQuery extends QueryBase {
    
    private static final long serialVersionUID = 3746732932260232208L;

    /**
     * POS编码
     */
    private String code;

    /**
     * POS厂商名称
     */
    private String posCompanyName;

    /**
     * POS型号名称
     */
    private String posTypeName;

    /**
     * 版本
     */
    private String version;
    
    /**
     * 绑定标识
     */
    private String bind;
    
    /**
     * POS状态
     */
    private String status;

    /**
     * POS所属城市code
     */
    private String cityCode;
    
    /**
     * POS所属省份code
     */
    private String provinceCode;
    
    /**
     * 绑定的商户编号
     */
    private String merCode;
    
    //绑定的商户名称
    private String merName;
    

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPosCompanyName() {
        return posCompanyName;
    }

    public void setPosCompanyName(String posCompanyName) {
        this.posCompanyName = posCompanyName;
    }

    public String getPosTypeName() {
        return posTypeName;
    }

    public void setPosTypeName(String posTypeName) {
        this.posTypeName = posTypeName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }
    
}
