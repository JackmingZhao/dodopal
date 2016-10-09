package com.dodopal.oss.business.model.dto;

import com.dodopal.common.model.QueryBase;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月21日 下午3:39:57
 */
public class UserQuery extends QueryBase {

    private static final long serialVersionUID = 6148302053739958171L;

    private String loginName;

    private String name;
    
    private String activate;

    //部门
    private String departmentCode;
    
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
