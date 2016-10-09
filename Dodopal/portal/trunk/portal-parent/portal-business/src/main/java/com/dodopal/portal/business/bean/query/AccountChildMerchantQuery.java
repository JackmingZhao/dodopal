package com.dodopal.portal.business.bean.query;

import com.dodopal.common.model.QueryBase;

/**
 * 查询子商户的账户信息
 * @author hxc
 * @version 2015年9月9日
 *
 */
public class AccountChildMerchantQuery extends QueryBase{
	private static final long serialVersionUID = -5736638371778969881L;
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
    
    
}
