package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CARD_SYS_ORDER database table.
 * 
 */
//@Entity
//@Table(name="CARD_SYS_ORDER")
//@NamedQuery(name="CardSysOrder.findAll", query="SELECT c FROM CardSysOrder c")
public class CardSysOrder implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private String id;

	private String aftercardnotes;

	private BigDecimal amt;

	private String beforecardnotes;

	private String beforeorderstates;

	private BigDecimal blackamt;

	private String cardcounter;

	private String cardfacecode;

	private String cardinnercode;

	private BigDecimal cardtype;

	private String carduid;

	private String chargecardno;

	private String chargecardposid;

	private String chargekey;

//	@Temporal(TemporalType.DATE)
	private Date chargerescarddate;

//	@Temporal(TemporalType.DATE)
	private Date chargesendcarddate;

	private BigDecimal chargetype;

	private String checkcardno;

	private String checkcardposid;

//	@Temporal(TemporalType.DATE)
	private Date checkrescarddate;

//	@Temporal(TemporalType.DATE)
	private Date checksendcarddate;

	private String checkyktkey;

	private String citycode;

	private BigDecimal dounknowflag;

//	@Temporal(TemporalType.DATE)
	private Date lastupdate;

	private String mercode;

//	@Temporal(TemporalType.DATE)
	private Date metropasschargeenddate;

//	@Temporal(TemporalType.DATE)
	private Date metropasschargestartdate;

	private BigDecimal metropasstype;

	private String ordercode;

	private String orderstates;

	private String parentcode;

	private String posid;

	private BigDecimal posseq;

	private BigDecimal postype;

	private BigDecimal proamt;

	private String productcode;

	private String respcode;

//	@Temporal(TemporalType.DATE)
	private Date resultrescarddate;

