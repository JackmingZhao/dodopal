package com.dodopal.product.web.param;

public class CreatePayTransactionRequest extends BaseRequest {

    //请求时间  yyyyMMddHHmmss
    private String reqdate;

    //来源   安卓手机：6 ,IOS手机：7 ,手机端：2 VC端设置为 3 固定, 自助终端：9
    private String source;

    //客户类型  个人用户：设值为0 固定, 商户用户：设置为 1 固定
    private String custtype;

    //客户号  都都宝平台生成的业务主键, 个人用户：用户号usercode, 商户用户：商户号 mercode
    private String custcode;

    //充值金额   账户充值金额“分”单位
    private String amont;

    //支付方式id 都都宝平台提供的支付方式ID
    private String payid;

    //产品编号ID  都都宝平台提供的产品编号
    private String procode;

    //卡面号  一卡通卡面号
    private String cardnum;

    //支付服务费率   支付服务费率（用户）
    private String payservicerate;

    //支付手续费率      支付手续费率（第三方）
    private String payprocerate;

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

    public String getAmont() {
        return amont;
    }

    public void setAmont(String amont) {
        this.amont = amont;
    }

    public String getPayservicerate() {
        return payservicerate;
    }

    public void setPayservicerate(String payservicerate) {
        this.payservicerate = payservicerate;
    }

    public String getPayprocerate() {
        return payprocerate;
    }

    public void setPayprocerate(String payprocerate) {
        this.payprocerate = payprocerate;
    }

}
