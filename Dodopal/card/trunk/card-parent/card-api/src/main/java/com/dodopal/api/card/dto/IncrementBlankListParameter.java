package com.dodopal.api.card.dto;

import com.dodopal.common.model.BaseEntity;

public class IncrementBlankListParameter extends BaseEntity{

	/**
	 * 参数下载-增量黑名单参数
	 * @author tao
	 */
	private static final long serialVersionUID = 1L;
	/** SAM卡号*/
    private String samno;
    /** 卡号*/
    private String cardno;
    /** 01：新增黑名单，02：删除黑名单 */
    private String blankType;
    /** 参数生效日期*/
    private String useddate;
	public String getSamno() {
		return samno;
	}
	public void setSamno(String samno) {
		this.samno = samno;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getBlankType() {
		return blankType;
	}
	public void setBlankType(String blankType) {
		this.blankType = blankType;
	}
	public String getUseddate() {
		return useddate;
	}
	public void setUseddate(String useddate) {
		this.useddate = useddate;
	}
}
