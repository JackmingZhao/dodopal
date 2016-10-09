package com.dodopal.payment.business.model.query;

import com.dodopal.common.model.QueryBase;

/**
 * 
 * @author hxc
 *
 */
public class PayWayQuery extends QueryBase{

    private static final long serialVersionUID = 7324353215841873431L;

    //商户编号
    private String merCode;
     
    //商户名称
    private String merName;
    
    //启用标示
    private String activate;
    
    //支付类型
    private String payType;
    
    //支付名称
    private String payWayName;

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

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

   
    
}
