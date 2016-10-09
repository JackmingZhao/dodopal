package com.dodopal.oss.business.model;

public class CrdConsumeOrderExption {
	// 订单状态
	private String states;
	// 操作人
	private String userName;
	// 订单号
	private String orderNo;
	// 客户名称
	private String customerNo;
	// 内部状态
	private String innerStates;
	// 主订单备注
	private String comments;

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getInnerStates() {
		return innerStates;
	}

	public void setInnerStates(String innerStates) {
		this.innerStates = innerStates;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
