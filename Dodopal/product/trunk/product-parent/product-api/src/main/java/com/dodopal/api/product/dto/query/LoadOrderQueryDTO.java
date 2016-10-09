package com.dodopal.api.product.dto.query;

import com.dodopal.common.model.QueryBase;

public class LoadOrderQueryDTO extends QueryBase {

    private static final long serialVersionUID = -3330346763699042960L;

    private String orderNum;

    private String sourceOrderNum;

    private String cardNum;

    private String merchantNum;

    private String merchantName;
    
    private String yktCode;

    private String orderStatus;
    
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

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(String merchantNum) {
        this.merchantNum = merchantNum;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

}
