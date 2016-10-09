package com.dodopal.oss.business.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.model.BaseEntity;

public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String loginName;

    private String name;

    private String password;

    private Date lastLoginTime;

    private Date passwordExpiredDate;

    private List<Role> roles = new ArrayList<Role>();

    private String roleIds;
    
    private String email;
    /**
     *  BUG编号116修改新增
     */
    //证件类型
    private String identityType;
    //证件号
    private String identityId;
    //性别
    private String sexId;
    //固定电话
    private String tel;
    //移动电话
    private String mobile;
    //部门
    private String departmentCode;
    //最后登录IP
    private String loginIp;

    //最后登录时间
    private Date loginDate;

    //邮编
    private String zipCode;
    //省份
    private String provinceId;
    //城市
    private String cityId;
    //地址
    private String address;
    //备注
    private String comments;
    
    //昵称
    private String nickName;
    
    /**
     * END
     */
    private Integer activate;
  
    private String deptName;
    

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getSexId() {
        return sexId;
    }

    public void setSexId(String sexId) {
        this.sexId = sexId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getRoleNames() {
        String roleNames = "";
        if (roles != null) {
            for (Role role : roles) {
                roleNames += role.getName() + " ";
            }
        }
        return roleNames;
    }

    public String getUserId() {
        return getId();
    }

    /**
     * 合并角色权限，去除不同角色下的相同权限
     * @return
     */
    public List<String> getOperationIdList() {
        Set<String> sysOperationIds = new HashSet<String>();
        for (Role role : roles) {
            if (role != null) {
                sysOperationIds.addAll(role.getOperationIdList());
            }
        }
        List<String> operationIds = new ArrayList<String>();
        operationIds.addAll(sysOperationIds);
        return operationIds;
    }

    public List<String> getRoleIdList() {
        if (StringUtils.isNotEmpty(roleIds)) {
            String[] ids = roleIds.split(",");
            return Arrays.asList(ids);
        }
        return null;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPasswordExpiredDate() {
        return passwordExpiredDate;
    }

    public void setPasswordExpiredDate(Date passwordExpiredDate) {
        this.passwordExpiredDate = passwordExpiredDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getActivate() {
        return activate;
    }

    public void setActivate(Integer activate) {
        this.activate = activate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
    public String getActivateStr(){
        if(this.activate==0){
            return "启用";
        }else if(this.activate == 1){
            return "停用";
        }
        return null;
    }
}