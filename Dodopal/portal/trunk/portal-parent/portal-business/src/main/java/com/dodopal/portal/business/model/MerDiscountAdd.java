package com.dodopal.portal.business.model;

import java.util.List;
import java.util.Map;

public class MerDiscountAdd {
    //商户号
    private String merCode;

    //直营网点
    private List<Map<String,String>> directMer;
    //折扣
    private String discount;
    //排序号
    private String sort;
    // 启用标识
    private String activateFlag;
    
    

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public List<Map<String, String>> getDirectMer() {
        return directMer;
    }

    public void setDirectMer(List<Map<String, String>> directMer) {
        this.directMer = directMer;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getActivateFlag() {
        return activateFlag;
    }

    public void setActivateFlag(String activateFlag) {
        this.activateFlag = activateFlag;
    }


    

}
