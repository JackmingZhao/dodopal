package com.dodopal.oss.business.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dodopal.common.model.BaseEntity;
import com.dodopal.oss.business.util.CustomDateSerializer;

public class Ddic extends BaseEntity {

    private static final long serialVersionUID = 3746732932260232208L;

    private String code;

    private String name;

    private String description;

    private Integer orderList;

    private String categoryCode;

    private String categoryName;

    /**
     * 启用标识，0启用，1禁用
     */
    private String activate;

    private String activateName;

    /**
     * 删除标识，0正常，1删除
     */
    private String delFlg;

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

    public Integer getOrderList() {
        return orderList;
    }

    public void setOrderList(Integer orderList) {
        this.orderList = orderList;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getActivateName() {
        return activateName;
    }

    public void setActivateName(String activateName) {
        this.activateName = activateName;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getViewCreateDate() {
        return getCreateDate();
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getViewUpdateDate() {
        return getUpdateDate();
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

}
