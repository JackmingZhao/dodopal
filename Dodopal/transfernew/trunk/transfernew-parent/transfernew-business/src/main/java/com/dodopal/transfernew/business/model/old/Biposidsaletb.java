package com.dodopal.transfernew.business.model.old;

import java.io.Serializable;
import java.math.BigDecimal;

public class Biposidsaletb implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -1192003236961179625L;
    private BigDecimal saleid;
    private String posid;
    public BigDecimal getSaleid() {
        return saleid;
    }
    public void setSaleid(BigDecimal saleid) {
        this.saleid = saleid;
    }
    public String getPosid() {
        return posid;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    
}
