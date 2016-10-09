package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * 类说明 ：
 * @author lifeng
 */

public class MerFunctionInfo extends BaseEntity {
    private static final long serialVersionUID = -3076372324143319219L;
    /*启用标示*/
    private String activate;
    /*功能编码*/
    private String merFunCode;
    /*功能名称*/
    private String merFunName;
    /*父级功能编码*/
    private String parentCode;
    /*描述*/
    private String description;
    /*功能类型*/
    private String type;
    /*可见标志*/
    private int visibility;
    /*所在功能层级关系*/
    private int levels;
    /*功能所处位置*/
    private int position;
    /*应用系统名称*/
    private String appName;

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getMerFunCode() {
        return merFunCode;
    }

    public void setMerFunCode(String merFunCode) {
        this.merFunCode = merFunCode;
    }

    public String getMerFunName() {
        return merFunName;
    }

    public void setMerFunName(String merFunName) {
        this.merFunName = merFunName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}
