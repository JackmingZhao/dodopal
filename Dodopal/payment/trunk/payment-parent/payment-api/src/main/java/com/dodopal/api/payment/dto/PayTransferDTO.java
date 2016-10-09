package com.dodopal.api.payment.dto;

import java.io.Serializable;

/**
 * 转账
 * @author xiongzhijing@dodopal.com
 * @version 2015年8月31日
 */
public class PayTransferDTO implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -5087074091835329772L;

    /**
     * 枚举：个人、企业 表示转出账户的客户类型。
     * 最大长度 1
     */
    private String sourceCustType;
    
    /**
     * 类型是商户：商户号；类型是个人：用户编号。表示转出账户的客户号。
     * 最大长度40
     */
    private String sourceCustNum;
    
    /**
     * 交易流水号
     * 最大长度40
     */
    private String tradeNum;
    
    /**
     * 业务类型
     */
    private String businessType;
    
    /**
     * 转账的金额，单位为分。必须为正整数。
     */
    private double amount;
    
    /**
     * 枚举：个人、企业表示转入账户的客户类型。
     * 最大长度1
     */
    private String targetCustType;
    
    /**
     * 类型是商户：商户号;类型是个人：用户编号。表示转入账户的客户号。
     * 最大长度1
     */
    private String targetCustNum;
    
    /**
     * 备注说明信息
     * 最大长度255
     */
    private String comments;
    
    /**
     * 创建人
     */
    private String createUser;

    /**
     * 7.转账  9.提取额度
     */
    private String transferFlag; 
    
    
    public String getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(String transferFlag) {
        this.transferFlag = transferFlag;
    }



    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSourceCustType() {
        return sourceCustType;
    }

    public void setSourceCustType(String sourceCustType) {
        this.sourceCustType = sourceCustType;
    }

    public String getSourceCustNum() {
        return sourceCustNum;
    }

    public void setSourceCustNum(String sourceCustNum) {
        this.sourceCustNum = sourceCustNum;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTargetCustType() {
        return targetCustType;
    }

    public void setTargetCustType(String targetCustType) {
        this.targetCustType = targetCustType;
    }

    public String getTargetCustNum() {
        return targetCustNum;
    }

    public void setTargetCustNum(String targetCustNum) {
        this.targetCustNum = targetCustNum;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    
}
