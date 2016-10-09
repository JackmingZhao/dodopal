package com.dodopal.oss.business.model.dto;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class LogDLogQuery extends QueryBase{
	private static final long serialVersionUID = 8000987045134039708L;
	//产品库主订单号
	private String dlogPrdorderNum;
	//卡号
	private String dlogTradeCard;
	//创建时间(开始)
    private Date createDateStart;
    //创建时间(结束)
    private Date createDateEnd;
    
	public String getDlogPrdorderNum() {
		return dlogPrdorderNum;
	}
	public void setDlogPrdorderNum(String dlogPrdorderNum) {
		this.dlogPrdorderNum = dlogPrdorderNum;
	}
	public String getDlogTradeCard() {
		return dlogTradeCard;
	}
	public void setDlogTradeCard(String dlogTradeCard) {
		this.dlogTradeCard = dlogTradeCard;
	}
	public Date getCreateDateStart() {
		return createDateStart;
	}
	public void setCreateDateStart(Date createDateStart) {
		this.createDateStart = createDateStart;
	}
	public Date getCreateDateEnd() {
		return createDateEnd;
	}
	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}
	
}
