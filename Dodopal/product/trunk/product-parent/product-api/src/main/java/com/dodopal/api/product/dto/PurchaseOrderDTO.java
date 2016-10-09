package com.dodopal.api.product.dto;

import java.io.Serializable;

/**
 * 门户网点消费订单汇总/详细/导出DTO
 * 
 */
public class PurchaseOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /********************    汇总信息      ***************************/
    // 消费笔数
    private String sum;
    
    // 消费总金额
    private String sumAmt;
    /********************    汇总信息      ***************************/
    
    /********************    详细信息      ***************************/
    // 产品库订单编号
    private String proOrderNum;

    // 城市名称
    private String cityName;
    
    // 卡号 
    private String cardNum;
    
    // POS号
    private String posCode;
    
    //应收金额
    private String originalPrice;

    //实收金额
    private String receivedPrice;

    // 消费前金额
    private String befbal;

    // 消费后金额
    private String blackAmt;

    // 订单状态
    private String proOrderState;

    // 订单创建时间
    private String proOrderCreateDate;
    /********************    详细信息      ***************************/

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getSumAmt() {
        return sumAmt;
    }

    public void setSumAmt(String sumAmt) {
        this.sumAmt = sumAmt;
    }

    public String getProOrderNum() {
        return proOrderNum;
    }

    public void setProOrderNum(String proOrderNum) {
        this.proOrderNum = proOrderNum;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(String receivedPrice) {
        this.receivedPrice = receivedPrice;
    }

    public String getBefbal() {
        return befbal;
    }

    public void setBefbal(String befbal) {
        this.befbal = befbal;
    }

    public String getBlackAmt() {
        return blackAmt;
    }

    public void setBlackAmt(String blackAmt) {
        this.blackAmt = blackAmt;
    }

    public String getProOrderState() {
        return proOrderState;
    }

    public void setProOrderState(String proOrderState) {
        this.proOrderState = proOrderState;
    }

    public String getProOrderCreateDate() {
        return proOrderCreateDate;
    }

    public void setProOrderCreateDate(String proOrderCreateDate) {
        this.proOrderCreateDate = proOrderCreateDate;
    }

}
