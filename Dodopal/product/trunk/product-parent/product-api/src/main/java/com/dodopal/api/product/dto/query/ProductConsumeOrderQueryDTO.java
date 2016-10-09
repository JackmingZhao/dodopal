package com.dodopal.api.product.dto.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

/**
 * 查询 一卡通收单记录
 * @author xiongzhijing@ddopal.com
 * @version 2015年10月15日
 */

public class ProductConsumeOrderQueryDTO extends QueryBase{
    
    private static final long serialVersionUID = 6882418748991990751L;
    
    // 订单编号
    private String orderNum;
    
    // 订单状态    
    private String states;
    
    // 订单内部状态    
    private String innerStates;
    
    // 订单日期（起）
    private Date orderDateStart;
    
    // 订单日期（止）
    private Date orderDateEnd;
    
    // 卡号 
    private String CardNum;
    
    // 商户名称
    private String merName;
    
    // 业务城市
    private String cityName;
    
    // POS
    private String posCode;
    
    // 实收金额（起）
    private String txnAmtStart;

    // 实收金额（止）
    private String txnAmtEnd;
    
    // 可疑状态     
    private String suspiciouStates;
    
    // 通卡公司编号     
    private String yktCode;
    
    /**************************************************/
    //客户号   个人编号或者商户号
    private String customerCode;
    
    //客户类型 
    private String customerType;
    
    //操作员
    private String userId;
    /**************************************************/
    
    public String getSuspiciouStates() {
        return suspiciouStates;
    }

    public void setSuspiciouStates(String suspiciouStates) {
        this.suspiciouStates = suspiciouStates;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Date getOrderDateStart() {
        return orderDateStart;
    }

    public void setOrderDateStart(Date orderDateStart) {
        this.orderDateStart = orderDateStart;
    }

    public Date getOrderDateEnd() {
        return orderDateEnd;
    }

    public void setOrderDateEnd(Date orderDateEnd) {
        this.orderDateEnd = orderDateEnd;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCardNum() {
        return CardNum;
    }

    public void setCardNum(String cardNum) {
        CardNum = cardNum;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getTxnAmtStart() {
        return txnAmtStart;
    }

    public void setTxnAmtStart(String txnAmtStart) {
        this.txnAmtStart = txnAmtStart;
    }

    public String getTxnAmtEnd() {
        return txnAmtEnd;
    }

    public void setTxnAmtEnd(String txnAmtEnd) {
        this.txnAmtEnd = txnAmtEnd;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getInnerStates() {
        return innerStates;
    }

    public void setInnerStates(String innerStates) {
        this.innerStates = innerStates;
    }
    

}
