package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

public class LogInfoQuery extends QueryBase{
	private static final long serialVersionUID = -6462324154734249089L;
	
	//服务名称：子系统名称
	private String serverName;
	//订单号
	private String orderNum;
	//交易流水号
	private String tranNum;
	//交易状态(0:成功;1:失败)
	private String tradeType;
	//来源
	private String source;
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getTranNum() {
		return tranNum;
	}
	
	public String getTradeType() {
        return tradeType;
    }
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
    public void setTranNum(String tranNum) {
		this.tranNum = tranNum;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}
