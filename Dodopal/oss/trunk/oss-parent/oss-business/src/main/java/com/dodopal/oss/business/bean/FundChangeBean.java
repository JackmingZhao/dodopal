package com.dodopal.oss.business.bean;

import com.dodopal.common.model.BaseEntity;

//资金变更记录表
public class FundChangeBean extends BaseEntity {

    private static final long serialVersionUID = -8786949876628744631L;
    //资金账户号
    private String fundAccountCode;
    //资金类别
    private String fundType;
    //时间戳
    private String accountChangeTime;
    //交易流水号
    private String tranCode;
    //变动类型
    private String changeType;
    //变动金额
    private String changeAmount;
    //变动前账户总余额
    private String beforeChangeAmount;
    //变动前可用余额
    private String beforeChangeAvailableAmount;
    //变动前冻结金额
    private String beforeChangeFrozenAmount;
    //变动日期
    private String changeDate;

    public String getFundAccountCode() {
        return fundAccountCode;
    }

    public void setFundAccountCode(String fundAccountCode) {
        this.fundAccountCode = fundAccountCode;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getAccountChangeTime() {
        return accountChangeTime;
    }

    public void setAccountChangeTime(String accountChangeTime) {
        this.accountChangeTime = accountChangeTime;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(String changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getBeforeChangeAmount() {
        return beforeChangeAmount;
    }

    public void setBeforeChangeAmount(String beforeChangeAmount) {
        this.beforeChangeAmount = beforeChangeAmount;
    }

    public String getBeforeChangeAvailableAmount() {
        return beforeChangeAvailableAmount;
    }

    public void setBeforeChangeAvailableAmount(String beforeChangeAvailableAmount) {
        this.beforeChangeAvailableAmount = beforeChangeAvailableAmount;
    }

    public String getBeforeChangeFrozenAmount() {
        return beforeChangeFrozenAmount;
    }

    public void setBeforeChangeFrozenAmount(String beforeChangeFrozenAmount) {
        this.beforeChangeFrozenAmount = beforeChangeFrozenAmount;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

}
