package com.dodopal.product.business.model.query;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.dodopal.common.enums.BJConsumptionOrderStatusEnum;
import com.dodopal.common.enums.RechargeOrderResultEnum;
import com.dodopal.common.model.QueryBase;

public class ConsumptionOrderQuery extends QueryBase{
    private static final long serialVersionUID = -3861074151589530006L;
    private static final String [] accounting = {"1000000001"};
    private static final String [] success = {"1000000006","1000000007","1000000010","1000000012","1000000013"};
    private static final String [] fail = {"1000000002","1000000003","1000000004","1000000008","1000000009","1000000011","1000000014","1000000015","1000000016"};        
    private static final String [] suspicious = {"1000000005","1000000017","1000000018"};
    
    
    /** 商户号*/
    private String mchnitid;
    /** 商户订单号*/
    private String transactionid;
    /** 查询起始时间*/
    private Date startdate;
    /** 查询结束时间*/
    private Date enddate;
    /** Pos编号*/
    private String posid;
    /** 卡号 */
    private String cardno;
    /** 用户名称 */
    private String username;
    /** 订单状态 */
    private String status;
    
    private List<String> statusList;
    
    
    public String getMchnitid() {
        return mchnitid;
    }
    public void setMchnitid(String mchnitid) {
        this.mchnitid = mchnitid;
    }
    public String getTransactionid() {
        return transactionid;
    }
    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }
    public Date getStartdate() {
        return startdate;
    }
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }
    public Date getEnddate() {
        return enddate;
    }
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
    public String getPosid() {
        return posid;
    }
    public void setPosid(String posid) {
        this.posid = posid;
    }
    public String getCardno() {
        return cardno;
    }
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
        if(BJConsumptionOrderStatusEnum.ACCOUNTING.getCode().equals(status)){
            setAccountingStatusList();
        }else if(BJConsumptionOrderStatusEnum.SUCCESS.getCode().equals(status)){
            setSuccessStatusList();
        }else if(BJConsumptionOrderStatusEnum.FAIL.getCode().equals(status)){
            setFailStatus();
        }else if(BJConsumptionOrderStatusEnum.SUSPICIOUS.getCode().equals(status)){
            setSuspiciousStatus();
        }
    }
    
    public void setSuccessStatusList() {
        this.statusList = Arrays.asList(success);
    }
    public void setAccountingStatusList() {
        this.statusList = Arrays.asList(accounting);
    }
    public void setFailStatus() {
        this.statusList = Arrays.asList(fail);
    }
    
    public void setSuspiciousStatus() {
        this.statusList = Arrays.asList(suspicious);
    }
    public List<String> getStatusList() {
        return statusList;
    }
    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }
    
}
