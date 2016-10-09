package com.dodopal.oss.business.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dodopal.common.enums.ActivateEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.SourceEnum;
import com.dodopal.common.enums.IdentityTypeEnum;
import com.dodopal.common.enums.MerStateEnum;
import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.enums.SexEnum;
import com.dodopal.common.model.BaseEntity;
import com.dodopal.common.service.DdicService;
import com.dodopal.common.util.SpringBeanUtil;
import com.dodopal.oss.business.util.CustomDateSerializer;
import com.dodopal.oss.business.util.DateJsonDeserializer;

public class MerchantUserBean extends BaseEntity implements Serializable{

    
    /**
     * 
     */
    private static final long serialVersionUID = 5133007680932321656L;

    //用户标志
    private String merUserFlag;   
    
    //用户类型
    private String merUserType;
    
    //商户编码
    private String merCode;
    
    //商户用户登录账号
    private String merUserName;
    
    //商户用户登录密码
    private String merUserPWD;
    
    //商户用户证件类型
    private String merUserIdentityType;
    
    //商户用户证件号码
    private String merUserIdentityNumber;
    
    //商户用户昵称（真实姓名）
    private String merUserNickName;
    
    //用户性别
    private String merUserSex;
    //用户手机号
    private String merUserMobile;
    
    //用户固定电话
    private String merUserTelephone;
    
    //用户地址
    private String merUserAdds;
    
    //用户生日
    private Date merUserBirthday;
    
    //用户邮箱
    private String merUserEmail;
    
    //用户入职时间
    private Date merUserEmployeeDate;
    
    //用户最后一次登录时间
    private Date merUserLastLoginDate;
    
    //用户最后一次登录IP
    private String merUserLastLoginIp;
    
    //用户连续登陆次数
    private int merUserLoginFaiCount;
    
    //锁定用户时间
    private Date merUserLockedDate;
    
    //备注
    private String merUserRemark;
    
    //启用标志
    private String activate;
    
    //删除标志
    private String delFlag;
    
    //用户编号
    private String userCode;
    
    //用户注册来源
    private String merUserSource;
    
    //支付确认信息
    private String payInfoFlg;
    
    //默认开通一卡通编号
    private String cityCode;
    
    //开户时间起始
    private Date createDateStart;
    //开户结束
    private Date createDateEnd;
    //所属商户名称
    private String merchantName;
    
    //所属商户类型
    private String merchantType;
    //审核人
    private String merchantStateUser;
    /*审核日期*/
    private Date merchantStateDate;
    /*审核状态*/
    private String merchantState;  
    //用户管辖部门ID
    private List<String> merGroupDeptList;
    
    //新增字段 ----2012-12-05 -------
    /*学历*/
    private String education;
    /*收入*/
    private Long income;
    /*生日，格式：1990-09-10*/
    private String birthday;
    /*是否已婚，0是，1否*/
    private String isMarried;
    /*商圈*/
    private String tradeArea;
    //-------------新增省份，城市   Time:2016-01-13 Name: Joe -----------
    /* 用户所属省份 */
    private String merUserPro;
    /* 用户所属城市 */
    private String merUserCity;
    
    /* 用户所属省份名称 */
    private String merUserProName;
    /* 用户所属城市名称 */
    private String merUserCityName;
    
    
    public String getMerUserProName() {
        return merUserProName;
    }

    public void setMerUserProName(String merUserProName) {
        this.merUserProName = merUserProName;
    }

    public String getMerUserCityName() {
        return merUserCityName;
    }

    public void setMerUserCityName(String merUserCityName) {
        this.merUserCityName = merUserCityName;
    }

    public String getMerUserPro() {
        return merUserPro;
    }

    public void setMerUserPro(String merUserPro) {
        this.merUserPro = merUserPro;
    }

    public String getMerUserCity() {
        return merUserCity;
    }

    public void setMerUserCity(String merUserCity) {
        this.merUserCity = merUserCity;
    }

    public String getEducation() {
        return education;
    }

