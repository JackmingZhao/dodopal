/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.api.product.dto;

/**
 * Created by lenovo on 2016-03-15.
 * 脱机消费文件记录格式
 */
public class ReslutData {
    //交易类型
    private String txntype;
    //储存在SAM卡中的编号
    private String sam;
    //交易金额
    private String txnamt;
    //交易顺序号
    private String txnseq;
    //交易后一卡通卡内余额
    private String aftbal;
    //交易日期
    private String txndate;
    //交易时间
    private String txntime;
    //卡序列号，一卡通内部序列号CSN，没有CSN时写FF
    private String cardseq;
    //卡计数器，一卡通卡中累计交易计数
    private String cardcnt;
    //卡号
    private String cardno;
    //有SAM对相关数据项计算得出的交易验证码
    private String tac;
    //交易前余额，一卡通交易前卡内余额
    private String befbal;
    //卡逻辑类型
    private String cardtype;
    //卡物理类型
    private String cardphytype;
    //记录序号：在数据包中的顺序编号
    private String recno;
    //锁卡时0x00表示黑名单列表内的卡锁卡，0x01因卡本身异常锁卡，消费时填写0x00
    private String txnstat;
    //密钥及算法标识
    private String algorithm;
    //钱包交易序号
    private String baltxnseq;
    //终端交易序号，针对CPU用户卡，PSAM产生，M1卡填写0X00000000
    private String termtxnseq;
    //个性化信息
    private String plivatemsg;
    //应收金额
    private String recvamount;
    //商户代码
    private String unitid;
    //设备代码，没有时填0
    private String devid;
    //批次号，没有时填0
    private String batchid;
    //操作员号，没有时填0
    private String operid;
    //填全F
    private String resv;
    //应收金额
    private String amtreceivable;
    //用户折扣
    private String userdiscount;
    //结算折扣
    private String settldiscount;

    public String getTxntype() {
        return txntype;
    }

    public void setTxntype(String txntype) {
        this.txntype = txntype;
    }

    public String getSam() {
        return sam;
    }

    public void setSam(String sam) {
        this.sam = sam;
    }

    public String getTxnamt() {
        return txnamt;
    }

    public void setTxnamt(String txnamt) {
        this.txnamt = txnamt;
    }

    public String getTxnseq() {
        return txnseq;
    }

    public void setTxnseq(String txnseq) {
        this.txnseq = txnseq;
    }

    public String getAftbal() {
        return aftbal;
    }

    public void setAftbal(String aftbal) {
        this.aftbal = aftbal;
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

    public String getCardseq() {
        return cardseq;
    }

    public void setCardseq(String cardseq) {
        this.cardseq = cardseq;
    }

    public String getCardcnt() {
        return cardcnt;
    }

    public void setCardcnt(String cardcnt) {
        this.cardcnt = cardcnt;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getBefbal() {
        return befbal;
    }

    public void setBefbal(String befbal) {
        this.befbal = befbal;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getCardphytype() {
        return cardphytype;
    }

    public void setCardphytype(String cardphytype) {
        this.cardphytype = cardphytype;
    }

    public String getRecno() {
        return recno;
    }

    public void setRecno(String recno) {
        this.recno = recno;
    }

    public String getTxnstat() {
        return txnstat;
    }

    public void setTxnstat(String txnstat) {
        this.txnstat = txnstat;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getBaltxnseq() {
        return baltxnseq;
    }

    public void setBaltxnseq(String baltxnseq) {
        this.baltxnseq = baltxnseq;
    }

    public String getTermtxnseq() {
        return termtxnseq;
    }

    public void setTermtxnseq(String termtxnseq) {
        this.termtxnseq = termtxnseq;
    }

    public String getPlivatemsg() {
        return plivatemsg;
    }

    public void setPlivatemsg(String plivatemsg) {
        this.plivatemsg = plivatemsg;
    }

    public String getRecvamount() {
        return recvamount;
    }

    public void setRecvamount(String recvamount) {
        this.recvamount = recvamount;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getResv() {
        return resv;
    }

    public void setResv(String resv) {
        this.resv = resv;
    }

    public String getAmtreceivable() {
        return amtreceivable;
    }

    public void setAmtreceivable(String amtreceivable) {
        this.amtreceivable = amtreceivable;
    }

    public String getUserdiscount() {
        return userdiscount;
    }

    public void setUserdiscount(String userdiscount) {
        this.userdiscount = userdiscount;
    }

    public String getSettldiscount() {
        return settldiscount;
    }

    public void setSettldiscount(String settldiscount) {
        this.settldiscount = settldiscount;
    }
}
