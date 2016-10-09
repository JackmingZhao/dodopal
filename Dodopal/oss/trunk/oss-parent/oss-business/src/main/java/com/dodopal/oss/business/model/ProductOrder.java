package com.dodopal.oss.business.model;

import com.dodopal.common.model.BaseEntity;

import java.util.Date;

/**
 * 
 * @ClassName: ProductOrder
 * @Description: 数据库表PRD_ORDER 对应实体对象
 *
 */
public class ProductOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    // 产品库订单编号:全局唯一（O + 20150428222211 +五位数据库cycle sequence（循环使用））    全局唯一    产品库自动生成。
    private String proOrderNum;

    // 公交卡充值产品的编号
    private String proCode;

    // 公交卡充值产品的名称
    private String proName;

    // 充值的金额，来自于当时的公交卡充值产品的面额。
    private long txnAmt;

    // 城市Code
    private String cityCode;

    // 通卡公司Code
    private String yktCode;

    // 城市名称
    private String cityName;

    // 通卡公司名称
    private String yktName;

    // 实付（收）金额：商户支付给DDP的金额（一般从账户里扣）。在UI设计的时候，展现给商户的页面叫做：实付金额；OSS内展示为：实收金额。在正常情况下，这个跟“商户进货价”的数据相同。但是如果有针对商户进行促销活动的时候，这个价格可能低于“商户进货价”。
    private Long receivedPrice;

    // 商户费率:在进行这一笔公交卡充值的时候，商户与DDP之间的公交卡充值业务费率。
    private Double merRate;

    // 商户费率类型
    private String merRateType;

    // 服务费率类型
    private String payServiceType;

    // 服务费率
    private Double payServiceRate;

    // 通卡公司一卡通充值费率（固定类型：千分比）
    private Double yktRechargeRate;

    // 商户进货价:针对商户而言，是商户采购DDP公交卡充值产品的成本价。从DDP的角度来看，也就是DDP的出货价。这个价格是结合公交卡充值产品的面价以及与DDP与该商户之间定义的费率来计算的。在订单上会复制记录当前的费率以及冗余记录基于当前费率和面价计算出的进货价。对于个人用户，此项没有意义。
    private Long merPurchaasePrice;

    // 订单时间:式yyyy-MM-dd HH:MM:SS,实际为订单的创建时间。
    private Date proOrderDate;

    // 订单日期:格式yyyyMMdd,这里实际上为订单的创建日期（有点类似订单时间）。冗余的主要目的是为了满足数据库系统的分区。
    private String proOrderDay;

    // 卡面号:与商户UI上的卡号查询条件一致。在个人UI上，不用提供卡号查询条件。
    private String orderCardno;

    // POS号:冗余保存，提高查询效率目的。
    private String posCode;

    // 商户利润:面价减去实付（收）金额。(不要考虑：商户不是采用账户支付，而是采用银行/支付宝支付，从而产生银行手续费这种情况。
    private long merGain;

    // 充值前金额:在充值之前的公交卡内的余额。
    private long befbal;

    // 充值后金额:在充值之后的公交卡内的余额。由卡服务提供，只有成功充值后才有。
    private Long blackAmt;

    // 支付类型:0:账户支付;1:一卡通支付;2:在线支付;3:银行支付
    private String payType;

    // 支付方式
    private String payWay;

    // 订单状态(展示外部状态)
    private String proOrderState;

    // 订单内部状态
    private String proInnerState;

    // 订单前内部状态
    private String proBeforInnerState;

    // 可疑处理状态:0:不可疑:；1:可疑；2：可疑已处理；3：申请解冻；4：等待解冻；5：已解冻；6：划账成功；7：划账失败。（这个字段对外不显示，而且只针对可疑订单生效。）
    private String proSuspiciousState;

    // 可疑处理说明:OSS用户处理可疑订单的时候输入
    private String proSuspiciousExplain;

    // 商户号:标识在哪个商户进行的公交卡充值业务。对于个人用户，为空。
    private String merCode;

    // 客户名称
    private String merName;

    // 客户类型:0:个人;1:企业
    private String merUserType;

    // 圈存标识:0:圈存；1:非圈存
    private String loadFlag;

    // 外部订单号:记录外接商户关联的订单号。
    private String merOrderNum;

    // 圈存订单号
    private String loadOrderNum;

    // 来源:这笔订单是来自于个人、网点还是外接商户以及接入方式等（比如WEB，手机等）
    private String source;

    // 清算标志:用于判别是否已经对这条订单记录进行了清分清算。
    private String clearingMark;

    // 操作员:用户的数据库ID。是商户的哪个操作人操作了这个公交卡充值业务。
    private String userId;

    // 操作员名称(用户的名称)
    private String userName;

    // 备注
    private String comments;

    // 页面URL:页面回调通知外接商户
    private String pageCallBackURL;

    // 服务器URL:服务器端回调通知外接商户
    private String serviceNoticeURL;

    // 资金处理结果:0:没有处理（默认值）；1：处理完毕
    private String fundProcessResult;

    // 用户code：对于个人用户，这里记录的是个人在DDP业务平台上的唯一标识号。
    private String userCode;

    // 部门ID，集团商户用用于查询
    private String depId;

    // 外接商户用户code
    private String extUserCode;

    public String getProOrderNum() {
        return proOrderNum;
    }

    public void setProOrderNum(String proOrderNum) {
        this.proOrderNum = proOrderNum;
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

    public long getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(long txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getYktCode() {
        return yktCode;
    }

    public void setYktCode(String yktCode) {
        this.yktCode = yktCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getYktName() {
        return yktName;
    }

    public void setYktName(String yktName) {
        this.yktName = yktName;
    }

    public Long getReceivedPrice() {
        return receivedPrice;
    }

    public void setReceivedPrice(Long receivedPrice) {
        this.receivedPrice = receivedPrice;
    }

    public Double getMerRate() {
        return merRate;
    }

    public void setMerRate(Double merRate) {
        this.merRate = merRate;
    }

    public String getMerRateType() {
        return merRateType;
    }

    public void setMerRateType(String merRateType) {
        this.merRateType = merRateType;
    }

    public String getPayServiceType() {
        return payServiceType;
    }

    public void setPayServiceType(String payServiceType) {
        this.payServiceType = payServiceType;
    }

    public Double getPayServiceRate() {
        return payServiceRate;
    }

    public void setPayServiceRate(Double payServiceRate) {
        this.payServiceRate = payServiceRate;
    }

    public Double getYktRechargeRate() {
        return yktRechargeRate;
    }

    public void setYktRechargeRate(Double yktRechargeRate) {
        this.yktRechargeRate = yktRechargeRate;
    }

    public Long getMerPurchaasePrice() {
        return merPurchaasePrice;
    }

    public void setMerPurchaasePrice(Long merPurchaasePrice) {
        this.merPurchaasePrice = merPurchaasePrice;
    }

    public Date getProOrderDate() {
        return proOrderDate;
    }

    public void setProOrderDate(Date proOrderDate) {
        this.proOrderDate = proOrderDate;
    }

    public String getProOrderDay() {
        return proOrderDay;
    }

    public void setProOrderDay(String proOrderDay) {
        this.proOrderDay = proOrderDay;
    }

    public String getOrderCardno() {
        return orderCardno;
    }

    public void setOrderCardno(String orderCardno) {
        this.orderCardno = orderCardno;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public long getMerGain() {
        return merGain;
    }

    public void setMerGain(long merGain) {
        this.merGain = merGain;
    }

    public long getBefbal() {
        return befbal;
    }

    public void setBefbal(long befbal) {
        this.befbal = befbal;
    }

    public Long getBlackAmt() {
        return blackAmt;
    }

    public void setBlackAmt(Long blackAmt) {
        this.blackAmt = blackAmt;
    }

    public String getPayType() {
        return payType;
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

    public String getProOrderState() {
        return proOrderState;
    }

    public void setProOrderState(String proOrderState) {
        this.proOrderState = proOrderState;
    }

    public String getProInnerState() {
        return proInnerState;
    }

    public void setProInnerState(String proInnerState) {
        this.proInnerState = proInnerState;
    }

    public String getProBeforInnerState() {
        return proBeforInnerState;
    }

    public void setProBeforInnerState(String proBeforInnerState) {
        this.proBeforInnerState = proBeforInnerState;
    }

    public String getProSuspiciousState() {
        return proSuspiciousState;
    }

    public void setProSuspiciousState(String proSuspiciousState) {
        this.proSuspiciousState = proSuspiciousState;
    }

    public String getProSuspiciousExplain() {
        return proSuspiciousExplain;
    }

    public void setProSuspiciousExplain(String proSuspiciousExplain) {
        this.proSuspiciousExplain = proSuspiciousExplain;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerUserType() {
        return merUserType;
    }

    public void setMerUserType(String merUserType) {
        this.merUserType = merUserType;
    }

    public String getLoadFlag() {
        return loadFlag;
    }

    public void setLoadFlag(String loadFlag) {
        this.loadFlag = loadFlag;
    }

    public String getMerOrderNum() {
        return merOrderNum;
    }

    public void setMerOrderNum(String merOrderNum) {
        this.merOrderNum = merOrderNum;
    }

    public String getLoadOrderNum() {
        return loadOrderNum;
    }

    public void setLoadOrderNum(String loadOrderNum) {
        this.loadOrderNum = loadOrderNum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getClearingMark() {
        return clearingMark;
    }

    public void setClearingMark(String clearingMark) {
        this.clearingMark = clearingMark;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPageCallBackURL() {
        return pageCallBackURL;
    }

    public void setPageCallBackURL(String pageCallBackURL) {
        this.pageCallBackURL = pageCallBackURL;
    }

    public String getServiceNoticeURL() {
        return serviceNoticeURL;
    }

    public void setServiceNoticeURL(String serviceNoticeURL) {
        this.serviceNoticeURL = serviceNoticeURL;
    }

    public String getFundProcessResult() {
        return fundProcessResult;
    }

    public void setFundProcessResult(String fundProcessResult) {
        this.fundProcessResult = fundProcessResult;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getExtUserCode() {
        return extUserCode;
    }

    public void setExtUserCode(String extUserCode) {
        this.extUserCode = extUserCode;
    }
}
