package com.dodopal.portal.business.bean;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerLimitExtractConfirmEnum;
import com.dodopal.common.model.BaseEntity;

public class MerLimitExtractConfirmBean extends BaseEntity{
    /**
     * 
     */
    private static final long serialVersionUID = -2619667730491207053L;
    // 父商户号
    private String parentMerCode;
    // 父商户名称
    private String parentMerName;
    // 提取人;父商户登录人ID
    private String extractUser;
    // 提取时间;格式：2015-12-18 9:27:12
    private String extractDate;
    // 提取金额
    private String extractAmt;
    // 子商户号
    private String childMerCode;
    // 子商户名称
    private String childMerName;
    // 确认人;子商户确认人或拒绝人ID
    private String confirmUser;
    // 确认时间;格式：2015-12-18 9:27:12
    private String confirmDate;
    // 状态;0：待确认 1：已确认 2：已取消 3：已拒绝 ;1、2、3为最终状态，不可再修改
    private String state ;
    // 取消人
    private String concelUser;
    // 取消时间
    private String concelDate;
    
    //冗余字段，界面使用
    private String merName;
    
    
    public String getMerName() {
        return merName;
    }
    public void setMerName(String merName) {
        this.merName = merName;
    }
    public String getParentMerName() {
        return parentMerName;
    }
    public String getChildMerName() {
        return childMerName;
    }
    public void setParentMerName(String parentMerName) {
        this.parentMerName = parentMerName;
    }
    public void setChildMerName(String childMerName) {
        this.childMerName = childMerName;
    }
    public String getParentMerCode() {
        return parentMerCode;
    }
    public String getExtractUser() {
        return extractUser;
    }
    public String getExtractDate() {
        return extractDate;
    }
    public String getExtractAmt() {
        return extractAmt;
    }
    public String getChildMerCode() {
        return childMerCode;
    }
    public String getConfirmUser() {
        return confirmUser;
    }
    public String getConfirmDate() {
        return confirmDate;
    }
    public String getState() {
        return state;
    }
    public String getConcelUser() {
        return concelUser;
    }
    public String getConcelDate() {
        return concelDate;
    }
    public void setParentMerCode(String parentMerCode) {
        this.parentMerCode = parentMerCode;
    }
    public void setExtractUser(String extractUser) {
        this.extractUser = extractUser;
    }
    public void setExtractDate(String extractDate) {
        this.extractDate = extractDate;
    }
    public void setExtractAmt(String extractAmt) {
        this.extractAmt = extractAmt;
    }
    public void setChildMerCode(String childMerCode) {
        this.childMerCode = childMerCode;
    }
    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }
    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setConcelUser(String concelUser) {
        this.concelUser = concelUser;
    }
    public void setConcelDate(String concelDate) {
        this.concelDate = concelDate;
    }
    /**** 提取状态*****/
    public String getStateView() {
        if (StringUtils.isBlank(this.state)) {
            return null;
        }
       return MerLimitExtractConfirmEnum.getByCode(this.state).getName();
    }
}
