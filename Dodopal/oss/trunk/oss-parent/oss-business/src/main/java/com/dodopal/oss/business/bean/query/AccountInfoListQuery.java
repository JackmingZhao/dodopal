package com.dodopal.oss.business.bean.query;

import com.dodopal.common.model.QueryBase;

/**
 * 资金授信账户信息列表查询条件
 * @author guanjinglun
 */
public class AccountInfoListQuery extends QueryBase {

    private static final long serialVersionUID = -7603292133225556858L;

    /*
     * 枚举：类型：个人、企业
     */
    public String aType;

    /*
     * 商户号（个人用户编号）模糊查询
     */
    public String custNum;

    /*
     * 商户(个人)名称；模糊查询
     */
    public String custName;

    /*
     * 账户状态；精确。默认查询正常状态的账户。
     */
    public String status;

    /*
     * 账户类别;0-资金;1-授信
     */
    public String fundType;

    public String getaType() {
        return aType;
    }

    public void setaType(String aType) {
        this.aType = aType;
    }

    public String getCustNum() {
        return custNum;
    }

    public void setCustNum(String custNum) {
        this.custNum = custNum;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    @Override
    public String toString() {
        return "AccountInfoListQuery [aType=" + aType + ", custNum=" + custNum + ", custName=" + custName + ", status=" + status + ",fundType=" + fundType + "]";
    }

}
