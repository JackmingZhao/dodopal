package com.dodopal.product.business.bean;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dodopal.api.users.dto.MerchantUserDTO;
import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.SpringBeanUtil;




public class MerchantBean extends BaseEntity{
	private static final long serialVersionUID = 4775157467920483025L;
	/*商户编码*/
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
    /*经营范围*/
    private String merBusinessScopeId;
    /*商户所属地区*/
    private String merArea;
    /*商户所属省份*/
    private String merPro;
    /*商户所属省份名称*/
    private String merProName;
    /*商户所属城市*/
    private String merCity;
    /*商户所属城市名称*/
    private String merCityName;
    /*用户信息(管理员)*/
    private MerchantUserDTO merchantUserDTO;
    
    
    public MerchantUserDTO getMerchantUserDTO() {
		return merchantUserDTO;
	}

	public void setMerchantUserDTO(MerchantUserDTO merchantUserDTO) {
		this.merchantUserDTO = merchantUserDTO;
	}

	public String getMerProName() {
        return merProName;
    }

    public void setMerProName(String merProName) {
        this.merProName = merProName;
    }

    public String getMerCityName() {
        return merCityName;
    }

    public void setMerCityName(String merCityName) {
        this.merCityName = merCityName;
    }
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
    // ---------------------------------------
    /*都都宝固定IP*/
    private String merDdpLinkIp;
    /*都都宝联系人*/
    private String merDdpLinkUser;
    /*都都宝联系人电话*/
    private String merDdpLinkUserMobile;
    // ---------------------------------------

    public String getMerchantId() {
        return getId();
    }
    
