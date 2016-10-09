package com.dodopal.api.product.dto;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * 圈存订单的DTO对象
 *
 */
public class LoadOrderDTO extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 7477749147387155058L;

    // 圈存订单编号
    private String orderNum;

    // 圈存订单时间
    private Date orderTime;
    
    // 外部订单号
    private String extMerOrderNum;

    // 外部下单时间
    private String extMerOrderTime;

    // 卡面号
    private String cardFaceNum;

    // 转化后卡号
    private String exchangedCardNum;

    // 产品编号
    private String productCode;
    
    // 产品名称
    private String productName;

    // 城市编号
    private String cityCode;
    
    // 城市名称
    private String cityName;

    // 通卡编号
    private String yktCode;
    
    // 通卡名称
    private String yktName;

    // 客户号
    private String customerCode;

    // 客户名称
    private String customerName;

    // 客户类型
    private String customerType;
    
    // 圈存费率
    private Double loadRate;

    // 圈存费率类型
    private String loadRateType;
    
    // 圈存金额
    private Long loadAmt;
    
    // 客户实付金额
    private Long customerPayAmt;   
    
    // 客户利润
    private Long customerGain;    
    
    // 支付方式ID
    private String paywayId;
  
    // 支付方式名称
    private String paywayName;
    
    // 支付类型
    private String payType;
    
    // 服务费率类型
    private String payServiceType;
    
    // 服务费率
    private Double payServiceRate;
    
    // 状态
    private String status;
    
    // 来源
    private String source;
    
    // 备注
    private String comments;


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getExtMerOrderNum() {
        return extMerOrderNum;
    }

    public void setExtMerOrderNum(String extMerOrderNum) {
        this.extMerOrderNum = extMerOrderNum;
    }

    public String getCardFaceNum() {
        return cardFaceNum;
    }

    public void setCardFaceNum(String cardFaceNum) {
        this.cardFaceNum = cardFaceNum;
    }

    public String getExchangedCardNum() {
        return exchangedCardNum;
    }

    public void setExchangedCardNum(String exchangedCardNum) {
        this.exchangedCardNum = exchangedCardNum;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getExtMerOrderTime() {
        return extMerOrderTime;
    }

    public void setExtMerOrderTime(String extMerOrderTime) {
        this.extMerOrderTime = extMerOrderTime;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Double getLoadRate() {
        return loadRate;
    }

    public void setLoadRate(Double loadRate) {
        this.loadRate = loadRate;
    }

    public String getLoadRateType() {
        return loadRateType;
    }

    public void setLoadRateType(String loadRateType) {
        this.loadRateType = loadRateType;
    }

    public Long getLoadAmt() {
        return loadAmt;
    }

    public void setLoadAmt(Long loadAmt) {
        this.loadAmt = loadAmt;
    }

    public Long getCustomerPayAmt() {
        return customerPayAmt;
    }

    public void setCustomerPayAmt(Long customerPayAmt) {
        this.customerPayAmt = customerPayAmt;
    }

    public Long getCustomerGain() {
        return customerGain;
    }

    public void setCustomerGain(Long customerGain) {
        this.customerGain = customerGain;
    }

    public String getPaywayId() {
        return paywayId;
    }

    public void setPaywayId(String paywayId) {
        this.paywayId = paywayId;
    }

    public String getPaywayName() {
        return paywayName;
    }

    public void setPaywayName(String paywayName) {
        this.paywayName = paywayName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayServiceType() {
        return payServiceType;
    }

    public void setPayServiceType(String payServiceType) {
        this.payServiceType = payServiceType;
    }

    public Double getPayServiceRate() {
        return payServiceRate;
    }

    public void setPayServiceRate(Double payServiceRate) {
        this.payServiceRate = payServiceRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
