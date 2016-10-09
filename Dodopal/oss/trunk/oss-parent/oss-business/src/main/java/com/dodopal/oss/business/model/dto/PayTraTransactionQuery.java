package com.dodopal.oss.business.model.dto;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年7月23日 下午1:50:15
 */
public class PayTraTransactionQuery extends QueryBase{
    private static final long serialVersionUID = -935589797172195781L;

    /**
     * 用户名称/商户名称
     */
    private String merOrUserName;
    
    /**
     *  商户编号
     */
    private String merCode;
    
    /**
     * 交易流水号
     */
    private String tranCode;
    
    
    /**
     * 订单号
     */
    private String orderNumber;
    
    
    /**
     * 业务类型代码
     */
    private String businessType;
    
    /**
     * 外部交易状态
     */
    private String tranOutStatus;
    
    /**
     * 内部交易状态
     */
    private String tranInStatus;
    
    /**
     * 时间结束
     */
    private Date createDateEnd;
    
    /**
     * 时间开始
     */
    private Date createDateStart;

    /**
     * 最小金额
     */
    private long realMinTranMoney;
    
    /**
     * 最大金额
     */
    private long realMaxTranMoney;
    
    /**
     * 交易名称
     */
    private String tranName;
    
    /**
     * 交易类型
     * 1：账户充值、3：账户消费、5：退款、7：转出、9：转入11：正调账13：反调账
     */
    private String tranType;
    
    /**
     * 所属上级商户编码
     */
    private String merParentCode;
    
    /**
     * 支付方式
     */
    private String payWay;
    
    /**
     * 用户类型
     */
    private String userType;
    
    /**
     * 当前用户ID
     */
    private String createUser;
    
    /**
     * 支付类型
     */
    private String payType;

    
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
    
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getTranInStatus() {
        return tranInStatus;
    }

    public void setTranInStatus(String tranInStatus) {
        this.tranInStatus = tranInStatus;
    }

    public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getMerParentCode() {
		return merParentCode;
	}

	public void setMerParentCode(String merParentCode) {
		this.merParentCode = merParentCode;
	}

	public long getRealMinTranMoney() {
		return realMinTranMoney;
	}

	public void setRealMinTranMoney(long realMinTranMoney) {
		this.realMinTranMoney = realMinTranMoney;
	}

	public long getRealMaxTranMoney() {
		return realMaxTranMoney;
	}

	public void setRealMaxTranMoney(long realMaxTranMoney) {
		this.realMaxTranMoney = realMaxTranMoney;
	}

	public String getTranName() {
		return tranName;
	}

	public void setTranName(String tranName) {
		this.tranName = tranName;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getMerOrUserName() {
        return merOrUserName;
    }

    public void setMerOrUserName(String merOrUserName) {
        this.merOrUserName = merOrUserName;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getTranOutStatus() {
        return tranOutStatus;
    }

    public void setTranOutStatus(String tranOutStatus) {
        this.tranOutStatus = tranOutStatus;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

}
