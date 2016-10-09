package com.dodopal.api.card.dto;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BJIntegralConsumeSpecialDTO implements Serializable{
    private static final long serialVersionUID = 789951484554232705L;

    //交易类型  size4
    private String txntype; 
    
    //原交易类型  
    private String oritxntype; 
    
    //交易状态  
    private String txnstate; 
    
    //交易步骤
    private String txnstep;
    
    //Pos通讯流水号
    private String postranseq;
    
    //交易日期   
    private String txndate; 
    
    //交易时间 
    private String txntime; 
    
    //卡号 
    private String cardno; 
    
    //卡片验证信息
    private String cardmsg; 
    
    //卡内余额 
    private String cardbal; 
    
    //积分金额/撤销金额 
    private String preauthamt; 
    
    //主机交易流水号
    private String txnseqno;
    
    //账户个人校验码pin
    private String encpasswd;
    
    //原主机交易流水号
    private String origtxnseqno;
    
    //原交易日期
    private String origdate;
    
    /**    账户号        */
    private String accountid;
    
    
    //加密信息
    private String encryptinfo;
    
    
    public String getEncryptinfo() {
        return encryptinfo;
    }

    public void setEncryptinfo(String encryptinfo) {
        this.encryptinfo = encryptinfo;
    }

    public String getTxnstate() {
        return txnstate;
    }

    public void setTxnstate(String txnstate) {
        this.txnstate = txnstate;
    }

    public String getTxnstep() {
        return txnstep;
    }

    public void setTxnstep(String txnstep) {
        this.txnstep = txnstep;
    }

    public String getOritxntype() {
        return oritxntype;
    }

    public void setOritxntype(String oritxntype) {
        this.oritxntype = oritxntype;
    }

    public String getOrigtxnseqno() {
        return origtxnseqno;
    }

    public void setOrigtxnseqno(String origtxnseqno) {
        this.origtxnseqno = origtxnseqno;
    }

    public String getOrigdate() {
        return origdate;
    }

    public void setOrigdate(String origdate) {
        this.origdate = origdate;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getTxntype() {
        return txntype;
    }

    public void setTxntype(String txntype) {
        this.txntype = txntype;
    }

    public String getPostranseq() {
        return postranseq;
    }

    public void setPostranseq(String postranseq) {
        this.postranseq = postranseq;
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

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getCardmsg() {
        return cardmsg;
    }

    public void setCardmsg(String cardmsg) {
        this.cardmsg = cardmsg;
    }

    public String getCardbal() {
        return cardbal;
    }

    public void setCardbal(String cardbal) {
        this.cardbal = cardbal;
    }

    public String getPreauthamt() {
        return preauthamt;
    }

    public void setPreauthamt(String preauthamt) {
        this.preauthamt = preauthamt;
    }

    public String getTxnseqno() {
        return txnseqno;
    }

    public void setTxnseqno(String txnseqno) {
        this.txnseqno = txnseqno;
    }

    public String getEncpasswd() {
        return encpasswd;
    }

    public void setEncpasswd(String encpasswd) {
        this.encpasswd = encpasswd;
    }

    @Override
    public String toString() {
        return "{\"txntype\":\"" + txntype + "\",\"oritxntype\":\"" + oritxntype + "\",\"txnstate\":\"" + txnstate + "\",\"txnstep\":\"" + txnstep + "\",\"postranseq\":\"" + postranseq + "\",\"txndate\":\"" + txndate + "\",\"txntime\":\"" + txntime + "\",\"cardno\":\"" + cardno + "\",\"cardmsg\":\"" + cardmsg + "\",\"cardbal\":\"" + cardbal + "\",\"preauthamt\":\"" + preauthamt + "\",\"txnseqno\":\"" + txnseqno + "\",\"encpasswd\":\"" + encpasswd + "\",\"origtxnseqno\":\"" + origtxnseqno
            + "\",\"origdate\":\"" + origdate + "\",\"accountid\":\"" + accountid + "\",\"encryptinfo\":\"" + encryptinfo + "\"}  ";
    }

}
