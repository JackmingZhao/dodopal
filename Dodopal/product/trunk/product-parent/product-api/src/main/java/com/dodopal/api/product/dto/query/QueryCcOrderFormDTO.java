package com.dodopal.api.product.dto.query;

import com.dodopal.common.model.QueryBase;

public class QueryCcOrderFormDTO extends  QueryBase{
    /**
     * 
     */
    private static final long serialVersionUID = -4071913041125629874L;
    private String mchnitid;
    private String bankorderid;
    private String startdate;
    private String enddate;
    private String posid;
    private String cardno;
    private String username;
    private String status;
  
    public String getMchnitid() {
        return mchnitid;
    }
    public void setMchnitid(String mchnitid) {
        this.mchnitid = mchnitid;
    }
    public String getBankorderid() {
        return bankorderid;
    }
    public void setBankorderid(String bankorderid) {
        this.bankorderid = bankorderid;
    }
    public String getStartdate() {
        return startdate;
    }
    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }
    public String getEnddate() {
        return enddate;
    }
    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
    public String getPosid() {
        return posid;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    public String getCardno() {
        return cardno;
    }
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
