package com.dodopal.api.card.dto;

import java.io.Serializable;

/*
 * 签退结果上传
 */
public class ReslutDataParameter implements Serializable {
    private static final long serialVersionUID = 1452080074445578199L;
    //消息类型
    private String messagetype;
    //版本号
    private String ver;
    //发送时间YYYYMMDDhhmmss
    private String sysdatetime;
    //特殊域启用标志
    private String istsused;
    //应答码
    private String respcode;
    //城市代码
    private String citycode;
    //交易状态
    private String txnstat;
    //商户类型
    private String mertype;
    //TODO 商户号
    private String mercode;
    //银行编号
    private String bankid;
    //TODO 用户编号
    private String userid;
    //设备类型
    private String postype;
    //设备编号
    private String posid;
    //设备流水号
    private String posseq;
    //操作员号
    private String operid;
    //卡物理类型
    private String cardphytype;
    //订单号
    private String prdordernum;
    //支付订单号
    private String payorder;
    //卡号
    private String cardno;
    //卡面号
    private String cardfaceno;
    //交易日期
    private String txndate;
    //交易时间
    private String txntime;
    //交易金额
    private String txnamt;
    //交易前余额
    private String befbal;
    //交易余额
    private String aftbal;
    //卡计数器
    private String cardcnt;
    //TAC(失败、未知FFFFFFFF)
    private String tac;
    //MAC2(失败、未知FFFFFFFF)
    private String mac2;
    //特殊域
    private ReslutDataSpecial crdm;
    //保留字段
    private String reserved;
    //TODO 一卡通code
    private String yktcode;

    //TODO 商户名称
    private String mername;
    //TODO 城市名称
    private String cityname;
    //TODO 通卡名称
    private String yktname;
    //TODO 通卡支付费率
    private String yktpayrate;
    //用户号
    private String usercode;

    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getSysdatetime() {
        return sysdatetime;
    }

    public void setSysdatetime(String sysdatetime) {
        this.sysdatetime = sysdatetime;
    }

    public String getIstsused() {
        return istsused;
    }

    public void setIstsused(String istsused) {
        this.istsused = istsused;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getTxnstat() {
        return txnstat;
    }

    public void setTxnstat(String txnstat) {
        this.txnstat = txnstat;
    }

    public String getMertype() {
        return mertype;
    }

    public void setMertype(String mertype) {
        this.mertype = mertype;
    }

    public String getMercode() {
        return mercode;
    }

    public void setMercode(String mercode) {
        this.mercode = mercode;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPostype() {
        return postype;
    }

    public void setPostype(String postype) {
        this.postype = postype;
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getPosseq() {
        return posseq;
    }

    public void setPosseq(String posseq) {
        this.posseq = posseq;
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getCardphytype() {
        return cardphytype;
    }

    public void setCardphytype(String cardphytype) {
        this.cardphytype = cardphytype;
    }

    public String getPrdordernum() {
        return prdordernum;
    }

    public void setPrdordernum(String prdordernum) {
        this.prdordernum = prdordernum;
    }

    public String getPayorder() {
        return payorder;
    }

    public void setPayorder(String payorder) {
        this.payorder = payorder;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getCardfaceno() {
        return cardfaceno;
    }

    public void setCardfaceno(String cardfaceno) {
        this.cardfaceno = cardfaceno;
    }

    public String getTxndate() {
        return txndate;
    }

    public void setTxndate(String txndate) {
        this.txndate = txndate;
    }

    public String getTxntime() {
        return txntime;
    }

    public void setTxntime(String txntime) {
        this.txntime = txntime;
    }

    public String getTxnamt() {
        return txnamt;
    }

    public void setTxnamt(String txnamt) {
        this.txnamt = txnamt;
    }

    public String getBefbal() {
        return befbal;
    }

    public void setBefbal(String befbal) {
        this.befbal = befbal;
    }

    public String getAftbal() {
        return aftbal;
    }

    public void setAftbal(String aftbal) {
        this.aftbal = aftbal;
    }

    public String getCardcnt() {
        return cardcnt;
    }

    public void setCardcnt(String cardcnt) {
        this.cardcnt = cardcnt;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getMac2() {
        return mac2;
    }

    public void setMac2(String mac2) {
        this.mac2 = mac2;
    }

    public ReslutDataSpecial getCrdm() {
        return crdm;
    }

    public void setCrdm(ReslutDataSpecial crdm) {
        this.crdm = crdm;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getYktcode() {
        return yktcode;
    }

    public void setYktcode(String yktcode) {
        this.yktcode = yktcode;
    }

    public String getMername() {
        return mername;
    }

    public void setMername(String mername) {
        this.mername = mername;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getYktname() {
        return yktname;
    }

    public void setYktname(String yktname) {
        this.yktname = yktname;
    }

    public String getYktpayrate() {
        return yktpayrate;
    }

    public void setYktpayrate(String yktpayrate) {
        this.yktpayrate = yktpayrate;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

}
