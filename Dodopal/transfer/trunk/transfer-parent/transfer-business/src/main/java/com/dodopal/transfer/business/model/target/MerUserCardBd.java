package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the MER_USER_CARD_BD database table.
 * 
 */
//@Entity
//@Table(name="MER_USER_CARD_BD")
//@NamedQuery(name="MerUserCardBd.findAll", query="SELECT m FROM MerUserCardBd m")
public class MerUserCardBd implements Serializable {
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
	
//	@Column(name="BUNDLING_TYPE")
	private String bundLingType;

//	@Column(name="CARD_CITY_NAME")
	private String cardCityName;

//	@Column(name="CARD_CODE")
	private String cardCode;

//	@Column(name="CARD_NAME")
	private String cardName;

//	@Column(name="CARD_TYPE")
	private String cardType;

//	@Temporal(TemporalType.DATE)
//	@Column(name="CREATE_DATE")
	private Date createDate;

//	@Column(name="CREATE_USER")
	private String createUser;

//	@Column(name="MER_USER_NAME")
	private String merUserName;

	private String remark;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UNBUNDLING_DATE")
	private Date unbundlingDate;

//	@Column(name="UNBUNDLING_USER")
	private String unbundlingUser;

//	@Temporal(TemporalType.DATE)
//	@Column(name="UPDATE_DATE")
	private Date updateDate;

//	@Column(name="UPDATE_USER")
	private String updateUser;
	
	private String cardOuterCode;

	private String yktCode;

	public String getCardOuterCode() {
		return cardOuterCode;
	}

	public void setCardOuterCode(String cardOuterCode) {
		this.cardOuterCode = cardOuterCode;
	}

	public String getYktCode() {
		return yktCode;
	}

	public void setYktCode(String yktCode) {
		this.yktCode = yktCode;
	}

	public MerUserCardBd() {
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

	public String getBundLingType() {
		return this.bundLingType;
	}

	public void setBundLingType(String bundLingType) {
		this.bundLingType = bundLingType;
	}

	public String getCardCityName() {
		return this.cardCityName;
	}

	public void setCardCityName(String cardCityName) {
		this.cardCityName = cardCityName;
	}

	public String getCardCode() {
		return this.cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardName() {
		return this.cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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

	public String getMerUserName() {
		return this.merUserName;
	}

	public void setMerUserName(String merUserName) {
		this.merUserName = merUserName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUnbundlingDate() {
		return this.unbundlingDate;
	}

	public void setUnbundlingDate(Date unbundlingDate) {
		this.unbundlingDate = unbundlingDate;
	}

	public String getUnbundlingUser() {
		return this.unbundlingUser;
	}

	public void setUnbundlingUser(String unbundlingUser) {
		this.unbundlingUser = unbundlingUser;
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