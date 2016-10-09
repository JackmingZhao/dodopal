/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.oss.business.model;

import com.dodopal.common.model.BaseEntity;

import java.util.Date;

/**
 * Created by lenovo on 2015/10/23.
 */
public class PayTransaction extends BaseEntity{
    private String tranCode;
    private String tranName;
    private String tranOutStatus;
    private String tranInStatus;
    private long amountMoney;
    private String tranType;
    private String userType;
    private String merOrUserCode;
    private String orderNumber;
    private String commodity;
    private String businessType;
    private String source;
    private String orderDate;
    private String payType;
    private String payWay;
    private double payServiceRate;
    private long payServiceFee;
    private double payProceRate;
    private long payProceFee;
    private long realTranMoney;
    private String turnOutMerCode;
    private String turnIntoMerCode;
    private String oldTranCode;
    private String clearFlag;
    private String acceptCiphertext;
    private String sendCiphertext;
    private String finishDate;
    private String comments;
    private String payServiceType;
    private String merUserType;
    private String merUserName;
    private Date createDate;
    private String payGateway;
    private String payWayName;

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getPayGateway() {
        return payGateway;
    }

    public void setPayGateway(String payGateway) {
        this.payGateway = payGateway;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    public String getTranOutStatus() {
        return tranOutStatus;
    }

    public void setTranOutStatus(String tranOutStatus) {
        this.tranOutStatus = tranOutStatus;
    }

    public String getTranInStatus() {
        return tranInStatus;
    }

    public void setTranInStatus(String tranInStatus) {
        this.tranInStatus = tranInStatus;
    }

    public long getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(long amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMerOrUserCode() {
        return merOrUserCode;
    }

    public void setMerOrUserCode(String merOrUserCode) {
        this.merOrUserCode = merOrUserCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public double getPayServiceRate() {
        return payServiceRate;
    }

    public void setPayServiceRate(double payServiceRate) {
        this.payServiceRate = payServiceRate;
    }

    public long getPayServiceFee() {
        return payServiceFee;
    }

    public void setPayServiceFee(long payServiceFee) {
        this.payServiceFee = payServiceFee;
    }

    public double getPayProceRate() {
        return payProceRate;
    }

    public void setPayProceRate(double payProceRate) {
        this.payProceRate = payProceRate;
    }

    public long getPayProceFee() {
        return payProceFee;
    }

    public void setPayProceFee(long payProceFee) {
        this.payProceFee = payProceFee;
    }

    public long getRealTranMoney() {
        return realTranMoney;
    }

    public void setRealTranMoney(long realTranMoney) {
        this.realTranMoney = realTranMoney;
    }

    public String getTurnOutMerCode() {
        return turnOutMerCode;
    }

    public void setTurnOutMerCode(String turnOutMerCode) {
        this.turnOutMerCode = turnOutMerCode;
    }

    public String getTurnIntoMerCode() {
        return turnIntoMerCode;
    }

    public void setTurnIntoMerCode(String turnIntoMerCode) {
        this.turnIntoMerCode = turnIntoMerCode;
    }

    public String getOldTranCode() {
        return oldTranCode;
    }

    public void setOldTranCode(String oldTranCode) {
        this.oldTranCode = oldTranCode;
    }

    public String getClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(String clearFlag) {
        this.clearFlag = clearFlag;
    }

    public String getAcceptCiphertext() {
        return acceptCiphertext;
    }

    public void setAcceptCiphertext(String acceptCiphertext) {
        this.acceptCiphertext = acceptCiphertext;
    }

    public String getSendCiphertext() {
        return sendCiphertext;
    }

    public void setSendCiphertext(String sendCiphertext) {
        this.sendCiphertext = sendCiphertext;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPayServiceType() {
        return payServiceType;
    }

    public void setPayServiceType(String payServiceType) {
        this.payServiceType = payServiceType;
    }

    public String getMerUserType() {
        return merUserType;
    }

    public void setMerUserType(String merUserType) {
        this.merUserType = merUserType;
    }

    public String getMerUserName() {
        return merUserName;
    }

    public void setMerUserName(String merUserName) {
        this.merUserName = merUserName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
