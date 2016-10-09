package com.dodopal.product.business.model;

import com.dodopal.common.model.BaseEntity;

public class ProxyCardAdd extends BaseEntity{
    private static final long serialVersionUID = -1463080173647645700L;
    private String yktid;
    private String cityid;
    private String proxyid;
    private String proxyname;
    private String proxyorderno;
    private String cardno;
    private String posid;
    private String txndate;
    private String txntime;
    private double befsurpluslimit;
    private double txnamt;
    private double aftsurpluslimit;
    private String status;
    private String verifymd5;
    private String txndatestart;
    private String txndateend;
    private String remarks;  //2011-05-05  康宁 增加 pos机的备注信息
    private String bankorderid;//2011-05-24 康宁增加银行订单号
    private String statusone;
    private double paidamt;//实收金额
    private double rebatesamt;//返利金额
    private String yktname;//通卡公司名称
    private String statusname;//订单状态中文解释
    private String mobiltel;
    private String activerebate;
    private double sumamt;//一个订单中使用的优惠券总额 20150120
    private String cardkfhao; 
    private String proamt;
    private String blackamt;
    private String chargecardresult;
    private String orderstates;
    private String posseq1;//终端流水号
    public String getYktid() {
        return yktid;
    }
    public void setYktid(String yktid) {
        this.yktid = yktid;
    }
    public String getCityid() {
        return cityid;
    }
    public void setCityid(String cityid) {
        this.cityid = cityid;
    }
    public String getProxyid() {
        return proxyid;
    }
    public void setProxyid(String proxyid) {
        this.proxyid = proxyid;
    }
    public String getProxyname() {
        return proxyname;
    }
    public void setProxyname(String proxyname) {
        this.proxyname = proxyname;
    }
    public String getProxyorderno() {
        return proxyorderno;
    }
    public void setProxyorderno(String proxyorderno) {
        this.proxyorderno = proxyorderno;
    }
    public String getCardno() {
        return cardno;
    }
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    public String getPosid() {
        return posid;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    public String getTxndate() {
        return txndate;
    }
    public void setTxndate(String txndate) {
        this.txndate = txndate;
    }
    public String getTxntime() {
        return txntime;
    }
    public void setTxntime(String txntime) {
        this.txntime = txntime;
    }
   
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getVerifymd5() {
        return verifymd5;
    }
    public void setVerifymd5(String verifymd5) {
        this.verifymd5 = verifymd5;
    }
    public String getTxndatestart() {
        return txndatestart;
    }
    public void setTxndatestart(String txndatestart) {
        this.txndatestart = txndatestart;
    }
    public String getTxndateend() {
        return txndateend;
    }
    public void setTxndateend(String txndateend) {
        this.txndateend = txndateend;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getBankorderid() {
        return bankorderid;
    }
    public void setBankorderid(String bankorderid) {
        this.bankorderid = bankorderid;
    }
    public String getStatusone() {
        return statusone;
    }
    public void setStatusone(String statusone) {
        this.statusone = statusone;
    }
   
    public String getYktname() {
        return yktname;
    }
    public void setYktname(String yktname) {
        this.yktname = yktname;
    }
    public String getStatusname() {
        return statusname;
    }
    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }
    public String getMobiltel() {
        return mobiltel;
    }
    public void setMobiltel(String mobiltel) {
        this.mobiltel = mobiltel;
    }
    public String getActiverebate() {
        return activerebate;
    }
    public void setActiverebate(String activerebate) {
        this.activerebate = activerebate;
    }
    
    public double getBefsurpluslimit() {
        return befsurpluslimit;
    }
    public void setBefsurpluslimit(double befsurpluslimit) {
        this.befsurpluslimit = befsurpluslimit;
    }
    public double getTxnamt() {
        return txnamt;
    }
    public void setTxnamt(double txnamt) {
        this.txnamt = txnamt;
    }
    public double getAftsurpluslimit() {
        return aftsurpluslimit;
    }
    public void setAftsurpluslimit(double aftsurpluslimit) {
        this.aftsurpluslimit = aftsurpluslimit;
    }
    public double getPaidamt() {
        return paidamt;
    }
    public void setPaidamt(double paidamt) {
        this.paidamt = paidamt;
    }
    public double getRebatesamt() {
        return rebatesamt;
    }
    public void setRebatesamt(double rebatesamt) {
        this.rebatesamt = rebatesamt;
    }
    public double getSumamt() {
        return sumamt;
    }
    public void setSumamt(double sumamt) {
        this.sumamt = sumamt;
    }
    public String getCardkfhao() {
        return cardkfhao;
    }
    public void setCardkfhao(String cardkfhao) {
        this.cardkfhao = cardkfhao;
    }
    public String getProamt() {
        return proamt;
    }
    public void setProamt(String proamt) {
        this.proamt = proamt;
    }
    public String getBlackamt() {
        return blackamt;
    }
    public void setBlackamt(String blackamt) {
        this.blackamt = blackamt;
    }
    public String getChargecardresult() {
        return chargecardresult;
    }
    public void setChargecardresult(String chargecardresult) {
        this.chargecardresult = chargecardresult;
    }
    public String getOrderstates() {
        return orderstates;
    }
    public void setOrderstates(String orderstates) {
        this.orderstates = orderstates;
    }
    public String getPosseq1() {
        return posseq1;
    }
    public void setPosseq1(String posseq1) {
        this.posseq1 = posseq1;
    }
    
    
    
}
