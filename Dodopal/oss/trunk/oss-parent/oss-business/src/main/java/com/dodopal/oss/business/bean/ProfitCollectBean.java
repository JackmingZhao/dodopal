package com.dodopal.oss.business.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.model.BaseEntity;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2015年10月27日 下午8:07:50 
  * @version 1.0 
  * @parameter    
  */
public class ProfitCollectBean extends BaseEntity{
	private static final long serialVersionUID = 8432827107410870963L;
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
	public String getCollectDateFmt() {
		String yyyy = this.getCollectDate().substring(0, 4);
		String MM = this.getCollectDate().substring(4, 6);
		String dd = this.getCollectDate().substring(6);
		return yyyy+"-"+MM+"-"+dd;
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
	public String getBussinessTypeView() {
	    if (StringUtils.isBlank(this.bussinessType)) {
            return null;
        }
	    if(null==RateCodeEnum.getRateTypeByCode(this.bussinessType)){
	    	return null;
	    }else{
	    	return RateCodeEnum.getRateTypeByCode(this.bussinessType).getName();
	    }
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
	
	/**
	 * matianzuo
	 */
	public String getProfitPercent() {
		return (this.profit/100.0)+"";
	}
	/**
	 * matianzuo	
	 */
	public String getTradeAmountPercent() {
		return (this.tradeAmount/100.0)+"";
	}
}
