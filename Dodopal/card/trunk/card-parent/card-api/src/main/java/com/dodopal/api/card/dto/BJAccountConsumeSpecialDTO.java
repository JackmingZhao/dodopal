package com.dodopal.api.card.dto;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年5月26日 下午3:55:45 
  * @version 1.0 
  * @parameter    
  */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BJAccountConsumeSpecialDTO implements Serializable{
    private static final long serialVersionUID = 7753808142479216896L;

    //交易类型  size2
    private String txntype; 
    
    //原交易类型  size2
    private String oritxntype; 

    //业务模式
    private String txnmode;
    
    //业务信息
    private String txnmsg;
    
    //Pos通讯流水号
    private String postranseq;
    
    //交易日期  size 8
    private String txndate; 
    
    //交易时间 size 8
    private String txntime; 
    
    //卡号 size 8
    private String cardno; 
    
    //卡片验证信息 size 8
    private String cardmsg; 
    
    //卡内余额 size 8
    private String cardbal; 
    
    //账户号 size 8
    private String accountid; 
    
    //交易金额 size 8
    private String preauthamt;
    
    //主机交易流水号
    private String txnseqno;
    
    //账户个人校验码pin
    private String encpasswd;
    
    //自动圈存交易类型
    private String autotxntype;
    
    //自动圈存交易状态
    private String autotxnstate;
    
    //自动圈存金额
    private String autoloadamt;
    
    //自动圈存下步动作集合
    private String ntxtstep;
    
    //原主机交易流水号
    private String origtxnseqno;
    
    //原交易日期
    private String origdate;
    
    //加密信息
    private String encryptinfo;
    
    public String getEncryptinfo() {
        return encryptinfo;
    }

    public void setEncryptinfo(String encryptinfo) {
        this.encryptinfo = encryptinfo;
    }

    public String getTxntype() {
        return txntype;
    }

    public void setTxntype(String txntype) {
        this.txntype = txntype;
    }

    public String getOritxntype() {
        return oritxntype;
    }

    public void setOritxntype(String oritxntype) {
        this.oritxntype = oritxntype;
    }

    public String getTxnmode() {
        return txnmode;
    }

    public void setTxnmode(String txnmode) {
        this.txnmode = txnmode;
    }

    public String getTxnmsg() {
        return txnmsg;
    }

    public void setTxnmsg(String txnmsg) {
        this.txnmsg = txnmsg;
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

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
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

    public String getAutotxntype() {
        return autotxntype;
    }

    public void setAutotxntype(String autotxntype) {
        this.autotxntype = autotxntype;
    }

    public String getAutotxnstate() {
        return autotxnstate;
    }

    public void setAutotxnstate(String autotxnstate) {
        this.autotxnstate = autotxnstate;
    }

    public String getAutoloadamt() {
        return autoloadamt;
    }

    public void setAutoloadamt(String autoloadamt) {
        this.autoloadamt = autoloadamt;
    }

    public String getNtxtstep() {
        return ntxtstep;
    }

    public void setNtxtstep(String ntxtstep) {
        this.ntxtstep = ntxtstep;
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

    /* 
     * 
     */
    @Override
    public String toString() {
        return "{\"txntype\":\"" + txntype + "\",\"oritxntype\":\"" + oritxntype + "\",\"txnmode\":\"" + txnmode + "\",\"txnmsg\":\"" + txnmsg + "\",\"postranseq\":\"" + postranseq + "\",\"txndate\":\"" + txndate + "\",\"txntime\":\"" + txntime + "\",\"cardno\":\"" + cardno + "\",\"cardmsg\":\"" + cardmsg + "\",\"cardbal\":\"" + cardbal + "\",\"accountid\":\"" + accountid + "\",\"preauthamt\":\"" + preauthamt + "\",\"txnseqno\":\"" + txnseqno + "\",\"encpasswd\":\"" + encpasswd + "\",\"autotxntype\":\""
            + autotxntype + "\",\"autotxnstate\":\"" + autotxnstate + "\",\"autoloadamt\":\"" + autoloadamt + "\",\"ntxtstep\":\"" + ntxtstep + "\",\"origtxnseqno\":\"" + origtxnseqno + "\",\"origdate\":\"" + origdate + "\",\"encryptinfo\":\"" + encryptinfo + "\"}  ";
    }

    
}
