package com.dodopal.portal.business.bean.query;
import com.dodopal.common.model.QueryBase;
/**
 * 
 * @author lenovo
 *
 */
public class MerCountQuery extends QueryBase{

    private static final long serialVersionUID = 6447201500618330924L;
    
    //商户名称
    private String merName;
    
    //商户管理员（名称）
    private String merUserName;
    
    //用户手机号
    private String merUserMobile;
    
    //城市名称
    private String cityName;
    
    //通卡公司code
    private String providerCode;

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    
    

}
