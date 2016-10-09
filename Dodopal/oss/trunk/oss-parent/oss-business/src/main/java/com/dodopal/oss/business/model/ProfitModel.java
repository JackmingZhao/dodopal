/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.oss.business.model;

import java.util.Date;

/**
 * Created by lenovo on 2015/10/28.
 */
public class ProfitModel {
    private String id;//主键ID
    private String orderNo;//订单交易号
    private Date orderTime;//订单时间
    private String customerCode;//商户编号
    private String customerName;//商户名称
    private String bussinessType; //业务类型
    private String customerType;//商户类型
    private String source;//来源
    private String iscircle;//是否圈存
    private String customerRateType; //商户业务费率类型
    private double customerRate;//商户业务费率
    private long orderAmount;//订单金额
    private long customerShouldProfit;//商户应得分润
    private long customerRealProfit;//商户实际分润
    private String subCustomerCode;//下级商户编号
    private String subCustomerName;//下级商户名称
    private long subCustomerShouldProfit;//下级商户应得分润
    private String customerSettlementTime; //与客户结算时间
    private String profitDate; //分润日期

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIscircle() {
        return iscircle;
    }

    public void setIscircle(String iscircle) {
        this.iscircle = iscircle;
    }

    public String getCustomerRateType() {
        return customerRateType;
    }

    public void setCustomerRateType(String customerRateType) {
        this.customerRateType = customerRateType;
    }

    public double getCustomerRate() {
        return customerRate;
    }

    public void setCustomerRate(double customerRate) {
        this.customerRate = customerRate;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public long getCustomerShouldProfit() {
        return customerShouldProfit;
    }

    public void setCustomerShouldProfit(long customerShouldProfit) {
        this.customerShouldProfit = customerShouldProfit;
    }

    public long getCustomerRealProfit() {
        return customerRealProfit;
    }

    public void setCustomerRealProfit(long customerRealProfit) {
        this.customerRealProfit = customerRealProfit;
    }

    public String getSubCustomerCode() {
        return subCustomerCode;
    }

    public void setSubCustomerCode(String subCustomerCode) {
        this.subCustomerCode = subCustomerCode;
    }

    public String getSubCustomerName() {
        return subCustomerName;
    }

    public void setSubCustomerName(String subCustomerName) {
        this.subCustomerName = subCustomerName;
    }

    public long getSubCustomerShouldProfit() {
        return subCustomerShouldProfit;
    }

    public void setSubCustomerShouldProfit(long subCustomerShouldProfit) {
        this.subCustomerShouldProfit = subCustomerShouldProfit;
    }

    public String getCustomerSettlementTime() {
        return customerSettlementTime;
    }

    public void setCustomerSettlementTime(String customerSettlementTime) {
        this.customerSettlementTime = customerSettlementTime;
    }

    public String getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(String profitDate) {
        this.profitDate = profitDate;
    }
}