//	@Temporal(TemporalType.DATE)
	private Date resultsendcarddate;

	private String resultyktmw;

	private String setyktmw;

	private String tradestep;

	private String txndate;

	private BigDecimal txnseq;

	private String txntime;

	private BigDecimal txntype;

	private String usercode;

	private String yktcode;

	public CardSysOrder() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
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

	public String getCardfacecode() {
		return this.cardfacecode;
	}

	public void setCardfacecode(String cardfacecode) {
		this.cardfacecode = cardfacecode;
	}

	public String getCardinnercode() {
		return this.cardinnercode;
	}

	public void setCardinnercode(String cardinnercode) {
		this.cardinnercode = cardinnercode;
	}

	public BigDecimal getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(BigDecimal cardtype) {
		this.cardtype = cardtype;
	}

	public String getCarduid() {
		return this.carduid;
	}

	public void setCarduid(String carduid) {
		this.carduid = carduid;
	}

	public String getChargecardno() {
		return this.chargecardno;
	}

	public void setChargecardno(String chargecardno) {
		this.chargecardno = chargecardno;
	}

	public String getChargecardposid() {
		return this.chargecardposid;
	}

	public void setChargecardposid(String chargecardposid) {
		this.chargecardposid = chargecardposid;
	}

	public String getChargekey() {
		return this.chargekey;
	}

	public void setChargekey(String chargekey) {
		this.chargekey = chargekey;
	}

	public Date getChargerescarddate() {
		return this.chargerescarddate;
	}

	public void setChargerescarddate(Date chargerescarddate) {
		this.chargerescarddate = chargerescarddate;
	}

	public Date getChargesendcarddate() {
		return this.chargesendcarddate;
	}

	public void setChargesendcarddate(Date chargesendcarddate) {
		this.chargesendcarddate = chargesendcarddate;
	}

	public BigDecimal getChargetype() {
		return this.chargetype;
	}

	public void setChargetype(BigDecimal chargetype) {
		this.chargetype = chargetype;
	}

	public String getCheckcardno() {
		return this.checkcardno;
	}

	public void setCheckcardno(String checkcardno) {
		this.checkcardno = checkcardno;
	}

	public String getCheckcardposid() {
		return this.checkcardposid;
	}

	public void setCheckcardposid(String checkcardposid) {
		this.checkcardposid = checkcardposid;
	}

	public Date getCheckrescarddate() {
		return this.checkrescarddate;
	}

	public void setCheckrescarddate(Date checkrescarddate) {
		this.checkrescarddate = checkrescarddate;
	}

	public Date getChecksendcarddate() {
		return this.checksendcarddate;
	}

	public void setChecksendcarddate(Date checksendcarddate) {
		this.checksendcarddate = checksendcarddate;
	}

	public String getCheckyktkey() {
		return this.checkyktkey;
	}

	public void setCheckyktkey(String checkyktkey) {
		this.checkyktkey = checkyktkey;
	}

	public String getCitycode() {
		return this.citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public BigDecimal getDounknowflag() {
		return this.dounknowflag;
	}

	public void setDounknowflag(BigDecimal dounknowflag) {
		this.dounknowflag = dounknowflag;
	}

	public Date getLastupdate() {
		return this.lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getMercode() {
		return this.mercode;
	}

	public void setMercode(String mercode) {
		this.mercode = mercode;
	}

	public Date getMetropasschargeenddate() {
		return this.metropasschargeenddate;
	}

	public void setMetropasschargeenddate(Date metropasschargeenddate) {
		this.metropasschargeenddate = metropasschargeenddate;
	}

	public Date getMetropasschargestartdate() {
		return this.metropasschargestartdate;
	}

	public void setMetropasschargestartdate(Date metropasschargestartdate) {
		this.metropasschargestartdate = metropasschargestartdate;
	}

	public BigDecimal getMetropasstype() {
		return this.metropasstype;
	}

	public void setMetropasstype(BigDecimal metropasstype) {
		this.metropasstype = metropasstype;
	}

	public String getOrdercode() {
		return this.ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getOrderstates() {
		return this.orderstates;
	}

	public void setOrderstates(String orderstates) {
		this.orderstates = orderstates;
	}

	public String getParentcode() {
		return this.parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	public String getPosid() {
		return this.posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

	public BigDecimal getPosseq() {
		return this.posseq;
	}

	public void setPosseq(BigDecimal posseq) {
		this.posseq = posseq;
	}

	public BigDecimal getPostype() {
		return this.postype;
	}

	public void setPostype(BigDecimal postype) {
		this.postype = postype;
	}

	public BigDecimal getProamt() {
		return this.proamt;
	}

	public void setProamt(BigDecimal proamt) {
		this.proamt = proamt;
	}

	public String getProductcode() {
		return this.productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getRespcode() {
		return this.respcode;
	}

	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}

	public Date getResultrescarddate() {
		return this.resultrescarddate;
	}

	public void setResultrescarddate(Date resultrescarddate) {
		this.resultrescarddate = resultrescarddate;
	}

	public Date getResultsendcarddate() {
		return this.resultsendcarddate;
	}

	public void setResultsendcarddate(Date resultsendcarddate) {
		this.resultsendcarddate = resultsendcarddate;
	}

	public String getResultyktmw() {
		return this.resultyktmw;
	}

	public void setResultyktmw(String resultyktmw) {
		this.resultyktmw = resultyktmw;
	}

	public String getSetyktmw() {
		return this.setyktmw;
	}

	public void setSetyktmw(String setyktmw) {
		this.setyktmw = setyktmw;
	}

	public String getTradestep() {
		return this.tradestep;
	}

	public void setTradestep(String tradestep) {
		this.tradestep = tradestep;
	}

	public String getTxndate() {
		return this.txndate;
	}

	public void setTxndate(String txndate) {
		this.txndate = txndate;
	}

	public BigDecimal getTxnseq() {
		return this.txnseq;
	}

	public void setTxnseq(BigDecimal txnseq) {
		this.txnseq = txnseq;
	}

	public String getTxntime() {
		return this.txntime;
	}

	public void setTxntime(String txntime) {
		this.txntime = txntime;
	}

	public BigDecimal getTxntype() {
		return this.txntype;
	}

	public void setTxntype(BigDecimal txntype) {
		this.txntype = txntype;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getYktcode() {
		return this.yktcode;
	}

	public void setYktcode(String yktcode) {
		this.yktcode = yktcode;
	}

}