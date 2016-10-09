package com.dodopal.api.card.dto;

import com.dodopal.common.model.BaseEntity;

public class TerminalParameter extends BaseEntity{

	/**
	 * 参数下载-终端运营参数（机具运营参数）
	 * @author tao
	 */
	private static final long serialVersionUID = 1L;
	/** SAM卡号*/
    private String samno;
    /** 分账单位代码*/
    private String unitid;
    /** 发送方单位代码*/
    private String sendunitid;
    /** 接收方单位代码*/
    private String receunitid;
    /** 代理商简码*/
    private String mchntcode;
    /** 商户代码*/
    private String mchntid;
    /** 商户名称*/
    private String mchntname;
    /** 网点名称*/
    private String netname;
    /** 终端代码*/
    private String posid;
    /** 卡片联机消费交易临界值*/
    private String fhtxncritical;
    /** 用户卡密钥处理方式*/
    private String keytype;
    /** 数据上传模式*/
    private String dataupmode;
    /** 定时上传时间*/
    private String timingupmode;
    /** 退款方式*/
    private String refundtype;
	public String getSamno() {
		return samno;
	}
	public void setSamno(String samno) {
		this.samno = samno;
	}
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getSendunitid() {
		return sendunitid;
	}
	public void setSendunitid(String sendunitid) {
		this.sendunitid = sendunitid;
	}
	public String getReceunitid() {
		return receunitid;
	}
	public void setReceunitid(String receunitid) {
		this.receunitid = receunitid;
	}
	public String getMchntcode() {
		return mchntcode;
	}
	public void setMchntcode(String mchntcode) {
		this.mchntcode = mchntcode;
	}
	public String getMchntid() {
		return mchntid;
	}
	public void setMchntid(String mchntid) {
		this.mchntid = mchntid;
	}
	public String getMchntname() {
		return mchntname;
	}
	public void setMchntname(String mchntname) {
		this.mchntname = mchntname;
	}
	public String getNetname() {
		return netname;
	}
	public void setNetname(String netname) {
		this.netname = netname;
	}
	public String getPosid() {
		return posid;
	}
	public void setPosid(String posid) {
		this.posid = posid;
	}
	public String getFhtxncritical() {
		return fhtxncritical;
	}
	public void setFhtxncritical(String fhtxncritical) {
		this.fhtxncritical = fhtxncritical;
	}
	public String getKeytype() {
		return keytype;
	}
	public void setKeytype(String keytype) {
		this.keytype = keytype;
	}
	public String getDataupmode() {
		return dataupmode;
	}
	public void setDataupmode(String dataupmode) {
		this.dataupmode = dataupmode;
	}
	public String getTimingupmode() {
		return timingupmode;
	}
	public void setTimingupmode(String timingupmode) {
		this.timingupmode = timingupmode;
	}
	public String getRefundtype() {
		return refundtype;
	}
	public void setRefundtype(String refundtype) {
		this.refundtype = refundtype;
	}

    
}
