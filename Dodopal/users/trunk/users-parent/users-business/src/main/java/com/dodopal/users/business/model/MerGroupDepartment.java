package com.dodopal.users.business.model;

import com.dodopal.common.model.BaseEntity;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年5月5日 上午10:00:43
 */
public class MerGroupDepartment extends BaseEntity{

    private static final long serialVersionUID = 6174011343060673047L;
    
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

    public MerGroupDepartment(String activate, String depName, String merCode, String remark) {
        super();
        this.activate = activate;
        this.depName = depName;
        this.merCode = merCode;
        this.remark = remark;
    }

    public MerGroupDepartment() {
        super();
    }
    
}
