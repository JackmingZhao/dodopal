package com.dodopal.payment.business.model;

import com.dodopal.common.model.BaseEntity;

import java.util.Date;

/**
 * 类说明 :支付提交参数处理
 * @author lifeng
 */

public class PaySubmit extends BaseEntity {
    private String tranCode;
    //交易名称
    private String tranName;
    //金额
    private double tranMoney ;
    private double realTranMoney;
    //商品名称
    private String commodityName;
    //客户类型
    private String customerType;
//    private long amountMoney;
    //客户号
    private String merOrUserCode;
    //订单号
    private String orderNumber;
    //业务类型
    private String businessType;
    //来源
    private String source;
    //商户类型，是否外接
    private String merchantType;
    //支付方式id
    private String payWayId;
    //外接支付表或通用支付表的configid
    private String payId;
    //外接支付表或通用支付表id
    private String payCommonId;
    //支付方式类型
    private String payWayKind;
    //获取服务费率
    private double rate;
    //服务费四舍五入 两位小数
//    private double realServiceMoney;
    ////服务费以分存入数据库
//    private long serviceMoney;
    //返回路径
    private String returnUrl;
    //回调路径
    private String notifyUrl;

    private String payServiceType;

    private String genId;

    public String getPayServiceType() {
        return payServiceType;
    }

    public void setPayServiceType(String payServiceType) {
        this.payServiceType = payServiceType;
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

    public double getTranMoney() {
        return tranMoney;
    }

    public void setTranMoney(double tranMoney) {
        this.tranMoney = tranMoney;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

//    public long getAmountMoney() {
//        return amountMoney;
//    }
//
//    public void setAmountMoney(long amountMoney) {
//        this.amountMoney = amountMoney;
//    }

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

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getPayWayId() {
        return payWayId;
    }

    public void setPayWayId(String payWayId) {
        this.payWayId = payWayId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayCommonId() {
        return payCommonId;
    }

    public void setPayCommonId(String payCommonId) {
        this.payCommonId = payCommonId;
    }

    public String getPayWayKind() {
        return payWayKind;
    }

    public void setPayWayKind(String payWayKind) {
        this.payWayKind = payWayKind;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

//    public double getRealServiceMoney() {
//        return realServiceMoney;
//    }
//
//    public void setRealServiceMoney(double realServiceMoney) {
//        this.realServiceMoney = realServiceMoney;
//    }
//
//    public long getServiceMoney() {
//        return serviceMoney;
//    }
//
//    public void setServiceMoney(long serviceMoney) {
//        this.serviceMoney = serviceMoney;
//    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public double getRealTranMoney() {
        return realTranMoney;
    }

    public void setRealTranMoney(double realTranMoney) {
        this.realTranMoney = realTranMoney;
    }

    public String getGenId() {
        return genId;
    }

    public void setGenId(String genId) {
        this.genId = genId;
    }
}
