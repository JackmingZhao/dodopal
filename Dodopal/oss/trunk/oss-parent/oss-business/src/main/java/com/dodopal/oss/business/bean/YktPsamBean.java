package com.dodopal.oss.business.bean;

import com.dodopal.common.model.BaseEntity;

public class YktPsamBean extends BaseEntity{

    private static final long serialVersionUID = 2374121629145408337L;

    //pos表的id
    private String posSeqId;
    
    //pos商户中间表id
    private String posMertbId;
    
    //签到控制表id
    private String samSignId;
    
    //Sam卡号
    private String samNo;

    //通卡公司code
    private String yktCode;

    //通卡公司名称
    private String yktName;

    //城市code
    private String cityCode;

    //城市名称
    private String cityName;

    //商户号
    private String merCode;

    //商户名称
    private String merName;
    
    
    //通卡商户号
    private String mchntid;
    
    //终端编码 POS号
    private String posId;

    //POS类型
    private String posType;
    
    //批次号
    private String batchNo;
    
    //授权截止时间
    private String limitTime;
    
    //终端IC交易流水号
    private String  posIcSeq;
    
    //终端账户交易流水号
    private String  posAccSeq;
    
    //通讯流水号
    private String posCommSeq;
    
    //签到标志
    private String  signinFlag;
    
    //签到时间
    private String signinDate;
    
    //签退标志
    private String signoffFlag;
    
    //签退时间
    private String signoffDate;
    
    //结算日期  YYYYMMDD
    private String settDate;
    
    //具体参数下载标志
    private String downLoadFlag;  
    
    //Sam参数下载标志
    private String needDownLoad;
    
    //日切步骤控制
    private String  batchStep;

    
    
    public String getPosSeqId() {
        return posSeqId;
    }

    public void setPosSeqId(String posSeqId) {
        this.posSeqId = posSeqId;
    }

    public String getPosMertbId() {
        return posMertbId;
    }

    public void setPosMertbId(String posMertbId) {
        this.posMertbId = posMertbId;
    }

    public String getSamSignId() {
        return samSignId;
    }

    public void setSamSignId(String samSignId) {
        this.samSignId = samSignId;
    }

    public String getSamNo() {
        return samNo;
    }

    public void setSamNo(String samNo) {
        this.samNo = samNo;
    }

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMchntid() {
        return mchntid;
    }

    public void setMchntid(String mchntid) {
        this.mchntid = mchntid;
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

    public String getSigninFlag() {
        return signinFlag;
    }

    public void setSigninFlag(String signinFlag) {
        this.signinFlag = signinFlag;
    }

    public String getSigninDate() {
        return signinDate;
    }

    public void setSigninDate(String signinDate) {
        this.signinDate = signinDate;
    }

    public String getSignoffFlag() {
        return signoffFlag;
    }

    public void setSignoffFlag(String signoffFlag) {
        this.signoffFlag = signoffFlag;
    }

    public String getSignoffDate() {
        return signoffDate;
    }

    public void setSignoffDate(String signoffDate) {
        this.signoffDate = signoffDate;
    }

    public String getSettDate() {
        return settDate;
    }

    public void setSettDate(String settDate) {
        this.settDate = settDate;
    }

    public String getDownLoadFlag() {
        return downLoadFlag;
    }

    public void setDownLoadFlag(String downLoadFlag) {
        this.downLoadFlag = downLoadFlag;
    }

    public String getNeedDownLoad() {
        return needDownLoad;
    }

    public void setNeedDownLoad(String needDownLoad) {
        this.needDownLoad = needDownLoad;
    }

    public String getBatchStep() {
        return batchStep;
    }

    public void setBatchStep(String batchStep) {
        this.batchStep = batchStep;
    }

}
