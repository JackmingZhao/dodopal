package com.dodopal.oss.business.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.SpringBeanUtil;

public class MerchantNotUnauditedExpBean extends BaseEntity{

    /**
     * 
     */
    private static final long serialVersionUID = 546279318373747496L;
    private String merCode;
    /*商户名称*/
    private String merName;
    /*商户地址*/
    private String merAdds;
    /*商户类型*/
    private String merType;
    /*商户联系人*/
    private String merLinkUser;
    /*商户邮编*/
    private String merZip;
    /*商户联系人电话*/
    private String merLinkUserMobile;
    /*商户固定电话*/
    private String merTelephone;
    /*商户邮箱*/
    private String merEmail;
    /*所属上级商户编码*/
    private String merParentCode;
    /*上级商户类型*/
    private String merParentType;
    /*上级商户名称*/
    private String merParentName;
    /*审核状态*/
    private String merState;
    /*激活时间*/
    private Date merActivateDate;
    /*启用标示*/
    private String activate;
    /*停用/启用时间*/
    private String merDeactivateDate;
    /*传真*/
    private String merFax;
    /*注册时间*/
    private Date merRegisterDate;
    /*开户银行*/
    private String merBankName;
    /*开户行账号*/
    private String merBankAccount;
    /*开户名称*/
    private String merBankUserName;
    /*经营范围ID*/
    private String merBusinessScopeId;
    /*商户所属地区*/
    private String merArea;
    /*商户所属地区名称*/
    private String merAreaName;
    /*商户所属省份*/
    private String merPro;
    /*商户所属省份名称*/
    private String merProName;
    /*商户所属城市*/
    private String merCity;
    /*商户所属城市名称*/
    private String merCityName;
    /*删除标示*/
    private String delFlg;
    /*商户分类*/
    private String merClassify;
    /*商户属性*/
    private String merProperty;
    /*审核人*/
    private String merStateUser;
    /*审核日期*/
    private Date merStateDate;
    /*审核不通过原因*/
    private String merRejectReason;
    /*账户资金类型*/
    private String fundType;
    /*来源*/
    private String source;
    // -----------//商户补充信息表----------------------------
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
    public String getMerCode() {
        return merCode;
    }
    public String getMerName() {
        return merName;
    }
    public String getMerAdds() {
        return merAdds;
    }
    public String getMerType() {
        return merType;
    }
    public String getMerLinkUser() {
        return merLinkUser;
    }
    public String getMerZip() {
        return merZip;
    }
    public String getMerLinkUserMobile() {
        return merLinkUserMobile;
    }
    public String getMerTelephone() {
        return merTelephone;
    }
    public String getMerEmail() {
        return merEmail;
    }
    public String getMerParentCode() {
        return merParentCode;
    }
    public String getMerParentType() {
        return merParentType;
    }
    public String getMerParentName() {
        return merParentName;
    }
    public String getMerState() {
        return merState;
    }
    public Date getMerActivateDate() {
        return merActivateDate;
    }
    public String getActivate() {
        return activate;
    }
    public String getMerDeactivateDate() {
        return merDeactivateDate;
    }
    public String getMerFax() {
        return merFax;
    }
    public Date getMerRegisterDate() {
        return merRegisterDate;
    }
    public String getMerBankName() {
        return merBankName;
    }
    public String getMerBankAccount() {
        return merBankAccount;
    }
    public String getMerBankUserName() {
        return merBankUserName;
    }
    public String getMerBusinessScopeId() {
        return merBusinessScopeId;
    }
    public String getMerArea() {
        return merArea;
    }
    public String getMerAreaName() {
        return merAreaName;
    }
    public String getMerPro() {
        return merPro;
    }
    public String getMerProName() {
        return merProName;
    }
    public String getMerCity() {
        return merCity;
    }
    public String getMerCityName() {
        return merCityName;
    }
    public String getDelFlg() {
        return delFlg;
    }
    public String getMerClassify() {
        return merClassify;
    }
    public String getMerProperty() {
        return merProperty;
    }
    public String getMerStateUser() {
        return merStateUser;
    }
    public Date getMerStateDate() {
        return merStateDate;
    }
    public String getMerRejectReason() {
        return merRejectReason;
    }
    public String getFundType() {
        return fundType;
    }
    public String getSource() {
        return source;
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
    public Long getSettlementThreshold() {
        return settlementThreshold;
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
    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }
    public void setMerName(String merName) {
        this.merName = merName;
    }
    public void setMerAdds(String merAdds) {
        this.merAdds = merAdds;
    }
    public void setMerType(String merType) {
        this.merType = merType;
    }
    public void setMerLinkUser(String merLinkUser) {
        this.merLinkUser = merLinkUser;
    }
    public void setMerZip(String merZip) {
        this.merZip = merZip;
    }
    public void setMerLinkUserMobile(String merLinkUserMobile) {
        this.merLinkUserMobile = merLinkUserMobile;
    }
    public void setMerTelephone(String merTelephone) {
        this.merTelephone = merTelephone;
    }
    public void setMerEmail(String merEmail) {
        this.merEmail = merEmail;
    }
    public void setMerParentCode(String merParentCode) {
        this.merParentCode = merParentCode;
    }
    public void setMerParentType(String merParentType) {
        this.merParentType = merParentType;
    }
    public void setMerParentName(String merParentName) {
        this.merParentName = merParentName;
    }
    public void setMerState(String merState) {
        this.merState = merState;
    }
    public void setMerActivateDate(Date merActivateDate) {
        this.merActivateDate = merActivateDate;
    }
    public void setActivate(String activate) {
        this.activate = activate;
    }
    public void setMerDeactivateDate(String merDeactivateDate) {
        this.merDeactivateDate = merDeactivateDate;
    }
    public void setMerFax(String merFax) {
        this.merFax = merFax;
    }
    public void setMerRegisterDate(Date merRegisterDate) {
        this.merRegisterDate = merRegisterDate;
    }
    public void setMerBankName(String merBankName) {
        this.merBankName = merBankName;
    }
    public void setMerBankAccount(String merBankAccount) {
        this.merBankAccount = merBankAccount;
    }
    public void setMerBankUserName(String merBankUserName) {
        this.merBankUserName = merBankUserName;
    }
    public void setMerBusinessScopeId(String merBusinessScopeId) {
        this.merBusinessScopeId = merBusinessScopeId;
    }
    public void setMerArea(String merArea) {
        this.merArea = merArea;
    }
    public void setMerAreaName(String merAreaName) {
        this.merAreaName = merAreaName;
    }
    public void setMerPro(String merPro) {
        this.merPro = merPro;
    }
    public void setMerProName(String merProName) {
        this.merProName = merProName;
    }
    public void setMerCity(String merCity) {
        this.merCity = merCity;
    }
    public void setMerCityName(String merCityName) {
        this.merCityName = merCityName;
    }
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }
    public void setMerClassify(String merClassify) {
        this.merClassify = merClassify;
    }
    public void setMerProperty(String merProperty) {
        this.merProperty = merProperty;
    }
    public void setMerStateUser(String merStateUser) {
        this.merStateUser = merStateUser;
    }
    public void setMerStateDate(Date merStateDate) {
        this.merStateDate = merStateDate;
    }
    public void setMerRejectReason(String merRejectReason) {
        this.merRejectReason = merRejectReason;
    }
    public void setFundType(String fundType) {
        this.fundType = fundType;
    }
    public void setSource(String source) {
        this.source = source;
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
    public void setSettlementThreshold(Long settlementThreshold) {
        this.settlementThreshold = settlementThreshold;
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
    /**
     * 前端展示  begin 
     */
    //商户类型
    public String getMerTypeView() {     
        if (StringUtils.isBlank(this.merType)) {
            return null;
        }
       return MerTypeEnum.getMerTypeByCode(this.merType).getName();
    }
    //上级商户类型
    public String getMerParentTypeView() {     
        if (StringUtils.isBlank(this.merParentType)) {
            return null;
        }
       return MerTypeEnum.getMerTypeByCode(this.merParentType).getName();
    }
    //商户分类
    public String getMerClassifyView() {     
        if (StringUtils.isBlank(this.merClassify)) {
            return null;
        }
       return MerClassifyEnum.getMerClassifyByCode(this.merClassify).getName();
    }
    //商户属性
    public String getMerPropertyView() {     
        if (StringUtils.isBlank(this.merProperty)) {
            return null;
        }
       return MerPropertyEnum.getMerPropertyByCode(this.merProperty).getName();
    }
  //停启用
    public String getActivateView() {
        if (StringUtils.isBlank(this.activate)) {
            return null;
        }
       return ActivateEnum.getActivateByCode(this.activate).getName();
    }
    //来源
    public String getSourceView() {     
        if (StringUtils.isBlank(this.source)) {
            return null;
        }
       return SourceEnum.getSourceByCode(this.source).getName();
    }
    //开户银行
    public String getMerBankNameView(){
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.merBankName)) {
            return null;
        }
        return ddicService.getDdicNameByCode("MER_BANK", this.merBankName).toString();
    }
    //经营范围
    public String getMerBusinessScopeIdView() {
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.merBusinessScopeId)) {
            return null;
        }
        return ddicService.getDdicNameByCode("BUSINESS_SCOPE", this.merBusinessScopeId).toString();
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
    //资金类型
    public String getFundTypeView() {
        FundTypeEnum temp = FundTypeEnum.getFundTypeByCode(fundType);
        if (temp == null) {
            return null;
        }
        return temp.getName();
    }
}
