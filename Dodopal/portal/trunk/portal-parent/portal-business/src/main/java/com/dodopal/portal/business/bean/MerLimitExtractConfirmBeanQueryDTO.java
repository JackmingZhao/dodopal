package com.dodopal.portal.business.bean;

import com.dodopal.common.model.QueryBase;

public class MerLimitExtractConfirmBeanQueryDTO extends QueryBase{
    /**
     * 
     */
    private static final long serialVersionUID = -5502452813191620404L;
    /**父商户号**/
    private String parentMerCode;
    /**子商户号**/
    private String childMerCode;
    /**子商户名称**/
    private String childMerName;
    /**开始时间**/
    private String extractStartDate;
    /**结束时间**/
    private String extractEndDate;
    /**冗余字段，商户类型，查询使用**/
    private String merType;
    /**状态**/
    private String state;
    
    
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getMerType() {
        return merType;
    }
    public void setMerType(String merType) {
        this.merType = merType;
    }
    public String getParentMerCode() {
        return parentMerCode;
    }
    public String getChildMerCode() {
        return childMerCode;
    }
    public String getChildMerName() {
        return childMerName;
    }
    public String getExtractStartDate() {
        return extractStartDate;
    }
    public String getExtractEndDate() {
        return extractEndDate;
    }
    public void setParentMerCode(String parentMerCode) {
        this.parentMerCode = parentMerCode;
    }
    public void setChildMerCode(String childMerCode) {
        this.childMerCode = childMerCode;
    }
    public void setChildMerName(String childMerName) {
        this.childMerName = childMerName;
    }
    public void setExtractStartDate(String extractStartDate) {
        this.extractStartDate = extractStartDate;
    }
    public void setExtractEndDate(String extractEndDate) {
        this.extractEndDate = extractEndDate;
    }
    
    
}
