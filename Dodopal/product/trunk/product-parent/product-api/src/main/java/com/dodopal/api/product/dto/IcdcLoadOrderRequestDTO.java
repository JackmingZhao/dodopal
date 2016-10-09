package com.dodopal.api.product.dto;

import java.io.Serializable;

public class IcdcLoadOrderRequestDTO implements Serializable{

    private static final long serialVersionUID = 2252869932960596997L;
    private String source; // 来源
    private String cardNum; //需要进行充值的一卡通卡面号
    private String productNum; //这两个参数至少提供一个。如果提供了productNum，则以对应的一卡通充值产品的面额为准，此时忽略rechargeAmt。
    private String cityCode; //  城市code
    private String payType; //支付类型 枚举
    private String customerCode; //客户号
    private String payWayId; //支付方式id
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getCardNum() {
        return cardNum;
    }
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
    public String getProductNum() {
        return productNum;
    }
    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }
    public String getCustomerCode() {
        return customerCode;
    }
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    public String getPayWayId() {
        return payWayId;
    }
    public void setPayWayId(String payWayId) {
        this.payWayId = payWayId;
    }
    
}
