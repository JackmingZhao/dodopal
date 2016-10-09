package com.dodopal.api.account.dto.query;

import com.dodopal.common.model.QueryBase;
/**
 * 查询子商户的账户信息
 * @author xiongzhijing@dodopal.com
 * @version 2015年9月8日
 *
 */
public class AccountChildMerchantQueryDTO extends QueryBase{
    private static final long serialVersionUID = -7221980215115526119L;
    /**
     * 上级商户编号
     */
    private String merParentCode;
    /**
     * 商户名称
     */
    private String merName;
    
    /**
     * 账户类别
     */
    private String fundType;
    
    public String getFundType() {
        return fundType;
    }
    public void setFundType(String fundType) {
        this.fundType = fundType;
    }
    public String getMerParentCode() {
        return merParentCode;
    }
    public void setMerParentCode(String merParentCode) {
        this.merParentCode = merParentCode;
    }
    public String getMerName() {
        return merName;
    }
    public void setMerName(String merName) {
        this.merName = merName;
    }
    @Override
    public String toString() {
        return "AccountChildMerchantQueryDTO [merParentCode=" + merParentCode + ", merName=" + merName + ", fundType=" + fundType + "]";
    }
    

}
