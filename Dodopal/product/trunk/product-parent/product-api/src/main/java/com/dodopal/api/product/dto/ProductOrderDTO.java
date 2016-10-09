package com.dodopal.api.product.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dodopal.common.enums.ClearingMarkEnum;
import com.dodopal.common.enums.MerTypeEnum;
import com.dodopal.common.enums.PayTypeEnum;
import com.dodopal.common.enums.RechargeOrderInternalStatesEnum;
import com.dodopal.common.enums.RechargeOrderStatesEnum;
import com.dodopal.common.enums.SuspiciousStatesEnum;
import com.dodopal.common.model.BaseEntity;

/**
 * 产品库中公交卡充值订单查询结果 订单编号、订单时间
 * 、卡号、充值金额、充值前金额、充值后金额、实收金额、商户利润、订单状态、内部状态、POS号、城市、商户名称、卡类型、充值方式、支付类型、支付方式
 * 、操作员名称
 */
public class ProductOrderDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    // 产品库订单编号
    private String proOrderNum;

    // 订单时间:式yyyy-MM-dd HH:MM:SS,实际为订单的创建时间。
    private Date proOrderDate;

    // 卡号:
    private String orderCardno;

    // 充值的金额
    private Long txnAmt;

    // 充值前金额:在充值之前的公交卡内的余额。
    private Long befbal;

    // 充值后金额:在充值之后的公交卡内的余额。
    private Long blackAmt;

    // 实付（收）金额：商户支付给DDP的金额（一般从账户里扣）。
    private Long receivedPrice;

    // 利润（不适用于个人）
    private Long merGain;

    // 订单状态(展示外部状态)
    private String proOrderState;

    // POS号:冗余保存，提高查询效率目的。
    private String posCode;

    // 城市名称
    private String cityName;

    // 以下为oss 查询需要返回的字段
    // 订单内部状态
    private String proInnerState;

    //    商户名称、
    private String merName;

    // 卡类型、

    //支付方式、
    private String payWay;
    //    支付类型、
    private String payType;

    // 操作员:用户的数据库ID。是商户的哪个操作人操作了这个公交卡充值业务。
    private String userId;

    // 操作员名称(用户的名称)
    private String userName;

    // 公交卡充值产品的编号
    private String proCode;

    // 公交卡充值产品的名称
    private String proName;

    // 商户进货价:针对商户而言，是商户采购DDP公交卡充值产品的成本价。从DDP的角度来看，也就是DDP的出货价。这个价格是结合公交卡充值产品的面价以及与DDP与该商户之间定义的费率来计算的。在订单上会复制记录当前的费率以及冗余记录基于当前费率和面价计算出的进货价。对于个人用户，此项没有意义。
    private Long merPurchaasePrice;

    // 外部订单号:记录外接商户关联的订单号。
    private String merOrderNum;

    // 充值的金额,显示页面的实际值
    private String showTxnAmt;

    // 充值前金额:在充值之前的公交卡内的余额。显示页面的实际值
    private String showBefbal;

    // 充值后金额:在充值之后的公交卡内的余额。显示页面的实际值
    private String showBlackAmt;

    // 实付（收）金额：商户支付给DDP的金额（一般从账户里扣）。显示页面的实际值
    private String showReceivedPrice;

    // 利润（不适用于个人） 显示页面的实际值
    private String showMerGain;

    // 商户号:标识在哪个商户进行的公交卡充值业务。对于个人用户，为空。
    private String merCode;

    //add by guanjl 20160107 start

    // 可疑处理状态:0:不可疑:；1:可疑；2：可疑已处理；3：申请解冻；4：等待解冻；5：已解冻；6：划账成功；7：划账失败。（这个字段对外不显示，而且只针对可疑订单生效。）
    private String proSuspiciousState;

    // 可疑处理说明:OSS用户处理可疑订单的时候输入
    private String proSuspiciousExplain;

    // 清算标志:用于判别是否已经对这条订单记录进行了清分清算。
    private String clearingMark;

    //  支付类型
    private String payTypeView;

    // 商户进货价view
    private String merPurchaasePriceView;

    // 客户类型:0:个人;1:企业
    private String merUserType;

    //add by guanjl 20160107 end

    
    // POS备注
    private String posComments;
    //add by xiong 20160428 end
    

    public String getPosComments() {
        return posComments;
    }

    public void setPosComments(String posComments) {
        this.posComments = posComments;
    }

    public String getMerCode() {
        return merCode;
    }


    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Long getMerPurchaasePrice() {
        return merPurchaasePrice;
    }

    public void setMerPurchaasePrice(Long merPurchaasePrice) {
        this.merPurchaasePrice = merPurchaasePrice;
    }

    public String getMerOrderNum() {
        return merOrderNum;
    }

    public void setMerOrderNum(String merOrderNum) {
        this.merOrderNum = merOrderNum;
    }

    public String getProOrderNum() {
        return proOrderNum;
    }

    public void setProOrderNum(String proOrderNum) {
        this.proOrderNum = proOrderNum;
    }

    public Date getProOrderDate() {
        return proOrderDate;
    }

    public void setProOrderDate(Date proOrderDate) {
        this.proOrderDate = proOrderDate;
    }

    public String getOrderCardno() {
        return orderCardno;
    }

    public void setOrderCardno(String orderCardno) {
        this.orderCardno = orderCardno;
    }

    public Long getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(Long txnAmt) {
        this.txnAmt = txnAmt;
    }

    public Long getBefbal() {
        return befbal;
    }

    public void setBefbal(Long befbal) {
        this.befbal = befbal;
    }

    public Long getBlackAmt() {
        return blackAmt;
    }

    public void setBlackAmt(Long blackAmt) {
        this.blackAmt = blackAmt;
    }

    public Long getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(Long receivedPrice) {
        this.receivedPrice = receivedPrice;
    }

    public Long getMerGain() {
        return merGain;
    }

    public void setMerGain(Long merGain) {
        this.merGain = merGain;
    }

    public String getProOrderState() {
        return proOrderState;
    }

    //订单状态
    public String getProOrderStateView() {
        if (StringUtils.isBlank(this.proOrderState)) {
            return null;
        }
        return RechargeOrderStatesEnum.getRechargeOrderStatesEnumByCode(this.proOrderState).getName();
    }

    public void setProOrderState(String proOrderState) {
        this.proOrderState = proOrderState;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProInnerState() {
        return proInnerState;
    }

    public void setProInnerState(String proInnerState) {
        this.proInnerState = proInnerState;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayType() {
        return payType;
    }

    public String getPayTypeStr() {
        if (StringUtils.isBlank(this.payType)) {
            return null;
        }
        //return payType;
        return PayTypeEnum.getPayTypeNameByCode(this.payType);
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getShowTxnAmt() {
        return showTxnAmt;
    }

    public void setShowTxnAmt(String showTxnAmt) {
        this.showTxnAmt = showTxnAmt;
    }

    public String getShowBefbal() {
        return showBefbal;
    }

    public void setShowBefbal(String showBefbal) {
        this.showBefbal = showBefbal;
    }

    public String getShowBlackAmt() {
        return showBlackAmt;
    }

    public void setShowBlackAmt(String showBlackAmt) {
        this.showBlackAmt = showBlackAmt;
    }

    public String getShowReceivedPrice() {
        return showReceivedPrice;
    }

    public void setShowReceivedPrice(String showReceivedPrice) {
        this.showReceivedPrice = showReceivedPrice;
    }

    public String getShowMerGain() {
        return showMerGain;
    }

    public void setShowMerGain(String showMerGain) {
        this.showMerGain = showMerGain;
    }

    public String getProInnerStateStr() {
        if (StringUtils.isBlank(this.proInnerState)) {
            return null;
        }
        return RechargeOrderInternalStatesEnum.getRechargeOrderInternalStatesNameByCode(this.proInnerState);
    }

    public String getProSuspiciousState() {
        if (StringUtils.isBlank(this.proSuspiciousState)) {
            return null;
        }
        return SuspiciousStatesEnum.getSuspiciousStateNameByCode(this.proSuspiciousState);
    }

    public void setProSuspiciousState(String proSuspiciousState) {
        this.proSuspiciousState = proSuspiciousState;
    }

    public String getClearingMark() {
        if (StringUtils.isBlank(this.clearingMark)) {
            return null;
        }
        return ClearingMarkEnum.getClearingMarkNameByCode(this.clearingMark);
    }

    public void setClearingMark(String clearingMark) {
        this.clearingMark = clearingMark;
    }

    public String getProSuspiciousExplain() {
        return proSuspiciousExplain;
    }

    public void setProSuspiciousExplain(String proSuspiciousExplain) {
        this.proSuspiciousExplain = proSuspiciousExplain;
    }

    public String getPayTypeView() {
        if (StringUtils.isBlank(this.payTypeView)) {
            return null;
        }
        return PayTypeEnum.getPayTypeNameByCode(this.payTypeView);
    }

    public void setPayTypeView(String payTypeView) {
        this.payTypeView = payTypeView;
    }

    public String getMerPurchaasePriceView() {
        return merPurchaasePriceView;
    }

    public void setMerPurchaasePriceView(String merPurchaasePriceView) {
        this.merPurchaasePriceView = merPurchaasePriceView;
    }

    public String getMerUserType() {
        if (StringUtils.isBlank(this.merUserType)) {
            return null;
        }
        return MerTypeEnum.getMerTypeByCode(this.merUserType).getName();
    }

    public void setMerUserType(String merUserType) {
        this.merUserType = merUserType;
    }

}
