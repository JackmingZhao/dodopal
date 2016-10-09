package com.dodopal.oss.business.bean;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.FundTypeEnum;
import com.dodopal.common.enums.MerClassifyEnum;
import com.dodopal.common.enums.MerPropertyEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.SpringBeanUtil;
import com.dodopal.oss.business.util.CustomDateSerializer;



public class MerchantBean extends BaseEntity{
    /**
     * 
     */
    private static final long serialVersionUID = 9045771364122813017L;
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
    /*经营范围ID*/
    private String merBusinessScopeId;
	/*经营范围名称*/
	private String merBusinessScopeName;
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
  //--------------新增字段 Time:2016-01-14 Name: Joe
    /*商户打款人*/
    private String merPayMoneyUser;
    
    

    public String getMerPayMoneyUser() {
        return merPayMoneyUser;
    }

    public void setMerPayMoneyUser(String merPayMoneyUser) {
        this.merPayMoneyUser = merPayMoneyUser;
    }
    // ---------------------------------------
    /*账户资金类型*/
    private String fundType;
    
    /*来源*/
    private String source;
    //--------------------------------------
    /*业务信息列表*/
    private List<MerRateSupplementBean> merRateSpmtList;
    
    // ---------------------------------------
    //商户补充信息表
    private MerDdpInfoBean merDdpInfoBean;
    
    public MerDdpInfoBean getMerDdpInfoBean() {
        return merDdpInfoBean;
    }

    public void setMerDdpInfoBean(MerDdpInfoBean merDdpInfoBean) {
        this.merDdpInfoBean = merDdpInfoBean;
    }
    // ---------------------------------------
    /*商户业务费率*/
    private MerBusRateBean[] merBusRateBeanList;
    // ---------------------------------------
    /*用户信息(管理员)*/
    private MerchantUserBean merchantUserBean;
    // ---------------------------------------
    /*商户自定义功能*/
    private MerFunctionInfoBean[] merDefineFunBeanList;
   //---------------------------------------------
    /*商户自定义功能*/
    private List<MerFunctionInfoBean> merFunctionInfoBeanList;
   
    // ---------------------------------------
    /*商户签名密钥*/
    private MerKeyTypeBean merKeyTypeBean;
  
    //----------------------------
    
    // ---------------------------------------
    /*自动分配额度*/
    private MerAutoAmtBean merAutoAmtBean;
    //----------------------------
    
    
    public String getFundType() {
        return fundType;
    }

    public MerAutoAmtBean getMerAutoAmtBean() {
        return merAutoAmtBean;
    }

    public void setMerAutoAmtBean(MerAutoAmtBean merAutoAmtBean) {
        this.merAutoAmtBean = merAutoAmtBean;
    }

    public String getFundTypeView() {
        FundTypeEnum temp = FundTypeEnum.getFundTypeByCode(fundType);
        if (temp == null) {
            return null;
        }
        return temp.getName();
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }
    public List<MerRateSupplementBean> getMerRateSpmtList() {
        return merRateSpmtList;
    }

    public void setMerRateSpmtList(List<MerRateSupplementBean> merRateSpmtList) {
        this.merRateSpmtList = merRateSpmtList;
    }
    public List<MerFunctionInfoBean> getMerFunctionInfoBeanList() {
        return merFunctionInfoBeanList;
    }

    public void setMerFunctionInfoBeanList(List<MerFunctionInfoBean> merFunctionInfoBeanList) {
        this.merFunctionInfoBeanList = merFunctionInfoBeanList;
    }
    public MerKeyTypeBean getMerKeyTypeBean() {
		return merKeyTypeBean;
	}

	public void setMerKeyTypeBean(MerKeyTypeBean merKeyTypeBean) {
		this.merKeyTypeBean = merKeyTypeBean;
	}

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

	public MerBusRateBean[] getMerBusRateBeanList() {
		return merBusRateBeanList;
	}
	public void setMerBusRateBeanList(MerBusRateBean[] merBusRateBeanList) {
		this.merBusRateBeanList = merBusRateBeanList;
	}
	public MerchantUserBean getMerchantUserBean() {
		return merchantUserBean;
	}
	public void setMerchantUserBean(MerchantUserBean merchantUserBean) {
		this.merchantUserBean = merchantUserBean;
	}
	public MerFunctionInfoBean[] getMerDefineFunBeanList() {
		return merDefineFunBeanList;
	}
	public void setMerDefineFunBeanList(
			MerFunctionInfoBean[] merDefineFunBeanList) {
		this.merDefineFunBeanList = merDefineFunBeanList;
	}

    public String getMerAreaName() {
        return merAreaName;
    }

    public void setMerAreaName(String merAreaName) {
        this.merAreaName = merAreaName;
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
    
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    @JsonSerialize(using = CustomDateSerializer.class) 
    public Date getCreateDateView() {
        
        return getCreateDate();
    }
    
    @JsonSerialize(using = CustomDateSerializer.class) 
    public Date getUpdateDateView() {
        
        return getUpdateDate();
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

	public String getMerBusinessScopeName() {
		return merBusinessScopeName;
	}

	public void setMerBusinessScopeName(String merBusinessScopeName) {
		this.merBusinessScopeName = merBusinessScopeName;
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
    public String getProviderNameView(){
        //TODO 通卡公司名称，先放
        MerBusRateBean[] arrayBean = this.merBusRateBeanList;
        StringBuffer str = new StringBuffer();
        if(null!=arrayBean&&arrayBean.length>0){
            for(MerBusRateBean bean:arrayBean){
                str.append(bean.getProName());
            }
        }
        return str.toString();
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
}
