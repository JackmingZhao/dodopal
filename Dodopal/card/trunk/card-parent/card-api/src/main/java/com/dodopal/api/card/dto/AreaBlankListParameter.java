package com.dodopal.api.card.dto;

import com.dodopal.common.model.BaseEntity;

public class AreaBlankListParameter extends BaseEntity{

	/**
	 * 参数下载-区域黑名单参数
	 * @author tao
	 */
	private static final long serialVersionUID = 1L;
	/** SAM卡号*/
    private String samno;
    /** 开始卡号*/
    private String startcardno;
    /** 结束卡号*/
    private String endcardno;
    /** 参数生效日期*/
    private String useddate;
    
	public String getSamno() {
		return samno;
	}
	public void setSamno(String samno) {
		this.samno = samno;
	}
	public String getStartcardno() {
		return startcardno;
	}
	public void setStartcardno(String startcardno) {
		this.startcardno = startcardno;
	}
	public String getEndcardno() {
		return endcardno;
	}
	public void setEndcardno(String endcardno) {
		this.endcardno = endcardno;
	}
	public String getUseddate() {
		return useddate;
	}
	public void setUseddate(String useddate) {
		this.useddate = useddate;
	}
}
