package com.dodopal.api.users.dto;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * @author lifeng@dodopal.com
 */

public class MerBatchRechargeItemDTO extends BaseEntity {
	private static final long serialVersionUID = -7671230325097335990L;
	/* 批次单号 */
	private String batchOrderId;
	/* 人员ID */
	private String groupUserId;
	/* 产品库订单号 */
	private String prdOrderId;
	/* 充值金额 */
	private Long rechargeAmt;
	/* 卡号 */
	private String cardCode;
	/* 状态 */
	private String status;
	/* 备注 */
	private String remark;

	// ----------扩展属性
	/** 员工姓名 */
	private String gpUserName;
	/** 部门名称 */
	private String depName;
	/** 卡类型 */
	private String cardType;
	/** 充值时间 */
	private Date proOrderDate;

	public String getBatchOrderId() {
		return batchOrderId;
	}

	public void setBatchOrderId(String batchOrderId) {
		this.batchOrderId = batchOrderId;
	}

	public String getGroupUserId() {
		return groupUserId;
	}

	public void setGroupUserId(String groupUserId) {
		this.groupUserId = groupUserId;
	}

	public String getPrdOrderId() {
		return prdOrderId;
	}

	public void setPrdOrderId(String prdOrderId) {
		this.prdOrderId = prdOrderId;
	}

	public Long getRechargeAmt() {
		return rechargeAmt;
	}

	public void setRechargeAmt(Long rechargeAmt) {
		this.rechargeAmt = rechargeAmt;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGpUserName() {
		return gpUserName;
	}

	public void setGpUserName(String gpUserName) {
		this.gpUserName = gpUserName;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Date getProOrderDate() {
		return proOrderDate;
	}

	public void setProOrderDate(Date proOrderDate) {
		this.proOrderDate = proOrderDate;
	}

}
