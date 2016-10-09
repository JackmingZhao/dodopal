package com.dodopal.api.users.dto;

import com.dodopal.common.model.BaseEntity;

public class MerAutoAmtDTO extends BaseEntity{
    /**
     * 
     */
    private static final long serialVersionUID = -7346135629888611097L;
    //商户号
    private String merCode;
    //额度阀值
    private String limitThreshold;
    //自动分配额度阀值
    private String autoLimitThreshold;
    public String getMerCode() {
        return merCode;
    }
    public String getLimitThreshold() {
        return limitThreshold;
    }
    public String getAutoLimitThreshold() {
        return autoLimitThreshold;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public void setLimitThreshold(String limitThreshold) {
        this.limitThreshold = limitThreshold;
    }
    public void setAutoLimitThreshold(String autoLimitThreshold) {
        this.autoLimitThreshold = autoLimitThreshold;
    }
    
    
}
