package com.dodopal.oss.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

public class LogInfo extends BaseEntity {
	private static final long serialVersionUID = -3413902443343241049L;
	//服务名称：子系统名称
	private String serverName;
	//日期
	private Date logDate;
	//线程名
	private String threadName;
	//优先级
	private String logLevel;
	//类名
	private String className;
	//异常堆栈
	private String stackTrace;
	//消息体
	private String message;
	//响应码
	private String repCode;
	//响应码描述
	private String repMessage;
	//唯一标识(UUID):串联各个系统
	private String uniqueCode;
	//入参
	private String inParas;
	//出参
	private String outParas;
	//订单号
	private String orderNum;
	//交易流水号
	private String tranNum;
	//方法
	private String methodName;
	//方法描述
	private String methodDes;
	//ip地址
	private String ipAddr;
	//交易状态(0:成功;1:失败)
	private Integer tradeType;
	//开始时间:格式(YYYYMMDDhhmmss)
	private long tradeStart;
	//结束时间:格式(YYYYMMDDhhmmss)
	private long tradeEnd;
	//操作时长(毫秒)
	private long tradeTrack;
	//来源
	private String source;
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRepCode() {
		return repCode;
	}
	public void setRepCode(String repCode) {
		this.repCode = repCode;
	}
	public String getRepMessage() {
		return repMessage;
	}
	public void setRepMessage(String repMessage) {
		this.repMessage = repMessage;
	}
	public String getUniqueCode() {
		return uniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	public String getInParas() {
		return inParas;
	}
	public void setInParas(String inParas) {
		this.inParas = inParas;
	}
	public String getOutParas() {
		return outParas;
	}
	public void setOutParas(String outParas) {
		this.outParas = outParas;
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
	public void setTranNum(String tranNum) {
		this.tranNum = tranNum;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMethodDes() {
		return methodDes;
	}
	public void setMethodDes(String methodDes) {
		this.methodDes = methodDes;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	
	public Integer getTradeType() {
        return tradeType;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}