    public String getMerParentType() {
		return merParentType;
	}
	public void setMerParentType(String merParentType) {
		this.merParentType = merParentType;
	}
	public String getMerParentName() {
		return merParentName;
	}
	public void setMerParentName(String merParentName) {
		this.merParentName = merParentName;
	}
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
	public String getMerAdds() {
		return merAdds;
	}
	public void setMerAdds(String merAdds) {
		this.merAdds = merAdds;
	}
	public String getMerType() {
		return merType;
	}
	public void setMerType(String merType) {
		this.merType = merType;
	}
	public String getMerLinkUser() {
		return merLinkUser;
	}
	public void setMerLinkUser(String merLinkUser) {
		this.merLinkUser = merLinkUser;
	}
	public String getMerZip() {
		return merZip;
	}
	public void setMerZip(String merZip) {
		this.merZip = merZip;
	}
	public String getMerLinkUserMobile() {
		return merLinkUserMobile;
	}
	public void setMerLinkUserMobile(String merLinkUserMobile) {
		this.merLinkUserMobile = merLinkUserMobile;
	}
	public String getMerTelephone() {
		return merTelephone;
	}
	public void setMerTelephone(String merTelephone) {
		this.merTelephone = merTelephone;
	}
	public String getMerEmail() {
		return merEmail;
	}
	public void setMerEmail(String merEmail) {
		this.merEmail = merEmail;
	}
	public String getMerParentCode() {
		return merParentCode;
	}
	public void setMerParentCode(String merParentCode) {
		this.merParentCode = merParentCode;
	}
	public String getMerState() {
		return merState;
	}
	public void setMerState(String merState) {
		this.merState = merState;
	}
	public Date getMerActivateDate() {
		return merActivateDate;
	}
	public void setMerActivateDate(Date merActivateDate) {
		this.merActivateDate = merActivateDate;
	}
	public String getActivate() {
		return activate;
	}
	public void setActivate(String activate) {
		this.activate = activate;
	}
	public String getMerDeactivateDate() {
		return merDeactivateDate;
	}
	public void setMerDeactivateDate(String merDeactivateDate) {
		this.merDeactivateDate = merDeactivateDate;
	}
	public String getMerFax() {
		return merFax;
	}
	public void setMerFax(String merFax) {
		this.merFax = merFax;
	}
	public Date getMerRegisterDate() {
		return merRegisterDate;
	}
	public void setMerRegisterDate(Date merRegisterDate) {
		this.merRegisterDate = merRegisterDate;
	}
	public String getMerBankName() {
		return merBankName;
	}
	public void setMerBankName(String merBankName) {
		this.merBankName = merBankName;
	}
	public String getMerBankAccount() {
		return merBankAccount;
	}
	public void setMerBankAccount(String merBankAccount) {
		this.merBankAccount = merBankAccount;
	}
	public String getMerBankUserName() {
		return merBankUserName;
	}
	public void setMerBankUserName(String merBankUserName) {
		this.merBankUserName = merBankUserName;
	}
	public String getMerBusinessScopeId() {
		return merBusinessScopeId;
	}
	public void setMerBusinessScopeId(String merBusinessScopeId) {
		this.merBusinessScopeId = merBusinessScopeId;
	}
	public String getMerArea() {
		return merArea;
	}
	public void setMerArea(String merArea) {
		this.merArea = merArea;
	}
	public String getMerPro() {
		return merPro;
	}
	public void setMerPro(String merPro) {
		this.merPro = merPro;
	}
	public String getMerCity() {
		return merCity;
	}
	public void setMerCity(String merCity) {
		this.merCity = merCity;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public String getMerClassify() {
		return merClassify;
	}
	public void setMerClassify(String merClassify) {
		this.merClassify = merClassify;
	}
	public String getMerProperty() {
		return merProperty;
	}
	public void setMerProperty(String merProperty) {
		this.merProperty = merProperty;
	}
	public String getMerStateUser() {
		return merStateUser;
	}
	public void setMerStateUser(String merStateUser) {
		this.merStateUser = merStateUser;
	}
	public Date getMerStateDate() {
		return merStateDate;
	}
	public void setMerStateDate(Date merStateDate) {
		this.merStateDate = merStateDate;
	}

    public String getMerRejectReason() {
        return merRejectReason;
    }

    public void setMerRejectReason(String merRejectReason) {
        this.merRejectReason = merRejectReason;
    }

    public String getMerDdpLinkIp() {
		return merDdpLinkIp;
	}
	public void setMerDdpLinkIp(String merDdpLinkIp) {
		this.merDdpLinkIp = merDdpLinkIp;
	}
	public String getMerDdpLinkUser() {
		return merDdpLinkUser;
	}
	public void setMerDdpLinkUser(String merDdpLinkUser) {
		this.merDdpLinkUser = merDdpLinkUser;
	}
	public String getMerDdpLinkUserMobile() {
		return merDdpLinkUserMobile;
	}
	public void setMerDdpLinkUserMobile(String merDdpLinkUserMobile) {
		this.merDdpLinkUserMobile = merDdpLinkUserMobile;
	}
	   /**
     * 前端展示  begin 
     */
    public String getMerTypeView() {     
        if (StringUtils.isBlank(this.merType)) {
            return null;
        }
       return MerTypeEnum.getMerTypeByCode(this.merType).getName();
    }
    public String getMerClassifyView() {     
        if (StringUtils.isBlank(this.merClassify)) {
            return null;
        }
       return MerClassifyEnum.getMerClassifyByCode(this.merClassify).getName();
    }
    public String getMerPropertyView() {     
        if (StringUtils.isBlank(this.merProperty)) {
            return null;
        }
       return MerPropertyEnum.getMerPropertyByCode(this.merProperty).getName();
    }
    public String getActivateView() {     
        if (StringUtils.isBlank(this.activate)) {
            return null;
        }
       return ActivateEnum.getActivateByCode(this.activate).getName();
    }
   
    public String getMerBankNameView(){
        return null!=this.merBankName?this.merBankName:"";
    }
    
    public String getMerBusinessScopeIdView(){
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.merBusinessScopeId)) {
            return null;
        }
        return ddicService.getDdicNameByCode("BUSINESS_SCOPE", this.merBusinessScopeId).toString();
    }
    /**
     * 前端展示 end
     */
}
