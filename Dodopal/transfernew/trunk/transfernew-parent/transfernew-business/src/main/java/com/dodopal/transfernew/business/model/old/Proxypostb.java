package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
//import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the PROXYPOSTB database table.
 */
//@Entity
//@NamedQuery(name="Proxypostb.findAll", query="SELECT p FROM Proxypostb p")
public class Proxypostb implements Serializable {
    private static final long serialVersionUID = 1L;

    //	@EmbeddedId
    private ProxypostbPK id;

    private String adddate;

    private String addtime;

    private BigDecimal poscount;

    private String remarks;

    private String proxyid;

    private String posid;
    
    

    public String getProxyid() {
        return proxyid;
    }

    public void setProxyid(String proxyid) {
        this.proxyid = proxyid;
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public Proxypostb() {
    }

    public ProxypostbPK getId() {
        return this.id;
    }

    public void setId(ProxypostbPK id) {
        this.id = id;
    }

    public String getAdddate() {
        return this.adddate;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    public String getAddtime() {
        return this.addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public BigDecimal getPoscount() {
        return this.poscount;
    }

    public void setPoscount(BigDecimal poscount) {
        this.poscount = poscount;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}