package com.dodopal.portal.business.model;

import java.util.Date;
import java.util.List;

import com.dodopal.common.model.BaseEntity;

public class MerchantUser extends BaseEntity {
    
    /**
     * 
     */
    private static final long serialVersionUID = 3946542386824931215L;

    //用户标志
    private String merUserFlag;   
    
    //用户类型
    private String merUserType;
    
    //商户编码
    private String merCode;
    
    //商户用户登录账号
    private String merUserName;
    
    //商户用户登录密码
    private String merUserPWD;
    
    //商户用户证件类型
    private String merUserIdentityType;
    
    //商户用户证件号码
    private String merUserIdentityNumber;
    
    //商户用户昵称（真实姓名）
    private String merUserNickName;
    
    //用户性别
    private String merUserSex;
    
    //用户手机号
    private String merUserMobile;
    
    //用户固定电话
    private String merUserTelephone;
    
    //用户地址
    private String merUserAdds;
    
    //用户生日
    private Date merUserBirthday;
    
    //用户邮箱
    private String merUserEmail;
    
    //用户入职时间
    private Date merUserEmployeeDate;
    
    //用户最后一次登录时间
    private Date merUserLastLoginDate;
    
    //用户最后一次登录IP
    private String merUserLastLoginIp;
    
    //用户连续登陆次数
    private int merUserLoginFaiCount;
    
    //锁定用户时间
    private Date merUserLockedDate;
    
    //备注
    private String merUserRemark;
    
    //启用标志
    private String activate;
    
    //删除标志
    private String delFlag;
    
    //用户编号
    private String userCode;
    
    //用户注册来源
    private String merUserSource;
    
    //支付确认信息
    private String payInfoFlg;
    
    //默认开通一卡通编号
    private String cityCode;
    
    //开户时间起始
    private Date createDateStart;
    //开户结束
    private Date createDateEnd;
  
    /**------------------------------------------------*/
    //管辖部门列表id
    private List<String> merGroupDeptList;
    //管辖部门列表名称
    private List<String> merGroupDeptNameList;
    //所属商户名称
    private String merchantName;
    
    //所属商户类型
    private String merchantType;
    /*审核人*/
    private String merchantStateUser;
    /*审核日期*/
    private Date merchantStateDate;
    /*审核状态*/
    private String merchantState;
    public String getMerUserFlag() {
        return merUserFlag;
    }
    public void setMerUserFlag(String merUserFlag) {
        this.merUserFlag = merUserFlag;
    }
    public String getMerUserType() {
        return merUserType;
    }
    public void setMerUserType(String merUserType) {
        this.merUserType = merUserType;
    }
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public String getMerUserName() {
        return merUserName;
    }
    public void setMerUserName(String merUserName) {
        this.merUserName = merUserName;
    }
    public String getMerUserPWD() {
        return merUserPWD;
    }
    public void setMerUserPWD(String merUserPWD) {
        this.merUserPWD = merUserPWD;
    }
    public String getMerUserIdentityType() {
        return merUserIdentityType;
    }
    public void setMerUserIdentityType(String merUserIdentityType) {
        this.merUserIdentityType = merUserIdentityType;
    }
    public String getMerUserIdentityNumber() {
        return merUserIdentityNumber;
    }
    public void setMerUserIdentityNumber(String merUserIdentityNumber) {
        this.merUserIdentityNumber = merUserIdentityNumber;
    }
    public String getMerUserNickName() {
        return merUserNickName;
    }
    public void setMerUserNickName(String merUserNickName) {
        this.merUserNickName = merUserNickName;
    }
    public String getMerUserSex() {
        return merUserSex;
    }
    public void setMerUserSex(String merUserSex) {
        this.merUserSex = merUserSex;
    }
    public String getMerUserMobile() {
        return merUserMobile;
    }
    public void setMerUserMobile(String merUserMobile) {
        this.merUserMobile = merUserMobile;
    }
    public String getMerUserTelephone() {
        return merUserTelephone;
    }
    public void setMerUserTelephone(String merUserTelephone) {
        this.merUserTelephone = merUserTelephone;
    }
    public String getMerUserAdds() {
        return merUserAdds;
    }
    public void setMerUserAdds(String merUserAdds) {
        this.merUserAdds = merUserAdds;
    }
    public Date getMerUserBirthday() {
        return merUserBirthday;
    }
    public void setMerUserBirthday(Date merUserBirthday) {
        this.merUserBirthday = merUserBirthday;
    }
    public String getMerUserEmail() {
        return merUserEmail;
    }
    public void setMerUserEmail(String merUserEmail) {
        this.merUserEmail = merUserEmail;
    }
    public Date getMerUserEmployeeDate() {
        return merUserEmployeeDate;
    }
    public void setMerUserEmployeeDate(Date merUserEmployeeDate) {
        this.merUserEmployeeDate = merUserEmployeeDate;
    }
    public Date getMerUserLastLoginDate() {
        return merUserLastLoginDate;
    }
    public void setMerUserLastLoginDate(Date merUserLastLoginDate) {
        this.merUserLastLoginDate = merUserLastLoginDate;
    }
    public String getMerUserLastLoginIp() {
        return merUserLastLoginIp;
    }
    public void setMerUserLastLoginIp(String merUserLastLoginIp) {
        this.merUserLastLoginIp = merUserLastLoginIp;
    }
    public int getMerUserLoginFaiCount() {
        return merUserLoginFaiCount;
    }
    public void setMerUserLoginFaiCount(int merUserLoginFaiCount) {
        this.merUserLoginFaiCount = merUserLoginFaiCount;
    }
    public Date getMerUserLockedDate() {
        return merUserLockedDate;
    }
    public void setMerUserLockedDate(Date merUserLockedDate) {
        this.merUserLockedDate = merUserLockedDate;
    }
    public String getMerUserRemark() {
        return merUserRemark;
    }
    public void setMerUserRemark(String merUserRemark) {
        this.merUserRemark = merUserRemark;
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
    public String getMerUserSource() {
        return merUserSource;
    }
    public void setMerUserSource(String merUserSource) {
        this.merUserSource = merUserSource;
    }
    public String getPayInfoFlg() {
        return payInfoFlg;
    }
    public void setPayInfoFlg(String payInfoFlg) {
        this.payInfoFlg = payInfoFlg;
    }
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public Date getCreateDateStart() {
        return createDateStart;
    }
    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }
    public Date getCreateDateEnd() {
        return createDateEnd;
    }
    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }
    public List<String> getMerGroupDeptList() {
        return merGroupDeptList;
    }
    public void setMerGroupDeptList(List<String> merGroupDeptList) {
        this.merGroupDeptList = merGroupDeptList;
    }
    public List<String> getMerGroupDeptNameList() {
        return merGroupDeptNameList;
    }
    public void setMerGroupDeptNameList(List<String> merGroupDeptNameList) {
        this.merGroupDeptNameList = merGroupDeptNameList;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public String getMerchantType() {
        return merchantType;
    }
    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }
    public String getMerchantStateUser() {
        return merchantStateUser;
    }
    public void setMerchantStateUser(String merchantStateUser) {
        this.merchantStateUser = merchantStateUser;
    }
    public Date getMerchantStateDate() {
        return merchantStateDate;
    }
    public void setMerchantStateDate(Date merchantStateDate) {
        this.merchantStateDate = merchantStateDate;
    }
    public String getMerchantState() {
        return merchantState;
    }
    public void setMerchantState(String merchantState) {
        this.merchantState = merchantState;
    }  
    
    /**------------------------------------------------*/
    
}
