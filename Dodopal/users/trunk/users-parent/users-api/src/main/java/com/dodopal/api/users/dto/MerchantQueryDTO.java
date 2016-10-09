package com.dodopal.api.users.dto;

import com.dodopal.common.model.QueryBase;

/**
 * 类说明 ：分页查询商户信息
 * @author lifeng
 */

public class MerchantQueryDTO extends QueryBase {
    private static final long serialVersionUID = 4745183696369858981L;
    /*商户名称*/
    private String merName;
    /*商户编码*/
    private String merCode;
    /*上级商户代码*/
    private String merParentCode;
    /*上级商户名称*/
    private String merParentName;
    /*商户类型*/
    private String merType;
    /*商户分类*/
    private String merClassify;
    /*商户属性*/
    private String merProperty;
    /*商户所属省份*/
    private String merPro;
    /*商户所属城市*/
    private String merCity;
    /*启用标识*/
    private String activate;
    /*商户联系人*/
    private String merLinkUser;
    /*商户联系人电话*/
    private String merLinkUserMobile;
    /*审核状态*/
    private String merState;
    /*来源*/
    private String source;
    
    /*业务类型*/
    private String bussQuery;
    
    public String getBussQuery() {
        return bussQuery;
    }

    public void setBussQuery(String bussQuery) {
        this.bussQuery = bussQuery;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerParentCode() {
        return merParentCode;
    }

    public void setMerParentCode(String merParentCode) {
        this.merParentCode = merParentCode;
    }

    public String getMerParentName() {
        return merParentName;
    }

    public void setMerParentName(String merParentName) {
        this.merParentName = merParentName;
    }

    public String getMerType() {
        return merType;
    }

    public void setMerType(String merType) {
        this.merType = merType;
    }

    public String getMerClassify() {
        return merClassify;
    }

    public void setMerClassify(String merClassify) {
        this.merClassify = merClassify;
    }

    public String getMerProperty() {
        return merProperty;
    }

    public void setMerProperty(String merProperty) {
        this.merProperty = merProperty;
    }

    public String getMerPro() {
        return merPro;
    }

    public void setMerPro(String merPro) {
        this.merPro = merPro;
    }

    public String getMerCity() {
        return merCity;
    }

    public void setMerCity(String merCity) {
        this.merCity = merCity;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
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

    public String getMerState() {
        return merState;
    }

    public void setMerState(String merState) {
        this.merState = merState;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
