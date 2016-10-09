package com.dodopal.oss.business.bean;

import com.dodopal.common.model.BaseEntity;

/**
 * 资金账户风控信息
 * @author guanjinglun
 */
public class AccountControlBean extends BaseEntity {
    
    private static final long serialVersionUID = -4534189706450887916L;
    /*
     * 资金授信账户号（表示风控记录是针对哪个资金账户的）
     */
    private String fundAccountCode;
    /*
     * 日消费交易单笔限额
     */
    private String dayConsumeSingleLimit;
    /*
     * 日消费交易累计限额
     */
    private String dayConsumeSumLimit;
    /*
     * 日充值交易单笔限额
     */
    private String dayRechargeSingleLimit;
    /*
     * 日充值交易累计限额
     */
    private String dayRechargeSumLimit;
    /*
     * 日转账交易最大次数
     */
    private String dayTransferMax;
    /*
     * 日转账交易单笔限额
     */
    private String dayTransferSingleLimit;
    /*
     * 日转账交易累计限额
     */
    private String dayTransferSumLimit;

    public String getFundAccountCode() {
        return fundAccountCode;
    }

    public void setFundAccountCode(String fundAccountCode) {
        this.fundAccountCode = fundAccountCode;
    }

    public String getDayConsumeSingleLimit() {
        return dayConsumeSingleLimit;
    }

    public void setDayConsumeSingleLimit(String dayConsumeSingleLimit) {
        this.dayConsumeSingleLimit = dayConsumeSingleLimit;
    }

    public String getDayConsumeSumLimit() {
        return dayConsumeSumLimit;
    }

    public void setDayConsumeSumLimit(String dayConsumeSumLimit) {
        this.dayConsumeSumLimit = dayConsumeSumLimit;
    }

    public String getDayRechargeSingleLimit() {
        return dayRechargeSingleLimit;
    }

    public void setDayRechargeSingleLimit(String dayRechargeSingleLimit) {
        this.dayRechargeSingleLimit = dayRechargeSingleLimit;
    }

    public String getDayRechargeSumLimit() {
        return dayRechargeSumLimit;
    }

    public void setDayRechargeSumLimit(String dayRechargeSumLimit) {
        this.dayRechargeSumLimit = dayRechargeSumLimit;
    }

    public String getDayTransferMax() {
        return dayTransferMax;
    }

    public void setDayTransferMax(String dayTransferMax) {
        this.dayTransferMax = dayTransferMax;
    }

    public String getDayTransferSingleLimit() {
        return dayTransferSingleLimit;
    }

    public void setDayTransferSingleLimit(String dayTransferSingleLimit) {
        this.dayTransferSingleLimit = dayTransferSingleLimit;
    }

    public String getDayTransferSumLimit() {
        return dayTransferSumLimit;
    }

    public void setDayTransferSumLimit(String dayTransferSumLimit) {
        this.dayTransferSumLimit = dayTransferSumLimit;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
