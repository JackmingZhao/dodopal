package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * 类说明 ：商户角色功能
 * @author lifeng
 */

public class MerRoleFun extends BaseEntity {
    private static final long serialVersionUID = 863884792446655223L;
    private String activate;
    private String merRoleCode;
    private String merFunCode;
    private String merFunName;

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getMerRoleCode() {
        return merRoleCode;
    }

    public void setMerRoleCode(String merRoleCode) {
        this.merRoleCode = merRoleCode;
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

}
