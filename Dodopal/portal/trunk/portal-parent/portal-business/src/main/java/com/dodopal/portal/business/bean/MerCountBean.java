package com.dodopal.portal.business.bean;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.model.BaseEntity;
/**
 * 供应商(通卡)用户统计城市下所有的商户的信息
 * @author lenovo
 *
 */
public class MerCountBean extends BaseEntity{
    
    private static final long serialVersionUID = 3292689932433462235L;
    //商户名称
    private String merName;
    
    //商户编号
    private String merCode;
    
    //商户管理员（名称）
    private String merUserName;
    
    //用户手机号
    private String merUserMobile;
    
    //拥有pos数量
    private String posCount;
    
    //店面地址
    private String merAddress;
    
    //启用标识
    private String activate;

    
    
    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerUserName() {
        return merUserName;
    }

    public void setMerUserName(String merUserName) {
        this.merUserName = merUserName;
    }

    public String getMerUserMobile() {
        return merUserMobile;
    }

    public void setMerUserMobile(String merUserMobile) {
        this.merUserMobile = merUserMobile;
    }

    public String getPosCount() {
        return posCount;
    }

    public void setPosCount(String posCount) {
        this.posCount = posCount;
    }

    public String getMerAddress() {
        return merAddress;
    }

    public void setMerAddress(String merAddress) {
        this.merAddress = merAddress;
    }

    public String getActivate() {
        return activate;
    }
    
    public String getActivateView(){
        if(StringUtils.isBlank(this.activate)){
            return null;
        }
        if(ActivateEnum.getActivateByCode(this.activate)==null){
            return null;
        }
        return ActivateEnum.getActivateByCode(this.activate).getName();
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }
    
    
}
