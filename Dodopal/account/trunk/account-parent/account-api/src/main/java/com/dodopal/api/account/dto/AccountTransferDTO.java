package com.dodopal.api.account.dto;

import java.io.Serializable;

/**
 * 账户转账单个信息
 * @author 袁越
 *
 */
public class AccountTransferDTO implements Serializable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5271800096922272587L;
    
    /**
     * 交易流水的交易类型（需要再转化为账户交易类型）类型：转入转出
     */
    private String transferType;
    
    /**
     * 客户类型
     */
    private String sourceCustType;
    
    /**
     * 客户号（类型是商户：商户号；类型是个人：用户编号。最大长度40）
     */
    private String sourceCustNum;
    
    /**
     * 交易流水号（最大长度40）
     */
    private String tradeNum;
    
    /**
     * 转账金额（单位为分，必须为正整数）
     */
    private long amount;
    
    /**
     * 备注说明信息（最大长度255）
     */
    private String comments;

    private String targetCustType;
    
    private String targetCustNum;
    
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
    
}