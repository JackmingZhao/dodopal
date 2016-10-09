package com.dodopal.oss.business.bean;

import com.dodopal.common.model.BaseEntity;

public class CrdSysSupplementBean extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = -262624769875576718L;
    //卡服务订单号
    private String crdOrderNum;
    //交易类型
    private Integer txnType;
    //验卡特殊域信息
    private String checkYktmw;
    //验卡成功返回的信息
    private String checkYktkey;
    //充值申请特殊域信息
    private String chargeYktmw;
    //充值申请成功返回的信息
    private String chargeKey;
    //结果上传特殊域信息
    private String resultYktmw;
    //交易日期
    private String txnDate;
    //交易时间
    private String txnTime;
    //最近一次申请读卡密钥时间
    private String lastReadKeyTime;
    //最近一次申请充消密钥时间
    private String lastChargeKeyTime;
    //最近一次上传结果时间
    private String lastResultTime;
    //申请读卡密钥次数
    private long requestReadKeyCount;
    //申请充消密钥次数
    private long requestChargeKeyCount;
    //上传结果次数
    private long sendResultCount;
    public String getCrdOrderNum() {
        return crdOrderNum;
    }
    public void setCrdOrderNum(String crdOrderNum) {
        this.crdOrderNum = crdOrderNum;
    }
    public Integer getTxnType() {
        return txnType;
    }
    public void setTxnType(Integer txnType) {
        this.txnType = txnType;
    }
    public String getCheckYktmw() {
        return checkYktmw;
    }
    public void setCheckYktmw(String checkYktmw) {
        this.checkYktmw = checkYktmw;
    }
    public String getCheckYktkey() {
        return checkYktkey;
    }
    public void setCheckYktkey(String checkYktkey) {
        this.checkYktkey = checkYktkey;
    }
    public String getChargeYktmw() {
        return chargeYktmw;
    }
    public void setChargeYktmw(String chargeYktmw) {
        this.chargeYktmw = chargeYktmw;
    }
    public String getChargeKey() {
        return chargeKey;
    }
    public void setChargeKey(String chargeKey) {
        this.chargeKey = chargeKey;
    }
    public String getResultYktmw() {
        return resultYktmw;
    }
    public void setResultYktmw(String resultYktmw) {
        this.resultYktmw = resultYktmw;
    }
    public String getTxnDate() {
        return txnDate;
    }
    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }
    public String getTxnTime() {
        return txnTime;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    public String getLastReadKeyTime() {
        return lastReadKeyTime;
    }
    public void setLastReadKeyTime(String lastReadKeyTime) {
        this.lastReadKeyTime = lastReadKeyTime;
    }
    public String getLastChargeKeyTime() {
        return lastChargeKeyTime;
    }
    public void setLastChargeKeyTime(String lastChargeKeyTime) {
        this.lastChargeKeyTime = lastChargeKeyTime;
    }
    public String getLastResultTime() {
        return lastResultTime;
    }
    public void setLastResultTime(String lastResultTime) {
        this.lastResultTime = lastResultTime;
    }
    public long getRequestReadKeyCount() {
        return requestReadKeyCount;
    }
    public void setRequestReadKeyCount(long requestReadKeyCount) {
        this.requestReadKeyCount = requestReadKeyCount;
    }
    public long getRequestChargeKeyCount() {
        return requestChargeKeyCount;
    }
    public void setRequestChargeKeyCount(long requestChargeKeyCount) {
        this.requestChargeKeyCount = requestChargeKeyCount;
    }
    public long getSendResultCount() {
        return sendResultCount;
    }
    public void setSendResultCount(long sendResultCount) {
        this.sendResultCount = sendResultCount;
    }


}
