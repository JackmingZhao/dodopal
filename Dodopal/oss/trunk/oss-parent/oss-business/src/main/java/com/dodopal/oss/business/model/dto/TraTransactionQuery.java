package com.dodopal.oss.business.model.dto;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class TraTransactionQuery extends QueryBase{
    private static final long serialVersionUID = -4890934056144109899L;

    /**
     * 用户名称/商户名称
     */
    private String merOrUserName;
    
    /**
     * 交易流水号
     */
    private String tranCode;
    
    
    /**
     * 订单号
     */
    private String orderNumber;
    
    
    /**
     * 业务类型代码
     */
    private String businessType;
    
    /**
     * 外部交易状态
     */
    private String tranOutStatus;
    
    /**
     * 内部交易状态
     */
    private String tranInStatus;
    
    /**
     * 创建时间结束
     */
    private Date createDateEnd;
    
    /**
     * 创建时间开始
     */
    private Date createDateStart;
    
    /**
     * 最小金额
     */
    private String realMinTranMoney;
    
    /**
     * 最大金额
     */
    private String realMaxTranMoney;

    public String getTranInStatus() {
        return tranInStatus;
    }

    public void setTranInStatus(String tranInStatus) {
        this.tranInStatus = tranInStatus;
    }

   

    public String getRealMinTranMoney() {
        return realMinTranMoney;
    }

    public void setRealMinTranMoney(String realMinTranMoney) {
        this.realMinTranMoney = realMinTranMoney;
    }

    public String getRealMaxTranMoney() {
        return realMaxTranMoney;
    }

    public void setRealMaxTranMoney(String realMaxTranMoney) {
        this.realMaxTranMoney = realMaxTranMoney;
    }

    public String getMerOrUserName() {
        return merOrUserName;
    }

    public void setMerOrUserName(String merOrUserName) {
        this.merOrUserName = merOrUserName;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getTranOutStatus() {
        return tranOutStatus;
    }

    public void setTranOutStatus(String tranOutStatus) {
        this.tranOutStatus = tranOutStatus;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }
    

}
