package com.dodopal.product.business.bean;

import com.dodopal.common.model.BaseEntity;

public class CreatePayTransactionBean extends BaseEntity {
    private static final long serialVersionUID = 3720353839633519073L;
    //请求时间  yyyyMMddHHmmss
    private String reqdate;

    //来源   安卓手机：6 ,IOS手机：7 ,手机端：2 VC端设置为 3 固定, 自助终端：9
    private String source;

    //客户类型  个人用户：设值为0 固定, 商户用户：设置为 1 固定
    private String custtype;

    //客户号  都都宝平台生成的业务主键, 个人用户：用户号usercode, 商户用户：商户号 mercode
    private String custcode;

    //充值金额   账户充值金额“分”单位
    private long amont;

    //支付方式id 都都宝平台提供的支付方式ID
    private String payid;

    //产品编号ID  都都宝平台提供的产品编号
    private String procode;

    //卡面号  一卡通卡面号
    private String cardnum;

    //支付服务费率   支付服务费率（用户）
    private double payservicerate;

    //支付手续费率      支付手续费率（第三方）
    private double payprocerate;

    //业务类型
    private String businessType;

    //商品名称
    private String goodsName;

    /**
     * 圈存订单号
     * @return
     */

    private String orderNum;

    private String citycode;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getReqdate() {
        return reqdate;
    }

    public void setReqdate(String reqdate) {
        this.reqdate = reqdate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCusttype() {
        return custtype;
    }

    public void setCusttype(String custtype) {
        this.custtype = custtype;
    }

    public String getCustcode() {
        return custcode;
    }

    public void setCustcode(String custcode) {
        this.custcode = custcode;
    }

    public long getAmont() {
        return amont;
    }

    public void setAmont(long amont) {
        this.amont = amont;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        this.payid = payid;
    }

    public String getProcode() {
        return procode;
    }

    public void setProcode(String procode) {
        this.procode = procode;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public double getPayservicerate() {
        return payservicerate;
    }

    public void setPayservicerate(double payservicerate) {
        this.payservicerate = payservicerate;
    }

    public double getPayprocerate() {
        return payprocerate;
    }

    public void setPayprocerate(double payprocerate) {
        this.payprocerate = payprocerate;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

}
