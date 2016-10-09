package com.dodopal.api.payment.dto.query;

import com.dodopal.common.model.QueryBase;
/**
 * 外接支付方式
 * @author xiongzhijing@dodopal.com
 * @version 2015年7月30日
 */
public class PayAwayQueryDTO extends QueryBase{
  
    private static final long serialVersionUID = -6691849808694244928L;
    //商户名称
    private String merName;
    //商户编号
    private String merCode;
    //支付类型
    private String payType;
    //支付名称
    private String payWayName;
    //启用标示  
    private String activate;
    
    //支付方式
    private String payAwayType;
    
    public String getPayAwayType() {
        return payAwayType;
    }
    public void setPayAwayType(String payAwayType) {
        this.payAwayType = payAwayType;
    }
    public String getMerName() {
        return merName;
    }
    public void setMerName(String merName) {
        this.merName = merName;
    }
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
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
    public String getActivate() {
        return activate;
    }
    public void setActivate(String activate) {
        this.activate = activate;
    }

}
