package com.dodopal.portal.business.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.RateTypeEnum;
import com.dodopal.common.model.BaseEntity;

/**
 * 分润明细
 * @author hxc
 *
 */
public class ProfitDetailsBean extends BaseEntity{

	private static final long serialVersionUID = 2936548632869919270L;

	/**
	 * 订单交易号
	 */
	private String orderNo;
	
	/**
	 * 订单时间
	 */
	private Date orderTime;
	
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
	 * 商户类型
	 */
	private String customerType;
	
	/**
	 * 来源
	 */
	private String source;
	
	/**
	 * 是否圈存
	 */
	private String iscircle;
	
	/**
	 * 商户业务费率类型
	 */
	private String customerRateType;
	
	/**
	 * 商户业务费率
	 */
	private Long customerRate;

	/**
	 * 订单金额
	 */
	private Long orderAmount;
	
	/**
	 * 商户应得分润
	 */
	private Long customerShouldProfit;
	
	/**
	 * 商户实际分润
	 */
	private Long customerRealProfit;

	/**
	 * 下级商户编号
	 */
	private String subCustomerCode;

	/**
	 * 下级商户名称
	 */
	private String subCustomerName;
	
	/**
	 * 下级商户应得分润
	 */
	private Long subCustomerShouldProfit;
	
	/**
	 * 与客户结算时间
	 */
	private Date customerSettlementTime;
	
	/**
	 * 分润时间
	 */
	private String profitTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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

	public String getCustomerType() {
		
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIscircle() {
		return iscircle;
	}

	public void setIscircle(String iscircle) {
		this.iscircle = iscircle;
	}

	public String getCustomerRateType() {
		if(StringUtils.isBlank(this.customerRateType)){
			return null;
		}
		return RateTypeEnum.getRateTypeByCode(this.customerRateType).getName();
		//return customerRateType;
	}

	public void setCustomerRateType(String customerRateType) {
		this.customerRateType = customerRateType;
	}

	public Long getCustomerRate() {
		return customerRate;
	}

	public void setCustomerRate(Long customerRate) {
		this.customerRate = customerRate;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getCustomerShouldProfit() {
		return customerShouldProfit;
	}

	public void setCustomerShouldProfit(Long customerShouldProfit) {
		this.customerShouldProfit = customerShouldProfit;
	}

	public Long getCustomerRealProfit() {
		return customerRealProfit;
	}

	public void setCustomerRealProfit(Long customerRealProfit) {
		this.customerRealProfit = customerRealProfit;
	}

	public String getSubCustomerCode() {
		return subCustomerCode;
	}

	public void setSubCustomerCode(String subCustomerCode) {
		this.subCustomerCode = subCustomerCode;
	}

	public String getSubCustomerName() {
		return subCustomerName;
	}

	public void setSubCustomerName(String subCustomerName) {
		this.subCustomerName = subCustomerName;
	}

	public Long getSubCustomerShouldProfit() {
		return subCustomerShouldProfit;
	}

	public void setSubCustomerShouldProfit(Long subCustomerShouldProfit) {
		this.subCustomerShouldProfit = subCustomerShouldProfit;
	}

	public Date getCustomerSettlementTime() {
		return customerSettlementTime;
	}

	public void setCustomerSettlementTime(Date customerSettlementTime) {
		this.customerSettlementTime = customerSettlementTime;
	}

	public String getProfitTime() {
		return profitTime;
	}

	public void setProfitTime(String profitTime) {
		this.profitTime = profitTime;
	}
	
}
