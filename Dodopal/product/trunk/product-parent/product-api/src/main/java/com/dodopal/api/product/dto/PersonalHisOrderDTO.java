package com.dodopal.api.product.dto;

import com.dodopal.common.model.BaseEntity;

/**
 * @author lifeng@dodopal.com
 */

public class PersonalHisOrderDTO extends BaseEntity {
	private static final long serialVersionUID = -4170991920857022306L;
	/* 订单号 */
	private String orderdd;
	/* 城市名称 */
	private String yktname;
	/* 卡号 */
	private String cardno;
	/* 充值前卡内金额(分) */
	private Integer befamt;
	/* 交易金额(分) */
	private Integer amt;
	/* 充值后卡内金额(分) */
	private Integer proamt;
	/* 交易日期，格式：yyyy-MM-dd hh:mm:ss */
	private String senddate;
	/* 订单状态 */
	private String orderstates;

	public String getOrderdd() {
		return orderdd;
	}

	public void setOrderdd(String orderdd) {
		this.orderdd = orderdd;
	}

	public String getYktname() {
		return yktname;
	}

	public void setYktname(String yktname) {
		this.yktname = yktname;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public Integer getBefamt() {
		return befamt;
	}

	public void setBefamt(Integer befamt) {
		this.befamt = befamt;
	}

	public Integer getAmt() {
		return amt;
	}

	public void setAmt(Integer amt) {
		this.amt = amt;
	}

	public Integer getProamt() {
		return proamt;
	}

	public void setProamt(Integer proamt) {
		this.proamt = proamt;
	}

	public String getSenddate() {
		return senddate;
	}

	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}

	public String getOrderstates() {
		return orderstates;
	}

	public void setOrderstates(String orderstates) {
		this.orderstates = orderstates;
	}

}
