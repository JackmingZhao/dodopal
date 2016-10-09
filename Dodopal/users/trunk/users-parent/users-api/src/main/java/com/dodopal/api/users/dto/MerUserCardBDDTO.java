package com.dodopal.api.users.dto;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

/**
 * @author Dingkuiyuan@dodopal.com
 * @version 创建时间：2015年5月4日 下午3:52:52
 */
public class MerUserCardBDDTO extends BaseEntity{
    
    private static final long serialVersionUID = -847828837030631892L;
    //用户编号
    private String userCode;
    //用户登录账号（用户名）
    private String merUserName;
    //卡号
    private String cardCode;
    //卡片名称
    private String cardName;
    //状态
    private String bundLingType;
    //解绑人
    private String unBundLingUser;
    //解绑时间
    private Date unBundLingDate;
    //备注
    private String remark;
    //用户真实姓名
    private String merUserNameName;
    //解绑人真实姓名
    private String unBundLingUserName;
    //来源（记录日志）
    private String source;
    
    //卡片类型
    private String cardType;
    
    //卡城市展示列
    private String cardCityName;
    

    public String getCardType() {
		return cardType;
	}



	public void setCardType(String cardType) {
		this.cardType = cardType;
	}



	public String getCardCityName() {
		return cardCityName;
	}



	public void setCardCityName(String cardCityName) {
		this.cardCityName = cardCityName;
	}



	public String getUserCode() {
        return userCode;
    }



    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }



    public String getMerUserName() {
        return merUserName;
    }
    
    

    public String getCardName() {
        return cardName;
    }



    public void setCardName(String cardName) {
        this.cardName = cardName;
    }



    public String getSource() {
        return source;
    }



    public void setSource(String source) {
        this.source = source;
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
    
    
}
