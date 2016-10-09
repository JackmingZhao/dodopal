package com.dodopal.transfer.business.model.target;

import java.io.Serializable;
/**
 * 商户用户扩展表
 * @author lenovo
 *
 */
public class MerchantUserExtend implements Serializable {

    private static final long serialVersionUID = -3856851896834429493L;

    private String Id;
    
    private String userCode;//用户编码

    private String oldUserId;//老系统用户ID

    private String oldUserType;//老系统用户类型   个人用户0，   集团用户1，  网点用户2

    private String userType1; //用户注册来源  详见表usersourcetb

    private String activateId;//通过活动注册的用户活动id 详见表usersourcetb

    private String wechatId;//微信号

    private String wechatIcon; //微信头像

    private String occupation;//职业

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getOldUserId() {
        return oldUserId;
    }

    public void setOldUserId(String oldUserId) {
        this.oldUserId = oldUserId;
    }

    public String getOldUserType() {
        return oldUserType;
    }

    public void setOldUserType(String oldUserType) {
        this.oldUserType = oldUserType;
    }

    public String getUserType1() {
        return userType1;
    }

    public void setUserType1(String userType1) {
        this.userType1 = userType1;
    }

    public String getActivateId() {
        return activateId;
    }

    public void setActivateId(String activateId) {
        this.activateId = activateId;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getWechatIcon() {
        return wechatIcon;
    }

    public void setWechatIcon(String wechatIcon) {
        this.wechatIcon = wechatIcon;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    
    

}
