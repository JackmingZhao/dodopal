package com.dodopal.api.product.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="loadOrder")
public class LoadOrderResponseDTO implements Serializable {

    public LoadOrderResponseDTO() {
        
    }
    
    public LoadOrderResponseDTO(String responseCode, String orderNum, String sourceOrderNum) {
        this.responseCode = responseCode;
        this.orderNum = orderNum;
        this.sourceOrderNum = sourceOrderNum;
    }
    
    /**
     * 
     */
    private static final long serialVersionUID = 8081610628131143340L;

    private String responseCode;
    
    private String orderNum;

    private String sourceOrderNum;

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
