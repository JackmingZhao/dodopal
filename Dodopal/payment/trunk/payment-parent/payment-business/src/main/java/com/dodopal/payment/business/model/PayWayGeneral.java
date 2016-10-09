package com.dodopal.payment.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月24日 上午9:51:18
 * 通用支付方式
 */
public class PayWayGeneral extends BaseEntity   {

    private static final long serialVersionUID = 865660164556326247L;
    
    /**
     * 支付服务费率
     */
    private double payServiceRate;
    
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
     * 银行网关类型
     */
    private String bankGateWayType;
    /**
     * 支付名称
     */
    private String payWayName;
    /**
     * 支付类型
     */
    private String payType;
    
    /**
     * 图标名称
     */
    private String imageName;

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

    public String getBankGateWayType() {
        return bankGateWayType;
    }

    public void setBankGateWayType(String bankGateWayType) {
        this.bankGateWayType = bankGateWayType;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    
}
