package com.dodopal.api.payment.dto;

import com.dodopal.common.model.BaseEntity;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月24日 上午9:56:45
 */
public class PayWayCommonDTO  extends BaseEntity  {
    private static final long serialVersionUID = 8718212466207562356L;

    /**
     * 用户/商户 编号
     */
    private String userCode;
    
    /**
     * 用户/商户 名称
     */
    private String userName;
    
    /**
     * 支付名称
     */
    private String payWayName;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 支付服务费率
     */
    private double payServiceRate;
    
    /**
     * 启用标示
     */
    private String activate;
    /**
     * 支付方式图标名称
     */
    private String payLogo;
    

    /**
     * 用户类型 
     */
    private String userType;
    
    /**
     * 支付方式分类		
     */
    private String payWayKind;
    
    /**
     * 支付方式ID		
     */
    private String payWayId;
    
    public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPayWayKind() {
		return payWayKind;
	}

	public void setPayWayKind(String payWayKind) {
		this.payWayKind = payWayKind;
	}

	public String getPayWayId() {
		return payWayId;
	}

	public void setPayWayId(String payWayId) {
		this.payWayId = payWayId;
	}

	public String getPayLogo() {
		return payLogo;
	}

	public void setPayLogo(String payLogo) {
		this.payLogo = payLogo;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public double getPayServiceRate() {
        return payServiceRate;
    }

    public void setPayServiceRate(double payServiceRate) {
        this.payServiceRate = payServiceRate;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }
    
}
