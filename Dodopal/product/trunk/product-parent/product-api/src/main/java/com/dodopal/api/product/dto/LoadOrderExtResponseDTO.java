package com.dodopal.api.product.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "loadOrder")
public class LoadOrderExtResponseDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8081610628131143340L;

    private String responseCode;

    private String orderNum;

    private String sourceOrderNum;

    private String sourceOrderTime;

    private String cardNum;

    private String orderStatus;

    public LoadOrderExtResponseDTO() {

    }

    public String getSourceOrderTime() {
        return sourceOrderTime;
    }

    public void setSourceOrderTime(String sourceOrderTime) {
        this.sourceOrderTime = sourceOrderTime;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LoadOrderExtResponseDTO(String responseCode, String orderNum, String sourceOrderNum, String sourceOrderTime, String cardNum, String orderStatus) {
        this.responseCode = responseCode;
        this.orderNum = orderNum;
        this.sourceOrderNum = sourceOrderNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getSourceOrderNum() {
        return sourceOrderNum;
    }

    public void setSourceOrderNum(String sourceOrderNum) {
        this.sourceOrderNum = sourceOrderNum;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

}
