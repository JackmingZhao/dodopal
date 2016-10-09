package com.dodopal.common.model;

import com.dodopal.common.model.BaseEntity;

public class DdicCategoryVo extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -4517241311147989437L;

    /**
     * 启用标识，0启用，1禁用
     */
    private boolean activate;

    /**
     * 分类代码
     */
    private String categoryCode;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 描述
     */
    private String description;

    
    public String getDdicCategoryId() {
        return getId();
    }
    
    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
