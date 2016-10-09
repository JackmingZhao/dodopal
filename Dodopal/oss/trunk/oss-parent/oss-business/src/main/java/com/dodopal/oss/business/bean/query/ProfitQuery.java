package com.dodopal.oss.business.bean.query;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.dodopal.common.model.QueryBase;
import com.dodopal.oss.business.util.DateJsonDeserializer;

public class ProfitQuery extends QueryBase{
	private static final long serialVersionUID = 3092403903659665885L;

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
	@JsonDeserialize(using = DateJsonDeserializer.class)
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	@JsonDeserialize(using = DateJsonDeserializer.class)
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
