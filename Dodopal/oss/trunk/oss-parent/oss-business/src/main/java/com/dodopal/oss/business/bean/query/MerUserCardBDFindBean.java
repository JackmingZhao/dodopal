package com.dodopal.oss.business.bean.query;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.dodopal.common.model.QueryBase;
import com.dodopal.oss.business.util.DateJsonDeserializer;

/**
 * 卡片信息查新条件
 * @author 
 * @version 创建时间：2015年5月4日 下午3:56:58
 */
public class MerUserCardBDFindBean extends QueryBase{

	private static final long serialVersionUID = 6130550264103935690L;
	 //用户号
    private String merUserName;
    //卡号
    private String cardCode;
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
    //卡片类型
    private String cardType;
    
    
    public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Date getBundLingDateStart() {
        return bundLingDateStart;
    }
    
    @JsonDeserialize(using = DateJsonDeserializer.class)
    public void setBundLingDateStart(Date bundLingDateStart) {
        this.bundLingDateStart = bundLingDateStart;
    }
    public Date getBundLingDateEnd() {
        return bundLingDateEnd;
    }
    
    @JsonDeserialize(using = DateJsonDeserializer.class)
    public void setBundLingDateEnd(Date bundLingDateEnd) {
        this.bundLingDateEnd = bundLingDateEnd;
    }
    public Date getUnBundLingDateStart() {
        return unBundLingDateStart;
    }
    
    @JsonDeserialize(using = DateJsonDeserializer.class)
    public void setUnBundLingDateStart(Date unBundLingDateStart) {
        this.unBundLingDateStart = unBundLingDateStart;
    }
    public Date getUnBundLingDateEnd() {
        return unBundLingDateEnd;
    }
    
    @JsonDeserialize(using = DateJsonDeserializer.class)
    public void setUnBundLingDateEnd(Date unBundLingDateEnd) {
        this.unBundLingDateEnd = unBundLingDateEnd;
    }
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
