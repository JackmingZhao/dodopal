package com.dodopal.card.business.model.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dodopal.api.card.dto.Specdata;

/**
 * 城市前置传输对象
 * @author guanjinglun
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CityFrontOrderDTO {

    private static final long serialVersionUID = -2088927227532353096L;

    //消息类型
    private String messagetype;

    //版本号
    private String ver;

    //前端发送时间格式如下 YYYYMMDDhhmmss
    private String sysdatetime;

    //交易应答码
    private String respcode;

    //一卡通代码
    private String yktcode;

    //城市代码
    private String citycode;

    //卡物理类型
    private String cardtype;

    //卡号(验卡返回 充值和结果回传)
    private String cardinnerno;

    //卡面号(验卡返回 充值和结果回传)
    private String cardfaceno;

    //0-查询
    private String txntype;

    //0-交易透传 1-交易结束
    private String tradeendflag;

    //0-交易透传 1-交易开始
    private String tradestartflag;

    //设备类型 0：NFC手机；1：V61 V60；2：V70
    private String postype;

    //充值类型 0:钱包;1:月票
    private String chargetype;

    //设备编号
    private String posid;

    //设备流水号
    private String posseq;

    //交易流水号(充值和结果回传上来)
    private String txnseq;

    //用户编号
    private String usercode;

    //卡服务订单号
    private String crdordernum;

    //交易日期(后台返回一笔交易此字段保持一致)
    private String txndate;

    //交易时间(后台返回一笔交易此字段保持一致)
    private String txntime;

    //交易金额(查询时不需要传)
    private String txnamt;

    //交易前卡余额(查询时不需要传)
    private String befbal;

    //交易后卡余额
    private String blackamt;

    //卡内允许最大金额
    private String cardlimit;

    //安全认证码
    private String secmac;

    //卡计数器由前置圈存指令请求返回
    private String cardcounter;

    //结果上传交易状态0：成功 1：失败 2：未知
    private String txnstat;

    //特殊域
    private Specdata specdata;

    //交易记录域JSON数组
    private String[] txnrecode;

    //金融IC卡文件数据JSON数组
    private String[] bankfile;

    //APDU指令域JSON数组
    private String[] apdu;

    //上传后台原路返回(返存前端)
    private String retdata;

    //保留字段
    private String reserved;

    //mac随机数
    private String maccounter;

    // 一卡通中心联机交易流水号(昆山特有)
    private String ykttxnseq;
    
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

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    public String getYktcode() {
        return yktcode;
    }

    public void setYktcode(String yktcode) {
        this.yktcode = yktcode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getCardinnerno() {
        return cardinnerno;
    }

    public void setCardinnerno(String cardinnerno) {
        this.cardinnerno = cardinnerno;
    }

    public String getCardfaceno() {
        return cardfaceno;
    }

    public void setCardfaceno(String cardfaceno) {
        this.cardfaceno = cardfaceno;
    }

    public String getTxntype() {
        return txntype;
    }

    public void setTxntype(String txntype) {
        this.txntype = txntype;
    }

    public String getTradeendflag() {
        return tradeendflag;
    }

    public void setTradeendflag(String tradeendflag) {
        this.tradeendflag = tradeendflag;
    }

    public String getTradestartflag() {
        return tradestartflag;
    }

    public void setTradestartflag(String tradestartflag) {
        this.tradestartflag = tradestartflag;
    }

    public String getPostype() {
        return postype;
    }

    public void setPostype(String postype) {
        this.postype = postype;
    }

    public String getChargetype() {
        return chargetype;
    }

    public void setChargetype(String chargetype) {
        this.chargetype = chargetype;
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

    public String getTxnseq() {
        return txnseq;
    }

    public void setTxnseq(String txnseq) {
        this.txnseq = txnseq;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getCrdordernum() {
        return crdordernum;
    }

    public void setCrdordernum(String crdordernum) {
        this.crdordernum = crdordernum;
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

    public String getBlackamt() {
        return blackamt;
    }

    public void setBlackamt(String blackamt) {
        this.blackamt = blackamt;
    }

    public String getCardlimit() {
        return cardlimit;
    }

    public void setCardlimit(String cardlimit) {
        this.cardlimit = cardlimit;
    }

    public String getSecmac() {
        return secmac;
    }

    public void setSecmac(String secmac) {
        this.secmac = secmac;
    }

    public String getCardcounter() {
        return cardcounter;
    }

    public void setCardcounter(String cardcounter) {
        this.cardcounter = cardcounter;
    }

    public String getTxnstat() {
        return txnstat;
    }

    public void setTxnstat(String txnstat) {
        this.txnstat = txnstat;
    }

    public Specdata getSpecdata() {
        return specdata;
    }

    public void setSpecdata(Specdata specdata) {
        this.specdata = specdata;
    }

    public String[] getTxnrecode() {
        return txnrecode;
    }

    public void setTxnrecode(String[] txnrecode) {
        this.txnrecode = txnrecode;
    }

    public String[] getBankfile() {
        return bankfile;
    }

    public void setBankfile(String[] bankfile) {
        this.bankfile = bankfile;
    }

    public String[] getApdu() {
        return apdu;
    }

    public void setApdu(String[] apdu) {
        this.apdu = apdu;
    }

    public String getRetdata() {
        return retdata;
    }

    public void setRetdata(String retdata) {
        this.retdata = retdata;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getMaccounter() {
        return maccounter;
    }

    public void setMaccounter(String maccounter) {
        this.maccounter = maccounter;
    }

    public String getYkttxnseq() {
        return ykttxnseq;
    }

    public void setYkttxnseq(String ykttxnseq) {
        this.ykttxnseq = ykttxnseq;
    }

}
