package com.dodopal.api.card.dto;

import com.dodopal.common.model.BaseEntity;

public class ConsumeDiscountParameter extends BaseEntity{

	/**
	 * 参数下载-消费折扣参数
	 * @author tao
	 */
	private static final long serialVersionUID = 1L;
	/** 用户折扣*/
    private String trandiscount;
    /** 结算折扣*/
    private String settdiscount;
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
