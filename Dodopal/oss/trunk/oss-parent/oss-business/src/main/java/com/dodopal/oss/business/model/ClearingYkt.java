package com.dodopal.oss.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * 通卡公司分类清算表CLEARING_YKT对应实体类
 * @author dodopal
 *
 */
public class ClearingYkt extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5291090327766313062L;
    
    /**
     * 清算日期
     */
    private String clearingDay;
    
    /**
     * 清算时间
     */
    private Date clearingDate;
    
    /**
     * 通卡公司编号
     */
    private String yktCode;
    
    /**
     * 通卡公司名称
     */
    private String yktName;
    
    /**
     * 交易笔数
     */
    private long tradeCount;
    
    /**
     * 交易金额(界面展示单位：元，数据库：分)
     */
    private double tradeAmount;
    
    /**
     * 手续费(界面展示单位：元，数据库：分)
     */
    private double yktFee;
    
    /**
     * 转账金额(界面展示单位：元，数据库：分)
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

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
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

    public double getYktFee() {
        return yktFee;
    }

    public void setYktFee(double yktFee) {
        this.yktFee = yktFee;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    
}
