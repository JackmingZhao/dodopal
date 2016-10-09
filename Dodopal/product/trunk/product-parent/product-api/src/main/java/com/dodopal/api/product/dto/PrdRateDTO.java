package com.dodopal.api.product.dto;

import com.dodopal.common.model.BaseEntity;

public class PrdRateDTO extends BaseEntity{

    /**
     * 
     */
    private static final long serialVersionUID = -7913449381336986138L;

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
