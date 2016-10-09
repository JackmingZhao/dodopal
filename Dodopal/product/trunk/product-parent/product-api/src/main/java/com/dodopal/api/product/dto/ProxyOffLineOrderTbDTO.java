package com.dodopal.api.product.dto;

import com.dodopal.common.model.BaseEntity;

public class ProxyOffLineOrderTbDTO extends BaseEntity{
    private static final long serialVersionUID = -7991464382907478765L;
    private String orderid;
    private String mchnitorderid;//订单号
    private double amt;//实付金额
    private String orderstates;//状态
    private String checkcardposid;//pos号
    private String yktid;
    private String checkcardno;//卡号
    private String cardkfhao;
    private String ordertime;//交易时间
    private String sale;//折扣
    private double facevalue;//消费金额
    private double proamt;//原有金额
    private double blackamt;//卡余额
    private String proxyname;
    private String posremark;//pos备注
    
    /*
     * Herman新增字段
     * 用于网点充消小票打印信息查询
     * 时间：20151117
     */
    private String orderserror;//终端设备流水号
    private String cityid;//城市编号
    public String getOrderid() {
        return orderid;
    }
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public String getMchnitorderid() {
        return mchnitorderid;
    }
    public void setMchnitorderid(String mchnitorderid) {
        this.mchnitorderid = mchnitorderid;
    }

    public double getAmt() {
        return amt;
    }
    public void setAmt(double amt) {
        this.amt = amt;
    }
    public String getOrderstates() {
        return orderstates;
    }
    public void setOrderstates(String orderstates) {
        this.orderstates = orderstates;
    }
    public String getCheckcardposid() {
        return checkcardposid;
    }
    public void setCheckcardposid(String checkcardposid) {
        this.checkcardposid = checkcardposid;
    }
    public String getYktid() {
        return yktid;
    }
    public void setYktid(String yktid) {
        this.yktid = yktid;
    }
    public String getCheckcardno() {
        return checkcardno;
    }
    public void setCheckcardno(String checkcardno) {
        this.checkcardno = checkcardno;
    }
    public String getCardkfhao() {
        return cardkfhao;
    }
    public void setCardkfhao(String cardkfhao) {
        this.cardkfhao = cardkfhao;
    }
    public String getOrdertime() {
        return ordertime;
    }
    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }
    public String getSale() {
        return sale;
    }
    public void setSale(String sale) {
        this.sale = sale;
    }
   
    public double getFacevalue() {
        return facevalue;
    }
    public void setFacevalue(double facevalue) {
        this.facevalue = facevalue;
    }
    public double getProamt() {
        return proamt;
    }
    public void setProamt(double proamt) {
        this.proamt = proamt;
    }
    public double getBlackamt() {
        return blackamt;
    }
    public void setBlackamt(double blackamt) {
        this.blackamt = blackamt;
    }
    public String getProxyname() {
        return proxyname;
    }
    public void setProxyname(String proxyname) {
        this.proxyname = proxyname;
    }
    public String getPosremark() {
        return posremark;
    }
    public void setPosremark(String posremark) {
        this.posremark = posremark;
    }
    public String getOrderserror() {
        return orderserror;
    }
    public void setOrderserror(String orderserror) {
        this.orderserror = orderserror;
    }
    public String getCityid() {
        return cityid;
    }
    public void setCityid(String cityid) {
        this.cityid = cityid;
    }
    
    
}
