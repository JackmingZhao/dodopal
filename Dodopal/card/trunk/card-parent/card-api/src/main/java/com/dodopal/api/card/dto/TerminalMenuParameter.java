package com.dodopal.api.card.dto;

import com.dodopal.common.model.BaseEntity;

public class TerminalMenuParameter extends BaseEntity{

	/**
	 * 参数下载-终端菜单参数
	 * @author tao
	 */
	private static final long serialVersionUID = 1L;
	/** SAM卡号*/
    private String samno;
    /** 菜单级别*/
    private String menulevel;
    /** 菜单名称*/
    private String menuname;
    /** 交易类型*/
    private String txntype;
    /** 交易状态*/
    private String txnstatus;
    /** 菜单首笔动作集集合长度*/
    private String seq;
    /** 菜单首笔动作集合*/
    private String menufristactionset;
    
	public String getSamno() {
		return samno;
	}
	public void setSamno(String samno) {
		this.samno = samno;
	}
	public String getMenulevel() {
		return menulevel;
	}
	public void setMenulevel(String menulevel) {
		this.menulevel = menulevel;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getTxntype() {
		return txntype;
	}
	public void setTxntype(String txntype) {
		this.txntype = txntype;
	}
	public String getTxnstatus() {
		return txnstatus;
	}
	public void setTxnstatus(String txnstatus) {
		this.txnstatus = txnstatus;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getMenufristactionset() {
		return menufristactionset;
	}
	public void setMenufristactionset(String menufristactionset) {
		this.menufristactionset = menufristactionset;
	}

}
