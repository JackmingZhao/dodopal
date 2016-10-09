package com.dodopal.api.card.dto;

import com.dodopal.common.model.BaseEntity;

public class BlankListParameter extends BaseEntity {
    private static final long serialVersionUID = -2198566276747224251L;
    /** SAM卡号*/
    private String samno;
    /** 黑名单卡号*/
    private String cardcode;
    /** 参数生效日期*/
    private String useddate;
    public String getSamno() {
        return samno;
    }
    public void setSamno(String samno) {
        this.samno = samno;
    }
    public String getCardcode() {
        return cardcode;
    }
    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }
    public String getUseddate() {
        return useddate;
    }
    public void setUseddate(String useddate) {
        this.useddate = useddate;
    }
    
}
