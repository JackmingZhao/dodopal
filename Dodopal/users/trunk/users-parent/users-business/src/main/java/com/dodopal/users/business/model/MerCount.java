package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

public class MerCount extends BaseEntity{

    private static final long serialVersionUID = 1989001282815725419L;

    //商户名称
    private String merName;
    
    //商户编号
    private String merCode;
    
    //商户管理员（名称）
    private String merUserName;
    
    //用户手机号
    private String merUserMobile;
    
    //拥有pos数量
    private String posCount;
    
    //店面地址
    private String merAddress;
    
    //启用标识
    private String activate;

    
    
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

    public String getMerUserName() {
        return merUserName;
    }

    public void setMerUserName(String merUserName) {
        this.merUserName = merUserName;
    }

    public String getMerUserMobile() {
        return merUserMobile;
    }

    public void setMerUserMobile(String merUserMobile) {
        this.merUserMobile = merUserMobile;
    }

    public String getPosCount() {
        return posCount;
    }

    public void setPosCount(String posCount) {
        this.posCount = posCount;
    }

    public String getMerAddress() {
        return merAddress;
    }

    public void setMerAddress(String merAddress) {
        this.merAddress = merAddress;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }
    
    

}
