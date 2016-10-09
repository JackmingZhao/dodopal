package com.dodopal.portal.business.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.SpringBeanUtil;

public class PosBean extends BaseEntity {

    private static final long serialVersionUID = 8307441327270044050L;

    /** POS号 */
    private String code;

    /** POS状态 */
    private String status;

    /** 商户编号 */
    private String merchantCode;

    /** 商户名称 */
    private String merchantName;

    /** 城市CODE */
    private String cityCode;

    /** 启用标识，0启用，1不启用 */
    private String activate;

    /** 备注 */
    private String comments;

    /** 绑定时间 */
    private Date bundlingDate;
    
    /**
     * 是否绑定
     */
    private String bind;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getBundlingDate() {
        return bundlingDate;
    }

    public void setBundlingDate(Date bundlingDate) {
        this.bundlingDate = bundlingDate;
    }

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    /**
     * pos状态
     * 
     * @return
     */
    public String getStatusView() {
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.status)) {
            return null;
        }
        return ddicService.getDdicNameByCode("POS_STATUS", this.status).toString();
    }

}
