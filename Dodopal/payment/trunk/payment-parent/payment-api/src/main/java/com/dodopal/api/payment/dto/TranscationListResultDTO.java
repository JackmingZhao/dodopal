package com.dodopal.api.payment.dto;

import java.io.Serializable;

/**
 * 查询交易记录 （手机端和VC端）
 * @author xiongzhijing@dodopal.com
 * @version 2015年11月11日
 *
 */
public class TranscationListResultDTO implements Serializable{
 
    private static final long serialVersionUID = 152907308662813819L;

    //交易流水号
    private String trancode;
    
    //业务类型
    private String businesstype;
    
    //交易金额  单位“分“
    private long realtranmoney;
    
    //支付方式  支付方式名称
    private String paywayname;
    
    //交易类型 交易类型名称
    private String trantype;
    
    //交易状态 交易流水外部状态
    private String tranoutstatus;
    
    //交易时间 格式：yyyy-MM-dd HH:mm:ss
    private String createdate;

    public String getTrancode() {
        return trancode;
    }

    public void setTrancode(String trancode) {
        this.trancode = trancode;
    }

    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    public long getRealtranmoney() {
        return realtranmoney;
    }

    public void setRealtranmoney(long realtranmoney) {
        this.realtranmoney = realtranmoney;
    }

    public String getPaywayname() {
        return paywayname;
    }

    public void setPaywayname(String paywayname) {
        this.paywayname = paywayname;
    }

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }

    public String getTranoutstatus() {
        return tranoutstatus;
    }

    public void setTranoutstatus(String tranoutstatus) {
        this.tranoutstatus = tranoutstatus;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }


}
