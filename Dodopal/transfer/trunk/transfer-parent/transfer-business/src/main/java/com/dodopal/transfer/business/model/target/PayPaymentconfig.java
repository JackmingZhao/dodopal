package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the PAY_PAYMENTCONFIG database table.
 * 
 */
//@Entity
//@Table(name="PAY_PAYMENTCONFIG")
//@NamedQuery(name="PayPaymentconfig.findAll", query="SELECT p FROM PayPaymentconfig p")
public class PayPaymentconfig implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long id;

//	@Column(name="\"ACTIVATE\"")
	private BigDecimal activate;

//	@Column(name="BARGAINOR_ID")
	private String bargainorId;

//	@Column(name="BARGAINOR_KEY")
	private String bargainorKey;

	private String column1;

	private String column2;

	private String column3;

	private String column4;

	private String column5;

//	@Column(name="CREATE_DATE")
	private Timestamp createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

	private String defaultbank;

	private String memo;

//	@Column(name="MODIFY_DATE")
	private Timestamp modifyDate;

//	@Column(name="MODIFY_USER")
	private String modifyUser;

	private String name;

	private String orderbyid;

	private String payimgurl;

//	@Column(name="PAYMENT_CONFIG_TYPE")
	private String paymentConfigType;

//	@Column(name="PAYMENT_FEETYPE")
	private String paymentFeetype;

//	@Column(name="PAYMENT_PRODUCTID")
	private String paymentProductid;

	private BigDecimal paymentfee;

	public PayPaymentconfig() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getActivate() {
		return this.activate;
	}

	public void setActivate(BigDecimal activate) {
		this.activate = activate;
	}

	public String getBargainorId() {
		return this.bargainorId;
	}

	public void setBargainorId(String bargainorId) {
		this.bargainorId = bargainorId;
	}

	public String getBargainorKey() {
		return this.bargainorKey;
	}

	public void setBargainorKey(String bargainorKey) {
		this.bargainorKey = bargainorKey;
	}

	public String getColumn1() {
		return this.column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getColumn2() {
		return this.column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getColumn3() {
		return this.column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}

	public String getColumn4() {
		return this.column4;
	}

	public void setColumn4(String column4) {
		this.column4 = column4;
	}

	public String getColumn5() {
		return this.column5;
	}

	public void setColumn5(String column5) {
		this.column5 = column5;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getDefaultbank() {
		return this.defaultbank;
	}

	public void setDefaultbank(String defaultbank) {
		this.defaultbank = defaultbank;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Timestamp getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderbyid() {
		return this.orderbyid;
	}

	public void setOrderbyid(String orderbyid) {
		this.orderbyid = orderbyid;
	}

	public String getPayimgurl() {
		return this.payimgurl;
	}

	public void setPayimgurl(String payimgurl) {
		this.payimgurl = payimgurl;
	}

	public String getPaymentConfigType() {
		return this.paymentConfigType;
	}

	public void setPaymentConfigType(String paymentConfigType) {
		this.paymentConfigType = paymentConfigType;
	}

	public String getPaymentFeetype() {
		return this.paymentFeetype;
	}

	public void setPaymentFeetype(String paymentFeetype) {
		this.paymentFeetype = paymentFeetype;
	}

	public String getPaymentProductid() {
		return this.paymentProductid;
	}

	public void setPaymentProductid(String paymentProductid) {
		this.paymentProductid = paymentProductid;
	}

	public BigDecimal getPaymentfee() {
		return this.paymentfee;
	}

	public void setPaymentfee(BigDecimal paymentfee) {
		this.paymentfee = paymentfee;
	}

}