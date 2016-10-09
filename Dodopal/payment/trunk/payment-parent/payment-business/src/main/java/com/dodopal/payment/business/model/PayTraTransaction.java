package com.dodopal.payment.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * @author DingKuiyuan@dodopal.com
 * 交易流水表
 * @version 创建时间：2015年7月22日 下午2:13:03
 */
public class PayTraTransaction extends BaseEntity  {

    private static final long serialVersionUID = 2622189188761232370L;
    
    /**
     * 交易流水号
     */
    private String tranCode;

    /**
     * 交易名称
     */
    private String tranName;

    /**
     * 外部交易状态
     * 0待支付1已取消2支付中3已支付4待退款5已退款6待转帐7转帐成功
     */
    private String tranOutStatus;
    
    /**
     * 内部交易状态
     * 0待处理 1已处理 10账户更新失败 11内部更新失败
     */
    private String tranInStatus;
    
    /**
     * 备注
     */
    private String comments;
    
    /**
     * 页面回调地址
     */
    private String pageCallbackUrl;

    /**
     * 服务器端通知地址
     */
    private String serviceNoticeUrl;
    
    /**
     * 金额   “分”为单位
     */
    private long amountMoney;
    
    /**
     * 交易类型
     * 1：账户充值、3：账户消费、5：退款、7：转出、9：转入11：正调账13：反调账
     */
    private String tranType;
    
    /**
     * 用户类型
     * 0：个人  1：商户
     */
    private String userType;
    
    /**
     * 用户号/商户号
     */
    private String merOrUserCode;
    
    /**
     * 用户名/商户名
     */
    private String merOrUserName;
    
    /**
     * 订单号
     */
    private String orderNumber;
    
    /**
     * 商品名称
     */
    private String commodity;
    
    /**
     * 业务类型代码
     */
    private String businessType;
    
    /**
     * 来源
     */
    private String source;
    
    /**
     * 订单时间
     */
    private Date orderDate;
    
    /**
     * 支付类型
     */
    private String payType;
    
    /**
     * 支付方式
     */
    private String payWay;
    
    /**
     * 支付服务费率
     *  数字 两位小数  默认：0
     */
    private double payServiceRate;
    
    /**
     * 支付服务费
     */
    private long payServiceFee;
    
    /**
     * 支付手续费率
     */
    private double payProceRate;
    
    /**
     * 支付手续费
     */
    private long payProceFee;
    
    /**
     * 实际交易金额
     */
    private long realTranMoney;
    
    /**
     * 转出商户号
     */
    private String turnOutMerCode;
    
    /**
     * 转入商户号
     */
    private String turnIntoMerCode;
    
    /**
     * 原交易流水号
     */
    private String oldTranCode;
    
    /**
     * 清算标志位 0：未清算  1：已清算
     */
    private String clearFlag;
    
    /**
     * 接受密文
     */
    private String acceptCiphertext;
    
    /**
     * 发送密文
     */
    private String sendCiphertext;
    
    /**
     * 完成时间
     */
    private Date finishDate;
    /**
     * @comments  付款金额，实际传到第三方接口的金额数据
     */
    private double payMoney;
    
    /**
     * 商户编码
     */
    private String merCode;
    
    /**
     * 所属上级商户编码
     */
    private String merParentCode;
    
    /**
     * 所属上级商户名称
     */
    private String merParentName;
    /**
     * 网关
     */
    private String payGateway;
    /**
     * 服务费率类型
     */
    private String payServiceType;
  
    //支付方式名称
    private String payWayName;

    //uuid 唯一标识码
    private String genId;
    
    // 操作员Id
    private String operatorId;
    
    /**
     * 客户所属城市
     */
    private String merCityName;
    
    /**
     * 打款人
     */
    private String merPayMoneyUser;

    public String getMerPayMoneyUser() {
        return merPayMoneyUser;
    }

    public void setMerPayMoneyUser(String merPayMoneyUser) {
        this.merPayMoneyUser = merPayMoneyUser;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getPayServiceType() {
        return payServiceType;
    }

    public void setPayServiceType(String payServiceType) {
        this.payServiceType = payServiceType;
    }

    public String getPayGateway() {
        return payGateway;
    }

    public void setPayGateway(String payGateway) {
        this.payGateway = payGateway;
    }

    public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getMerParentCode() {
		return merParentCode;
	}

	public void setMerParentCode(String merParentCode) {
		this.merParentCode = merParentCode;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPageCallbackUrl() {
        return pageCallbackUrl;
    }

    public void setPageCallbackUrl(String pageCallbackUrl) {
        this.pageCallbackUrl = pageCallbackUrl;
    }

    public String getServiceNoticeUrl() {
        return serviceNoticeUrl;
    }

    public void setServiceNoticeUrl(String serviceNoticeUrl) {
        this.serviceNoticeUrl = serviceNoticeUrl;
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

    public String getMerOrUserName() {
        return merOrUserName;
    }

    public void setMerOrUserName(String merOrUserName) {
        this.merOrUserName = merOrUserName;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
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

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getGenId() {
        return genId;
    }

    public void setGenId(String genId) {
        this.genId = genId;
    }

    public String getMerParentName() {
        return merParentName;
    }

    public void setMerParentName(String merParentName) {
        this.merParentName = merParentName;
    }

    public String getMerCityName() {
        return merCityName;
    }

    public void setMerCityName(String merCityName) {
        this.merCityName = merCityName;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
    
}
