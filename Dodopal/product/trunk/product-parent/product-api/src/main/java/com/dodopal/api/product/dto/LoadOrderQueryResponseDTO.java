package com.dodopal.api.product.dto;

import java.io.Serializable;

/**
 * 6.2  根据卡号获取可用于一卡通充值的圈存订单
 * 
 *   返回参数信息  : 圈存订单编号、外部订单号、卡号、商户号
 * @author Mango
 *
 */
public class LoadOrderQueryResponseDTO extends LoadOrderResponseDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8081610628131143340L;
    private String cardNum;

    private String merchantNum;
    
    private String productCode;
    
    private String cityCode;

    private long chargeAmt;
    
    private String merchantName;
    
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public long getChargeAmt() {
        return chargeAmt;
    }

    public void setChargeAmt(long chargeAmt) {
        this.chargeAmt = chargeAmt;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

}
