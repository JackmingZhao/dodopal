/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.oss.business.model;

import com.dodopal.common.model.BaseEntity;

import java.util.Date;

/**
 * Created by lenovo on 2015/10/24.
 */
public class Purchase extends BaseEntity{
    private String orderNum;//订单编号
    private String cityCode;//城市code
    private String cityName;//城市名称
    private String yktCode;//通卡公司code
    private String yktName;//通卡公司名称
    private Date orderDate;//订单时间
    private String orderDay;//订单日期
    private double yktPayRate;//通卡公司一卡通支付费率
    private String cardNum;//卡号
    private String posCode;//pos号
    private long befbal;//支付前金额
    private long blackAmt;//支付后金额
    private long merDiscount;//商户折扣
    private String innerStates;//内部状态
    private String beforeinnerStates;//前内部状态
    private String suspiciousStates;//可疑处理状态
    private String suspiciousExplain;//可以处理说明
    private long originalPrice;//应收金额
    private long receivedPrice;//实收金额
    private String customerType;//客户类型
    private String customerCode;//客户号
    private String customerName;//客户名称
    private String businessType;//业务类型
    private String merRateType;//商户费率类型
    private double merRate;//商户费率
    private String serviceRateType;//服务费率类型
    private double serviceRate;//服务费率
    private long merGain;//商户利润
    private String payGateway;//支付网关
    private String payType;//支付类型
    private String payWay;//支付方式
    private String states;//状态
    private String source;//来源
    private String clearingMark;//清分状态
    private String userId;//操作id
    private String comments;//备注
    private String fundProcessResult;//资金处理结果

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(String orderDay) {
        this.orderDay = orderDay;
    }

    public double getYktPayRate() {
        return yktPayRate;
    }

    public void setYktPayRate(double yktPayRate) {
        this.yktPayRate = yktPayRate;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public long getBefbal() {
        return befbal;
    }

    public void setBefbal(long befbal) {
        this.befbal = befbal;
    }

    public long getBlackAmt() {
        return blackAmt;
    }

    public void setBlackAmt(long blackAmt) {
        this.blackAmt = blackAmt;
    }

    public long getMerDiscount() {
        return merDiscount;
    }

    public void setMerDiscount(long merDiscount) {
        this.merDiscount = merDiscount;
    }

    public String getInnerStates() {
        return innerStates;
    }

    public void setInnerStates(String innerStates) {
        this.innerStates = innerStates;
    }

    public String getBeforeinnerStates() {
        return beforeinnerStates;
    }

    public void setBeforeinnerStates(String beforeinnerStates) {
        this.beforeinnerStates = beforeinnerStates;
    }

    public String getSuspiciousStates() {
        return suspiciousStates;
    }

    public void setSuspiciousStates(String suspiciousStates) {
        this.suspiciousStates = suspiciousStates;
    }

    public String getSuspiciousExplain() {
        return suspiciousExplain;
    }

    public void setSuspiciousExplain(String suspiciousExplain) {
        this.suspiciousExplain = suspiciousExplain;
    }

    public long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public long getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(long receivedPrice) {
        this.receivedPrice = receivedPrice;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMerRateType() {
        return merRateType;
    }

    public void setMerRateType(String merRateType) {
        this.merRateType = merRateType;
    }

    public double getMerRate() {
        return merRate;
    }

    public void setMerRate(double merRate) {
        this.merRate = merRate;
    }

    public String getServiceRateType() {
        return serviceRateType;
    }

    public void setServiceRateType(String serviceRateType) {
        this.serviceRateType = serviceRateType;
    }

    public double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public long getMerGain() {
        return merGain;
    }

    public void setMerGain(long merGain) {
        this.merGain = merGain;
    }

    public String getPayGateway() {
        return payGateway;
    }

    public void setPayGateway(String payGateway) {
        this.payGateway = payGateway;
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

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getClearingMark() {
        return clearingMark;
    }

    public void setClearingMark(String clearingMark) {
        this.clearingMark = clearingMark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFundProcessResult() {
        return fundProcessResult;
    }

    public void setFundProcessResult(String fundProcessResult) {
        this.fundProcessResult = fundProcessResult;
    }
}
