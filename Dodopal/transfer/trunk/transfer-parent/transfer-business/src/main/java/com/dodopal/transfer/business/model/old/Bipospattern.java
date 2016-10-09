package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BIPOSPATTERN database table.
 * 
 */
//@Entity
//@NamedQuery(name="Bipospattern.findAll", query="SELECT b FROM Bipospattern b")
public class Bipospattern implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private long patternid;

	private String applytime;

	private String factorycode;

	private String patterncode;

	private String patternname;

	private BigDecimal price;

	public Bipospattern() {
	}

	public long getPatternid() {
		return this.patternid;
	}

	public void setPatternid(long patternid) {
		this.patternid = patternid;
	}

	public String getApplytime() {
		return this.applytime;
	}

	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}

	public String getFactorycode() {
		return this.factorycode;
	}

	public void setFactorycode(String factorycode) {
		this.factorycode = factorycode;
	}

	public String getPatterncode() {
		return this.patterncode;
	}

	public void setPatterncode(String patterncode) {
		this.patterncode = patterncode;
	}

	public String getPatternname() {
		return this.patternname;
	}

	public void setPatternname(String patternname) {
		this.patternname = patternname;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}