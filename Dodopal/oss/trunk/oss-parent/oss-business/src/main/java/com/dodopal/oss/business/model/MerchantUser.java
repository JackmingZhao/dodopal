package com.dodopal.oss.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

public class MerchantUser extends BaseEntity{
    private static final long serialVersionUID = 1L;
    
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerUserFlg() {
        return merUserFlg;
    }

    public void setMerUserFlg(String merUserFlg) {
        this.merUserFlg = merUserFlg;
    }

    public String getMerUserType() {
        return merUserType;
    }

    public void setMerUserType(String merUserType) {
        this.merUserType = merUserType;
    }

    //用户标志
    private String merUserFlg;   
    
    //用户类型
    private String merUserType;
    
    //商户编码
    private String merCode;
    
    //商户用户登录账号
    private String userName;
    
    //商户用户登录密码
    private String passWord;
    
    //商户用户证件类型
    private String identityType;
    
    //商户用户证件号码
    private String identityNumber;
    
    //商户用户昵称（真实姓名）
    private String realName;
    
    //用户性别
    private String sex;
    
    //用户手机号
    private String mobile;
    
    //用户固定电话
    private String telephone;
    
    //用户地址
    private String address;
    
    //用户生日
    private Date birthday;
    
    //用户邮箱
    private String email;
    
    //用户入职时间
    private Date employeeDate;
    
    //用户最后一次登录时间
    private Date lastLoginDate;
    
    //用户最后一次登录IP
    private String lastLoginIp;
    
    //用户连续登陆次数
    private int loginCount;
    
    //锁定用户时间
    private Date lockedDate;
    
    //备注
    private String remark;
    
    //启用标志
    private String activate;
    
    //删除标志
    private String delFlag;
    
    //用户编号
    private String userCode;
    
    //用户注册来源
    private String registerSource;
    
    //支付确认信息
    private String payInfoFlag;
    
    //默认开通一卡通编号
    private String yktCode;
    
    //页码
    private int page;
    
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    //每页显示
    private int pageSize;

    //页数统计
    private int pageCount;
    
    

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEmployeeDate() {
        return employeeDate;
    }

    public void setEmployeeDate(Date employeeDate) {
        this.employeeDate = employeeDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
    }

    public String getPayInfoFlag() {
        return payInfoFlag;
    }

    public void setPayInfoFlag(String payInfoFlag) {
        this.payInfoFlag = payInfoFlag;
    }

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }
    
}
