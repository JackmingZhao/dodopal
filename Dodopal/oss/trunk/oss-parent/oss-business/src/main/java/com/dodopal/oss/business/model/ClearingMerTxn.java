package com.dodopal.oss.business.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.model.BaseEntity;

/**
 * 通卡公司分类清算表CLEARING_YKT对应实体类
 * @author dodopal
 *
 */
public class ClearingMerTxn extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2224168436414643724L;

    /**
     * 清算日期
     */
    private String clearingDay;
    
    /**
     * 清算时间
     */
    private Date clearingDate;
    
    /**
     * 商户编号
     */
    private String merCode;
    
    /**
     * 商户名称
     */
    private String merName;
    
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
     * 交易金额(界面展示单位：元，数据库：分)
     */
    private double tradeAmount;
    
    /**
     * 服务费(界面展示单位：元，数据库：分)
     */
    private double serviceFee;
    
    /**
     * 分润(界面展示单位：元，数据库：分)
     */
    private double merProfit;

    /**
     * 实收本金(界面展示单位：元，数据库：分)
     */
    private double realFee;
    
    /**
     * 转出金额(界面展示单位：元，数据库：分)
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

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
    
    public long getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(long tradeCount) {
        this.tradeCount = tradeCount;
    }

    public double getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(double tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public double getMerProfit() {
        return merProfit;
    }

    public void setMerProfit(double merProfit) {
        this.merProfit = merProfit;
    }

    public double getRealFee() {
        return realFee;
    }

    public void setRealFee(double realFee) {
        this.realFee = realFee;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }
    
    public String getTxnTypeView() {
        String txnTypeView = "";
        if (StringUtils.isNotBlank(this.txnType)) {
            RateCodeEnum rateCodeEnum =  RateCodeEnum.getRateTypeByCode(this.txnType);
            if (rateCodeEnum != null) {
                txnTypeView = rateCodeEnum.getName();
            }
        }
        return txnTypeView;
    }

    public void setTxnTypeView(String txnTypeView) {
        this.txnTypeView = txnTypeView;
    }
   
}
