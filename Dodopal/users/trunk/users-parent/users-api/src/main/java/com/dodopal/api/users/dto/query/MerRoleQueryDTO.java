package com.dodopal.api.users.dto.query;

import com.dodopal.common.model.QueryBase;

/**
 * 类说明 ：商户角色查询
 * @author lifeng
 */

public class MerRoleQueryDTO extends QueryBase {
    private static final long serialVersionUID = 6828547402373620795L;
    /*商户号*/
    private String merCode;
    /*角色名称*/
    private String merRoleName;
    /*登录用户ID*/
    private String loginUserId;

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerRoleName() {
        return merRoleName;
    }

    public void setMerRoleName(String merRoleName) {
        this.merRoleName = merRoleName;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

}
