package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;

public class MerchantExtend implements Serializable{

    private static final long serialVersionUID = 1753080545180836626L;

    private String id;
    
    private String oldMerchantId;//老系统集团或商户或网点id
    
    private String merCode;//商户编码
    
    private String oldMerchantType;//老系统商户类型 集团0，网点1，商户2

    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldMerchantId() {
        return oldMerchantId;
    }

    public void setOldMerchantId(String oldMerchantId) {
        this.oldMerchantId = oldMerchantId;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getOldMerchantType() {
        return oldMerchantType;
    }

    public void setOldMerchantType(String oldMerchantType) {
        this.oldMerchantType = oldMerchantType;
    }
    
    
    
}
