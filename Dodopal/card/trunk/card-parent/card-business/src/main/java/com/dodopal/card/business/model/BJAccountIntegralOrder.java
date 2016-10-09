package com.dodopal.card.business.model;

import com.dodopal.common.model.BaseEntity;

public class BJAccountIntegralOrder extends BaseEntity {
    private static final long serialVersionUID = 6869688742819475199L;

    //账户与积分消费订单号
    private String crdAccIntOrderNum;
    //产品库主订单号
    private String proOrderNum;
    //产品编号
    private String proCode;
    //商户编号
    private String merCode;
    //业务类型
    private String businessType;
    //卡服务订单交易状态
    private String crdOrderStates;
    //上次卡服务订单交易状态
    private String crdBeforeorderStates;
    //交易应答码
    private String respCode;
    //交易时间
    private String tranDateTime;
    //城市代码
    private String cityCode;
    //商户类型
    private String merType;
   
    //设备类型 0：NFC手机 1：都都宝家用机  2：都都宝商用机
    private String posType;
    //设备编号
    private String posCode;
    //操作员号：前12位填写SAM号
    private String operId;
    //结算日期
    private String settDate;
    //通讯流水号
    private String comSeq;
    //终端IC交易流水号
    private String icseq;
    //批次号
    private String batchId;
    //系统时间
    private String dateTime;
    //卡号
    private String cardNo;
    //账户号
    private String accountNo;
    //交易金额
    private String txnAmt;    
    //积分金额
    private String preautheAmt; 
    //个性化信息
    private String priviMsg; 
    //账户数目
    private String accNum; 
    //账户信息
    private String accInfo; 
    //终端账户交易流水号
    private String accSeq;
    //保留字段
    private String reserved;
    //消费特殊域上传数据
    private String specialConsome;
    //消费特殊域返回数据
    private String specialConsomeBack;
    //结果上送特殊域上传数据
    private String specialSend;
    //结果上送特殊域返回数据
    private String specialSendBack;
    //撤销特殊域上传数据
    private String specialRevoke;
    //撤销特殊域返回数据
    private String specialRevokeBack;
    //Pos通讯流水号
    private String posSeq;
    //交易类型
    private String txnType;
    //交易日期
    private String txnDate;
    //交易时间 
    private String txnTime;
    //卡内余额
    private String cardBal;
    //主机交易流水号
    private String txnSeqNo;
    
