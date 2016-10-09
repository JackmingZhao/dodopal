package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TLBANKORDER20160114 database table.
 * 
 */
//@Entity
//@NamedQuery(name="Tlbankorder20160114.findAll", query="SELECT t FROM Tlbankorder20160114 t")
public class Tlbankorder20160114 implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private Tlbankorder20160114PK id;

	private String aftercardnotes;

	private BigDecimal amt;

	private String bankorderid;

	private String bankresult;

	private BigDecimal banksetdate;

	private String beforecardnotes;

	private String beforeorderstates;

	private BigDecimal billflag;

	private BigDecimal billoperid;

	private String billtime;

	private BigDecimal blackamt;

	private String cardcounter;

	private String cardfulino;

	private String cardkfhao;

	private BigDecimal cardtype;

	private String chargecardbak;

	private BigDecimal chargecarddate;

	private String chargecardno;

	private BigDecimal chargecardposid;

	private String chargecardresult;

	private BigDecimal chargecardtime;

	private String chargekey;

	private BigDecimal checkcarddate;

	private String checkcardno;

	private BigDecimal checkcardposid;

	private String checkcardresult;

	private BigDecimal checkcardtime;

	private String checkyktmw;

	private BigDecimal cityid;

	private BigDecimal dounknowflag;

	private BigDecimal hnd;

	private String orderserror;

	private String orderstates;

	private BigDecimal paidamt;

	private BigDecimal pointvalue;

	private BigDecimal posseq1;

	private BigDecimal posseq2;

	private BigDecimal posseq3;

	private BigDecimal posseq4;

	private String postype;

	private BigDecimal proamt;

	private String remw;

	private BigDecimal resdate;

	private BigDecimal restime;

	private BigDecimal senddate;

	private String sendmw;

	private BigDecimal sendtime;

	private BigDecimal setdate;

	private String setflag;

	private BigDecimal settcardposid;

	private String settcardresult;

	private BigDecimal settchargecarddate;

	private BigDecimal settchargecardtime;

	private String settkey;

	private String tradestep;

	private BigDecimal userid;

	private BigDecimal yktid;

	public Tlbankorder20160114() {
	}

	public Tlbankorder20160114PK getId() {
		return this.id;
	}

	public void setId(Tlbankorder20160114PK id) {
		this.id = id;
	}

	public String getAftercardnotes() {
		return this.aftercardnotes;
	}

	public void setAftercardnotes(String aftercardnotes) {
		this.aftercardnotes = aftercardnotes;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getBankorderid() {
		return this.bankorderid;
	}

	public void setBankorderid(String bankorderid) {
		this.bankorderid = bankorderid;
	}

	public String getBankresult() {
		return this.bankresult;
	}

	public void setBankresult(String bankresult) {
		this.bankresult = bankresult;
	}

	public BigDecimal getBanksetdate() {
		return this.banksetdate;
	}

	public void setBanksetdate(BigDecimal banksetdate) {
		this.banksetdate = banksetdate;
	}

	public String getBeforecardnotes() {
		return this.beforecardnotes;
	}

	public void setBeforecardnotes(String beforecardnotes) {
		this.beforecardnotes = beforecardnotes;
	}

	public String getBeforeorderstates() {
		return this.beforeorderstates;
	}

	public void setBeforeorderstates(String beforeorderstates) {
		this.beforeorderstates = beforeorderstates;
	}

	public BigDecimal getBillflag() {
		return this.billflag;
	}

	public void setBillflag(BigDecimal billflag) {
		this.billflag = billflag;
	}

	public BigDecimal getBilloperid() {
		return this.billoperid;
	}

	public void setBilloperid(BigDecimal billoperid) {
		this.billoperid = billoperid;
	}

	public String getBilltime() {
		return this.billtime;
	}

	public void setBilltime(String billtime) {
		this.billtime = billtime;
	}

	public BigDecimal getBlackamt() {
		return this.blackamt;
	}

	public void setBlackamt(BigDecimal blackamt) {
		this.blackamt = blackamt;
	}

	public String getCardcounter() {
		return this.cardcounter;
	}

	public void setCardcounter(String cardcounter) {
		this.cardcounter = cardcounter;
	}

	public String getCardfulino() {
		return this.cardfulino;
	}

	public void setCardfulino(String cardfulino) {
		this.cardfulino = cardfulino;
	}

	public String getCardkfhao() {
		return this.cardkfhao;
	}

	public void setCardkfhao(String cardkfhao) {
		this.cardkfhao = cardkfhao;
	}

	public BigDecimal getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(BigDecimal cardtype) {
		this.cardtype = cardtype;
	}

	public String getChargecardbak() {
		return this.chargecardbak;
	}

	public void setChargecardbak(String chargecardbak) {
		this.chargecardbak = chargecardbak;
	}

	public BigDecimal getChargecarddate() {
		return this.chargecarddate;
	}

	public void setChargecarddate(BigDecimal chargecarddate) {
		this.chargecarddate = chargecarddate;
	}

	public String getChargecardno() {
		return this.chargecardno;
	}

	public void setChargecardno(String chargecardno) {
		this.chargecardno = chargecardno;
	}

	public BigDecimal getChargecardposid() {
		return this.chargecardposid;
	}

	public void setChargecardposid(BigDecimal chargecardposid) {
		this.chargecardposid = chargecardposid;
	}

	public String getChargecardresult() {
		return this.chargecardresult;
	}

	public void setChargecardresult(String chargecardresult) {
		this.chargecardresult = chargecardresult;
	}

	public BigDecimal getChargecardtime() {
		return this.chargecardtime;
	}

	public void setChargecardtime(BigDecimal chargecardtime) {
		this.chargecardtime = chargecardtime;
	}

	public String getChargekey() {
		return this.chargekey;
	}

	public void setChargekey(String chargekey) {
		this.chargekey = chargekey;
	}

	public BigDecimal getCheckcarddate() {
		return this.checkcarddate;
	}

	public void setCheckcarddate(BigDecimal checkcarddate) {
		this.checkcarddate = checkcarddate;
	}

	public String getCheckcardno() {
		return this.checkcardno;
	}

	public void setCheckcardno(String checkcardno) {
		this.checkcardno = checkcardno;
	}

	public BigDecimal getCheckcardposid() {
		return this.checkcardposid;
	}

	public void setCheckcardposid(BigDecimal checkcardposid) {
		this.checkcardposid = checkcardposid;
	}

	public String getCheckcardresult() {
		return this.checkcardresult;
	}

	public void setCheckcardresult(String checkcardresult) {
		this.checkcardresult = checkcardresult;
	}

	public BigDecimal getCheckcardtime() {
		return this.checkcardtime;
	}

	public void setCheckcardtime(BigDecimal checkcardtime) {
		this.checkcardtime = checkcardtime;
	}

	public String getCheckyktmw() {
		return this.checkyktmw;
	}

	public void setCheckyktmw(String checkyktmw) {
		this.checkyktmw = checkyktmw;
	}

	public BigDecimal getCityid() {
		return this.cityid;
	}

	public void setCityid(BigDecimal cityid) {
		this.cityid = cityid;
	}

	public BigDecimal getDounknowflag() {
		return this.dounknowflag;
	}

	public void setDounknowflag(BigDecimal dounknowflag) {
		this.dounknowflag = dounknowflag;
	}

	public BigDecimal getHnd() {
		return this.hnd;
	}

	public void setHnd(BigDecimal hnd) {
		this.hnd = hnd;
	}

	public String getOrderserror() {
		return this.orderserror;
	}

	public void setOrderserror(String orderserror) {
		this.orderserror = orderserror;
	}

	public String getOrderstates() {
		return this.orderstates;
	}

	public void setOrderstates(String orderstates) {
		this.orderstates = orderstates;
	}

	public BigDecimal getPaidamt() {
		return this.paidamt;
	}

	public void setPaidamt(BigDecimal paidamt) {
		this.paidamt = paidamt;
	}

	public BigDecimal getPointvalue() {
		return this.pointvalue;
	}

	public void setPointvalue(BigDecimal pointvalue) {
		this.pointvalue = pointvalue;
	}

	public BigDecimal getPosseq1() {
		return this.posseq1;
	}

	public void setPosseq1(BigDecimal posseq1) {
		this.posseq1 = posseq1;
	}

	public BigDecimal getPosseq2() {
		return this.posseq2;
	}

	public void setPosseq2(BigDecimal posseq2) {
		this.posseq2 = posseq2;
	}

	public BigDecimal getPosseq3() {
		return this.posseq3;
	}

	public void setPosseq3(BigDecimal posseq3) {
		this.posseq3 = posseq3;
	}

	public BigDecimal getPosseq4() {
		return this.posseq4;
	}

	public void setPosseq4(BigDecimal posseq4) {
		this.posseq4 = posseq4;
	}

	public String getPostype() {
		return this.postype;
	}

	public void setPostype(String postype) {
		this.postype = postype;
	}

	public BigDecimal getProamt() {
		return this.proamt;
	}

	public void setProamt(BigDecimal proamt) {
		this.proamt = proamt;
	}

	public String getRemw() {
		return this.remw;
	}

	public void setRemw(String remw) {
		this.remw = remw;
	}

	public BigDecimal getResdate() {
		return this.resdate;
	}

	public void setResdate(BigDecimal resdate) {
		this.resdate = resdate;
	}

	public BigDecimal getRestime() {
		return this.restime;
	}

	public void setRestime(BigDecimal restime) {
		this.restime = restime;
	}

	public BigDecimal getSenddate() {
		return this.senddate;
	}

	public void setSenddate(BigDecimal senddate) {
		this.senddate = senddate;
	}

	public String getSendmw() {
		return this.sendmw;
	}

	public void setSendmw(String sendmw) {
		this.sendmw = sendmw;
	}

	public BigDecimal getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(BigDecimal sendtime) {
		this.sendtime = sendtime;
	}

	public BigDecimal getSetdate() {
		return this.setdate;
	}

	public void setSetdate(BigDecimal setdate) {
		this.setdate = setdate;
	}

	public String getSetflag() {
		return this.setflag;
	}

	public void setSetflag(String setflag) {
		this.setflag = setflag;
	}

	public BigDecimal getSettcardposid() {
		return this.settcardposid;
	}

	public void setSettcardposid(BigDecimal settcardposid) {
		this.settcardposid = settcardposid;
	}

	public String getSettcardresult() {
		return this.settcardresult;
	}

	public void setSettcardresult(String settcardresult) {
		this.settcardresult = settcardresult;
	}

	public BigDecimal getSettchargecarddate() {
		return this.settchargecarddate;
	}

	public void setSettchargecarddate(BigDecimal settchargecarddate) {
		this.settchargecarddate = settchargecarddate;
	}

	public BigDecimal getSettchargecardtime() {
		return this.settchargecardtime;
	}

	public void setSettchargecardtime(BigDecimal settchargecardtime) {
		this.settchargecardtime = settchargecardtime;
	}

	public String getSettkey() {
		return this.settkey;
	}

	public void setSettkey(String settkey) {
		this.settkey = settkey;
	}

	public String getTradestep() {
		return this.tradestep;
	}

	public void setTradestep(String tradestep) {
		this.tradestep = tradestep;
	}

	public BigDecimal getUserid() {
		return this.userid;
	}

	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}

	public BigDecimal getYktid() {
		return this.yktid;
	}

	public void setYktid(BigDecimal yktid) {
		this.yktid = yktid;
	}

}