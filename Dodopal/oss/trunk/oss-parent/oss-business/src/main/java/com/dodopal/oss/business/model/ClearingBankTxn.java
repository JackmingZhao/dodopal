package com.dodopal.oss.business.model;

import java.util.Date;

import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.model.BaseEntity;

/**
 * 支付网关-业务类型分类清算表CLEARING_BANK_TXN对应实体类
 * @author dodopal
 *
 */
public class ClearingBankTxn extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6740390873291609172L;
    
    /**
     * 清算日期
     */
    private String clearingDay;
    
    /**
     * 清算时间
     */
    private Date clearingDate;
    
    /**
     * 支付网关
     */
    private String payGateway;
    
    /**
     * 业务类型
     */
    private String txnType;
    
    /**
     * 业务类型界面显示
     */
    @SuppressWarnings("unused")
    private String txnTypeView;
    
    /**
     * 交易笔数
     */
    private long tradeCount;
    
    /**
     * 交易金额(页面展示单位：元；数据库：分)
     */
    private double tradeAmount;
    
    /**
     * 银行手续费(页面展示单位：元；数据库：分)
     */
    private double bankFee;
    
    /**
     * 转账金额(页面展示单位：元；数据库：分)
     */
    private double transferAmount;

    public String getClearingDay() {
        return clearingDay;
    }

    public void setClearingDay(String clearingDay) {
        this.clearingDay = clearingDay;
    }

    public Date getClearingDate() {
        return clearingDate;
    }

    public void setClearingDate(Date clearingDate) {
        this.clearingDate = clearingDate;
    }

    public String getPayGateway() {
        return payGateway;
    }

    public void setPayGateway(String payGateway) {
        this.payGateway = payGateway;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public long getTradeCount() {
        return tradeCount;
    }

    public double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public double getBankFee() {
        return bankFee;
    }

    public void setBankFee(double bankFee) {
        this.bankFee = bankFee;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public void setTradeCount(long tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String getTxnTypeView() {
        return RateCodeEnum.getRateTypeByCode(this.txnType).getName();
    }

    public void setTxnTypeView(String txnTypeView) {
        this.txnTypeView = txnTypeView;
    }

}
