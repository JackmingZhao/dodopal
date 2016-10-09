package com.dodopal.product.business.model.query;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

public class ProLoadOrderQuery extends QueryBase {

    private static final long serialVersionUID = 8562654930812206602L;

    //订单编号
    private String loadOrderNum;
    //订单状态
    private String loadOrderState;
    //订单创建时间
    private Date startDate;
    //结束时间
    private Date endDate;
    //卡号
    private String cardNo;
    //业务城市
    private String cityCode;
    //起始金额
    private String txnAmtStart;
    //结束金额
    private String txnAmtEnd;
    //用户编号
    private String userCode;

    public String getLoadOrderNum() {
        return loadOrderNum;
    }

    public void setLoadOrderNum(String loadOrderNum) {
        this.loadOrderNum = loadOrderNum;
    }

    public String getLoadOrderState() {
        return loadOrderState;
    }

    public void setLoadOrderState(String loadOrderState) {
        this.loadOrderState = loadOrderState;
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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getTxnAmtStart() {
        return txnAmtStart;
    }

    public void setTxnAmtStart(String txnAmtStart) {
        this.txnAmtStart = txnAmtStart;
    }

    public String getTxnAmtEnd() {
        return txnAmtEnd;
    }

    public void setTxnAmtEnd(String txnAmtEnd) {
        this.txnAmtEnd = txnAmtEnd;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

}
