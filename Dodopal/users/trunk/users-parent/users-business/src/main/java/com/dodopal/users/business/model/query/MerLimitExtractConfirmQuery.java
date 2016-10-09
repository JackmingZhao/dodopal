package com.dodopal.users.business.model.query;

import com.dodopal.common.model.QueryBase;

public class MerLimitExtractConfirmQuery extends QueryBase{
    
    private static final long serialVersionUID = 3083005680933902740L;
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
    /**状态**/
    private String state;
    
    
    
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getParentMerCode() {
        return parentMerCode;
    }
    public String getChildMerCode() {
        return childMerCode;
    }
    public void setParentMerCode(String parentMerCode) {
        this.parentMerCode = parentMerCode;
    }
    public void setChildMerCode(String childMerCode) {
        this.childMerCode = childMerCode;
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
