package com.dodopal.api.card.dto;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 北京卡服务接口传输对象:特殊域
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BJSpecdata implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1336209518682325847L;

    //卡唯一码
    private String uid;
    //ATS
    private String ats;
    //05文件
    private String file05;
    //06文件
    private String file06;
    //09文件
    private String file09;
    //11文件
    private String file11;
    //15文件
    private String file15;
    //16文件
    private String file16;
    //17文件
    private String file17;
    //18文件
    private String file18;
    //19文件
    private String file19;
    //1A文件
    private String file1A;
    //卡随机数(账户使用)
    private String rand;
    //个性化数据C(绍兴专用，非绍兴城市，不传数据)
    private String custom;
    //卡片执行APDU指令后返回
    private String apdudata;

    /*
     * 北京新加start
     */
    //动作集明文信息（北京专用）
    private String plainaction;
    //下一动作集（北京专用）
    private String nextstep;
    //动作集密文（北京专用）
    private String cipheraction;
    //动作集签名（北京专用）
    private String actionsign;
    //脱机消费记录
    private ReslutDataSpecial offlinedata;

    /*
     * 北京新加end
     */

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

    public String getFile09() {
        return file09;
    }

    public void setFile09(String file09) {
        this.file09 = file09;
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

    public String getFile16() {
        return file16;
    }

    public void setFile16(String file16) {
        this.file16 = file16;
    }

    public String getFile17() {
        return file17;
    }

    public void setFile17(String file17) {
        this.file17 = file17;
    }

    public String getFile18() {
        return file18;
    }

    public void setFile18(String file18) {
        this.file18 = file18;
    }

    public String getFile19() {
        return file19;
    }

    public void setFile19(String file19) {
        this.file19 = file19;
    }

    public String getFile1A() {
        return file1A;
    }

    public void setFile1A(String file1a) {
        file1A = file1a;
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

    public String getApdudata() {
        return apdudata;
    }

    public void setApdudata(String apdudata) {
        this.apdudata = apdudata;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPlainaction() {
        return plainaction;
    }

    public void setPlainaction(String plainaction) {
        this.plainaction = plainaction;
    }

    public String getNextstep() {
        return nextstep;
    }

    public void setNextstep(String nextstep) {
        this.nextstep = nextstep;
    }

    public String getCipheraction() {
        return cipheraction;
    }

    public void setCipheraction(String cipheraction) {
        this.cipheraction = cipheraction;
    }

    public String getActionsign() {
        return actionsign;
    }

    public void setActionsign(String actionsign) {
        this.actionsign = actionsign;
    }

    public ReslutDataSpecial getOfflinedata() {
        return offlinedata;
    }

    public void setOfflinedata(ReslutDataSpecial offlinedata) {
        this.offlinedata = offlinedata;
    }

}
