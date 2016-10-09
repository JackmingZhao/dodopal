package com.dodopal.oss.business.bean;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.model.BaseEntity;

public class MerRateSupplementBean extends BaseEntity{
    /**
     * 
     */
    private static final long serialVersionUID = 1577197154966537644L;
    /* 商户编码 */
    private String merCode;
    /* 业务编码 */
    private String rateCode;
    /* 页面回调通知外接商户 */
    private String pageCallbackUrl;
    /* 服务器回调通知外接商户 */
    private String serviceNoticeUrl;
    /* 服务器回调通知外接商户 （验卡） */
    private String pageCheckCallbackUrl;
    /*是否弹出错误信息,0是，1否，默认是*/
    private String isShowErrorMsg;
    
    
    public String getIsShowErrorMsg() {
        return isShowErrorMsg;
    }
    public void setIsShowErrorMsg(String isShowErrorMsg) {
        this.isShowErrorMsg = isShowErrorMsg;
    }
    public String getPageCheckCallbackUrl() {
        return pageCheckCallbackUrl;
    }
    public void setPageCheckCallbackUrl(String pageCheckCallbackUrl) {
        this.pageCheckCallbackUrl = pageCheckCallbackUrl;
    }
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public String getRateCode() {
        return rateCode;
    }
    public String getRateCodeView() {
        if (StringUtils.isBlank(this.rateCode)) {
            return null;
        }
     return RateCodeEnum.getRateTypeByCode(this.rateCode).getName();
    }
    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }
    public String getPageCallbackUrl() {
        return pageCallbackUrl;
    }
    public void setPageCallbackUrl(String pageCallbackUrl) {
        this.pageCallbackUrl = pageCallbackUrl;
    }
    public String getServiceNoticeUrl() {
        return serviceNoticeUrl;
    }
    public void setServiceNoticeUrl(String serviceNoticeUrl) {
        this.serviceNoticeUrl = serviceNoticeUrl;
    }
    
}
