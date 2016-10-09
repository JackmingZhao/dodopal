package com.dodopal.api.payment.dto;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年8月14日 下午1:43:19
 */
public class PayTranDTO extends BaseEntity {
    private static final long serialVersionUID = 1527215487898584029L;
    
    /**
     * 用户编号 商户号/个人用户号
     */
    private String cusTomerCode;
    /**
     * 0：个人1：商户
     * 用户类型
     */
    private String  cusTomerType;
    
    /**
     * 订单号
     */
    private String orderCode;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 商品名称
     */
    private String goodsName;
    
    /**
     * 来源
     */
    private String source;
    
    /**
     * 订单时间
     */
    private Date orderDate;
    
    /**
     * 城市编码
     */
    private String cityCode;
    
    /**
     * 外接商户标识
     * True：外接商户
     * False：非外接商户（包括个人用户）
     */
    private boolean extFlg;

    /**
     * 交易金额
     */
    private double amount;
    
    /**
     * 操作员
     */
    private String operatorId;
    
    /**
     * 支付方式ID
     */
    private String payId;
    
    /**
     * 交易类型
     */
    private String tranType;
    
    /**
     * notify_url
     * @return 
     */
    private String notifyUrl;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isExtFlg() {
        return extFlg;
    }

    public void setExtFlg(boolean extFlg) {
        this.extFlg = extFlg;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCusTomerCode() {
        return cusTomerCode;
    }

    public void setCusTomerCode(String cusTomerCode) {
        this.cusTomerCode = cusTomerCode;
    }

    public String getCusTomerType() {
        return cusTomerType;
    }

    public void setCusTomerType(String cusTomerType) {
        this.cusTomerType = cusTomerType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	
}
