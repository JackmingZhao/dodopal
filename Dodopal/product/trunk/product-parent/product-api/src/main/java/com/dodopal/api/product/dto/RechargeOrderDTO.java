package com.dodopal.api.product.dto;

import java.io.Serializable;

/**
 * 门户网点充值订单汇总/详细/导出DTO
 * 
 */
public class RechargeOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /********************    汇总信息      ***************************/
    // 充值笔数
    private String sum;
    
    // 充值总金额
    private String sumAmt;
    /********************    汇总信息      ***************************/
    
    /********************    详细信息      ***************************/
    // 产品库订单编号
    private String proOrderNum;

    // 城市名称
    private String cityName;
    
    // 充值的金额
    private String txnAmt;
    
    // 实付金额
    private String receivedPrice;

    // 利润
    private String merGain;
    
    // POS号
    private String posCode;
    
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

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(String receivedPrice) {
        this.receivedPrice = receivedPrice;
    }

    public String getMerGain() {
        return merGain;
    }

    public void setMerGain(String merGain) {
        this.merGain = merGain;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
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
