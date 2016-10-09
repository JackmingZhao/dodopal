package com.dodopal.portal.business.bean;

import com.dodopal.common.model.BaseEntity;

public class PrdRateBean extends BaseEntity{
    /**
     * 
     */
    private static final long serialVersionUID = 1368341175726673491L;
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
