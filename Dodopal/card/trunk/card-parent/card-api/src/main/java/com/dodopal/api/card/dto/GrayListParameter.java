package com.dodopal.api.card.dto;

import com.dodopal.common.model.BaseEntity;

public class GrayListParameter extends BaseEntity{

	/**
	 * 参数下载-灰名单参数
	 * @author tao
	 */
	private static final long serialVersionUID = 1L;
	/** SAM卡号*/
    private String samno;
    /** 灰名单标识*/
    private String graylistflag;
    /** 灰名单数值*/
    private String graylistval;
	public String getSamno() {
		return samno;
	}
	public void setSamno(String samno) {
		this.samno = samno;
	}
	public String getGraylistflag() {
		return graylistflag;
	}
	public void setGraylistflag(String graylistflag) {
		this.graylistflag = graylistflag;
	}
	public String getGraylistval() {
		return graylistval;
	}
	public void setGraylistval(String graylistval) {
		this.graylistval = graylistval;
	}
}
