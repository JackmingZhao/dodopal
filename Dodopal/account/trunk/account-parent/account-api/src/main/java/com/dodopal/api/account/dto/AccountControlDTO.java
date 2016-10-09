package com.dodopal.api.account.dto;

import java.math.BigDecimal;

import com.dodopal.common.model.BaseEntity;

/**
 * 资金账户风控信息
 * @author guanjinglun
 */
public class AccountControlDTO extends BaseEntity {

    private static final long serialVersionUID = 6209169449544462097L;
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
    
    
    
    public String getDayConsumeSingleLimitStr() {
        return getText(dayConsumeSingleLimit);
    }

    public String getDayConsumeSumLimitStr() {
        return getText(dayConsumeSumLimit);
    }

    public String getDayRechargeSingleLimitStr() {
        return getText(dayRechargeSingleLimit);
    }

    public String getDayRechargeSumLimitStr() {
        return getText(dayRechargeSumLimit);
    }

    public String getDayTransferSingleLimitStr() {
        return getText(dayTransferSingleLimit);
    }

    public String getDayTransferSumLimitStr() {
        return getText(dayTransferSumLimit);
    }
    
    protected String getText(String number) {
        BigDecimal amt = new BigDecimal(number);
        return amt.divide(new BigDecimal(100)).toPlainString();
    }
}
