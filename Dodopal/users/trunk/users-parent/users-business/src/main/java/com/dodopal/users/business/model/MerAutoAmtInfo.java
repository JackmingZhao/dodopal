package com.dodopal.users.business.model;

public class MerAutoAmtInfo {
    //自增id
    private String id;
    //商户号
    private String merCode;
    //额度阀值
    private String limitThreshold;
    //自动分配的额度值
    private String autoLimitThreshold;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
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
