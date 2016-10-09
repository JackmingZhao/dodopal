package com.dodopal.api.merdevice.dto;

import java.io.Serializable;


public class Specdata implements Serializable{

    private static final long serialVersionUID = -7636953340190928785L;
    
    //卡唯一码
    private String uid;
    //ATS
    private String ats;
    //05文件
    private String file05;
    //06文件
    private String file06;
    //11文件
    private String file11;
    //15文件
    private String file15;
    //卡随机数(账户使用)
    private String rand;
    //个性化数据C(绍兴专用，非绍兴城市，不传数据)
    private String custom;
    //卡片执行APDU指令后返回
    private String apdudata;

    public String getApdudata() {
        return apdudata;
    }

    public void setApdudata(String apdudata) {
        this.apdudata = apdudata;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAts() {
        return ats;
    }

    public void setAts(String ats) {
        this.ats = ats;
    }

    public String getFile05() {
        return file05;
    }

    public void setFile05(String file05) {
        this.file05 = file05;
    }

    public String getFile06() {
        return file06;
    }

    public void setFile06(String file06) {
        this.file06 = file06;
    }

    public String getFile11() {
        return file11;
    }

    public void setFile11(String file11) {
        this.file11 = file11;
    }

    public String getFile15() {
        return file15;
    }

    public void setFile15(String file15) {
        this.file15 = file15;
    }

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
