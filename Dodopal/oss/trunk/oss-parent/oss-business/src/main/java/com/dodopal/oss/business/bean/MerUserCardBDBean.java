package com.dodopal.oss.business.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dodopal.common.enums.BindEnum;
import com.dodopal.common.enums.CardTypeEnum;
import com.dodopal.common.model.BaseEntity;
import com.dodopal.oss.business.util.CustomDateSerializer;

/**
 *  用户卡片信息
 * @author 
 *
 */
public class MerUserCardBDBean extends BaseEntity{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1393291991343321868L;
    //用户号
    private String merUserName;
    //卡号
    private String cardCode;
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
    //卡片类型
    private String cardType;
    
    //卡城市展示列
    private String cardCityName;
    public String getMerUserIdentityTypeView() {     
        if (StringUtils.isBlank(this.cardType)) {
            return "";
        }
        if(null!=CardTypeEnum.getCardTypeByCode(cardType)){
        	return CardTypeEnum.getCardTypeByCode(cardType).getName();
        }
       return cardType;
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
    
    @JsonSerialize(using = CustomDateSerializer.class) 
    public Date getCreateDateView() {
    	
        return getCreateDate();
    }
    
    @JsonSerialize(using = CustomDateSerializer.class) 
    public Date getUnBundLingDateView() {
    	
        return getUnBundLingDate();
    }
    
    public String getBundLingTypeView() {
        if (StringUtils.isBlank(this.bundLingType)) {
            return "";
        }
        if(null!=BindEnum.getBindByCode(this.bundLingType)){
            return BindEnum.getBindByCode(this.bundLingType).getName();
        }
       return bundLingType;
    }
    
    public String getUserCardId() {
        return getId();
    }
    
    
}
