package com.dodopal.oss.business.model;

import com.dodopal.common.model.BaseEntity;

public class CrdSysLog extends BaseEntity  {
	private static final long serialVersionUID = 6094601459027499694L;
	//服务名称
	private String serverName;
	//卡服务订单号
	private String crdOrderNum;
	//产品库订单号
	private String proOrderNum;
	//交易状态(0:成功;1:失败)
	private Integer tradeType;
	//类名称
	private String	className;
	//方法名称
	private String	methodName;
	//交易类型 1-充值,2-消费
	private Integer txnType;
	//开始时间:格式(YYYYMMDDhhmmss)
	private long tradeStart;
	//结束时间:格式(YYYYMMDDhhmmss)
	private long tradeEnd;
	//操作时长(毫秒)
	private long tradeTrack;
	//入参
	private String inParas;
	//入参长度
	private long inParaLength;
	//出参
	private String outParas;
	//响应码
	private String respCode;
	//响应码描述
	private String respExplain;
	//方法描述
	private String description;
	//结束日期
	private String endDate;
	
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getCrdOrderNum() {
		return crdOrderNum;
	}
	public void setCrdOrderNum(String crdOrderNum) {
		this.crdOrderNum = crdOrderNum;
	}
	public String getProOrderNum() {
		return proOrderNum;
	}
	public void setProOrderNum(String proOrderNum) {
		this.proOrderNum = proOrderNum;
	}
	public int getTradeType() {
		return tradeType;
	}
	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public Integer getTxnType() {
		return txnType;
	}
	public void setTxnType(Integer txnType) {
		this.txnType = txnType;
	}
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
	public long getTradeStart() {
		return tradeStart;
	}
	public void setTradeStart(long tradeStart) {
		this.tradeStart = tradeStart;
	}
	public long getTradeEnd() {
		return tradeEnd;
	}
	public void setTradeEnd(long tradeEnd) {
		this.tradeEnd = tradeEnd;
	}
	public long getTradeTrack() {
		return tradeTrack;
	}
	public void setTradeTrack(long tradeTrack) {
		this.tradeTrack = tradeTrack;
	}
	public String getInParas() {
		return inParas;
	}
	public void setInParas(String inParas) {
		this.inParas = inParas;
	}
	public long getInParaLength() {
		return inParaLength;
	}
	public void setInParaLength(long inParaLength) {
		this.inParaLength = inParaLength;
	}
	public String getOutParas() {
		return outParas;
	}
	public void setOutParas(String outParas) {
		this.outParas = outParas;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespExplain() {
		return respExplain;
	}
	public void setRespExplain(String respExplain) {
		this.respExplain = respExplain;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
