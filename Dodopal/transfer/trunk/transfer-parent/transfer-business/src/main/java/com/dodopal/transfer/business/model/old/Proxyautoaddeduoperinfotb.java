package com.dodopal.transfer.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the PROXYAUTOADDEDUOPERINFOTB database table.
 */
//@Entity
//@NamedQuery(name="Proxyautoaddeduoperinfotb.findAll", query="SELECT p FROM Proxyautoaddeduoperinfotb p")
public class Proxyautoaddeduoperinfotb implements Serializable {
    private static final long serialVersionUID = 1L;

    //	@EmbeddedId
    private ProxyautoaddeduoperinfotbPK id;

    private String adddatetime;

    //	@Column(name="AUTO_ADD_ARRIVE_LIMIT")
    private BigDecimal autoAddArriveLimit;

    //	@Column(name="AUTO_ADD_TRIGGER_LIMIT")
    private BigDecimal autoAddTriggerLimit;

    private String impldatetime;

    private String operdatetime;

    private BigDecimal status;

    private String seq;

    private long groupid;

    private long proxyid;

    
    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public long getGroupid() {
        return groupid;
    }

    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

    public long getProxyid() {
        return proxyid;
    }

    public void setProxyid(long proxyid) {
        this.proxyid = proxyid;
    }

    public Proxyautoaddeduoperinfotb() {
    }

    public ProxyautoaddeduoperinfotbPK getId() {
        return this.id;
    }

    public void setId(ProxyautoaddeduoperinfotbPK id) {
        this.id = id;
    }

    public String getAdddatetime() {
        return this.adddatetime;
    }

    public void setAdddatetime(String adddatetime) {
        this.adddatetime = adddatetime;
    }

    public BigDecimal getAutoAddArriveLimit() {
        return this.autoAddArriveLimit;
    }

    public void setAutoAddArriveLimit(BigDecimal autoAddArriveLimit) {
        this.autoAddArriveLimit = autoAddArriveLimit;
    }

    public BigDecimal getAutoAddTriggerLimit() {
        return this.autoAddTriggerLimit;
    }

    public void setAutoAddTriggerLimit(BigDecimal autoAddTriggerLimit) {
        this.autoAddTriggerLimit = autoAddTriggerLimit;
    }

    public String getImpldatetime() {
        return this.impldatetime;
    }

    public void setImpldatetime(String impldatetime) {
        this.impldatetime = impldatetime;
    }

    public String getOperdatetime() {
        return this.operdatetime;
    }

    public void setOperdatetime(String operdatetime) {
        this.operdatetime = operdatetime;
    }

    public BigDecimal getStatus() {
        return this.status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

}