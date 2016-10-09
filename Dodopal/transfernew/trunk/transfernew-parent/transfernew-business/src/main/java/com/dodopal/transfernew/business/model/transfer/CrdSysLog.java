package com.dodopal.transfernew.business.model.transfer;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CRD_SYS_LOG database table.
 * 
 */
//@Entity
//@Table(name="CRD_SYS_LOG")
//@NamedQuery(name="CrdSysLog.findAll", query="SELECT c FROM CrdSysLog c")
public class CrdSysLog implements Serializable {
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

//	@Column(name="CRD_ORDER_NUM")
	private String crdOrderNum;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

	private String description;

//	@Column(name="END_DATE")
	private String endDate;

//	@Column(name="IN_PARA_LENGTH")
	private BigDecimal inParaLength;

//	@Column(name="IN_PARAS")
	private String inParas;

//	@Column(name="METHOD_NAME")
	private String methodName;

//	@Column(name="OUT_PARAS")
	private String outParas;

//	@Column(name="PRO_ORDER_NUM")
	private String proOrderNum;

//	@Column(name="RESP_CODE")
	private String respCode;

//	@Column(name="RESP_EXPLAIN")
	private String respExplain;

//	@Column(name="SERVER_NAME")
	private String serverName;

//	@Column(name="TRADE_END")
	private BigDecimal tradeEnd;

//	@Column(name="TRADE_START")
	private BigDecimal tradeStart;

//	@Column(name="TRADE_TRACK")
	private BigDecimal tradeTrack;

//	@Column(name="TRADE_TYPE")
	private BigDecimal tradeType;

//	@Column(name="TXN_TYPE")
	private BigDecimal txnType;

	public CrdSysLog() {
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

	public String getCrdOrderNum() {
		return this.crdOrderNum;
	}

	public void setCrdOrderNum(String crdOrderNum) {
		this.crdOrderNum = crdOrderNum;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getInParaLength() {
		return this.inParaLength;
	}

	public void setInParaLength(BigDecimal inParaLength) {
		this.inParaLength = inParaLength;
	}

	public String getInParas() {
		return this.inParas;
	}

	public void setInParas(String inParas) {
		this.inParas = inParas;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getOutParas() {
		return this.outParas;
	}

	public void setOutParas(String outParas) {
		this.outParas = outParas;
	}

	public String getProOrderNum() {
		return this.proOrderNum;
	}

	public void setProOrderNum(String proOrderNum) {
		this.proOrderNum = proOrderNum;
	}

	public String getRespCode() {
		return this.respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespExplain() {
		return this.respExplain;
	}

	public void setRespExplain(String respExplain) {
		this.respExplain = respExplain;
	}

	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
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

	public BigDecimal getTxnType() {
		return this.txnType;
	}

	public void setTxnType(BigDecimal txnType) {
		this.txnType = txnType;
	}

}