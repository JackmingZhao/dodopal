package com.dodopal.card.business.model;

import com.dodopal.common.model.BaseEntity;

public class SamSignInOffTb extends BaseEntity {
    private static final long serialVersionUID = -982060913658936505L;
    //通卡公司code
    private String yktcode;
    //Sam卡号
    private String samNo;
    //通卡商户号
    private String mchntId;
    //终端编码
    private String posId;
    //POS类型
    private String posType;
    //批次号
    private String batchNo;
    //授权截止时间
    private String limitTime;
    //终端IC交易流水号
    private String posIcSeq;
    //终端账户交易流水号
    private String posAccSeq;
    //通讯流水号
    private String posCommSeq;
    //签到标志
    private String signInFlag;
    //签到时间
    private String signInDate;
    //签退标志
    private String signOffFlag;
    //签退时间
    private String signOffDate;
    //结算日期YYYYMMDD
    private String settDate;
    //具体参数下载标志
    private String downloadFlag;
    //Sam参数下载标志
    private String needDownload;
    //日切步骤控制
    private String batchStep;

    public String getYktcode() {
        return yktcode;
    }

    public void setYktcode(String yktcode) {
        this.yktcode = yktcode;
    }

    public String getSamNo() {
        return samNo;
    }

    public void setSamNo(String samNo) {
        this.samNo = samNo;
    }

    public String getMchntId() {
        return mchntId;
    }

    public void setMchntId(String mchntId) {
        this.mchntId = mchntId;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public String getPosIcSeq() {
        return posIcSeq;
    }

    public void setPosIcSeq(String posIcSeq) {
        this.posIcSeq = posIcSeq;
    }

    public String getPosAccSeq() {
        return posAccSeq;
    }

    public void setPosAccSeq(String posAccSeq) {
        this.posAccSeq = posAccSeq;
    }

    public String getPosCommSeq() {
        return posCommSeq;
    }

    public void setPosCommSeq(String posCommSeq) {
        this.posCommSeq = posCommSeq;
    }

    public String getSignInFlag() {
        return signInFlag;
    }

    public void setSignInFlag(String signInFlag) {
        this.signInFlag = signInFlag;
    }

    public String getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(String signInDate) {
        this.signInDate = signInDate;
    }

    public String getSignOffFlag() {
        return signOffFlag;
    }

    public void setSignOffFlag(String signOffFlag) {
        this.signOffFlag = signOffFlag;
    }

    public String getSignOffDate() {
        return signOffDate;
    }

    public void setSignOffDate(String signOffDate) {
        this.signOffDate = signOffDate;
    }

    public String getSettDate() {
        return settDate;
    }

    public void setSettDate(String settDate) {
        this.settDate = settDate;
    }

    public String getDownloadFlag() {
        return downloadFlag;
    }

    public void setDownloadFlag(String downloadFlag) {
        this.downloadFlag = downloadFlag;
    }

    public String getNeedDownload() {
        return needDownload;
    }

    public void setNeedDownload(String needDownload) {
        this.needDownload = needDownload;
    }

    public String getBatchStep() {
        return batchStep;
    }

    public void setBatchStep(String batchStep) {
        this.batchStep = batchStep;
    }

}
