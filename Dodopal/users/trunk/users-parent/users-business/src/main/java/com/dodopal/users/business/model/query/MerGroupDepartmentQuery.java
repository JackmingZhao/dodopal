package com.dodopal.users.business.model.query;

import com.dodopal.common.model.QueryBase;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年6月5日 下午1:44:17
 */
public class MerGroupDepartmentQuery extends QueryBase{
    private static final long serialVersionUID = 6120806726156259444L;

    /**
     * 启用标识，0启用，1不启用
     */
    private String activate;
    
    /**
     * 部门名称
     */
    private String depName;
    
    /**
     * 商户号
     */
    private String merCode;
    
    /**
     * 备注
     */
    private String remark;

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
