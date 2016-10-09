package com.dodopal.api.users.facade;

import com.dodopal.common.model.QueryBase;

public class UserCardRecordQueryDTO extends QueryBase {

	private static final long serialVersionUID = 1143480368649259527L;

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
