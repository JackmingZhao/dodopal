package com.dodopal.users.business.model;

import java.util.List;

import com.dodopal.common.model.BaseEntity;

/**
 * 类说明 ：商户角色
 * @author lifeng
 */

public class MerRole extends BaseEntity {
    private static final long serialVersionUID = 3604817369139209607L;
    private String merCode;
    private String merRoleCode;
    private String merRoleName;
    private String description;
    private String activate;
    private List<MerFunctionInfo> merRoleFunList;

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerRoleCode() {
        return merRoleCode;
    }

    public void setMerRoleCode(String merRoleCode) {
        this.merRoleCode = merRoleCode;
    }

    public String getMerRoleName() {
        return merRoleName;
    }

    public void setMerRoleName(String merRoleName) {
        this.merRoleName = merRoleName;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public List<MerFunctionInfo> getMerRoleFunList() {
        return merRoleFunList;
    }

    public void setMerRoleFunList(List<MerFunctionInfo> merRoleFunList) {
        this.merRoleFunList = merRoleFunList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
