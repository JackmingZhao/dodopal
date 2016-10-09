package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

public class MerAutoAmt extends BaseEntity{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //商户号
    private String merCode;
    //额度阀值
    private String limitThreshold;
    //自动分配额度阀值
    private String autoLimitThreshold;
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public String getLimitThreshold() {
        return limitThreshold;
    }
    public void setLimitThreshold(String limitThreshold) {
        this.limitThreshold = limitThreshold;
    }
    public String getAutoLimitThreshold() {
        return autoLimitThreshold;
    }
    public void setAutoLimitThreshold(String autoLimitThreshold) {
        this.autoLimitThreshold = autoLimitThreshold;
    }
    

}
