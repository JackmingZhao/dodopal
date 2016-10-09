package com.dodopal.users.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;
/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2015年10月26日 下午4:02:38 
  * @version 1.0 
  * 分润汇总表
  */
public class ProfitCollect extends BaseEntity {
	private static final long serialVersionUID = -1887696940151362642L;
	
	/**
	 * 汇总日期
	 */
	private String collectDate;
	/**
	 * 汇总时间
	 */
	private Date collectTime;
	
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
	private Long tradeAmount;
	
	/**
	 * 分润
	 */
	private Long profit;

	public String getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
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
		return bussinessType;
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

	public Long getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Long tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public Long getProfit() {
		return profit;
	}

	public void setProfit(Long profit) {
		this.profit = profit;
	}
	
}
