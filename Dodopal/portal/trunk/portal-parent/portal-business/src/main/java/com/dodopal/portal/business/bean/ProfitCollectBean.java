package com.dodopal.portal.business.bean;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.model.BaseEntity;

public class ProfitCollectBean extends BaseEntity{

	private static final long serialVersionUID = -3557639520975454296L;

	/**
	 * 汇总日期
	 */
	private String collectDate;
	/**
	 * 汇总时间
	 */
	private String collectTime;
	
	/**
	 * 商户编号
	 */
	private String customerCode;
	
	/**
	 * 商户名称
	 */
	private String customerName;
	
	/**
	 * 业务类型
	 */
	private String bussinessType;
	
	/**
	 * 交易笔数
	 */
	private Long tradeCount;
	
	/**
	 * 交易金额
	 */
	private Double tradeAmount;
	
	/**
	 * 分润
	 */
	private Double profit;

	public String getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
	}

	public String getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
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
		if(StringUtils.isBlank(this.bussinessType)){
			return null;
		}
		return RateCodeEnum.getRateTypeByCode(this.bussinessType).getName();
	}

	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}

	public Long getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(Long tradeCount) {
		this.tradeCount = tradeCount;
	}

	public Double getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}
	
}
