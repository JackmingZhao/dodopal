package com.dodopal.product.business.model;

import com.dodopal.common.model.BaseEntity;

public class PrdRate extends BaseEntity{

    /**
     * 
     */
    private static final long serialVersionUID = 5918600552429172020L;
    private String rateCode;
    private String rateName;
    private String comments;
    public String getRateCode() {
        return rateCode;
    }
    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }
    public String getRateName() {
        return rateName;
    }
    public void setRateName(String rateName) {
        this.rateName = rateName;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    
}
