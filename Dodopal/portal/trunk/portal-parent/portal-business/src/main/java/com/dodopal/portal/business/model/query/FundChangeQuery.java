package com.dodopal.portal.business.model.query;

import com.dodopal.common.model.QueryBase;

public class FundChangeQuery extends QueryBase{

	private static final long serialVersionUID = -376802042830982625L;

	//主账户数据库id
    public String acid;
    
    //资金类别
    public String fundType;

    //变动类型
    public String changeType;
    
    //最小交易金额范围
    public String changeAmountMin;
    
   //最大交易金额范围
    public String changeAmountMax;

    //开始时间
    private String startDate;
    
    //结束时间
    private String endDate;
    
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

	public String getAcid() {
		return acid;
	}

	public void setAcid(String acid) {
		this.acid = acid;
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

	public String getChangeAmountMin() {
		return changeAmountMin;
	}

	public void setChangeAmountMin(String changeAmountMin) {
		this.changeAmountMin = changeAmountMin;
	}

	public String getChangeAmountMax() {
		return changeAmountMax;
	}

	public void setChangeAmountMax(String changeAmountMax) {
		this.changeAmountMax = changeAmountMax;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
    
    
}
