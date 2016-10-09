package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

/**
 * 类说明 ：
 * @author lifeng
 */

public class DdicQuery extends QueryBase {
    private static final long serialVersionUID = 579741363794342876L;
    private String code;
    private String name;
    private String categoryCode;
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

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

}
