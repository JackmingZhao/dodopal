package com.dodopal.product.business.bean;

import java.io.Serializable;
/**
 * 城市一卡通充值记录
 * @author lenovo
 *
 */
public class ProxyCardAddResultBean implements Serializable{
    private static final long serialVersionUID = -8296314342547401784L;
    private String yktid;//通卡
    private String cityid;//城市编号
    private String proxyid;//网点编号
    private String proxyname;//网点名称
    private String proxyorderno;//订单号
    private String cardno;//卡号
    private String posid;//pos号
    private String txndate;//交易日期
    private String txntime;//交易完成时间
    private String befsurpluslimit;//交易前剩余额度
    private String txnamt;//交易金额
    private String aftsurpluslimit;//交易后剩余额度
    private String status;//交易状态
    private String verifymd5;//
    private String txndatestart;
    private String txndateend;
    private String remarks;  //2011-05-05  康宁 增加 pos机的备注信息
    private String bankorderid;//2011-05-24 康宁增加银行订单号
    private String statusone;
    private String paidamt;//实收金额
    private String rebatesamt;//返利金额
    private String yktname;//通卡公司名称
    private String statusname;//订单状态中文解释
    private String mobiltel;
    private String activerebate;
    private String sumamt;//一个订单中使用的优惠券总额 20150120
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
    public String getBefsurpluslimit() {
        return befsurpluslimit;
    }
    public void setBefsurpluslimit(String befsurpluslimit) {
        this.befsurpluslimit = befsurpluslimit;
    }
    public String getTxnamt() {
        return txnamt;
    }
    public void setTxnamt(String txnamt) {
        this.txnamt = txnamt;
    }
    public String getAftsurpluslimit() {
        return aftsurpluslimit;
    }
    public void setAftsurpluslimit(String aftsurpluslimit) {
        this.aftsurpluslimit = aftsurpluslimit;
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
    public String getPaidamt() {
        return paidamt;
    }
    public void setPaidamt(String paidamt) {
        this.paidamt = paidamt;
    }
    public String getRebatesamt() {
        return rebatesamt;
    }
    public void setRebatesamt(String rebatesamt) {
        this.rebatesamt = rebatesamt;
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
    public String getSumamt() {
        return sumamt;
    }
    public void setSumamt(String sumamt) {
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
