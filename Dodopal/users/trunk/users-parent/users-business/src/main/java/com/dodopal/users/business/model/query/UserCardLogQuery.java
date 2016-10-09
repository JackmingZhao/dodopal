package com.dodopal.users.business.model.query;

import com.dodopal.common.model.QueryBase;

/**
 * 卡片操作日志
 * @author hxc
 *
 */
public class UserCardLogQuery extends QueryBase{

	private static final long serialVersionUID = 6210558454344310739L;

	//卡号
    private String code;
    //用户号
    private String userCode;
    //用户名
    private String merUserName;
    //操作人姓名
    private String operName;
    //来源
    private String source;
    //卡片类型
    private String cardType;
    
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
