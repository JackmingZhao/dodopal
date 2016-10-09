package com.dodopal.api.card.dto;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 北京NFC特属域
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BJNfcSpecdata implements Serializable {

    private static final long serialVersionUID = 7464898986244822676L;

    //APDU 指令包序号
    private String apdupacno;
    //APDU 指令/应答个数
    private String apduordernum;
    //APDU 指令/应答包长度
    private String apdupaclen;
    //APDU 指令/应答序列
    private String apduseq;
    //APDU 指令包总包数
    private String totalpacnum;
    //卡片CSN
    private String cardcsn;

    public String getApdupacno() {
        return apdupacno;
    }

    public void setApdupacno(String apdupacno) {
        this.apdupacno = apdupacno;
    }

    public String getApduordernum() {
        return apduordernum;
    }

    public void setApduordernum(String apduordernum) {
        this.apduordernum = apduordernum;
    }

    public String getApdupaclen() {
        return apdupaclen;
    }

    public void setApdupaclen(String apdupaclen) {
        this.apdupaclen = apdupaclen;
    }

    public String getApduseq() {
        return apduseq;
    }

    public void setApduseq(String apduseq) {
        this.apduseq = apduseq;
    }

    public String getTotalpacnum() {
        return totalpacnum;
    }

    public void setTotalpacnum(String totalpacnum) {
        this.totalpacnum = totalpacnum;
    }

    public String getCardcsn() {
        return cardcsn;
    }

    public void setCardcsn(String cardcsn) {
        this.cardcsn = cardcsn;
    }

}
