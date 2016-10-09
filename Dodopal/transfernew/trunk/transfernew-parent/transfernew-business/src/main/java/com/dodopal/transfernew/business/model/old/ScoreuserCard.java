package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;


/**
 * The persistent class for the SCOREUSER_CARD database table.
 * 
 */
//@Entity
//@Table(name="SCOREUSER_CARD")
//@NamedQuery(name="ScoreuserCard.findAll", query="SELECT s FROM ScoreuserCard s")
public class ScoreuserCard implements Serializable {
	private static final long serialVersionUID = 1L;

	private String boundtime;

	private String cardno;

	private String cardnoinner;

	private String status;

	private String unboundtime;

	private String userid;

	private String yktid;

	public ScoreuserCard() {
	}

	public String getBoundtime() {
		return this.boundtime;
	}

	public void setBoundtime(String boundtime) {
		this.boundtime = boundtime;
	}

	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getCardnoinner() {
		return this.cardnoinner;
	}

	public void setCardnoinner(String cardnoinner) {
		this.cardnoinner = cardnoinner;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUnboundtime() {
		return this.unboundtime;
	}

	public void setUnboundtime(String unboundtime) {
		this.unboundtime = unboundtime;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getYktid() {
		return this.yktid;
	}

	public void setYktid(String yktid) {
		this.yktid = yktid;
	}

}