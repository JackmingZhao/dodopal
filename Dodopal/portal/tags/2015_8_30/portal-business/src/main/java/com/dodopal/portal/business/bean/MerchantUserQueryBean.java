package com.dodopal.portal.business.bean;

import com.dodopal.common.model.QueryBase;

public class MerchantUserQueryBean extends QueryBase {

    private static final long serialVersionUID = -3698864781587043843L;

    //商户编码
    private String merCode;

    //商户用户登录账号
    private String merUserName;

    //商户用户昵称（真实姓名）
    private String merUserNickName;

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

    // 当前登录用户ID
    private String loginUserId;

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

    public String getMerUserNickName() {
        return merUserNickName;
    }

    public void setMerUserNickName(String merUserNickName) {
        this.merUserNickName = merUserNickName;
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

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

}
