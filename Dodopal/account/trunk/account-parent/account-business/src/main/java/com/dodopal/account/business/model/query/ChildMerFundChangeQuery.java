package com.dodopal.account.business.model.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;
/**
 * 子商户资金变更记录查询
 * @author xiongzhijing@dodopal.com
 * @version 2015年9月9日
 *
 */
public class ChildMerFundChangeQuery extends QueryBase {
    private static final long serialVersionUID = 8059925365589212372L;

    //上级商户编号
    private String merParentCode;
    
    //直营网点名称
    private String merName;
    
    //直营网点 商户编号
    private String merCode;
    
    //资金变更记录数据库id
    private String aId;
    
    //资金类别
    private String fundType;

    //变动类型
    private String changeType;
    
    //最小交易金额范围
    private long changeAmountMin;
    
   //最大交易金额范围
    private long changeAmountMax;

    //开始时间
    private Date startDate;
    
    //结束时间
    private Date endDate;

    /**
     * 当前用户ID
     */
    private String createUser;
    
    public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
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

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public long getChangeAmountMin() {
        return changeAmountMin;
    }

    public void setChangeAmountMin(long changeAmountMin) {
        this.changeAmountMin = changeAmountMin;
    }

    public long getChangeAmountMax() {
        return changeAmountMax;
    }

    public void setChangeAmountMax(long changeAmountMax) {
        this.changeAmountMax = changeAmountMax;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
