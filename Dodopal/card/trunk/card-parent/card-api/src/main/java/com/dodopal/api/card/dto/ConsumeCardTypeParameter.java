package com.dodopal.api.card.dto;

import com.dodopal.common.model.BaseEntity;

public class ConsumeCardTypeParameter extends BaseEntity {
    private static final long serialVersionUID = 3250601201574156253L;
    /** SAM卡号*/
    private String samno;
    /** 卡物理类型*/
    private String cardphytype;
    /** 卡逻辑类型*/
    private String cardtype;
    /** 卡逻辑类型名称*/
    private String cardtypename;
    /** 卡片属性*/
    private String cardproperty;
    /** 额度处理方式 联机消费额度处理方式*/
    private String fhlimitmana;
    /** 保留*/
    private String resv;
    public String getSamno() {
        return samno;
    }
    public void setSamno(String samno) {
        this.samno = samno;
    }
    public String getCardphytype() {
        return cardphytype;
    }
    public void setCardphytype(String cardphytype) {
        this.cardphytype = cardphytype;
    }
    public String getCardtype() {
        return cardtype;
    }
    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }
    public String getCardtypename() {
        return cardtypename;
    }
    public void setCardtypename(String cardtypename) {
        this.cardtypename = cardtypename;
    }
    public String getCardproperty() {
        return cardproperty;
    }
    public void setCardproperty(String cardproperty) {
        this.cardproperty = cardproperty;
    }
    public String getFhlimitmana() {
        return fhlimitmana;
    }
    public void setFhlimitmana(String fhlimitmana) {
        this.fhlimitmana = fhlimitmana;
    }
    public String getResv() {
        return resv;
    }
    public void setResv(String resv) {
        this.resv = resv;
    }
    
}
