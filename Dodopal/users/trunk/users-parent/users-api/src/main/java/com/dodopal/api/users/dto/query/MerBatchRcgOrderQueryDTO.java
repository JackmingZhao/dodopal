package com.dodopal.api.users.dto.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

/**
 * 批次单查询条件
 * 
 * @author lifeng@dodopal.com
 */

public class MerBatchRcgOrderQueryDTO extends QueryBase {
	private static final long serialVersionUID = -2389833263678107855L;
	/* 商户号(必传) */
	private String merCode;
	/* 登录用户ID(必传) */
	private String userId;
	/* 批次单ID */
	private String batchRcgOrderId;
	/* 开始日期 */
	private Date sDate;
	/* 结束日期 */
	private Date eDate;

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBatchRcgOrderId() {
		return batchRcgOrderId;
	}

	public void setBatchRcgOrderId(String batchRcgOrderId) {
		this.batchRcgOrderId = batchRcgOrderId;
	}

	public Date getsDate() {
		return sDate;
	}

	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}

	public Date geteDate() {
		return eDate;
	}

	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}

}