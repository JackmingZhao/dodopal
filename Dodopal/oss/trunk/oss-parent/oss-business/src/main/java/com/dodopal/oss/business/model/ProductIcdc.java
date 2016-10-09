package com.dodopal.oss.business.model;

import com.dodopal.common.model.BaseEntity;

public class ProductIcdc extends BaseEntity{
    private static final long serialVersionUID = 3746732932260232208L;
    private String proCode;
    private String proName;
    private String rateCode;
    private String rateName;
    public String getProCode() {
        return proCode;
    }
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }
    public String getProName() {
        return proName;
    }
    public void setProName(String proName) {
        this.proName = proName;
    }
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

}
