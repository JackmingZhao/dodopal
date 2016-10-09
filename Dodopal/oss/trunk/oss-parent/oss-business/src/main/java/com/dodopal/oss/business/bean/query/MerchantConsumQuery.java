package com.dodopal.oss.business.bean.query;

import com.dodopal.common.model.QueryBase;
/**
 * 数据中心__财务报表__商户消费明细报表
 * @author 
 * @version 
 */
public class MerchantConsumQuery extends QueryBase{

	private static final long serialVersionUID = 1L;
	private String merName;
	private String yktName;
	private String yktCode;
	private String bussinessType;
	private String startDate;
	private String endDate;
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getYktName() {
		return yktName;
	}
	public void setYktName(String yktName) {
		this.yktName = yktName;
	}
	public String getBussinessType() {
		return bussinessType;
	}
	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getYktCode() {
		return yktCode;
	}
	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}


	
}
