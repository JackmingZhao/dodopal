package com.dodopal.portal.business.bean;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.SpringBeanUtil;

public class MerDdpInfoBean  extends BaseEntity{ 
    private static final long serialVersionUID = -8336420437785213724L;
    /*启用标示*/
    public String activate;
    /*商户编码*/
    private String merCode;
    /*都都宝固定IP*/
    private String merDdpLinkIp;
    /*都都宝联系人*/
    private String merDdpLinkUser;
    /*都都宝联系人电话*/
    private String merDdpLinkUserMobile;
    /*结算类型*/
    private String settlementType;
    /*结算阀值*/
    private Long settlementThreshold;
    /*是否自动分配额度,0是，1否，默认否*/
    private String isAutoDistribute;
    /*商圈*/
    private String tradeArea;
    /*0：自己购买，1：上级分配；默认自己购买*/
    private String limitSource;
    public String getActivate() {
        return activate;
    }
    public String getMerCode() {
        return merCode;
    }
    public String getMerDdpLinkIp() {
        return merDdpLinkIp;
    }
    public String getMerDdpLinkUser() {
        return merDdpLinkUser;
    }
    public String getMerDdpLinkUserMobile() {
        return merDdpLinkUserMobile;
    }
    public String getSettlementType() {
        return settlementType;
    }
    public String getIsAutoDistribute() {
        return isAutoDistribute;
    }
    public String getTradeArea() {
        return tradeArea;
    }
    public String getLimitSource() {
        return limitSource;
    }
    public void setActivate(String activate) {
        this.activate = activate;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public void setMerDdpLinkIp(String merDdpLinkIp) {
        this.merDdpLinkIp = merDdpLinkIp;
    }
    public void setMerDdpLinkUser(String merDdpLinkUser) {
        this.merDdpLinkUser = merDdpLinkUser;
    }
    public void setMerDdpLinkUserMobile(String merDdpLinkUserMobile) {
        this.merDdpLinkUserMobile = merDdpLinkUserMobile;
    }
    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }
    public void setIsAutoDistribute(String isAutoDistribute) {
        this.isAutoDistribute = isAutoDistribute;
    }
    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
    }
    public void setLimitSource(String limitSource) {
        this.limitSource = limitSource;
    }
    
    public Long getSettlementThreshold() {
        return settlementThreshold;
    }
    public void setSettlementThreshold(Long settlementThreshold) {
        this.settlementThreshold = settlementThreshold;
    }
    //结算类型
    public String getSettlementTypeView(){
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.settlementType)) {
            return null;
        }
        return ddicService.getDdicNameByCode("SETTLEMENT_TYPE", this.settlementType).toString();
    }
    /*是否自动分配额度,0是，1否，默认否*/
    public String getIsAutoDistributeView(){
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.isAutoDistribute)) {
            return null;
        }
        return ddicService.getDdicNameByCode("IS_AUTO_DISTRIBUTE", this.isAutoDistribute).toString();
    }
    /*0：自己购买，1：上级分配；默认自己购买*/
    public String getLimitSourceView(){
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.limitSource)) {
            return null;
        }
        return ddicService.getDdicNameByCode("LIMIT_SOURCE_TYPE", this.limitSource).toString();
    }
}
