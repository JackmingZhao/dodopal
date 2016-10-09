package com.dodopal.api.users.dto;

import java.util.List;

import com.dodopal.common.model.BaseEntity;

/**
 * @author lifeng@dodopal.com
 */

public class MerBatchRechargeOrderDTO extends BaseEntity {
	private static final long serialVersionUID = 2260335882727954694L;
	/* 商户编码 */
	String merCode;
	/* 状态 */
	String status;
	/* 备注 */
	String remark;
	/* 充值项列表 */
	List<MerBatchRechargeItemDTO> batchItemList;

	// --------------------扩展属性
	/** 充值总笔数 */
	Long totalCount;
	/** 充值总金额 */
	Long totalAmt;

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Long totalAmt) {
		this.totalAmt = totalAmt;
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

	public List<MerBatchRechargeItemDTO> getBatchItemList() {
		return batchItemList;
	}

	public void setBatchItemList(List<MerBatchRechargeItemDTO> batchItemList) {
		this.batchItemList = batchItemList;
	}

}
