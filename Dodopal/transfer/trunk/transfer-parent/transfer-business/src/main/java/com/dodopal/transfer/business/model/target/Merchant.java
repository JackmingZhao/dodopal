package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the MERCHANT database table.
 * 
 */
//@Entity
//@NamedQuery(name="Merchant.findAll", query="SELECT m FROM Merchant m")
public class Merchant implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

//	@Column(name="\"ACTIVATE\"")
	private String activate;

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

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="DEL_FLG")
	private String delFlg;

//	@Temporal(TemporalType.DATE)
//	@Column(name="MER_ACTIVATE_DATE")
	private Date merActivateDate;

//	@Column(name="MER_ADDS")
	private String merAdds;

//	@Column(name="MER_AREA")
	private String merArea;

//	@Column(name="MER_BANK_ACCOUNT")
	private String merBankAccount;

//	@Column(name="MER_BANK_NAME")
	private String merBankName;

//	@Column(name="MER_BANK_USER_NAME")
	private String merBankUserName;

//	@Column(name="MER_BUSINESS_SCOPE_ID")
	private String merBusinessScopeId;

//	@Column(name="MER_CITY")
	private String merCity;

//	@Column(name="MER_CLASSIFY")
	private String merClassify;

//	@Column(name="MER_CODE")
	private String merCode;

//	@Temporal(TemporalType.DATE)
//	@Column(name="MER_DEACTIVATE_DATE")
	private Date merDeactivateDate;

//	@Column(name="MER_EMAIL")
	private String merEmail;

//	@Column(name="MER_FAX")
	private String merFax;

//	@Column(name="MER_LINK_USER")
	private String merLinkUser;

//	@Column(name="MER_LINK_USER_MOBILE")
	private String merLinkUserMobile;

//	@Column(name="MER_NAME")
	private String merName;

//	@Column(name="MER_PARENT_CODE")
	private String merParentCode;

//	@Column(name="MER_PAY_MONEY_USER")
	private String merPayMoneyUser;

//	@Column(name="MER_PRO")
	private String merPro;

//	@Column(name="MER_PROPERTY")
	private String merProperty;

//	@Temporal(TemporalType.DATE)
//	@Column(name="MER_REGISTER_DATE")
	private Date merRegisterDate;

//	@Column(name="MER_REJECT_REASON")
	private String merRejectReason;

//	@Column(name="MER_STATE")
	private String merState;

//	@Temporal(TemporalType.DATE)
//	@Column(name="MER_STATE_DATE")
	private Date merStateDate;

//	@Column(name="MER_STATE_USER")
	private String merStateUser;

//	@Column(name="MER_TELEPHONE")
	private String merTelephone;

//	@Column(name="MER_TYPE")
	private String merType;

//	@Column(name="MER_ZIP")
	private String merZip;

	private String source;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;

	public Merchant() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivate() {
		return this.activate;
	}

	public void setActivate(String activate) {
		this.activate = activate;
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

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public Date getMerActivateDate() {
		return this.merActivateDate;
	}

	public void setMerActivateDate(Date merActivateDate) {
		this.merActivateDate = merActivateDate;
	}

	public String getMerAdds() {
		return this.merAdds;
	}

	public void setMerAdds(String merAdds) {
		this.merAdds = merAdds;
	}

	public String getMerArea() {
		return this.merArea;
	}

	public void setMerArea(String merArea) {
		this.merArea = merArea;
	}

	public String getMerBankAccount() {
		return this.merBankAccount;
	}

	public void setMerBankAccount(String merBankAccount) {
		this.merBankAccount = merBankAccount;
	}

	public String getMerBankName() {
		return this.merBankName;
	}

	public void setMerBankName(String merBankName) {
		this.merBankName = merBankName;
	}

	public String getMerBankUserName() {
		return this.merBankUserName;
	}

	public void setMerBankUserName(String merBankUserName) {
		this.merBankUserName = merBankUserName;
	}

	public String getMerBusinessScopeId() {
		return this.merBusinessScopeId;
	}

	public void setMerBusinessScopeId(String merBusinessScopeId) {
		this.merBusinessScopeId = merBusinessScopeId;
	}

	public String getMerCity() {
		return this.merCity;
	}

	public void setMerCity(String merCity) {
		this.merCity = merCity;
	}

	public String getMerClassify() {
		return this.merClassify;
	}

	public void setMerClassify(String merClassify) {
		this.merClassify = merClassify;
	}

	public String getMerCode() {
		return this.merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public Date getMerDeactivateDate() {
		return this.merDeactivateDate;
	}

	public void setMerDeactivateDate(Date merDeactivateDate) {
		this.merDeactivateDate = merDeactivateDate;
	}

	public String getMerEmail() {
		return this.merEmail;
	}

	public void setMerEmail(String merEmail) {
		this.merEmail = merEmail;
	}

	public String getMerFax() {
		return this.merFax;
	}

	public void setMerFax(String merFax) {
		this.merFax = merFax;
	}

	public String getMerLinkUser() {
		return this.merLinkUser;
	}

	public void setMerLinkUser(String merLinkUser) {
		this.merLinkUser = merLinkUser;
	}

	public String getMerLinkUserMobile() {
		return this.merLinkUserMobile;
	}

	public void setMerLinkUserMobile(String merLinkUserMobile) {
		this.merLinkUserMobile = merLinkUserMobile;
	}

	public String getMerName() {
		return this.merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getMerParentCode() {
		return this.merParentCode;
	}

	public void setMerParentCode(String merParentCode) {
		this.merParentCode = merParentCode;
	}

	public String getMerPayMoneyUser() {
		return this.merPayMoneyUser;
	}

	public void setMerPayMoneyUser(String merPayMoneyUser) {
		this.merPayMoneyUser = merPayMoneyUser;
	}

	public String getMerPro() {
		return this.merPro;
	}

	public void setMerPro(String merPro) {
		this.merPro = merPro;
	}

	public String getMerProperty() {
		return this.merProperty;
	}

	public void setMerProperty(String merProperty) {
		this.merProperty = merProperty;
	}

	public Date getMerRegisterDate() {
		return this.merRegisterDate;
	}

	public void setMerRegisterDate(Date merRegisterDate) {
		this.merRegisterDate = merRegisterDate;
	}

	public String getMerRejectReason() {
		return this.merRejectReason;
	}

	public void setMerRejectReason(String merRejectReason) {
		this.merRejectReason = merRejectReason;
	}

	public String getMerState() {
		return this.merState;
	}

	public void setMerState(String merState) {
		this.merState = merState;
	}

	public Date getMerStateDate() {
		return this.merStateDate;
	}

	public void setMerStateDate(Date merStateDate) {
		this.merStateDate = merStateDate;
	}

	public String getMerStateUser() {
		return this.merStateUser;
	}

	public void setMerStateUser(String merStateUser) {
		this.merStateUser = merStateUser;
	}

	public String getMerTelephone() {
		return this.merTelephone;
	}

	public void setMerTelephone(String merTelephone) {
		this.merTelephone = merTelephone;
	}

	public String getMerType() {
		return this.merType;
	}

	public void setMerType(String merType) {
		this.merType = merType;
	}

	public String getMerZip() {
		return this.merZip;
	}

	public void setMerZip(String merZip) {
		this.merZip = merZip;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
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