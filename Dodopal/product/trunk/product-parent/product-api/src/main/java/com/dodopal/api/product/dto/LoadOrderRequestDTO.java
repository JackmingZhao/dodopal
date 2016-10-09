package com.dodopal.api.product.dto;

import java.io.Serializable;

/**
 * 外接商户__下圈存订单入参参数
 * @author dodopal
 *
 */
public class LoadOrderRequestDTO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1048014211348365537L;

    private String sourceOrderNum; // 调用方平台对应的订单编号
    private String cardNum; //需要进行充值的一卡通卡面号
    private String merchantNum; //调用方平台对应的商户号（由DDP预先分配）
    private String productNum; //这两个参数至少提供一个。如果提供了productNum，则以对应的一卡通充值产品的面额为准，此时忽略rechargeAmt。
    private String cityCode;
    private String chargeAmt; //需要对公交卡进行充值的金额。如果没有提供productNum，则必须提供chargeAmt。 虽然是String类型，但是要能够转化为正整数。
    private String sourceOrderTime; // 调用方平台调用此接口的时间，由对方平台提供。 格式：yyyyMMddHHMMSS
    private String signType; //签名类型。比如：RSA、MD5。  如果没有指明，默认为MD5。
    private String signData; //签名值
    private String signCharset; //签名字符集，比如utf-8。 如果没有指定，默认为GBK
  
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
 
    public String getChargeAmt() {
        return chargeAmt;
    }
    public void setChargeAmt(String chargeAmt) {
        this.chargeAmt = chargeAmt;
    }
    public String getSourceOrderTime() {
        return sourceOrderTime;
    }
    public void setSourceOrderTime(String sourceOrderTime) {
        this.sourceOrderTime = sourceOrderTime;
    }
    public String getSignType() {
        return signType;
    }
    public void setSignType(String signType) {
        this.signType = signType;
    }
    public String getSignData() {
        return signData;
    }
    public void setSignData(String signData) {
        this.signData = signData;
    }
    public String getSignCharset() {
        return signCharset;
    }
    public void setSignCharset(String signCharset) {
        this.signCharset = signCharset;
    }
}
