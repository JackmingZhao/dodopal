package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SYSUSERSTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Sysuserstb.findAll", query="SELECT s FROM Sysuserstb s")
public class Sysuserstb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String userid;

	private String activeid;

	private BigDecimal activerebate;

	private String address;

	private String answer;

	private String birthday;

	private BigDecimal cityid;

//	@Column(name="CURRENT_SCORE")
	private BigDecimal currentScore;

	private BigDecimal departid;

	private String education;

	private String email;

//	@Column(name="HISTORY_SCORE")
	private BigDecimal historyScore;

	private String identityid;

	private String identitytype;

	private String lastedittime;

	private String lastmobiltel;

	private String loginid;

	private String mchnitid;

	private String mchnmark;

	private String mobiltel;

	private String occupation;

	private String password;

	private BigDecimal point;

	private BigDecimal provinceid;

	private String proxyorderno;

	private BigDecimal qb;

	private String qianbaomoney;

	private String question;

	private String registtime;

	private String relname;

	private String sex;

	private String status;

	private String tel;

	private BigDecimal unitid;

	private String username;

	private BigDecimal usertype;

	private BigDecimal usertype1;

//	@Column(name="\"UUID\"")
	private String uuid;

	private String wechaticon;

	private String wechatid;

	private BigDecimal yktcityid;

	private String zipcode;

	public Sysuserstb() {
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getActiveid() {
		return this.activeid;
	}

	public void setActiveid(String activeid) {
		this.activeid = activeid;
	}

	public BigDecimal getActiverebate() {
		return this.activerebate;
	}

	public void setActiverebate(BigDecimal activerebate) {
		this.activerebate = activerebate;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public BigDecimal getCityid() {
		return this.cityid;
	}

	public void setCityid(BigDecimal cityid) {
		this.cityid = cityid;
	}

	public BigDecimal getCurrentScore() {
		return this.currentScore;
	}

	public void setCurrentScore(BigDecimal currentScore) {
		this.currentScore = currentScore;
	}

	public BigDecimal getDepartid() {
		return this.departid;
	}

	public void setDepartid(BigDecimal departid) {
		this.departid = departid;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getHistoryScore() {
		return this.historyScore;
	}

	public void setHistoryScore(BigDecimal historyScore) {
		this.historyScore = historyScore;
	}

	public String getIdentityid() {
		return this.identityid;
	}

	public void setIdentityid(String identityid) {
		this.identityid = identityid;
	}

	public String getIdentitytype() {
		return this.identitytype;
	}

	public void setIdentitytype(String identitytype) {
		this.identitytype = identitytype;
	}

	public String getLastedittime() {
		return this.lastedittime;
	}

	public void setLastedittime(String lastedittime) {
		this.lastedittime = lastedittime;
	}

	public String getLastmobiltel() {
		return this.lastmobiltel;
	}

	public void setLastmobiltel(String lastmobiltel) {
		this.lastmobiltel = lastmobiltel;
	}

	public String getLoginid() {
		return this.loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getMchnitid() {
		return this.mchnitid;
	}

	public void setMchnitid(String mchnitid) {
		this.mchnitid = mchnitid;
	}

	public String getMchnmark() {
		return this.mchnmark;
	}

	public void setMchnmark(String mchnmark) {
		this.mchnmark = mchnmark;
	}

	public String getMobiltel() {
		return this.mobiltel;
	}

	public void setMobiltel(String mobiltel) {
		this.mobiltel = mobiltel;
	}

	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getPoint() {
		return this.point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public BigDecimal getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(BigDecimal provinceid) {
		this.provinceid = provinceid;
	}

	public String getProxyorderno() {
		return this.proxyorderno;
	}

	public void setProxyorderno(String proxyorderno) {
		this.proxyorderno = proxyorderno;
	}

	public BigDecimal getQb() {
		return this.qb;
	}

	public void setQb(BigDecimal qb) {
		this.qb = qb;
	}

	public String getQianbaomoney() {
		return this.qianbaomoney;
	}

	public void setQianbaomoney(String qianbaomoney) {
		this.qianbaomoney = qianbaomoney;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getRegisttime() {
		return this.registtime;
	}

	public void setRegisttime(String registtime) {
		this.registtime = registtime;
	}

	public String getRelname() {
		return this.relname;
	}

	public void setRelname(String relname) {
		this.relname = relname;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public BigDecimal getUnitid() {
		return this.unitid;
	}

	public void setUnitid(BigDecimal unitid) {
		this.unitid = unitid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getUsertype() {
		return this.usertype;
	}

	public void setUsertype(BigDecimal usertype) {
		this.usertype = usertype;
	}

	public BigDecimal getUsertype1() {
		return this.usertype1;
	}

	public void setUsertype1(BigDecimal usertype1) {
		this.usertype1 = usertype1;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getWechaticon() {
		return this.wechaticon;
	}

	public void setWechaticon(String wechaticon) {
		this.wechaticon = wechaticon;
	}

	public String getWechatid() {
		return this.wechatid;
	}

	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	public BigDecimal getYktcityid() {
		return this.yktcityid;
	}

	public void setYktcityid(BigDecimal yktcityid) {
		this.yktcityid = yktcityid;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}