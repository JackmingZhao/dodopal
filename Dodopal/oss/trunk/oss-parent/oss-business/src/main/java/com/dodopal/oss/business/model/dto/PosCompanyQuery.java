package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

public class PosCompanyQuery extends QueryBase {

    /**
     * 
     */
    private static final long serialVersionUID = 3746732932260232208L;

    private String code;

    private String name;

    private String charger;

    private String activate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

}
