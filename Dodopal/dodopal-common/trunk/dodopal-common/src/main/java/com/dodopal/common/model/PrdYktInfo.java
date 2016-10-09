package com.dodopal.common.model;

public class PrdYktInfo extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 2198336360057308975L;

    private String yktCode;

    private String yktName;

    public String getYktCode() {
        return yktCode;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }
    
}
