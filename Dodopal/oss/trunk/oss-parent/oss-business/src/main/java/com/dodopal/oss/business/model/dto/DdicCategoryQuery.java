package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

/**
 * 类说明 ：
 * @author lifeng
 */

public class DdicCategoryQuery extends QueryBase {
    private static final long serialVersionUID = -460154986094761578L;
    private String categoryCode;
    private String categoryName;
    private String activate;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

}
