package com.dodopal.portal.business.bean;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.BaseEntity;

public class RechargeStatisticsYktBean extends BaseEntity {

	private static final long serialVersionUID = 7738671217922533033L;
	
	//pos号
	private String proCode;
	//启用标识
	private String bind;
	//商户名称
	private String merName;
	//充值交易笔数
	private long rechargeCount;
	//充值交易金额
	private double rechargeAmt;
	
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getBind() {
		if (StringUtils.isBlank(this.bind)) {
            return null;
        }
       return ActivateEnum.getActivateByCode(this.bind)!=null ? ActivateEnum.getActivateByCode(this.bind).getName():"";
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
	public double getRechargeAmt() {
		return rechargeAmt;
	}
	public void setRechargeAmt(double rechargeAmt) {
		this.rechargeAmt = rechargeAmt;
	}    
	
}
