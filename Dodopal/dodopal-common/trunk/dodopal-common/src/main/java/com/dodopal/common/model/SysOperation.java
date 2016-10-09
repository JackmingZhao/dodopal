package com.dodopal.common.model;

/**
 *
 */
public class SysOperation extends BaseEntity {

    private static final long serialVersionUID = -8623358035296743471L;

    private String code;

    private int version;

    private String commnets;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCommnets() {
        return commnets;
    }

    public void setCommnets(String commnets) {
        this.commnets = commnets;
    }

}
