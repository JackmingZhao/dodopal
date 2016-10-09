package com.dodopal.api.users.dto;

import java.io.Serializable;
import java.util.Date;

public class UserCardRecordDTO implements Serializable{
	private static final long serialVersionUID = 1417272494682903799L;
	private Date orderDate; 	// 交易时间
	private Double txnAmt; 		// 交易金额
	private String merName; 	// 商户名称
	private Double befBal; 		// 交易前卡余额
	private Double blackAmt; 		// 交易后卡余额
	private String cardFaceNo; 	// 卡号
	
	private String userId;		// 用户号
	private String type;		// 类型   CZ: 充值; XF: 消费
	private String orderNum;	// 订单号
	private String status;		// 交易状态
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Double getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(Double txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public Double getBefBal() {
		return befBal;
	}
	public void setBefBal(Double befBal) {
		this.befBal = befBal;
	}
	public Double getBlackAmt() {
		return blackAmt;
	}
	public void setBlackAmt(Double blackAmt) {
		this.blackAmt = blackAmt;
	}
	public String getCardFaceNo() {
		return cardFaceNo;
	}
	public void setCardFaceNo(String cardFaceNo) {
		this.cardFaceNo = cardFaceNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	

}
