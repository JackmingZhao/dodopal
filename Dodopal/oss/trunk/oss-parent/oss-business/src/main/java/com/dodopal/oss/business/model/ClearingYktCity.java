package com.dodopal.oss.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * 支付网关分类清算表CLEARING_BANK对应实体类
 * @author dodopal
 *
 */
public class ClearingYktCity extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6720141596801871046L;

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
     * 城市编号
     */
    private String cityCode;
    
    /**
     * 城市名称
     */
    private String cityName;

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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public void setTradeCount(long tradeCount) {
        this.tradeCount = tradeCount;
    }
}
