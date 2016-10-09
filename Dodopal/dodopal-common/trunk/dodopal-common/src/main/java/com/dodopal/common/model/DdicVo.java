package com.dodopal.common.model;

import com.dodopal.common.model.BaseEntity;

public class DdicVo extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 3746732932260232208L;

    private String code;

    private String name;

    private String description;

    private int orderList;

    private String categoryCode;

    private String categoryName;

    public String getDdicId() {
        return getId();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderList() {
        return orderList;
    }

    public void setOrderList(int orderList) {
        this.orderList = orderList;
    }

}
