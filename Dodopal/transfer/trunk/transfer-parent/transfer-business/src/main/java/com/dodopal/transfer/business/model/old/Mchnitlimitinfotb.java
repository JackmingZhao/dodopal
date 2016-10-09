package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MCHNITLIMITINFOTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Mchnitlimitinfotb.findAll", query="SELECT m FROM Mchnitlimitinfotb m")
public class Mchnitlimitinfotb implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal amtlimit;

	private BigDecimal currency;

	private BigDecimal extralimitamt;

	private String lastdate;

	private String lasttime;

	private BigDecimal mchnitid;

	private String remarks;

	private BigDecimal surpluslimit;

	private BigDecimal usedlimit;

	private BigDecimal warnlimt;

	public Mchnitlimitinfotb() {
	}

	public BigDecimal getAmtlimit() {
		return this.amtlimit;
	}

	public void setAmtlimit(BigDecimal amtlimit) {
		this.amtlimit = amtlimit;
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

	public String getLastdate() {
		return this.lastdate;
	}

	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}

	public String getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public BigDecimal getMchnitid() {
		return this.mchnitid;
	}

	public void setMchnitid(BigDecimal mchnitid) {
		this.mchnitid = mchnitid;
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