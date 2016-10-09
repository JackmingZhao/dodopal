package com.dodopal.api.card.dto;

import com.dodopal.common.model.BaseEntity;

public class SubPeriodDiscountParameter extends BaseEntity{

	/**
	 * 参数下载-分时段消费折扣参数
	 * @author tao
	 */
	private static final long serialVersionUID = 1L;
	/** 折扣日期*/
    private String discountdate;
    /** 折扣开始时间*/
    private String begintime;
    /** 折扣结束时间*/
    private String endtime;
    /** 用户折扣*/
    private String trandiscount;
    /** 结算折扣*/
    private String settdiscount;
	public String getDiscountdate() {
		return discountdate;
	}
	public void setDiscountdate(String discountdate) {
		this.discountdate = discountdate;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getTrandiscount() {
		return trandiscount;
	}
	public void setTrandiscount(String trandiscount) {
		this.trandiscount = trandiscount;
	}
	public String getSettdiscount() {
		return settdiscount;
	}
	public void setSettdiscount(String settdiscount) {
		this.settdiscount = settdiscount;
	}

}
