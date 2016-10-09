package com.dodopal.portal.business.bean;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.CardBindEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.BaseEntity;
/**
 * 卡片操作日志
 * @author lenovo
 *
 */
public class MerUserCardBDLogBean extends BaseEntity{

    private static final long serialVersionUID = -5336959725204648408L;
    //卡片名称
    private String cardName;
    //卡号
    private String code;
    //用户号
    private String userCode;
    //用户姓名
    private String merUserNickName;
    //操作类型 0：绑定，1:解绑
    private String operStatus;
    //操作人姓名
    private String operName;
    //来源
    private String source;
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
    public void setMerUserNickName(String merUserNickName) {
        this.merUserNickName = merUserNickName;
    }
    public String getOperStatus() {
        if (StringUtils.isBlank(this.operStatus)) {
            return null;
        }
        return CardBindEnum.getBindByCode(this.operStatus).getName();
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
        if (StringUtils.isBlank(this.source)) {
            return null;
        }
        return SourceEnum.getSourceByCode(this.source).getName();
    }
    public void setSource(String source) {
        this.source = source;
    }
    
    
}
