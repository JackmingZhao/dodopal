package com.dodopal.card.business.model.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dodopal.common.model.BaseEntity;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BJNfcCityFrontDTO extends BaseEntity {
    private static final long serialVersionUID = -7972421705984828912L;

    /********************** 公共参数 **********************/
    private String serviceversion; //接口版本
    private String requestno; //请求序号
    private String requesttime; //请求时间

    /********************** 手机信息参数 **********************/
    private String mobileno; //手机号
    private String mobiletype; //手机类型
    private String mobileimei; //手机IMEI号
    private String mobilesysver; //手机系统版本

    /********************** 订单信息参数 **********************/
    private String orderid; //系统订单号
    private String tradedate; //交易日期
    private String tradetime; //交易时间
    private String userid; //用户id

    /********************** 卡片信息参数 **********************/
    private String cardinnerno; //卡内号
    private String posid; //pos号
    private String txnamt; //交易金额
    private String cardno; //卡面号
    private String cardtype; //卡片类型
    private String cardbalance; //卡片余额 
    private String cardoverdraft; //卡片透支金额
    private String cardrecordten; //卡内最近10条交易记录
    private String cardcsn; //卡片CSN
    private String cardstartdate; //卡片启用日期
    private String cardenddate; //卡片失效日期
    private String cardstartflag; //卡启用标志
    private String cardblackflag; //黑名单标志

    private String yktid; //一卡通号
    private String cityid; //城市id

    private String imsi;
    private String tradetype; //交易类型0：查询 1：充值 2：d消费
    private String dealtype; //卡片处理方式
    private String paytype; //资金来源
    private String apdupacno; //APDU 指令包序号
    private String apduordernum; //APDU 指令/应答个数
    private String apdupaclen; //APDU 指令/应答包长度
    private String apduseq; //APDU 指令/应答序列

    /********************** 应答部分 **********************/
    private String responseno; //应答序号
    private String responsetime; //应答时间
    private String status; //应答码
    private String errmsg; //错误信息
    private String totalpacnum;

    private String prdordernum;//产品库订单号
    private String tradeendflag;//交易结束标志

    public String getServiceversion() {
        return serviceversion;
    }

    public void setServiceversion(String serviceversion) {
        this.serviceversion = serviceversion;
    }

    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }

    public String getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getMobiletype() {
        return mobiletype;
    }

    public void setMobiletype(String mobiletype) {
        this.mobiletype = mobiletype;
    }

    public String getMobileimei() {
        return mobileimei;
    }

    public void setMobileimei(String mobileimei) {
        this.mobileimei = mobileimei;
    }

    public String getMobilesysver() {
        return mobilesysver;
    }

    public void setMobilesysver(String mobilesysver) {
        this.mobilesysver = mobilesysver;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTradedate() {
        return tradedate;
    }

    public void setTradedate(String tradedate) {
        this.tradedate = tradedate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCardinnerno() {
        return cardinnerno;
    }

    public void setCardinnerno(String cardinnerno) {
        this.cardinnerno = cardinnerno;
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getTxnamt() {
        return txnamt;
    }

    public void setTxnamt(String txnamt) {
        this.txnamt = txnamt;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getCardbalance() {
        return cardbalance;
    }

    public void setCardbalance(String cardbalance) {
        this.cardbalance = cardbalance;
    }

    public String getCardoverdraft() {
        return cardoverdraft;
    }

    public void setCardoverdraft(String cardoverdraft) {
        this.cardoverdraft = cardoverdraft;
    }

    public String getCardrecordten() {
        return cardrecordten;
    }

    public void setCardrecordten(String cardrecordten) {
        this.cardrecordten = cardrecordten;
    }

    public String getCardcsn() {
        return cardcsn;
    }

    public void setCardcsn(String cardcsn) {
        this.cardcsn = cardcsn;
    }

    public String getCardstartdate() {
        return cardstartdate;
    }

    public void setCardstartdate(String cardstartdate) {
        this.cardstartdate = cardstartdate;
    }

    public String getCardenddate() {
        return cardenddate;
    }

    public void setCardenddate(String cardenddate) {
        this.cardenddate = cardenddate;
    }

    public String getCardstartflag() {
        return cardstartflag;
    }

    public void setCardstartflag(String cardstartflag) {
        this.cardstartflag = cardstartflag;
    }

    public String getCardblackflag() {
        return cardblackflag;
    }

    public void setCardblackflag(String cardblackflag) {
        this.cardblackflag = cardblackflag;
    }

    public String getYktid() {
        return yktid;
    }

    public void setYktid(String yktid) {
        this.yktid = yktid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getTradetype() {
        return tradetype;
    }

    public void setTradetype(String tradetype) {
        this.tradetype = tradetype;
    }

    public String getDealtype() {
        return dealtype;
    }

    public void setDealtype(String dealtype) {
        this.dealtype = dealtype;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getApdupacno() {
        return apdupacno;
    }

    public void setApdupacno(String apdupacno) {
        this.apdupacno = apdupacno;
    }

    public String getApduordernum() {
        return apduordernum;
    }

    public void setApduordernum(String apduordernum) {
        this.apduordernum = apduordernum;
    }

    public String getApdupaclen() {
        return apdupaclen;
    }

    public void setApdupaclen(String apdupaclen) {
        this.apdupaclen = apdupaclen;
    }

    public String getApduseq() {
        return apduseq;
    }

    public void setApduseq(String apduseq) {
        this.apduseq = apduseq;
    }

    public String getResponseno() {
        return responseno;
    }

    public void setResponseno(String responseno) {
        this.responseno = responseno;
    }

    public String getResponsetime() {
        return responsetime;
    }

    public void setResponsetime(String responsetime) {
        this.responsetime = responsetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTotalpacnum() {
        return totalpacnum;
    }

    public void setTotalpacnum(String totalpacnum) {
        this.totalpacnum = totalpacnum;
    }

    public String getTradetime() {
        return tradetime;
    }

    public void setTradetime(String tradetime) {
        this.tradetime = tradetime;
    }

    public String getPrdordernum() {
        return prdordernum;
    }

    public void setPrdordernum(String prdordernum) {
        this.prdordernum = prdordernum;
    }

    public String getTradeendflag() {
        return tradeendflag;
    }

    public void setTradeendflag(String tradeendflag) {
        this.tradeendflag = tradeendflag;
    }

}
