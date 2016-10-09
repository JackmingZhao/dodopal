package com.dodopal.users.business.model;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

public class MerUserCardBDLog extends BaseEntity{
    private static final long serialVersionUID = -8613852490509348776L;
    //卡片名称
    private String cardName;
    //卡号
    private String code;
    //用户号
    private String userCode;
    //用户名
    private String merUserName;
    
    //姓名
    private String merUserNickName;
    //操作类型 0：绑定，1:解绑
    private String operStatus;
    //操作人姓名
    private String operName;
    //来源
    private String source;
    
    //卡片类型
    private String cardType;
    
    //卡城市展示列
    private String cardCityName;
    
    public String getMerUserName() {
        return merUserName;
    }
    public void setMerUserName(String merUserName) {
        this.merUserName = merUserName;
    }
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
	public String getCardName() {
        return cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getUserCode() {
        return userCode;
    }
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getMerUserNickName() {
        return merUserNickName;
    }
    public void setMerUserNickName(String merUserNickname) {
        this.merUserNickName = merUserNickname;
    }
    public String getOperStatus() {
        return operStatus;
    }
    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus;
    }
    public String getOperName() {
        return operName;
    }
    public void setOperName(String operName) {
        this.operName = operName;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    
}
