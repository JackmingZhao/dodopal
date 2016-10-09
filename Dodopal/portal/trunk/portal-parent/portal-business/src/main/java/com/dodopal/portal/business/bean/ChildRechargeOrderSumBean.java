package com.dodopal.portal.business.bean;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

public class ChildRechargeOrderSumBean extends BaseEntity{
    private static final long serialVersionUID = -525823645853702235L;
    //pos号
    private String posCode;
    //商户编号
    private String merCode;
    //商户名称
    private String merName;
    //充值交易笔数
    private long rechargeCount;
    //充值交易金额
    private double rechargeAmt;
    //城市名称
    private String  cityName;
    //交易日期
    private String  tradeDate;
    //pos备注
    private String posComments;
    
    public String getPosComments() {
        return posComments;
    }

    public void setPosComments(String posComments) {
        this.posComments = posComments;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }


    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public long getRechargeCount() {
        return rechargeCount;
    }

    public void setRechargeCount(long rechargeCount) {
        this.rechargeCount = rechargeCount;
    }

    public double getRechargeAmt() {
        return rechargeAmt;
    }

    public void setRechargeAmt(double rechargeAmt) {
        this.rechargeAmt = rechargeAmt;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    
    

}
