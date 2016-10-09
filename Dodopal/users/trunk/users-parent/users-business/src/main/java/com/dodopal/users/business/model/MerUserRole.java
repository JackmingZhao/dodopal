package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * 类说明 ：商户用户角色
 * @author lifeng
 */

public class MerUserRole extends BaseEntity {
    private static final long serialVersionUID = -8847750437538180242L;
    private String activate;
    private String userCode;
    private String merRoleCode;

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getMerRoleCode() {
        return merRoleCode;
    }

    public void setMerRoleCode(String merRoleCode) {
        this.merRoleCode = merRoleCode;
    }

}
