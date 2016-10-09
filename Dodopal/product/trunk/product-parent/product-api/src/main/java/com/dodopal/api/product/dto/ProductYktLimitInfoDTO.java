package com.dodopal.api.product.dto;

import java.util.Date;

import com.dodopal.common.model.BaseEntity;

public class ProductYktLimitInfoDTO extends BaseEntity {

    private static final long serialVersionUID = 3669852915446368405L;

    /**
     * 通卡公司编号
     */
    private String yktCode;
    
    /**
     * 通卡公司名称
     */
    private String yktName;

    /**
     * 报警额度:在进行公交卡充值业务的时候，如果发现此时的剩余额度小于设定的报警额度，系统将会发出报警信息给相关的管理人员
     */
    private Long yktWarnLimit;

    /**
     * 终止充值额度:在进行公交卡充值业务的时候，如果发现此时的剩余额度小于设定的终止充值额度，提示用户以及发出报警信息给相关的管理人员，系统将停止公交卡充值业务
     */
    private Long yktStopLimit;

    /**
     * 有效期:都都宝平台与通卡公司进行交易的有效截止日期，当达到此时间段的时候，会暂停交易，并且会给予消息提示
     */
    private Date yktExpireDate;

    /**
     * 购买总额度:表示历次购买额度的总和
     */
    private Long totalAmtLimit;

    /**
     * 剩余额度
     */
    private Long surPlusLimit;

    /**
     * 已使用的额度
     */
    private Long usedLimit;

    /**
     * 备注
     */
    private String remark;

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public Long getYktWarnLimit() {
        return yktWarnLimit;
    }

    public void setYktWarnLimit(Long yktWarnLimit) {
        this.yktWarnLimit = yktWarnLimit;
    }

    public Long getYktStopLimit() {
        return yktStopLimit;
    }

    public void setYktStopLimit(Long yktStopLimit) {
        this.yktStopLimit = yktStopLimit;
    }

    public Long getTotalAmtLimit() {
        return totalAmtLimit;
    }

    public void setTotalAmtLimit(Long totalAmtLimit) {
        this.totalAmtLimit = totalAmtLimit;
    }

    public Long getSurPlusLimit() {
        return surPlusLimit;
    }

    public void setSurPlusLimit(Long surPlusLimit) {
        this.surPlusLimit = surPlusLimit;
    }

    public Long getUsedLimit() {
        return usedLimit;
    }

    public void setUsedLimit(Long usedLimit) {
        this.usedLimit = usedLimit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getYktExpireDate() {
        return yktExpireDate;
    }

    public void setYktExpireDate(Date yktExpireDate) {
        this.yktExpireDate = yktExpireDate;
    }

}
