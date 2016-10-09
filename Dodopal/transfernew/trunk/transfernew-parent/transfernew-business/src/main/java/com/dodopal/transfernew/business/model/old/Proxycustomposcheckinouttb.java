package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PROXYCUSTOMPOSCHECKINOUTTB database table.
 * 
 */
//@Entity
//@NamedQuery(name="Proxycustomposcheckinouttb.findAll", query="SELECT p FROM Proxycustomposcheckinouttb p")
public class Proxycustomposcheckinouttb implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
	private ProxycustomposcheckinouttbPK id;

	private BigDecimal autocheckoutdate;

	private BigDecimal checkindatetime;

	private BigDecimal checkoutdatetime;

//	@Column(name="\"STATE\"")
	private BigDecimal state;

	public Proxycustomposcheckinouttb() {
	}

	public ProxycustomposcheckinouttbPK getId() {
		return this.id;
	}

	public void setId(ProxycustomposcheckinouttbPK id) {
		this.id = id;
	}

	public BigDecimal getAutocheckoutdate() {
		return this.autocheckoutdate;
	}

	public void setAutocheckoutdate(BigDecimal autocheckoutdate) {
		this.autocheckoutdate = autocheckoutdate;
	}

	public BigDecimal getCheckindatetime() {
		return this.checkindatetime;
	}

	public void setCheckindatetime(BigDecimal checkindatetime) {
		this.checkindatetime = checkindatetime;
	}

	public BigDecimal getCheckoutdatetime() {
		return this.checkoutdatetime;
	}

	public void setCheckoutdatetime(BigDecimal checkoutdatetime) {
		this.checkoutdatetime = checkoutdatetime;
	}

	public BigDecimal getState() {
		return this.state;
	}

	public void setState(BigDecimal state) {
		this.state = state;
	}

}