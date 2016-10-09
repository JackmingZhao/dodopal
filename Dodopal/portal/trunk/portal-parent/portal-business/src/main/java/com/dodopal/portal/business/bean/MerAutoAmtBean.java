package com.dodopal.portal.business.bean;

import com.dodopal.common.model.BaseEntity;

/**
 * 自动转账额度和阀值
 * @author xiongzhijing@dodopal.com
 */
public class MerAutoAmtBean extends BaseEntity {

    private static final long serialVersionUID = 6319327051702920571L;
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
