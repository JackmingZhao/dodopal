package com.dodopal.card.business.model;

import com.dodopal.common.model.BaseEntity;

public class TlpPosMenuTb extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1302457977414480286L;
    private String samno;
    private String menulevel;
    private String menuname;
    private String txntype;
    private String txnstatus;
    private String menufristactionset;
    private String seq;

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

    public String getMenufristactionset() {
        return menufristactionset;
    }

    public void setMenufristactionset(String menufristactionset) {
        this.menufristactionset = menufristactionset;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

}
