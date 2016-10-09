package com.dodopal.api.product.dto;

import java.io.Serializable;

public class LoadOrderResponseDTO2 implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8081610628131143340L;
    private String responseCode;
    private String orderNum;
    private String sourceOrderNum;

    public LoadOrderResponseDTO2() {

    }

    public LoadOrderResponseDTO2(String responseCode, String orderNum, String sourceOrderNum) {
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