    public Long getIncome() {
        return income;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getIsMarried() {
        return isMarried;
    }

    public String getTradeArea() {
        return tradeArea;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setIsMarried(String isMarried) {
        this.isMarried = isMarried;
    }

    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
    }

    public String getMerchantStateUser() {
        return merchantStateUser;
    }

    public void setMerchantStateUser(String merchantStateUser) {
        this.merchantStateUser = merchantStateUser;
    }

    public Date getMerchantStateDate() {
        return merchantStateDate;
    }

    public void setMerchantStateDate(Date merchantStateDate) {
        this.merchantStateDate = merchantStateDate;
    }

    public String getMerchantState() {
        return merchantState;
    }

    public void setMerchantState(String merchantState) {
        this.merchantState = merchantState;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getMerUserFlag() {
        return merUserFlag;
    }

    public void setMerUserFlag(String merUserFlag) {
        this.merUserFlag = merUserFlag;
    }

    public String getMerUserType() {
        return merUserType;
    }

    public void setMerUserType(String merUserType) {
        this.merUserType = merUserType;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerUserName() {
        return merUserName;
    }

    public void setMerUserName(String merUserName) {
        this.merUserName = merUserName;
    }

    public String getMerUserPWD() {
        return merUserPWD;
    }

    public void setMerUserPWD(String merUserPWD) {
        this.merUserPWD = merUserPWD;
    }

    public String getMerUserIdentityType() {
        return merUserIdentityType;
    }

    public void setMerUserIdentityType(String merUserIdentityType) {
        this.merUserIdentityType = merUserIdentityType;
    }

    public String getMerUserIdentityNumber() {
        return merUserIdentityNumber;
    }

    public void setMerUserIdentityNumber(String merUserIdentityNumber) {
        this.merUserIdentityNumber = merUserIdentityNumber;
    }

    public String getMerUserNickName() {
        return merUserNickName;
    }

    public void setMerUserNickName(String merUserNickName) {
        this.merUserNickName = merUserNickName;
    }

    public String getMerUserSex() {
        return merUserSex;
    }
    public String getMerUserSexStr() {
        if (StringUtils.isBlank(merUserSex)) {
            return null;
        }       
        if(SexEnum.getSexByCode(this.merUserSex)==null){
            return null;
        }else{
            return SexEnum.getSexByCode(this.merUserSex).getName();
        }
    }
    
  
    public void setMerUserSex(String merUserSex) {
        this.merUserSex = merUserSex;
    }

    public String getMerUserMobile() {
        return merUserMobile;
    }

    public void setMerUserMobile(String merUserMobile) {
        this.merUserMobile = merUserMobile;
    }

    public String getMerUserTelephone() {
        return merUserTelephone;
    }

    public void setMerUserTelephone(String merUserTelephone) {
        this.merUserTelephone = merUserTelephone;
    }

    public String getMerUserAdds() {
        return merUserAdds;
    }

    public void setMerUserAdds(String merUserAdds) {
        this.merUserAdds = merUserAdds;
    }

    public Date getMerUserBirthday() {
        return merUserBirthday;
    }

    public void setMerUserBirthday(Date merUserBirthday) {
        this.merUserBirthday = merUserBirthday;
    }

    public String getMerUserEmail() {
        return merUserEmail;
    }

    public void setMerUserEmail(String merUserEmail) {
        this.merUserEmail = merUserEmail;
    }

    public Date getMerUserEmployeeDate() {
        return merUserEmployeeDate;
    }

    public void setMerUserEmployeeDate(Date merUserEmployeeDate) {
        this.merUserEmployeeDate = merUserEmployeeDate;
    }

    public Date getMerUserLastLoginDate() {
        return merUserLastLoginDate;
    }

    public void setMerUserLastLoginDate(Date merUserLastLoginDate) {
        this.merUserLastLoginDate = merUserLastLoginDate;
    }

    public String getMerUserLastLoginIp() {
        return merUserLastLoginIp;
    }

    public void setMerUserLastLoginIp(String merUserLastLoginIp) {
        this.merUserLastLoginIp = merUserLastLoginIp;
    }

    public int getMerUserLoginFaiCount() {
        return merUserLoginFaiCount;
    }

    public void setMerUserLoginFaiCount(int merUserLoginFaiCount) {
        this.merUserLoginFaiCount = merUserLoginFaiCount;
    }

    public Date getMerUserLockedDate() {
        return merUserLockedDate;
    }

    public void setMerUserLockedDate(Date merUserLockedDate) {
        this.merUserLockedDate = merUserLockedDate;
    }

    public String getMerUserRemark() {
        return merUserRemark;
    }

    public void setMerUserRemark(String merUserRemark) {
        this.merUserRemark = merUserRemark;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getMerUserSource() {
        return merUserSource;
    }

    public void setMerUserSource(String merUserSource) {
        this.merUserSource = merUserSource;
    }

    public String getPayInfoFlg() {
        return payInfoFlg;
    }

    public void setPayInfoFlg(String payInfoFlg) {
        this.payInfoFlg = payInfoFlg;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    } 

    
    public Date getCreateDateStart() {
		return createDateStart;
	}

    @JsonDeserialize(using = DateJsonDeserializer.class)
	public void setCreateDateStart(Date createDateStart) {
		this.createDateStart = createDateStart;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	@JsonDeserialize(using = DateJsonDeserializer.class)
	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	@JsonSerialize(using = CustomDateSerializer.class) 
    public Date getCreateDateView() {
    	
        return getCreateDate();
    }
    
    @JsonSerialize(using = CustomDateSerializer.class) 
    public Date getUpdateDateView() {
    	
        return getUpdateDate();
    }
    public String getMerUserSexView() {    	
    	 if (StringUtils.isBlank(this.merUserSex)) {
             return null;
         }
        return SexEnum.getSexByCode(this.merUserSex).getName();
    }
    public String getMerUserIdentityTypeView() {     
        if (StringUtils.isBlank(this.merUserIdentityType)) {
            return "";
        }
       return IdentityTypeEnum.getIdentityTypeByCode(this.merUserIdentityType).getName();
   }
 
    public String getMerUserSourceView() {     
        if (StringUtils.isBlank(this.merUserSource)) {
            return "";
        }
       return SourceEnum.getSourceByCode(this.merUserSource).getName();
   }
    
    public String getMerUserFlagView() {     
        if (StringUtils.isBlank(this.merUserFlag)) {
            return null;
        }
        return MerUserFlgEnum.getMerUserFlgByCode(this.merUserFlag).getName();
    }
    
    public String getActivateView() {     
        if (StringUtils.isBlank(this.activate)) {
            return null;
        }
        return ActivateEnum.getActivateByCode(this.activate).getName();
    }
    public String getUserId() {
    	
        return getId();
    }


    public List<String> getMerGroupDeptList() {
        return merGroupDeptList;
    }

    public void setMerGroupDeptList(List<String> merGroupDeptList) {
        this.merGroupDeptList = merGroupDeptList;
    }

    @Override
    public String toString() {
        return "MerchantUser [merUserFlag=" + merUserFlag + ", merUserType=" + merUserType + ", merCode=" + merCode + ", merUserName=" + merUserName + ", merUserPWD=" + merUserPWD + ", merUserIdentityType=" + merUserIdentityType + ", merUserIdentityNumber=" + merUserIdentityNumber + ", merUserNickName=" + merUserNickName + ", merUserSex=" + merUserSex + ", merUserMobile=" + merUserMobile + ", merUserTelephone=" + merUserTelephone + ", merUserAdds=" + merUserAdds + ", merUserBirthday=" + merUserBirthday
            + ", merUserEmail=" + merUserEmail + ", merUserEmployeeDate=" + merUserEmployeeDate + ", merUserLastLoginDate=" + merUserLastLoginDate + ", merUserLastLoginIp=" + merUserLastLoginIp + ", merUserLoginFaiCount=" + merUserLoginFaiCount + ", merUserLockedDate=" + merUserLockedDate + ", merUserRemark=" + merUserRemark + ", activate=" + activate + ", delFlag=" + delFlag + ", userCode=" + userCode + ", merUserSource=" + merUserSource + ", payInfoFlg=" + payInfoFlg + ", cityCode=" + cityCode + "]";
    }
    
    //学历
    public String getEducationView(){
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.education)) {
            return null;
        }
        return ddicService.getDdicNameByCode("EDUCATION_TYPE", this.education).toString();
    }
    //是否已婚
    public String getIsMarriedView(){
        DdicService ddicService = (DdicService) SpringBeanUtil.getBean("ddicService");
        if (StringUtils.isBlank(this.isMarried)) {
            return null;
        }
        return ddicService.getDdicNameByCode("IS_MARRIED_TYPE", this.isMarried).toString();
    }
    //商户类型
    public String getMerchantTypeView() {     
        if (StringUtils.isBlank(this.merchantType)) {
            return null;
        }
       return MerTypeEnum.getMerTypeByCode(this.merchantType).getName();
    }
    
    //审核状态
    public String getMerchantStateView() {     
        if (StringUtils.isBlank(this.merchantState)) {
            return null;
        }
       return MerStateEnum.getMerStateByCode(this.merchantState).getName();
    }
}
