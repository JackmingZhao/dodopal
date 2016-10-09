package com.dodopal.api.account.dto;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;
//资金变更记录表
public class FundChangeDTO extends BaseEntity{
    private static final long serialVersionUID = -5428536584827927054L;
    //资金账户号
    private String fundAccountCode;
    //资金类别
    private String fundType;
    //时间戳
    private Date accountChangeTime;
    //交易流水号
    private String tranCode;
    //变动类型
    private String changeType;
    //变动金额
    private long changeAmount;
    //变动前账户总余额
    private long beforeChangeAmount;
    //变动前可用余额
    private long beforeChangeAvailableAmount;
    //变动前冻结金额
    private long beforeChangeFrozenAmount;
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
    public Date getAccountChangeTime() {
        return accountChangeTime;
    }
    public void setAccountChangeTime(Date accountChangeTime) {
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
    public long getChangeAmount() {
        return changeAmount;
    }
    public void setChangeAmount(long changeAmount) {
        this.changeAmount = changeAmount;
    }
    public long getBeforeChangeAmount() {
        return beforeChangeAmount;
    }
    public void setBeforeChangeAmount(long beforeChangeAmount) {
        this.beforeChangeAmount = beforeChangeAmount;
    }
    public long getBeforeChangeAvailableAmount() {
        return beforeChangeAvailableAmount;
    }
    public void setBeforeChangeAvailableAmount(long beforeChangeAvailableAmount) {
        this.beforeChangeAvailableAmount = beforeChangeAvailableAmount;
    }
    public long getBeforeChangeFrozenAmount() {
        return beforeChangeFrozenAmount;
    }
    public void setBeforeChangeFrozenAmount(long beforeChangeFrozenAmount) {
        this.beforeChangeFrozenAmount = beforeChangeFrozenAmount;
    }
    public String getChangeDate() {
        return changeDate;
    }
    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }
   
    
}
