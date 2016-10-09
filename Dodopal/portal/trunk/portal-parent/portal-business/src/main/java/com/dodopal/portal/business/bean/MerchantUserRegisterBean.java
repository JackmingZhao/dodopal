package com.dodopal.portal.business.bean;

import java.io.Serializable;

import com.dodopal.common.model.BaseEntity;

public class MerchantUserRegisterBean extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6594705038671678622L;

    /*商户名称*/
    private String merName;

    /*商户联系人*/
    private String merLinkUser;

    /*商户联系人电话*/
    private String merLinkUserMobile;

    /*详细地址*/
    private String provinceCode;

    private String cityCode;

    private String address;

    //商户用户登录账号
    private String merUserName;

    //商户用户登录密码
    private String merUserPWD;
    
    // 手机验证码
    private String dypwd;
    
    // 手机验证码序号
    private String serialNumber;

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

    public String getMerLinkUserMobile() {
        return merLinkUserMobile;
    }

    public void setMerLinkUserMobile(String merLinkUserMobile) {
        this.merLinkUserMobile = merLinkUserMobile;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMerUserName() {
        return merUserName;
    }

    public void setMerUserName(String merUserName) {
        this.merUserName = merUserName;
    }

    public String getMerUserPWD() {
        return merUserPWD;
    }

    public void setMerUserPWD(String merUserPWD) {
        this.merUserPWD = merUserPWD;
    }

    public String getDypwd() {
        return dypwd;
    }

    public void setDypwd(String dypwd) {
        this.dypwd = dypwd;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

}
