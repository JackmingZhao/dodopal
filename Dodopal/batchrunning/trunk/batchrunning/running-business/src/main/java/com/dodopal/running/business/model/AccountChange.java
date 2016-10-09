package com.dodopal.running.business.model;

import com.dodopal.common.model.BaseEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 资金变动信息表ACCOUNT_CHANGE对应实体类
 */
public class AccountChange  extends BaseEntity{

    private static final long serialVersionUID = -4513174871712465190L;

    /**
     *资金账户号 
     */
    private String fundAccountCode;

    /**
     *资金类别 
     */
    private String fundType;

    /**
     *时间戳 
     */
    private Date accountChangeTime;

    /**
     *交易流水号 
     */
    private String tranCode;

    /**
     *变动类型 
     */
    private String changeType;

    /**
     *变动金额 
     */
    private long changeAmount;

    /**
     *变动前账户总余额 
     */
    private long beforeChangeAmount;

    /**
     *变动前可用余额 
     */
    private long beforeChangeAvailableAmount;

    /**
     *变动前冻结金额 
     */
    private long beforeChangeFrozenAmount;

    /**
     *变动日期 
     */
    private String changeDate;

    /**
     *备注 
     */
    private String comments;
    
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
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public AccountChange deepClone() throws IOException, ClassNotFoundException{
        //将对象写到流中
        ByteArrayOutputStream bo=new ByteArrayOutputStream();
        ObjectOutputStream oo=new ObjectOutputStream(bo);
        oo.writeObject(this);
        //从流中读出来
        ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi=new ObjectInputStream(bi);
        return (AccountChange) oi.readObject();
    }

    @Override
    public String toString() {
        return "AccountChange{" +
                "fundAccountCode='" + fundAccountCode + '\'' +
                ", fundType='" + fundType + '\'' +
                ", accountChangeTime=" + accountChangeTime +
                ", tranCode='" + tranCode + '\'' +
                ", changeType='" + changeType + '\'' +
                ", changeAmount=" + changeAmount +
                ", beforeChangeAmount=" + beforeChangeAmount +
                ", beforeChangeAvailableAmount=" + beforeChangeAvailableAmount +
                ", beforeChangeFrozenAmount=" + beforeChangeFrozenAmount +
                ", changeDate='" + changeDate + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
