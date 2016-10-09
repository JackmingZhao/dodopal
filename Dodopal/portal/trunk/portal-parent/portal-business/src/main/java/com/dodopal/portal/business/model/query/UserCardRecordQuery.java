package com.dodopal.portal.business.model.query;

import com.dodopal.common.model.QueryBase;

public class UserCardRecordQuery extends QueryBase {

	private static final long serialVersionUID = 5508485771613343955L;

	private String startDate; 	// 查询开始日期
	private String endDate; 	// 查询结束日期
	private String userId; 		// 客户号
	private String userName;	// 用户名
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
