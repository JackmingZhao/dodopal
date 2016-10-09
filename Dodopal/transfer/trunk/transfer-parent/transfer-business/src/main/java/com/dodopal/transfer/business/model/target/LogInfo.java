package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the LOG_INFO database table.
 * 
 */
//@Entity
//@Table(name="LOG_INFO")
//@NamedQuery(name="LogInfo.findAll", query="SELECT l FROM LogInfo l")
public class LogInfo implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

	private String bak1;

	private String bak10;

	private String bak2;

	private String bak3;

	private String bak4;

	private String bak5;

	private String bak6;

	private String bak7;

	private String bak8;

	private String bak9;

//	@Column(name="CLASS_NAME")
	private String className;

//	@Column(name="IN_PARAS")
	private String inParas;

//	@Column(name="IP_ADDR")
	private String ipAddr;

//	@Temporal(TemporalType.DATE)
//	@Column(name="LOG_DATE")
	private Date logDate;

//	@Column(name="LOG_LEVEL")
	private String logLevel;

//	@Column(name="\"MESSAGE\"")
	private String message;

//	@Column(name="METHOD_DES")
	private String methodDes;

//	@Column(name="METHOD_NAME")
	private String methodName;

//	@Column(name="ORDER_NUM")
	private String orderNum;

//	@Column(name="OUT_PARAS")
	private String outParas;

//	@Column(name="REP_CODE")
	private String repCode;

//	@Column(name="REP_MESSAGE")
	private String repMessage;

//	@Column(name="SERVER_NAME")
	private String serverName;

	private String source;

//	@Column(name="STACK_TRACE")
	private String stackTrace;

//	@Column(name="THREAD_NAME")
	private String threadName;

//	@Column(name="TRADE_END")
	private BigDecimal tradeEnd;

//	@Column(name="TRADE_START")
	private BigDecimal tradeStart;

//	@Column(name="TRADE_TRACK")
	private BigDecimal tradeTrack;

//	@Column(name="TRADE_TYPE")
	private BigDecimal tradeType;

//	@Column(name="TRAN_NUM")
	private String tranNum;

//	@Column(name="UNIQUE_CODE")
	private String uniqueCode;

	public LogInfo() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBak1() {
		return this.bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak10() {
		return this.bak10;
	}

	public void setBak10(String bak10) {
		this.bak10 = bak10;
	}

	public String getBak2() {
		return this.bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	public String getBak3() {
		return this.bak3;
	}

	public void setBak3(String bak3) {
		this.bak3 = bak3;
	}

	public String getBak4() {
		return this.bak4;
	}

	public void setBak4(String bak4) {
		this.bak4 = bak4;
	}

	public String getBak5() {
		return this.bak5;
	}

	public void setBak5(String bak5) {
		this.bak5 = bak5;
	}

	public String getBak6() {
		return this.bak6;
	}

	public void setBak6(String bak6) {
		this.bak6 = bak6;
	}

	public String getBak7() {
		return this.bak7;
	}

	public void setBak7(String bak7) {
		this.bak7 = bak7;
	}

	public String getBak8() {
		return this.bak8;
	}

	public void setBak8(String bak8) {
		this.bak8 = bak8;
	}

	public String getBak9() {
		return this.bak9;
	}

	public void setBak9(String bak9) {
		this.bak9 = bak9;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getInParas() {
		return this.inParas;
	}

	public void setInParas(String inParas) {
		this.inParas = inParas;
	}

	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public Date getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMethodDes() {
		return this.methodDes;
	}

	public void setMethodDes(String methodDes) {
		this.methodDes = methodDes;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOutParas() {
		return this.outParas;
	}

	public void setOutParas(String outParas) {
		this.outParas = outParas;
	}

	public String getRepCode() {
		return this.repCode;
	}

	public void setRepCode(String repCode) {
		this.repCode = repCode;
	}

	public String getRepMessage() {
		return this.repMessage;
	}

	public void setRepMessage(String repMessage) {
		this.repMessage = repMessage;
	}

	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStackTrace() {
		return this.stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getThreadName() {
		return this.threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public BigDecimal getTradeEnd() {
		return this.tradeEnd;
	}

	public void setTradeEnd(BigDecimal tradeEnd) {
		this.tradeEnd = tradeEnd;
	}

	public BigDecimal getTradeStart() {
		return this.tradeStart;
	}

	public void setTradeStart(BigDecimal tradeStart) {
		this.tradeStart = tradeStart;
	}

	public BigDecimal getTradeTrack() {
		return this.tradeTrack;
	}

	public void setTradeTrack(BigDecimal tradeTrack) {
		this.tradeTrack = tradeTrack;
	}

	public BigDecimal getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(BigDecimal tradeType) {
		this.tradeType = tradeType;
	}

	public String getTranNum() {
		return this.tranNum;
	}

	public void setTranNum(String tranNum) {
		this.tranNum = tranNum;
	}

	public String getUniqueCode() {
		return this.uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

}