    public String getCrdAccIntOrderNum() {
        return crdAccIntOrderNum;
    }
    public void setCrdAccIntOrderNum(String crdAccIntOrderNum) {
        this.crdAccIntOrderNum = crdAccIntOrderNum;
    }
    public String getProOrderNum() {
        return proOrderNum;
    }
    public void setProOrderNum(String proOrderNum) {
        this.proOrderNum = proOrderNum;
    }
    public String getProCode() {
        return proCode;
    }
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public String getBusinessType() {
        return businessType;
    }
    public String getPosSeq() {
        return posSeq;
    }
    public void setPosSeq(String posSeq) {
        this.posSeq = posSeq;
    }
    public String getTxnType() {
        return txnType;
    }
    public void setTxnType(String txnType) {
        this.txnType = txnType;
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
    public String getCardBal() {
        return cardBal;
    }
    public void setCardBal(String cardBal) {
        this.cardBal = cardBal;
    }
    public String getTxnSeqNo() {
        return txnSeqNo;
    }
    public void setTxnSeqNo(String txnSeqNo) {
        this.txnSeqNo = txnSeqNo;
    }
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
    public String getCrdOrderStates() {
        return crdOrderStates;
    }
    public void setCrdOrderStates(String crdOrderStates) {
        this.crdOrderStates = crdOrderStates;
    }
    public String getCrdBeforeorderStates() {
        return crdBeforeorderStates;
    }
    public void setCrdBeforeorderStates(String crdBeforeorderStates) {
        this.crdBeforeorderStates = crdBeforeorderStates;
    }
    public String getTranDateTime() {
        return tranDateTime;
    }
    public void setTranDateTime(String tranDateTime) {
        this.tranDateTime = tranDateTime;
    }
    
    public String getRespCode() {
        return respCode;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getPosType() {
        return posType;
    }
    public void setPosType(String posType) {
        this.posType = posType;
    }
    
    public String getPosCode() {
        return posCode;
    }
    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }
    public String getOperId() {
        return operId;
    }
    public void setOperId(String operId) {
        this.operId = operId;
    }
    public String getSettDate() {
        return settDate;
    }
    public void setSettDate(String settDate) {
        this.settDate = settDate;
    }
    public String getComSeq() {
        return comSeq;
    }
    public void setComSeq(String comSeq) {
        this.comSeq = comSeq;
    }
    public String getIcseq() {
        return icseq;
    }
    public void setIcseq(String icseq) {
        this.icseq = icseq;
    }
    public String getBatchId() {
        return batchId;
    }
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public String getTxnAmt() {
        return txnAmt;
    }
    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }
    public String getPreautheAmt() {
        return preautheAmt;
    }
    public void setPreautheAmt(String preautheAmt) {
        this.preautheAmt = preautheAmt;
    }
    public String getPriviMsg() {
        return priviMsg;
    }
    public void setPriviMsg(String priviMsg) {
        this.priviMsg = priviMsg;
    }
    public String getAccNum() {
        return accNum;
    }
    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }
    public String getAccInfo() {
        return accInfo;
    }
    public void setAccInfo(String accInfo) {
        this.accInfo = accInfo;
    }
    public String getAccSeq() {
        return accSeq;
    }
    public void setAccSeq(String accSeq) {
        this.accSeq = accSeq;
    }
    public String getReserved() {
        return reserved;
    }
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
    public String getSpecialConsome() {
        return specialConsome;
    }
    public void setSpecialConsome(String specialConsome) {
        this.specialConsome = specialConsome;
    }
    public String getSpecialConsomeBack() {
        return specialConsomeBack;
    }
    public void setSpecialConsomeBack(String specialConsomeBack) {
        this.specialConsomeBack = specialConsomeBack;
    }
    public String getSpecialSend() {
        return specialSend;
    }
    public void setSpecialSend(String specialSend) {
        this.specialSend = specialSend;
    }
    public String getSpecialSendBack() {
        return specialSendBack;
    }
    public void setSpecialSendBack(String specialSendBack) {
        this.specialSendBack = specialSendBack;
    }
    public String getSpecialRevoke() {
        return specialRevoke;
    }
    public void setSpecialRevoke(String specialRevoke) {
        this.specialRevoke = specialRevoke;
    }
    public String getSpecialRevokeBack() {
        return specialRevokeBack;
    }
    public void setSpecialRevokeBack(String specialRevokeBack) {
        this.specialRevokeBack = specialRevokeBack;
    }
    public String getMerType() {
        return merType;
    }
    public void setMerType(String merType) {
        this.merType = merType;
    }
    /* 
     * 
     */
    @Override
    public String toString() {
        return "{\"crdAccIntOrderNum\":\"" + crdAccIntOrderNum + "\",\"proOrderNum\":\"" + proOrderNum + "\",\"proCode\":\"" + proCode + "\",\"merCode\":\"" + merCode + "\",\"businessType\":\"" + businessType + "\",\"crdOrderStates\":\"" + crdOrderStates + "\",\"crdBeforeorderStates\":\"" + crdBeforeorderStates + "\",\"respCode\":\"" + respCode + "\",\"tranDateTime\":\"" + tranDateTime + "\",\"cityCode\":\"" + cityCode + "\",\"merType\":\"" + merType + "\",\"posType\":\"" + posType + "\",\"posCode\":\""
            + posCode + "\",\"operId\":\"" + operId + "\",\"settDate\":\"" + settDate + "\",\"comSeq\":\"" + comSeq + "\",\"icseq\":\"" + icseq + "\",\"batchId\":\"" + batchId + "\",\"dateTime\":\"" + dateTime + "\",\"cardNo\":\"" + cardNo + "\",\"accountNo\":\"" + accountNo + "\",\"txnAmt\":\"" + txnAmt + "\",\"preautheAmt\":\"" + preautheAmt + "\",\"priviMsg\":\"" + priviMsg + "\",\"accNum\":\"" + accNum + "\",\"accInfo\":\"" + accInfo + "\",\"accSeq\":\"" + accSeq + "\",\"reserved\":\"" + reserved
            + "\",\"specialConsome\":\"" + specialConsome + "\",\"specialConsomeBack\":\"" + specialConsomeBack + "\",\"specialSend\":\"" + specialSend + "\",\"specialSendBack\":\"" + specialSendBack + "\",\"specialRevoke\":\"" + specialRevoke + "\",\"specialRevokeBack\":\"" + specialRevokeBack + "\",\"posSeq\":\"" + posSeq + "\",\"txnType\":\"" + txnType + "\",\"txnDate\":\"" + txnDate + "\",\"txnTime\":\"" + txnTime + "\",\"cardBal\":\"" + cardBal + "\",\"txnSeqNo\":\"" + txnSeqNo + "\"}  ";
    }
    
}
