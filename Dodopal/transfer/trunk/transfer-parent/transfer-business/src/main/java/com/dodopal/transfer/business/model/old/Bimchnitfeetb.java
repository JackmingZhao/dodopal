package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIMCHNITFEETB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Bimchnitfeetb.findAll", query="SELECT b FROM Bimchnitfeetb b")
public class Bimchnitfeetb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private BimchnitfeetbPK id;

	private BigDecimal amtfee;

	private String cycletype;

	private BigDecimal eachbill;

	private String feetype;

	private BigDecimal lastsetdate;

	private BigDecimal mchretnfee;

	private BigDecimal modifyflag;

	private String resv;

	private BigDecimal setcycle;

	private String setflag;

	private BigDecimal setpara;
	private Integer yktid;
	private Integer txncode;
	private BigDecimal mchnitid;
	

	public BigDecimal getMchnitid() {
        return mchnitid;
    }

    public void setMchnitid(BigDecimal mchnitid) {
        this.mchnitid = mchnitid;
    }

    public Integer getYktid() {
        return yktid;
    }

    public void setYktid(Integer yktid) {
        this.yktid = yktid;
    }

    public Integer getTxncode() {
        return txncode;
    }

    public void setTxncode(Integer txncode) {
        this.txncode = txncode;
    }

    public Bimchnitfeetb() {
	}

	public BimchnitfeetbPK getId() {
		return this.id;
	}

	public void setId(BimchnitfeetbPK id) {
		this.id = id;
	}

	public BigDecimal getAmtfee() {
		return this.amtfee;
	}

	public void setAmtfee(BigDecimal amtfee) {
		this.amtfee = amtfee;
	}

	public String getCycletype() {
		return this.cycletype;
	}

	public void setCycletype(String cycletype) {
		this.cycletype = cycletype;
	}

	public BigDecimal getEachbill() {
		return this.eachbill;
	}

	public void setEachbill(BigDecimal eachbill) {
		this.eachbill = eachbill;
	}

	public String getFeetype() {
		return this.feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public BigDecimal getLastsetdate() {
		return this.lastsetdate;
	}

	public void setLastsetdate(BigDecimal lastsetdate) {
		this.lastsetdate = lastsetdate;
	}

	public BigDecimal getMchretnfee() {
		return this.mchretnfee;
	}

	public void setMchretnfee(BigDecimal mchretnfee) {
		this.mchretnfee = mchretnfee;
	}

	public BigDecimal getModifyflag() {
		return this.modifyflag;
	}

	public void setModifyflag(BigDecimal modifyflag) {
		this.modifyflag = modifyflag;
	}

	public String getResv() {
		return this.resv;
	}

	public void setResv(String resv) {
		this.resv = resv;
	}

	public BigDecimal getSetcycle() {
		return this.setcycle;
	}

	public void setSetcycle(BigDecimal setcycle) {
		this.setcycle = setcycle;
	}

	public String getSetflag() {
		return this.setflag;
	}

	public void setSetflag(String setflag) {
		this.setflag = setflag;
	}

	public BigDecimal getSetpara() {
		return this.setpara;
	}

	public void setSetpara(BigDecimal setpara) {
		this.setpara = setpara;
	}

}