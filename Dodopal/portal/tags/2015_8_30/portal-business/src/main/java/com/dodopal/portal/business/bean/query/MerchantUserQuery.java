package com.dodopal.portal.business.bean.query;

import java.util.Date;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.UserClassifyEnum;
import com.dodopal.common.model.QueryBase;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年5月22日 上午10:33:58
 */
public class MerchantUserQuery extends QueryBase{
    private static final long serialVersionUID = -4921456735201739654L;
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
    
    //商户用户昵称（真实姓名）
    private String merUserNickName;
    
    //用户性别
    private String merUserSex;
    
    //用户手机号
    private String merUserMobile;
    
    //用户固定电话
    private String merUserTelephone;
    
    //启用标志
    private String activate;
    
    //删除标志
    private String delFlag;
    
    //用户编号
    private String userCode;
    
    //用户注册来源
    private String merUserSource;
    
    //默认开通一卡通编号
    private String cityCode;
    
    /**-------------------列表查看-----------------------------*/
    //所属商户名称
    private String merchantName;
    
    //所属商户类型
    private String merchantType;
    
    /*审核状态*/
    private String merchantState;  
    /**-------------------列表查看end-------------------------*/
    
    /**
     * 商户类型   查找条件
     */
    private MerTypeEnum merType;
    
    /**
     * 测试用户
     */
    private UserClassifyEnum classify;
    
    //开户时间起始
    private Date createDateStart;
    //开户结束
    private Date createDateEnd;
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
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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
    public String getMerchantState() {
        return merchantState;
    }
    public void setMerchantState(String merchantState) {
        this.merchantState = merchantState;
    }
    public MerTypeEnum getMerType() {
        return merType;
    }
    public void setMerType(MerTypeEnum merType) {
        this.merType = merType;
    }
    public UserClassifyEnum getClassify() {
        return classify;
    }
    public void setClassify(UserClassifyEnum classify) {
        this.classify = classify;
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
}
