package com.dodopal.api.product.dto;

import com.dodopal.common.model.BaseEntity;

public class ProxyCountAllByIDDTO extends BaseEntity{
    private static final long serialVersionUID = -5966069875478975990L;
    private String total;   //交易总数
    private double amount; //交易总金额
    private String totalpen;//实收总笔数
    private double incomeamount;//实收总金额
    private String rebatenumber;//活动返利总笔数
    private double rebateamount;//活动返利总金额
    private String couponpennumber;//优惠券使用总笔数
    private double couponuse;//优惠券使用总金额
    private String allrebatesamt;//返利总笔数
    private double totalamount;//返利总金额
    private double amt;
    private String countamt;
    private double facevalue;
    private String countfacevalue;
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getTotalpen() {
        return totalpen;
    }
    public void setTotalpen(String totalpen) {
        this.totalpen = totalpen;
    }
    public double getIncomeamount() {
        return incomeamount;
    }
    public void setIncomeamount(double incomeamount) {
        this.incomeamount = incomeamount;
    }
    public String getRebatenumber() {
        return rebatenumber;
    }
    public void setRebatenumber(String rebatenumber) {
        this.rebatenumber = rebatenumber;
    }
    public double getRebateamount() {
        return rebateamount;
    }
    public void setRebateamount(double rebateamount) {
        this.rebateamount = rebateamount;
    }
    public String getCouponpennumber() {
        return couponpennumber;
    }
    public void setCouponpennumber(String couponpennumber) {
        this.couponpennumber = couponpennumber;
    }
    public double getCouponuse() {
        return couponuse;
    }
    public void setCouponuse(double couponuse) {
        this.couponuse = couponuse;
    }
    public String getAllrebatesamt() {
        return allrebatesamt;
    }
    public void setAllrebatesamt(String allrebatesamt) {
        this.allrebatesamt = allrebatesamt;
    }
    public double getTotalamount() {
        return totalamount;
    }
    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }
    public double getAmt() {
        return amt;
    }
    public void setAmt(double amt) {
        this.amt = amt;
    }
    public String getCountamt() {
        return countamt;
    }
    public void setCountamt(String countamt) {
        this.countamt = countamt;
    }
    public double getFacevalue() {
        return facevalue;
    }
    public void setFacevalue(double facevalue) {
        this.facevalue = facevalue;
    }
    public String getCountfacevalue() {
        return countfacevalue;
    }
    public void setCountfacevalue(String countfacevalue) {
        this.countfacevalue = countfacevalue;
    }
   
}
