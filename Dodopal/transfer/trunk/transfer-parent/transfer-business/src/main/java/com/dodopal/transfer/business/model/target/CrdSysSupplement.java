package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CRD_SYS_SUPPLEMENT database table.
 * 
 */
//@Entity
//@Table(name="CRD_SYS_SUPPLEMENT")
//@NamedQuery(name="CrdSysSupplement.findAll", query="SELECT c FROM CrdSysSupplement c")
public class CrdSysSupplement implements Serializable {
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

//	@Column(name="CHARGE_KEY")/
	private String chargeKey;

//	@Column(name="CHARGE_YKTMW")
	private String chargeYktmw;

//	@Column(name="CHECK_YKTKEY")
	private String checkYktkey;

//	@Column(name="CHECK_YKTMW")
	private String checkYktmw;

//	@Column(name="CRD_ORDER_NUM")
	private String crdOrderNum;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="LAST_CHARGE_KEY_TIME")
	private String lastChargeKeyTime;

//	@Column(name="LAST_READ_KEY_TIME")
	private String lastReadKeyTime;

//	@Column(name="LAST_RESULT_TIME")
	private String lastResultTime;

//	@Column(name="REQUEST_CHARGE_KEY_COUNT")
	private BigDecimal requestChargeKeyCount;

//	@Column(name="REQUEST_READ_KEY_COUNT")
	private BigDecimal requestReadKeyCount;

//	@Column(name="RESULT_YKTMW")
	private String resultYktmw;

//	@Column(name="SEND_RESULT_COUNT")
	private BigDecimal sendResultCount;

//	@Column(name="TXN_DATE")
	private String txnDate;

//	@Column(name="TXN_TIME")
	private String txnTime;

//	@Column(name="TXN_TYPE")
	private BigDecimal txnType;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

	public CrdSysSupplement() {
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

	public String getChargeKey() {
		return this.chargeKey;
	}

	public void setChargeKey(String chargeKey) {
		this.chargeKey = chargeKey;
	}

	public String getChargeYktmw() {
		return this.chargeYktmw;
	}

	public void setChargeYktmw(String chargeYktmw) {
		this.chargeYktmw = chargeYktmw;
	}

	public String getCheckYktkey() {
		return this.checkYktkey;
	}

	public void setCheckYktkey(String checkYktkey) {
		this.checkYktkey = checkYktkey;
	}

	public String getCheckYktmw() {
		return this.checkYktmw;
	}

	public void setCheckYktmw(String checkYktmw) {
		this.checkYktmw = checkYktmw;
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

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getLastChargeKeyTime() {
		return this.lastChargeKeyTime;
	}

	public void setLastChargeKeyTime(String lastChargeKeyTime) {
		this.lastChargeKeyTime = lastChargeKeyTime;
	}

	public String getLastReadKeyTime() {
		return this.lastReadKeyTime;
	}

	public void setLastReadKeyTime(String lastReadKeyTime) {
		this.lastReadKeyTime = lastReadKeyTime;
	}

	public String getLastResultTime() {
		return this.lastResultTime;
	}

	public void setLastResultTime(String lastResultTime) {
		this.lastResultTime = lastResultTime;
	}

	public BigDecimal getRequestChargeKeyCount() {
		return this.requestChargeKeyCount;
	}

	public void setRequestChargeKeyCount(BigDecimal requestChargeKeyCount) {
		this.requestChargeKeyCount = requestChargeKeyCount;
	}

	public BigDecimal getRequestReadKeyCount() {
		return this.requestReadKeyCount;
	}

	public void setRequestReadKeyCount(BigDecimal requestReadKeyCount) {
		this.requestReadKeyCount = requestReadKeyCount;
	}

	public String getResultYktmw() {
		return this.resultYktmw;
	}

	public void setResultYktmw(String resultYktmw) {
		this.resultYktmw = resultYktmw;
	}

	public BigDecimal getSendResultCount() {
		return this.sendResultCount;
	}

	public void setSendResultCount(BigDecimal sendResultCount) {
		this.sendResultCount = sendResultCount;
	}

	public String getTxnDate() {
		return this.txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnTime() {
		return this.txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public BigDecimal getTxnType() {
		return this.txnType;
	}

	public void setTxnType(BigDecimal txnType) {
		this.txnType = txnType;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}