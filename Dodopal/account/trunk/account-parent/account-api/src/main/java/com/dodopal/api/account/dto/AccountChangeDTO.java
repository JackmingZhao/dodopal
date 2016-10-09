package com.dodopal.api.account.dto;

import java.io.Serializable;

public class AccountChangeDTO implements Serializable{
    private static final long serialVersionUID = 7918483598872343316L;

    //资金类别 0：资金, 1：授信
    private String fundtype;
    
    //变动类型 都都宝平台账户资金变动类型
    private String changetype;
    
    //变动金额    变动金额：单位“分“
    private long amount;
    
    //变动前账户总余额 单位“分“
    private long befchangeamt;
    
    //变动前可用余额 单位“分“
    private long befchangeavailableamt;
    
    //变动前冻结金额 单位“分“
    private long befchangefrozenamt;
    
    //资金变更时间   格式：yyyy-MM-dd mm:ss
    private String changedate;

    public String getFundtype() {
        return fundtype;
    }

    public void setFundtype(String fundtype) {
        this.fundtype = fundtype;
    }

    public String getChangetype() {
        return changetype;
    }

    public void setChangetype(String changetype) {
        this.changetype = changetype;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getBefchangeamt() {
        return befchangeamt;
    }

    public void setBefchangeamt(long befchangeamt) {
        this.befchangeamt = befchangeamt;
    }

    public long getBefchangeavailableamt() {
        return befchangeavailableamt;
    }

    public void setBefchangeavailableamt(long befchangeavailableamt) {
        this.befchangeavailableamt = befchangeavailableamt;
    }

    public long getBefchangefrozenamt() {
        return befchangefrozenamt;
    }

    public void setBefchangefrozenamt(long befchangefrozenamt) {
        this.befchangefrozenamt = befchangefrozenamt;
    }

  
    public String getChangedate() {
        return changedate;
    }

    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }
    
    
}
