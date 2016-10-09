package com.dodopal.payment.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * @author DingKuiyuan@dodopal.com
 * 支付流水信息
 * @version 创建时间：2015年7月22日 下午3:51:46
 */
public class Payment extends BaseEntity{
    private static final long serialVersionUID = 513191498074191925L;
    /**
     * 交易流水号
     */
    private String tranCode;
    
    /**
     * 支付状态
     */
    private String payStatus;
    
    /**
     *   支付类型
     */
    private String payType;
    
    /**
     * 支付方式分类
     */
    private String payWayKind;
    
    /**
     * 支付方式ID
     */
    private String payWayId;
    
    /**
     * 支付服务费率
     */
    private double payServiceRate;
    
    /**
     * 支付服务费 
     */
    private long payServiceFee;
    
    /**
     * 支付金额
     */
    private long payMoney;
    
    /**
     * 页面回调支付状态
     */
    private String pageCallbackStatus;
    
    /**
     * 页面回调时间
     */
    private Date pageCallbackDate;
    
    /**
     * 服务器端通知支付状态
     */
    private String serviceNoticeStatus;
    
    /**
     * 服务器端通知时间
     */
    private Date serviceNoticeDate;
    
    /**
     * 发送支付密文
     */
    private String sendCiphertext;
   
    /**
     *  接收支付密文
     */
    private String acceptCiphertext;
    /**
     * 支付名称
     */
    private String payWayName;
    
    /**
     * 第三方回吐流水号
     */
    private String tradeNo;
    

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayWayKind() {
        return payWayKind;
    }

    public void setPayWayKind(String payWayKind) {
        this.payWayKind = payWayKind;
    }

    public String getPayWayId() {
        return payWayId;
    }

    public void setPayWayId(String payWayId) {
        this.payWayId = payWayId;
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

    public long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(long payMoney) {
        this.payMoney = payMoney;
    }

    public String getPageCallbackStatus() {
        return pageCallbackStatus;
    }

    public void setPageCallbackStatus(String pageCallbackStatus) {
        this.pageCallbackStatus = pageCallbackStatus;
    }

    public Date getPageCallbackDate() {
        return pageCallbackDate;
    }

    public void setPageCallbackDate(Date pageCallbackDate) {
        this.pageCallbackDate = pageCallbackDate;
    }

    public String getServiceNoticeStatus() {
        return serviceNoticeStatus;
    }

    public void setServiceNoticeStatus(String serviceNoticeStatus) {
        this.serviceNoticeStatus = serviceNoticeStatus;
    }

    public Date getServiceNoticeDate() {
        return serviceNoticeDate;
    }

    public void setServiceNoticeDate(Date serviceNoticeDate) {
        this.serviceNoticeDate = serviceNoticeDate;
    }

    public String getSendCiphertext() {
        return sendCiphertext;
    }

    public void setSendCiphertext(String sendCiphertext) {
        this.sendCiphertext = sendCiphertext;
    }

    public String getAcceptCiphertext() {
        return acceptCiphertext;
    }

    public void setAcceptCiphertext(String acceptCiphertext) {
        this.acceptCiphertext = acceptCiphertext;
    }

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

    
}
