package com.dodopal.api.card.dto;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dodopal.common.model.BaseEntity;

/**
 * 类说明 ：北京卡服务接口传输对象
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BJCrdSysOrderDTO extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -8045841134170243929L;

    //消息类型
    private String messagetype;

    //版本号
    private String ver;

    //前端发送时间格式如下 YYYYMMDDhhmmss
    private String sysdatetime;

    //产品编号
    private String productcode;

    //商户编号
    private String mercode;

    //商户订单编号/圈存订单编号
    private String merordercode;

    //交易应答码
    private String respcode;

    //一卡通代码
    private String yktcode;

    //城市代码
    private String citycode;

    //卡物理类型
    private String cardtype;

    //卡号(前端读出)
    private String tradecard;

    //卡号(验卡返回 充值和结果回传)
    private String cardinnerno;

    //卡面号(验卡返回 充值和结果回传)
    private String cardfaceno;

    //0-交易透传 1-交易结束
    private String tradeendflag;

    //0-交易透传 1-交易开始
    private String tradestartflag;

    //数据来源
    private String source;

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

    //产品库主订单号
    private String prdordernum;

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

    //支付类型
    private String paytype;

    //支付方式
    private String payway;

    //圈存订单号
    private String loadordernum;

    //用户id
    private String userid;

    //商户端签名值(关键字段签名值)
    private String m_sign;

    //动态库签名值
    private String d_sign;

    //产品库签名值
    private String p_sign;

    //交易流水号
    private String paymentTranNo;

    //mac随机数
    private String maccounter;

    //0-查询
    private String txntype;

    //结果上传交易状态0：成功 1：失败 2：未知
    private String txnstat;

    /*
     * 北京新加start
     */

    //POS通讯流水号
    private String postransseq;

    //密钥版本
    private String keyindex;

    //应用标识
    private String appid;

    //脱机标识 0：联机 1：脱机
    private String offlineflag;

    //PSAM号
    private String samno;

    //特殊域
    private BJSpecdata specdata;

    //黑名单标志
    private String blackflag;

    //北京通卡交易类型
    private String bjtktradetype;

    //北京通卡交易状态 0：成功 1：失败 2：未知
    private String bjtktradestat;

    //北京通卡卡逻辑类型
    private String cardlogictype;

    //北京通卡卡物理类型
    private String bjtkcardphytype;

    //通卡批次号
    private String batchno;

    //通卡商户号
    private String mchntid;
    /*
     * 北京新加end
     */

    /********************************** 产品库追加参数 START **************************************/

    // 圈存订单 list Map包含 圈存订单号 金额 卡号 参考 LoadOrderQueryResponseDTO
    private List<Map<String, String>> loadOrderList;

    // 应收金额（单位：分）
    private String receivableAmt;

    // 实收金额（单位：分）
    private String receivedAmt;

    // 商户折扣
    private String merdiscount;

    //用户折扣
    private String userdiscount;

    //结算折扣   如果是分时段折扣生成的交易记录，则第一位是”1”,以和旧版折扣区分
    private String settldiscount;

    /*********************************** 产品库追加参数 END *****************************************/
    /*********************************** 北京NFC增加 start *****************************************/
    //请求序号
    private String requestno;
    //手机号
    private String mobileno;
    //手机类型
    private String mobiletype;
    //手机IMEI号
    private String mobileimei;
    //手机系统版本
    private String mobilesysver;
    //卡片透支金额
    private String cardoverdraft;
    //卡片启用日期
    private String cardstartdate;
    //卡片失效日期
    private String cardenddate;
    //卡启用标志
    private String cardstartflag;
    //手机imsi号
    private String imsi;
    //卡片处理方式
    private String dealtype;
    //资金来源
    private String paysource;
    //应答序号
    private String responseno;
    //北京nfc特属域 
    private BJNfcSpecdata nfcspecdata;

    /*********************************** 北京NFC增加 end *****************************************/

    public String getUserdiscount() {
        return userdiscount;
    }

    public void setUserdiscount(String userdiscount) {
        this.userdiscount = userdiscount;
    }

    public String getSettldiscount() {
        return settldiscount;
    }

    public void setSettldiscount(String settldiscount) {
        this.settldiscount = settldiscount;
    }

    public String getReceivableAmt() {
        return receivableAmt;
    }

    public void setReceivableAmt(String receivableAmt) {
        this.receivableAmt = receivableAmt;
    }

    public String getReceivedAmt() {
        return receivedAmt;
    }

    public void setReceivedAmt(String receivedAmt) {
        this.receivedAmt = receivedAmt;
    }

    public String getMerdiscount() {
        return merdiscount;
    }

    public void setMerdiscount(String merdiscount) {
        this.merdiscount = merdiscount;
    }

    public List<Map<String, String>> getLoadOrderList() {
        return loadOrderList;
    }

    public void setLoadOrderList(List<Map<String, String>> loadOrderList) {
        this.loadOrderList = loadOrderList;
    }

    public String getP_sign() {
        return p_sign;
    }

    public void setP_sign(String p_sign) {
        this.p_sign = p_sign;
    }

    public String getM_sign() {
        return m_sign;
    }

    public void setM_sign(String m_sign) {
        this.m_sign = m_sign;
    }

    public String getD_sign() {
        return d_sign;
    }

    public void setD_sign(String d_sign) {
        this.d_sign = d_sign;
    }

    public String getLoadordernum() {
        return loadordernum;
    }

    public void setLoadordernum(String loadordernum) {
        this.loadordernum = loadordernum;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

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

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getMercode() {
        return mercode;
    }

    public void setMercode(String mercode) {
        this.mercode = mercode;
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

    public String getTradecard() {
        return tradecard;
    }

    public void setTradecard(String tradecard) {
        this.tradecard = tradecard;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getPrdordernum() {
        return prdordernum;
    }

    public void setPrdordernum(String prdordernum) {
        this.prdordernum = prdordernum;
    }

    public String getMerordercode() {
        return merordercode;
    }

    public void setMerordercode(String merordercode) {
        this.merordercode = merordercode;
    }

    public String getPaymentTranNo() {
        return paymentTranNo;
    }

    public void setPaymentTranNo(String paymentTranNo) {
        this.paymentTranNo = paymentTranNo;
    }

    public String getMaccounter() {
        return maccounter;
    }

    public void setMaccounter(String maccounter) {
        this.maccounter = maccounter;
    }

    public String getCardlogictype() {
        return cardlogictype;
    }

    public void setCardlogictype(String cardlogictype) {
        this.cardlogictype = cardlogictype;
    }

    public String getPostransseq() {
        return postransseq;
    }

    public void setPostransseq(String postransseq) {
        this.postransseq = postransseq;
    }

    public String getKeyindex() {
        return keyindex;
    }

    public void setKeyindex(String keyindex) {
        this.keyindex = keyindex;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getOfflineflag() {
        return offlineflag;
    }

    public void setOfflineflag(String offlineflag) {
        this.offlineflag = offlineflag;
    }

    public String getSamno() {
        return samno;
    }

    public void setSamno(String samno) {
        this.samno = samno;
    }

    public BJSpecdata getSpecdata() {
        return specdata;
    }

    public void setSpecdata(BJSpecdata specdata) {
        this.specdata = specdata;
    }

    public String getBlackflag() {
        return blackflag;
    }

    public void setBlackflag(String blackflag) {
        this.blackflag = blackflag;
    }

    public String getBjtktradetype() {
        return bjtktradetype;
    }

    public void setBjtktradetype(String bjtktradetype) {
        this.bjtktradetype = bjtktradetype;
    }

    public String getBjtktradestat() {
        return bjtktradestat;
    }

    public void setBjtktradestat(String bjtktradestat) {
        this.bjtktradestat = bjtktradestat;
    }

    public String getBjtkcardphytype() {
        return bjtkcardphytype;
    }

    public void setBjtkcardphytype(String bjtkcardphytype) {
        this.bjtkcardphytype = bjtkcardphytype;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getMchntid() {
        return mchntid;
    }

    public void setMchntid(String mchntid) {
        this.mchntid = mchntid;
    }

    public BJNfcSpecdata getNfcspecdata() {
        return nfcspecdata;
    }

    public void setNfcspecdata(BJNfcSpecdata nfcspecdata) {
        this.nfcspecdata = nfcspecdata;
    }

    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
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

    public String getCardoverdraft() {
        return cardoverdraft;
    }

    public void setCardoverdraft(String cardoverdraft) {
        this.cardoverdraft = cardoverdraft;
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

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getDealtype() {
        return dealtype;
    }

    public void setDealtype(String dealtype) {
        this.dealtype = dealtype;
    }

    public String getPaysource() {
        return paysource;
    }

    public void setPaysource(String paysource) {
        this.paysource = paysource;
    }

    public String getResponseno() {
        return responseno;
    }

    public void setResponseno(String responseno) {
        this.responseno = responseno;
    }

}
