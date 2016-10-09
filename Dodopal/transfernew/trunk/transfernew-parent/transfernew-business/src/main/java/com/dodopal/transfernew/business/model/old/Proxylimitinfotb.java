package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROXYLIMITINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Proxylimitinfotb.findAll", query="SELECT p FROM Proxylimitinfotb p")
public class Proxylimitinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long proxyid;

	private BigDecimal amtlimit;

//	@Column(name="AUTO_ADD_ARRIVE_LIMIT")
	private BigDecimal autoAddArriveLimit;

//	@Column(name="AUTO_ADD_TRIGGER_LIMIT")
	private BigDecimal autoAddTriggerLimit;

	private BigDecimal creditlimitamt;

	private BigDecimal currency;

	private BigDecimal extralimitamt;

	private String lasttime;

	private BigDecimal posbacklimit;

	private BigDecimal refundlimit;

	private String remarks;

	private BigDecimal surpluslimit;

	private BigDecimal usedlimit;

	private BigDecimal warnlimt;

	public Proxylimitinfotb() {
	}

	public long getProxyid() {
		return this.proxyid;
	}

	public void setProxyid(long proxyid) {
		this.proxyid = proxyid;
	}

	public BigDecimal getAmtlimit() {
		return this.amtlimit;
	}

	public void setAmtlimit(BigDecimal amtlimit) {
		this.amtlimit = amtlimit;
	}

	public BigDecimal getAutoAddArriveLimit() {
		return this.autoAddArriveLimit;
	}

	public void setAutoAddArriveLimit(BigDecimal autoAddArriveLimit) {
		this.autoAddArriveLimit = autoAddArriveLimit;
	}

	public BigDecimal getAutoAddTriggerLimit() {
		return this.autoAddTriggerLimit;
	}

	public void setAutoAddTriggerLimit(BigDecimal autoAddTriggerLimit) {
		this.autoAddTriggerLimit = autoAddTriggerLimit;
	}

	public BigDecimal getCreditlimitamt() {
		return this.creditlimitamt;
	}

	public void setCreditlimitamt(BigDecimal creditlimitamt) {
		this.creditlimitamt = creditlimitamt;
	}

	public BigDecimal getCurrency() {
		return this.currency;
	}

	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}

	public BigDecimal getExtralimitamt() {
		return this.extralimitamt;
	}

	public void setExtralimitamt(BigDecimal extralimitamt) {
		this.extralimitamt = extralimitamt;
	}

	public String getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public BigDecimal getPosbacklimit() {
		return this.posbacklimit;
	}

	public void setPosbacklimit(BigDecimal posbacklimit) {
		this.posbacklimit = posbacklimit;
	}

	public BigDecimal getRefundlimit() {
		return this.refundlimit;
	}

	public void setRefundlimit(BigDecimal refundlimit) {
		this.refundlimit = refundlimit;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getSurpluslimit() {
		return this.surpluslimit;
	}

	public void setSurpluslimit(BigDecimal surpluslimit) {
		this.surpluslimit = surpluslimit;
	}

	public BigDecimal getUsedlimit() {
		return this.usedlimit;
	}

	public void setUsedlimit(BigDecimal usedlimit) {
		this.usedlimit = usedlimit;
	}

	public BigDecimal getWarnlimt() {
		return this.warnlimt;
	}

	public void setWarnlimt(BigDecimal warnlimt) {
		this.warnlimt = warnlimt;
	}

}