package com.dodopal.oss.business.bean;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.util.DateUtils;

public class MerchantConsumBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private String orderDate;
	private String businessType = "一卡通消费";
	private String customerType;
	private String customerCode;
	private String customerName;
	private String yktName;
	private String source;
	private String receivedPrice;
	
	public String getOrderDate() {
		return DateUtils.dateToString(DateUtils.stringtoDate(orderDate, DateUtils.DATE_SMALL_STR),DateUtils.DATE_SMALL_STR);
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getCustomerType() {
		if (StringUtils.isBlank(this.customerType)) {
			return "";
		} else if (MerTypeEnum.getMerTypeByCode(this.customerType) == null) {
			return "";
		} else {
			return MerTypeEnum.getMerTypeByCode(this.customerType).getName();
		}
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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
	public String getYktName() {
		return yktName;
	}
	public void setYktName(String yktName) {
		this.yktName = yktName;
	}
	public String getSource() {
		if (StringUtils.isBlank(this.source)) {
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
	public String getReceivedPrice() {
		return String.format("%.2f", Double.valueOf(receivedPrice) / 100);
	}
	public void setReceivedPrice(String receivedPrice) {
		this.receivedPrice = receivedPrice;
	}

}
