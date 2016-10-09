package com.dodopal.oss.business.bean;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 *  通卡额度导出信息项
 *  
 * @author 
 *
 */
public class ProductYktLimitInfoForExport {

    private String yktCode;
    
    private String yktName;

    private String yktWarnLimit;

    private String yktStopLimit;

    private String yktExpireDate;

    private String totalAmtLimit;

    private String surPlusLimit;

    private String usedLimit;

    private String remark;

    private String createDate;

    private String updateDate;

    private String createUser;

    private String updateUser;  
    
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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

    public String getYktWarnLimit() {
        return yktWarnLimit;
    }

    public void setYktWarnLimit(String yktWarnLimit) {
        this.yktWarnLimit = yktWarnLimit;
    }

    public String getYktStopLimit() {
        return yktStopLimit;
    }

    public void setYktStopLimit(String yktStopLimit) {
        this.yktStopLimit = yktStopLimit;
    }

    public String getYktExpireDate() {
        return yktExpireDate;
    }

    public void setYktExpireDate(String yktExpireDate) {
        this.yktExpireDate = yktExpireDate;
    }

    public String getTotalAmtLimit() {
        return totalAmtLimit;
    }

    public void setTotalAmtLimit(String totalAmtLimit) {
        this.totalAmtLimit = totalAmtLimit;
    }

    public String getSurPlusLimit() {
        return surPlusLimit;
    }

    public void setSurPlusLimit(String surPlusLimit) {
        this.surPlusLimit = surPlusLimit;
    }

    public String getUsedLimit() {
        return usedLimit;
    }

    public void setUsedLimit(String usedLimit) {
        this.usedLimit = usedLimit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
