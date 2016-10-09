package com.dodopal.portal.business.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RateCodeEnum;
import com.dodopal.common.enums.TranOutStatusEnum;
import com.dodopal.common.enums.TranTypeEnum;
import com.dodopal.common.model.BaseEntity;

/**
 * 交易流水信息
 *  @author 
 *
 */
public class TraTransactionBean extends BaseEntity{
    private static final long serialVersionUID = -1127666510674456891L;
    /**
     * 交易流水号
     */
    private String tranCode;

    /**
     * 交易名称
     */
    private String tranName;

    /**
     * 外部交易状态
     * 0待支付1已取消2支付中3已支付4待退款5已退款6待转帐7转帐成功
     */
    private String tranOutStatus;
    
    /**
     * 内部交易状态
     * 0待处理 1已处理 10账户更新失败 11内部更新失败
     */
    private String tranInStatus;
    

    
    /**
     * 金额   “元”为单位
     */
    private String amountMoney;
    
    /**
     * 交易类型
     * 1：账户充值、3：账户消费、5：退款、7：转出、9：转入11：正调账13：反调账
     */
    private String tranType;
    
    
    /**
     * 订单号
     */
    private String orderNumber;
    
    /**
     * 商品名称
     */
    private String commodity;
    
    /**
     * 业务类型代码
     */
    private String businessType;
    
 
    
    /**
     * 订单时间
     */
    private Date orderDate;
    
    /**
     * 支付类型
     */
    private String payType;
    
    /**
     * 支付方式
     */
    private String payWay;
    
    /**
     * 实际交易金额(元)
     */
    private String realTranMoney;
    //优惠金额
    private String favourableMoney;
    //支付方式名称
    private String payWayName;

    /**
     * 备注
     */
    private String comments;
    
    
    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPayWayName() {
        return payWayName;
    }

    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public String getFavourableMoney() {
        return favourableMoney;
    }

    public void setFavourableMoney(String favourableMoney) {
        this.favourableMoney = favourableMoney;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    //前端展示
    public String getTranOutStatus() {
        if (StringUtils.isBlank(this.tranOutStatus)) {
            return null;
        }else if(TranOutStatusEnum.getTranOutStatusByCode(this.tranOutStatus)==null){
            return null;
        }
        return TranOutStatusEnum.getTranOutStatusByCode(this.tranOutStatus).getName();
    }

    public void setTranOutStatus(String tranOutStatus) {
        this.tranOutStatus = tranOutStatus;
    }

    public String getTranInStatus() {
        return tranInStatus;
    }

    public void setTranInStatus(String tranInStatus) {
        this.tranInStatus = tranInStatus;
    }

    //前端展示
    public String getTranType() {
        
        if (StringUtils.isBlank(this.tranType)) {
            return null;
        }else if(TranTypeEnum.getTranTypeByCode(this.tranType)==null){
            return null;
        }
        return TranTypeEnum.getTranTypeByCode(this.tranType).getName();
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getBusinessType() {
        if (StringUtils.isBlank(this.businessType)) {
            return null;
        }else if(RateCodeEnum.getRateTypeByCode(this.businessType)==null){
            return null;
        }
        return RateCodeEnum.getRateTypeByCode(this.businessType).getName();
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    //前端展示
    public String getPayType() {
        if (StringUtils.isBlank(this.payType)) {
            return null;
        }else if(PayTypeEnum.getPayTypeEnumByCode(this.payType)==null){
            return null; 
        }
        return PayTypeEnum.getPayTypeEnumByCode(this.payType).getName();
    }
    


    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

   
    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getRealTranMoney() {
        return realTranMoney;
    }

    public void setRealTranMoney(String realTranMoney) {
        this.realTranMoney = realTranMoney;
    }




}
