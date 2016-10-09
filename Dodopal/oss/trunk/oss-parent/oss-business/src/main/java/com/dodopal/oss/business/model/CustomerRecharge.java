package com.dodopal.oss.business.model;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.util.DateUtils;

/**
 * 客户充值明细统计导出实体
 * 
 * @author
 *
 */
public class CustomerRecharge {
	// 交易时间
	private String prdOrderDate;
	// 城市
	private String cityName;
	// 订单类型
	private String orderType = "充值";
	// 客户编码
	private String merCode;
	// 客户名称
	private String merName;
	// 客户类型
	private String merUerType;
	// 充值终端类型
	private String source;
	// 充值笔数
	private String rechargeCount;
	// 充值额度
	private String receivedPrice;

	public String getPrdOrderDate() {
		return DateUtils.dateToString(DateUtils.stringtoDate(prdOrderDate, DateUtils.DATE_SMALL_STR),
				DateUtils.DATE_SMALL_STR);
	}

	public void setPrdOrderDate(String prdOrderDate) {
		this.prdOrderDate = prdOrderDate;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getMerUerType() {
		if (StringUtils.isBlank(this.merUerType)) {
			return "";
		} else if (MerTypeEnum.getMerTypeByCode(this.merUerType) == null) {
			return "";
		} else {
			return MerTypeEnum.getMerTypeByCode(this.merUerType).getName();
		}
	}

	public void setMerUerType(String merUerType) {
		this.merUerType = merUerType;
	}

	public String getSource() {
		if (StringUtils.isBlank(this.merUerType)) {
			return "";
		} else if (SourceEnum.getSourceByCode(this.source) == null) {
			return "";
		} else {
			return SourceEnum.getSourceByCode(this.source).getName();
		}
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRechargeCount() {
		return rechargeCount;
	}

	public void setRechargeCount(String rechargeCount) {
		this.rechargeCount = rechargeCount;
	}

	public String getReceivedPrice() {
		if(StringUtils.isNotBlank(receivedPrice)){
			return String.format("%.2f", Double.valueOf(receivedPrice) / 100);
		}else{
			return "";
		}
	}

	public void setReceivedPrice(String receivedPrice) {
		this.receivedPrice = receivedPrice;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
