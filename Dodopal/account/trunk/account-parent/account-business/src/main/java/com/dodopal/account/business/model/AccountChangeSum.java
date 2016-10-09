/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.account.business.model;

import com.dodopal.common.model.BaseEntity;

import java.util.Date;

/**
 * Created by lenovo on 2015/8/25.
 */
public class AccountChangeSum extends BaseEntity {
    //资金授信账户号
    private String fundAccountCode;
    //日期
    private String accountChangeDate;
    //日消费交易累计
    private long dayConsumeSum;
    //日充值交易累计金额
    private long dayRechargeSum;
    //日转账交易最大次数
    private long dayTranTimes;
    //日转账交易累计限额
    private long dayTranLimit;
    //变动日期
    private String changeDate;

    public AccountChangeSum() {
        super();
    }

    public AccountChangeSum(String fundAccountCode, String accountChangeDate, long dayConsumeSum, long dayRechargeSum, long dayTranTimes, long dayTranLimit, String changeDate) {
        super();
        this.fundAccountCode = fundAccountCode;
        this.accountChangeDate = accountChangeDate;
        this.dayConsumeSum = dayConsumeSum;
        this.dayRechargeSum = dayRechargeSum;
        this.dayTranTimes = dayTranTimes;
        this.dayTranLimit = dayTranLimit;
        this.changeDate = changeDate;
    }

    public String getFundAccountCode() {
        return fundAccountCode;
    }

    public void setFundAccountCode(String fundAccountCode) {
        this.fundAccountCode = fundAccountCode;
    }

    public String getAccountChangeDate() {
        return accountChangeDate;
    }

    public void setAccountChangeDate(String accountChangeDate) {
        this.accountChangeDate = accountChangeDate;
    }

    public long getDayConsumeSum() {
        return dayConsumeSum;
    }

    public void setDayConsumeSum(long dayConsumeSum) {
        this.dayConsumeSum = dayConsumeSum;
    }

    public long getDayRechargeSum() {
        return dayRechargeSum;
    }

    public void setDayRechargeSum(long dayRechargeSum) {
        this.dayRechargeSum = dayRechargeSum;
    }

    public long getDayTranTimes() {
        return dayTranTimes;
    }

    public void setDayTranTimes(long dayTranTimes) {
        this.dayTranTimes = dayTranTimes;
    }

    public long getDayTranLimit() {
        return dayTranLimit;
    }

    public void setDayTranLimit(long dayTranLimit) {
        this.dayTranLimit = dayTranLimit;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    @Override
    public String toString() {
        return "AccountChangeSum{" +
                "fundAccountCode='" + fundAccountCode + '\'' +
                ", accountChangeDate='" + accountChangeDate + '\'' +
                ", dayConsumeSum=" + dayConsumeSum +
                ", dayRechargeSum=" + dayRechargeSum +
                ", dayTranTimes=" + dayTranTimes +
                ", dayTranLimit=" + dayTranLimit +
                ", changeDate='" + changeDate + '\'' +
                '}';
    }
}
