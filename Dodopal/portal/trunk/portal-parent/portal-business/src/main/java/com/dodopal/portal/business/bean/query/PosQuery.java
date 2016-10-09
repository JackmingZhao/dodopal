package com.dodopal.portal.business.bean.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

/**
 * @author DingKuiyuan@dodopal.com
 * @version 创建时间：2015年6月24日 下午1:50:57
 */
public class PosQuery  extends QueryBase {

    private static final long serialVersionUID = -3226715934518143091L;

    /**商户编号*/
    private String merchantCode;
    
    /**商户名称*/
    private String merchantName;
      
    /** POS号 */
    private String code;
    
    /** 备注 */
    private String comments;
    
    /**城市名称*/
    private String cityName;
    
    /** 绑定开始时间 */
    private Date bundlingDateStart;
    
    /** 绑定结束时间 */
    private Date bundlingDateEnd;
    
    
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCode() {
        return code;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public void setCode(String code) {
        this.code = code;
    }   

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getBundlingDateStart() {
        return bundlingDateStart;
    }

    public void setBundlingDateStart(Date bundlingDateStart) {
        this.bundlingDateStart = bundlingDateStart;
    }

    public Date getBundlingDateEnd() {
        return bundlingDateEnd;
    }

    public void setBundlingDateEnd(Date bundlingDateEnd) {
        this.bundlingDateEnd = bundlingDateEnd;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

}
