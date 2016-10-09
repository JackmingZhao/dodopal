package com.dodopal.portal.business.bean.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;
/**
 * 子商户资金变更记录查询
 * @author hxc
 *
 */
public class ChildMerFundChangeQuery extends QueryBase{
    private static final long serialVersionUID = 4246268526249560080L;

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
    private String changeAmountMin;
    
   //最大交易金额范围
    private String changeAmountMax;

    //开始时间
    private String startDate;
    
    //结束时间
    private String endDate;

    //用户ID
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

	@Override
    public String toString() {
        return "ChildMerFundChangeQueryDTO [merParentCode=" + merParentCode + ", merName=" + merName + ", fundType=" + fundType + ", changeType=" + changeType + ", changeAmountMin=" + changeAmountMin + ", changeAmountMax=" + changeAmountMax + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
    
    
}
