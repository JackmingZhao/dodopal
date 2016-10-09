package com.dodopal.oss.business.bean;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.model.BaseEntity;

public class MerBusRateBean extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = -6725227273738825028L;
    /*启用标示*/
    private String activate;
    /*商户编码*/
    private String merCode;
    
    /*业务编码*/
    private String rateCode;
    
    // 业务名称
    private String rateName;
    
    /*业务所在一卡通编码*/
    private String proCode;
    
    // 通卡公司名称
    private String proName;
    
    /*费率类型*/
    private String rateType;
    
    /*数值*/
    private double rate;
    /*判断是否选中*/
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }



    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getRateType() {
        return rateType;
    }

    public String getRateTypeView() {
        if (StringUtils.isBlank(this.rateType)) {
            return null;
        }
       return RateTypeEnum.getRateTypeByCode(this.rateType).getName();
    }
    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

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

    
}
