package com.dodopal.portal.business.model;

import com.dodopal.common.model.BaseEntity;

public class Test extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 6904889116776830988L;

    private String name;

    private String description;

    private String appName;

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}