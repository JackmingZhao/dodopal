package com.dodopal.users.business.model.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;
/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2015年10月26日 下午1:52:55 
  * @version 1.0 
  * @parameter  
  * @since  
  * @return  
  */
public class ProfitQuery extends QueryBase{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 开始时间
	 */
	private Date startDate;
	
	/**
	 * 结束时间
	 */
	private Date endDate;
	
	/**
	 * 商户号
	 */
	private String merCode;
	
	/**
	 * 商户名称
	 */
	private String merName;

	/**
	 * 汇总日期
	 */
	private String collectDate;
	
	
	public String getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
}
