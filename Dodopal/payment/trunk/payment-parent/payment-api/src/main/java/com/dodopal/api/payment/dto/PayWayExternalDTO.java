package com.dodopal.api.payment.dto;

import com.dodopal.common.model.BaseEntity;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月24日 上午9:53:28
 * 支付流水信息
 */
public class PayWayExternalDTO extends BaseEntity   {
    private static final long serialVersionUID = 3598855251520534303L;

    /**
     * 商户号
     */
    private String merCode;
    
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
