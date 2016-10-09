package com.dodopal.portal.business.model.query;

import com.dodopal.common.model.QueryBase;

public class ChildMerchantQuery extends QueryBase{
    private static final long serialVersionUID = -9062341993115843502L;
    /*商户编码*/
    private String merCode;
    /*商户名称*/
    private String merName;
    /*商户联系人*/
    private String merLinkUser;
    /*商户联系人电话*/
    private String merLinkUserMobile;
    /*启用标示*/
    private String activate;
    /*商户所属省份*/
    private String merPro;
    /*商户所属省份名称*/
    private String merProName;
    /*商户所属城市*/
    private String merCity;
    /*商户所属城市名称*/
    private String merCityName;
    /*所属上级商户编码*/
    private String merParentCode;
    
    public String getMerPro() {
        return merPro;
    }
    public void setMerPro(String merPro) {
        this.merPro = merPro;
    }
    public String getMerProName() {
        return merProName;
    }
    public void setMerProName(String merProName) {
        this.merProName = merProName;
    }
    public String getMerCity() {
        return merCity;
    }
    public void setMerCity(String merCity) {
        this.merCity = merCity;
    }
    public String getMerCityName() {
        return merCityName;
    }
    public void setMerCityName(String merCityName) {
        this.merCityName = merCityName;
    }
    public String getMerParentCode() {
        return merParentCode;
    }
    public void setMerParentCode(String merParentCode) {
        this.merParentCode = merParentCode;
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
    public String getMerLinkUser() {
        return merLinkUser;
    }
    public void setMerLinkUser(String merLinkUser) {
        this.merLinkUser = merLinkUser;
    }
    public String getActivate() {
        return activate;
    }
    public void setActivate(String activate) {
        this.activate = activate;
    }
    public String getMerLinkUserMobile() {
        return merLinkUserMobile;
    }
    public void setMerLinkUserMobile(String merLinkUserMobile) {
        this.merLinkUserMobile = merLinkUserMobile;
    }
   
}
