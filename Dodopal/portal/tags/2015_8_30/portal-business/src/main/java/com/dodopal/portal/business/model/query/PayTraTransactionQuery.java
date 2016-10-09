package com.dodopal.portal.business.model.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class PayTraTransactionQuery extends QueryBase{

	private static final long serialVersionUID = -6657270139162747753L;

	/**
     * 用户名称/商户名称
     */
    private String merOrUserName;
    
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
     * 创建时间结束
     */
    private String createDateEnd;
    
    /**
     * 创建时间开始
     */
    private String createDateStart;
    
    /**
     * 最小金额
     */
    private String realMinTranMoney;
    
    /**
     * 最大金额
     */
    private String realMaxTranMoney;
    
    /**
     * 交易名称
     */
    private String tranName;
    
    /**
     * 交易类型
     * 1：账户充值、3：账户消费、5：退款、7：转出、9：转入11：正调账13：反调账
     */
    private String tranType;

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

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public String getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getRealMinTranMoney() {
		return realMinTranMoney;
	}

	public void setRealMinTranMoney(String realMinTranMoney) {
		this.realMinTranMoney = realMinTranMoney;
	}

	public String getRealMaxTranMoney() {
		return realMaxTranMoney;
	}

	public void setRealMaxTranMoney(String realMaxTranMoney) {
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
    
}
