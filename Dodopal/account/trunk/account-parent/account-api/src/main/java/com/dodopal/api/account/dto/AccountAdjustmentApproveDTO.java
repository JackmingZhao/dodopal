package com.dodopal.api.account.dto;

import java.io.Serializable;

/**
 * 9    账户调帐，输入参数List用DTO
 * 
 * @author dodopal
 *
 */
public class AccountAdjustmentApproveDTO  implements Serializable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8270267553336090764L;

    /**
     * 枚举：个人、企业
     */
    private String custType;

    /**
     * 类型是商户：商户号；类型是个人：用户编号
     */
    private String custNum;

    /**
     * 调账申请单号
     */
    private String tradeNum;
  
    /**
     * 充值的金额，单位为分。必须为正整数。
     */
    private long amount;
    
    /**
     * 账户类型：资金、授信
     */
    private String fundType;
    
    /**
     * 账户交易类型：正调账、反调账
     */
    private String accTranType;
    
    
    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getCustNum() {
        return custNum;
    }

    public void setCustNum(String custNum) {
        this.custNum = custNum;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getAccTranType() {
        return accTranType;
    }

    public void setAccTranType(String accTranType) {
        this.accTranType = accTranType;
    }

}
