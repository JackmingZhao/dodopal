package com.dodopal.users.business.model.query;

import com.dodopal.common.model.QueryBase;

public class MerDiscountQuery extends QueryBase {
    
    private static final long serialVersionUID = -8602982233203672095L;
    //商户号
    private String merCode;
    //启用标示
    private String activate;

    
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public String getActivate() {
        return activate;
    }
    public void setActivate(String activate) {
        this.activate = activate;
    }

    
    

}
