package com.dodopal.card.business.model;

import com.dodopal.common.model.BaseEntity;

public class CrdSys100000 extends BaseEntity {
    private static final long serialVersionUID = -8885591349336061655L;
    //卡服务订单号
    private String crdOrderNum;
    //产品库订单号
    private String prdOrderNum;
    //个性化信息
    private String plivatemsg;
    //卡号
    private String cardNo;
    //应收金额
    private String amtNeedRecv;
    //实收金额
    private String amtRecv;
    //优惠类型
    private String disTypeOne;
    //优惠金额
    private String disTypeOneAmt;
    //优惠类型
    private String disTypeTwo;
    //优惠金额
    private String disTypeTwoAmt;
    //优惠类型
    private String disTypeThree;
    //优惠金额
    private String disTypeThreeAmt;
    //商户代码
    private String unitId;
    //账户号
    private String accountId;
    //交易批次号
    private String batchId;
    //主机交易流水号
    private String hostSeq;
    //设备代码，没有时填0
    private String devId;
    //预留
    private String resv;
    //应收金额
    private String amtReceivable;
    //用户折扣
    private String userDiscount;
    //结算折扣
    private String settlDiscount;
    //交易日期
    private String txndate;
    //交易时间
    private String txntime;

    public String getCrdOrderNum() {
        return crdOrderNum;
    }

    public void setCrdOrderNum(String crdOrderNum) {
        this.crdOrderNum = crdOrderNum;
    }

    public String getPrdOrderNum() {
        return prdOrderNum;
    }

    public void setPrdOrderNum(String prdOrderNum) {
        this.prdOrderNum = prdOrderNum;
    }

    public String getPlivatemsg() {
        return plivatemsg;
    }

    public void setPlivatemsg(String plivatemsg) {
        this.plivatemsg = plivatemsg;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getAmtNeedRecv() {
        return amtNeedRecv;
    }

    public void setAmtNeedRecv(String amtNeedRecv) {
        this.amtNeedRecv = amtNeedRecv;
    }

    public String getAmtRecv() {
        return amtRecv;
    }

    public void setAmtRecv(String amtRecv) {
        this.amtRecv = amtRecv;
    }

    public String getDisTypeOne() {
        return disTypeOne;
    }

    public void setDisTypeOne(String disTypeOne) {
        this.disTypeOne = disTypeOne;
    }

    public String getDisTypeOneAmt() {
        return disTypeOneAmt;
    }

    public void setDisTypeOneAmt(String disTypeOneAmt) {
        this.disTypeOneAmt = disTypeOneAmt;
    }

    public String getDisTypeTwo() {
        return disTypeTwo;
    }

    public void setDisTypeTwo(String disTypeTwo) {
        this.disTypeTwo = disTypeTwo;
    }

    public String getDisTypeTwoAmt() {
        return disTypeTwoAmt;
    }

    public void setDisTypeTwoAmt(String disTypeTwoAmt) {
        this.disTypeTwoAmt = disTypeTwoAmt;
    }

    public String getDisTypeThree() {
        return disTypeThree;
    }

    public void setDisTypeThree(String disTypeThree) {
        this.disTypeThree = disTypeThree;
    }

    public String getDisTypeThreeAmt() {
        return disTypeThreeAmt;
    }

    public void setDisTypeThreeAmt(String disTypeThreeAmt) {
        this.disTypeThreeAmt = disTypeThreeAmt;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getHostSeq() {
        return hostSeq;
    }

    public void setHostSeq(String hostSeq) {
        this.hostSeq = hostSeq;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getResv() {
        return resv;
    }

    public void setResv(String resv) {
        this.resv = resv;
    }

    public String getAmtReceivable() {
        return amtReceivable;
    }

    public void setAmtReceivable(String amtReceivable) {
        this.amtReceivable = amtReceivable;
    }

    public String getUserDiscount() {
        return userDiscount;
    }

    public void setUserDiscount(String userDiscount) {
        this.userDiscount = userDiscount;
    }

    public String getSettlDiscount() {
        return settlDiscount;
    }

    public void setSettlDiscount(String settlDiscount) {
        this.settlDiscount = settlDiscount;
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

}
