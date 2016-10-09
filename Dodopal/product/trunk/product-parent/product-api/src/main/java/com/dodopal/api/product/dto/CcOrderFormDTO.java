package com.dodopal.api.product.dto;

import com.dodopal.common.model.BaseEntity;

public class CcOrderFormDTO extends BaseEntity{
    /**
     * 
     */
    private static final long serialVersionUID = -4987602470880903419L;
    private String  orderid;
    private String  mchnitname;
    private String  userid;
    private String  orderstates;
    private String  checkcardno;
    private String  checkcardposid;
    private String  amt;
    private String  senddate;
    private String  sendtime;
    //商户订单号
    private String bankorderid;
    private String paidamt;
    private String flamt;
    private String ordertime1;
    private String ordertime2;
    private String orderstates1;
    private String username;
    
    private String posid;
    private String cardno;
    private String status;
    

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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getOrderid() {
        return orderid;
    }
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public String getMchnitname() {
        return mchnitname;
    }
    public void setMchnitname(String mchnitname) {
        this.mchnitname = mchnitname;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getOrderstates() {
        return orderstates;
    }
    public void setOrderstates(String orderstates) {
        this.orderstates = orderstates;
    }
    public String getCheckcardno() {
        return checkcardno;
    }
    public void setCheckcardno(String checkcardno) {
        this.checkcardno = checkcardno;
    }
    public String getCheckcardposid() {
        return checkcardposid;
    }
    public void setCheckcardposid(String checkcardposid) {
        this.checkcardposid = checkcardposid;
    }
    public String getAmt() {
        return amt;
    }
    public void setAmt(String amt) {
        this.amt = amt;
    }
    public String getSenddate() {
        return senddate;
    }
    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }
    public String getSendtime() {
        return sendtime;
    }
    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }
    public String getBankorderid() {
        return bankorderid;
    }
    public void setBankorderid(String bankorderid) {
        this.bankorderid = bankorderid;
    }
    public String getPaidamt() {
        return paidamt;
    }
    public void setPaidamt(String paidamt) {
        this.paidamt = paidamt;
    }
    public String getFlamt() {
        return flamt;
    }
    public void setFlamt(String flamt) {
        this.flamt = flamt;
    }
    public String getOrdertime1() {
        return ordertime1;
    }
    public void setOrdertime1(String ordertime1) {
        this.ordertime1 = ordertime1;
    }
    public String getOrdertime2() {
        return ordertime2;
    }
    public void setOrdertime2(String ordertime2) {
        this.ordertime2 = ordertime2;
    }
    public String getOrderstates1() {
        return orderstates1;
    }
    public void setOrderstates1(String orderstates1) {
        this.orderstates1 = orderstates1;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
