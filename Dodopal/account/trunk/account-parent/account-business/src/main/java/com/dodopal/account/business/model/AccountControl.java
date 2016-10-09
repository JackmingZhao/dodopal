/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.account.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * @description AccountControlDto bean
 * Created by leCodevo on 2015/8/21.
 */
public class AccountControl extends BaseEntity{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //资金授信账户号
    private String fundAccountCode;
    //日消费交易单笔限额
    private long dayConsumeSingleLimit;
    //日消费交易累计限额
    private long dayConsumeSumLimit;
    //日充值交易单笔限额
    private long dayRechargeSingleLimit;
    //日充值交易累计限额
    private long dayRechargeSumLimit;
    //日转账交易最大次数
    private long dayTransferMax;
    //日转账交易单笔限额
    private long dayTransferSingleLimit;
    //日转账交易累计限额
    private long dayTransferSumLimit;

    public String getFundAccountCode() {
        return fundAccountCode;
    }

    public void setFundAccountCode(String fundAccountCode) {
        this.fundAccountCode = fundAccountCode;
    }

    public long getDayConsumeSingleLimit() {
        return dayConsumeSingleLimit;
    }

    public void setDayConsumeSingleLimit(long dayConsumeSingleLimit) {
        this.dayConsumeSingleLimit = dayConsumeSingleLimit;
    }

    public long getDayConsumeSumLimit() {
        return dayConsumeSumLimit;
    }

    public void setDayConsumeSumLimit(long dayConsumeSumLimit) {
        this.dayConsumeSumLimit = dayConsumeSumLimit;
    }

    public long getDayRechargeSingleLimit() {
        return dayRechargeSingleLimit;
    }

    public void setDayRechargeSingleLimit(long dayRechargeSingleLimit) {
        this.dayRechargeSingleLimit = dayRechargeSingleLimit;
    }

    public long getDayRechargeSumLimit() {
        return dayRechargeSumLimit;
    }

    public void setDayRechargeSumLimit(long dayRechargeSumLimit) {
        this.dayRechargeSumLimit = dayRechargeSumLimit;
    }

    public long getDayTransferMax() {
        return dayTransferMax;
    }

    public void setDayTransferMax(long dayTransferMax) {
        this.dayTransferMax = dayTransferMax;
    }

    public long getDayTransferSingleLimit() {
        return dayTransferSingleLimit;
    }

    public void setDayTransferSingleLimit(long dayTransferSingleLimit) {
        this.dayTransferSingleLimit = dayTransferSingleLimit;
    }

    public long getDayTransferSumLimit() {
        return dayTransferSumLimit;
    }

    public void setDayTransferSumLimit(long dayTransferSumLimit) {
        this.dayTransferSumLimit = dayTransferSumLimit;
    }

    @Override
    public String toString() {
        return "AccountControl{" +
                "fundAccountCode='" + fundAccountCode + '\'' +
                ", dayConsumeSingleLimit=" + dayConsumeSingleLimit +
                ", dayConsumeSumLimit=" + dayConsumeSumLimit +
                ", dayRechargeSingleLimit=" + dayRechargeSingleLimit +
                ", dayRechargeSumLimit=" + dayRechargeSumLimit +
                ", dayTransferMax=" + dayTransferMax +
                ", dayTransferSingleLimit=" + dayTransferSingleLimit +
                ", dayTransferSumLimit=" + dayTransferSumLimit +
                '}';
    }
}
