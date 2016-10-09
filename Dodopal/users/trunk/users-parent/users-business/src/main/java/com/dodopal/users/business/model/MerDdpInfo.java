package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * 类说明 :商户补充信息
 * 
 * @author lifeng
 */

public class MerDdpInfo extends BaseEntity {
	private static final long serialVersionUID = -8336420437785213724L;
	/* 启用标示 */
	public String activate;
	/* 商户编码 */
	private String merCode;
	/* 都都宝固定IP */
	private String merDdpLinkIp;
	/* 都都宝联系人 */
	private String merDdpLinkUser;
	/* 都都宝联系人电话 */
	private String merDdpLinkUserMobile;
	/* 结算类型 */
	private String settlementType;
	/* 结算阀值 */
	private Long settlementThreshold;
	/* 是否自动分配额度,0是，1否，默认否 */
	private String isAutoDistribute;
	/* 商圈 */
	private String tradeArea;
	/* 0：自己购买，1：上级分配；默认自己购买 */
	private String limitSource;

	public String getActivate() {
		return activate;
	}

	public void setActivate(String activate) {
		this.activate = activate;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getMerDdpLinkIp() {
		return merDdpLinkIp;
	}

	public void setMerDdpLinkIp(String merDdpLinkIp) {
		this.merDdpLinkIp = merDdpLinkIp;
	}

	public String getMerDdpLinkUser() {
		return merDdpLinkUser;
	}

	public void setMerDdpLinkUser(String merDdpLinkUser) {
		this.merDdpLinkUser = merDdpLinkUser;
	}

	public String getMerDdpLinkUserMobile() {
		return merDdpLinkUserMobile;
	}

	public void setMerDdpLinkUserMobile(String merDdpLinkUserMobile) {
		this.merDdpLinkUserMobile = merDdpLinkUserMobile;
	}

	public String getSettlementType() {
		return settlementType;
	}

	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}

	public Long getSettlementThreshold() {
		return settlementThreshold;
	}

	public void setSettlementThreshold(Long settlementThreshold) {
		this.settlementThreshold = settlementThreshold;
	}

	public String getIsAutoDistribute() {
		return isAutoDistribute;
	}

	public void setIsAutoDistribute(String isAutoDistribute) {
		this.isAutoDistribute = isAutoDistribute;
	}

	public String getTradeArea() {
		return tradeArea;
	}

	public void setTradeArea(String tradeArea) {
		this.tradeArea = tradeArea;
	}

	public String getLimitSource() {
		return limitSource;
	}

	public void setLimitSource(String limitSource) {
		this.limitSource = limitSource;
	}

}
