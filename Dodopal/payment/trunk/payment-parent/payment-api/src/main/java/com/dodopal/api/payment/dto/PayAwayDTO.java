package com.dodopal.api.payment.dto;

import com.dodopal.common.model.BaseEntity;

public class PayAwayDTO extends BaseEntity {
    
    /**
     * 
     */
    private static final long serialVersionUID = 3187044742557540686L;
    /**
     * 商户名称
     */
    private String merName;
    /**
     * 商户号
     */
    private String merCode;
    
    /**
     * 支付服务费率
     */
    private double payServiceRate;
    
    /**
     * 支付类型
     */
    private String payType;

    /**
     * 银行网关类型
     */
    private String bankGateWayType;
    
    /**
     * 网关号
     */
    private String gateWayNum;
    
    /**
     * 图标名称
     */
    private String imageName;
    
    
    /**
     * 支付名称
     */
    private String payWayName;
    
    /**
     * 排序号
     */
    private int sort;
    
    /**
     * 启用标示
     */
    private String activate;
    
    /**
     * 支付方式配置信息表ID
     */
    private String payConfigId;
    
    /**
     * 备注
     */
    private String comments;
    
    /**
     * 支付方式(0.通用支付方式；1.外接支付方式)
     */
    private String payAwayType;
    
    
    
    
    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getBankGateWayType() {
        return bankGateWayType;
    }

    public void setBankGateWayType(String bankGateWayType) {
        this.bankGateWayType = bankGateWayType;
    }

    public String getGateWayNum() {
        return gateWayNum;
    }

    public void setGateWayNum(String gateWayNum) {
        this.gateWayNum = gateWayNum;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }



    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getPayAwayType() {
        return payAwayType;
    }

    public void setPayAwayType(String payAwayType) {
        this.payAwayType = payAwayType;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public double getPayServiceRate() {
        return payServiceRate;
    }

    public void setPayServiceRate(double payServiceRate) {
        this.payServiceRate = payServiceRate;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getPayConfigId() {
        return payConfigId;
    }

    public void setPayConfigId(String payConfigId) {
        this.payConfigId = payConfigId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
