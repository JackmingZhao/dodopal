package com.dodopal.api.payment.dto;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * @author DingKuiyuan@dodopal.com
 * 支付方式配置信息
 * @version 创建时间：2015年7月24日 上午9:50:47
 */
public class PayConfigDTO extends BaseEntity  {
    private static final long serialVersionUID = 8728425333571524198L;

    /**
     * 支付类型编码
     */
    private String payType;
    
    /**
     * 支付类型名称
     */
    private String payTypeName;
    
    /**
     * 支付渠道标示
     */
    private String payChannelMark;
    
    /**
     * 银行网关类型
     */
    private String bankGatewayType;
    
    /**
     * 银行网关类型
     */
    private String bankGatewayName;
    
    /**
     * 支付方式名称
     */
    private String payWayName;
    
    /**
     * 第三方账户号
     */
    private String anotherAccountCode;
    
    /**
     *  私钥
     */
    private String payKey;
    
    /**
     * 手续费率
     */
    private double proceRate;
    
    /**
     * 后手续费率
     */
    private double afterProceRate;
    
    /**
     * 后费率生效时间
     */
    private Date afterProceRateDate;
    
    /**
     * 默认银行
     */
    private String defaultBank;
    
    /**
     * 图标名称
     */
    private String imageName;
    
    /**
     * 提供给外接商户的网关号
     */
    private String gatewayNumber;
    
    /**
     * 启用标志
     */
    private String activate;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public String getPayChannelMark() {
        return payChannelMark;
    }

    public void setPayChannelMark(String payChannelMark) {
        this.payChannelMark = payChannelMark;
    }

    public String getBankGatewayType() {
        return bankGatewayType;
    }

    public void setBankGatewayType(String bankGatewayType) {
        this.bankGatewayType = bankGatewayType;
    }
    
    public String getBankGatewayName() {
		return bankGatewayName;
	}

	public void setBankGatewayName(String bankGatewayName) {
		this.bankGatewayName = bankGatewayName;
	}

	public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getAnotherAccountCode() {
        return anotherAccountCode;
    }

    public void setAnotherAccountCode(String anotherAccountCode) {
        this.anotherAccountCode = anotherAccountCode;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public double getProceRate() {
        return proceRate;
    }

    public void setProceRate(double proceRate) {
        this.proceRate = proceRate;
    }


    public double getAfterProceRate() {
        return afterProceRate;
    }

    public void setAfterProceRate(double afterProceRate) {
        this.afterProceRate = afterProceRate;
    }

    public Date getAfterProceRateDate() {
        return afterProceRateDate;
    }

    public void setAfterProceRateDate(Date afterProceRateDate) {
        this.afterProceRateDate = afterProceRateDate;
    }

    public String getDefaultBank() {
        return defaultBank;
    }

    public void setDefaultBank(String defaultBank) {
        this.defaultBank = defaultBank;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getGatewayNumber() {
        return gatewayNumber;
    }

    public void setGatewayNumber(String gatewayNumber) {
        this.gatewayNumber = gatewayNumber;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }
}
