package com.dodopal.portal.business.bean;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.model.QueryBase;
/**
 * 用户卡片
 * @author lenovo
 *
 */
public class MerUserCardBDBean extends BaseEntity{
    private static final long serialVersionUID = -2005240847173309993L;
    //用户号
    private String merUserName;
    //卡号
    private String cardCode;
    //卡片名称
    private String cardName;
    //状态
    private String bundLingType;
    //解绑人
    private String unBundLingUser;
    //解绑日期
    private Date unBundLingDate;
    //备注
    private String remark;
    
    //---------------------------------
    //用户真实姓名
    private String merUserNameName;
    //解绑人真实姓名
    private String unBundLingUserName;
    //------------------------------------------
    
    //绑卡时间开始
    private Date bundLingDateStart;
    //绑卡时间结束
    private Date bundLingDateEnd;
    //解绑时间开始
    private Date unBundLingDateStart;
    //解绑时间结束
    private Date unBundLingDateEnd;
    public String getMerUserName() {
        return merUserName;
    }
    public void setMerUserName(String merUserName) {
        this.merUserName = merUserName;
    }
    public String getCardCode() {
        return cardCode;
    }
    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }
    public String getCardName() {
        return cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public String getBundLingType() {
        return bundLingType;
    }
    public void setBundLingType(String bundLingType) {
        this.bundLingType = bundLingType;
    }
    public String getUnBundLingUser() {
        return unBundLingUser;
    }
    public void setUnBundLingUser(String unBundLingUser) {
        this.unBundLingUser = unBundLingUser;
    }
    public Date getUnBundLingDate() {
        return unBundLingDate;
    }
    public void setUnBundLingDate(Date unBundLingDate) {
        this.unBundLingDate = unBundLingDate;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getMerUserNameName() {
        return merUserNameName;
    }
    public void setMerUserNameName(String merUserNameName) {
        this.merUserNameName = merUserNameName;
    }
    public String getUnBundLingUserName() {
        return unBundLingUserName;
    }
    public void setUnBundLingUserName(String unBundLingUserName) {
        this.unBundLingUserName = unBundLingUserName;
    }
    public Date getBundLingDateStart() {
        return bundLingDateStart;
    }
    public void setBundLingDateStart(Date bundLingDateStart) {
        this.bundLingDateStart = bundLingDateStart;
    }
    public Date getBundLingDateEnd() {
        return bundLingDateEnd;
    }
    public void setBundLingDateEnd(Date bundLingDateEnd) {
        this.bundLingDateEnd = bundLingDateEnd;
    }
    public Date getUnBundLingDateStart() {
        return unBundLingDateStart;
    }
    public void setUnBundLingDateStart(Date unBundLingDateStart) {
        this.unBundLingDateStart = unBundLingDateStart;
    }
    public Date getUnBundLingDateEnd() {
        return unBundLingDateEnd;
    }
    public void setUnBundLingDateEnd(Date unBundLingDateEnd) {
        this.unBundLingDateEnd = unBundLingDateEnd;
    }
    
 
}
