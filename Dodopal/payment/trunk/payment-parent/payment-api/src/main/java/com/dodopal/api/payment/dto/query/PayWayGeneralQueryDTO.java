package com.dodopal.api.payment.dto.query;

import com.dodopal.common.model.QueryBase;

public class PayWayGeneralQueryDTO extends QueryBase{

    private static final long serialVersionUID = 6432899422980290818L;

    //支付类型
    private String payType;
    //支付名称
    private String payWayName;
    //启用标示  
    private String activate;
    
    //支付方式
    private String payAwayType;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getPayAwayType() {
        return payAwayType;
    }

    public void setPayAwayType(String payAwayType) {
        this.payAwayType = payAwayType;
    }
    
    
}
