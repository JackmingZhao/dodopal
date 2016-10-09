package com.dodopal.product.business.model;

import com.dodopal.common.model.BaseEntity;
/**
 * 一卡通充值统计
 * @author lenovo
 *
 */
public class RechargeStatisticsYktOrder extends BaseEntity{

	private static final long serialVersionUID = 6856071914329314800L;
	//pos号
	private String proCode;
	//启用标识
	private String bind;
	//商户名称
	private String merName;
	//充值交易笔数
	private long rechargeCount;
	//充值交易金额
	private long rechargeAmt;
	
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getBind() {
		return bind;
	}
	public void setBind(String bind) {
		this.bind = bind;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public long getRechargeCount() {
		return rechargeCount;
	}
	public void setRechargeCount(long rechargeCount) {
		this.rechargeCount = rechargeCount;
	}
	public long getRechargeAmt() {
		return rechargeAmt;
	}
	public void setRechargeAmt(long rechargeAmt) {
		this.rechargeAmt = rechargeAmt;
	}
	
}
