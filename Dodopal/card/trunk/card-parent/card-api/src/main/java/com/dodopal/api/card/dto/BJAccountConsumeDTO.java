package com.dodopal.api.card.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;


/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2016年5月18日 下午4:43:32 
  * @version 1.0 
  * @parameter  消费优惠-账户消费 
  */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BJAccountConsumeDTO extends BJConsumeDiscountHeadDTO{

    private static final long serialVersionUID = -4529192602955657151L;
    
    //卡服务订单号
    private String crdordernum;
    
    // 产品库主订单号
    private String proordernum;
    // 产品编号
    private String procode;
    // 商户编号
    private String mercode;
    
    // 城市代码 size4 
    private String citycode;
    
    // 商户类型 0:网点1:集团 2:外接 size2
    private String mertype; 
    
    // 设备类型 size2 0：NFC手机 1：都都宝家用机  2：都都宝商用机
    private String postype; 
    
    // 设备编号 size12
    private String poscode; 
    
    // 操作员号：前12位填写SAM号 size16
    private String operid; 
    
    // 结算日期size16
    private String settdate; 
    
    // 通讯流水号size10
    private String comseq; 
    
    // 终端IC交易流水号size10
    private String icseq;  
    
    // 终端账户交易流水号size10
    private String accseq; 
    
    // 批次号 size10
    private String batchid; 
    
    //系统时间 YYYYMMDDhhmmss  size14
    private String datetime;     
    
    //卡号   size20
    private String cardno; 
    
    //账户号  size10
    private String accountno; 
    
    //交易金额  size10
    private String txnamt; 
    
    //卡号 BCD 8
    private String carno; 
    
    //应收金额
    private String receivableamt; 
    
    //实收金额
    private String receivedamt; 
    
    //个性化信息
    private String privimsg;
    
    //账户个数
    private String accnum;
    
    //账户信息
    private String accinfo;
      
    //交易状态0-成功，1-失败；仅仅在失败的情况下特殊中有值
    private String txnstat;
    
    /**
     * 特殊域
     */
    private BJAccountConsumeSpecialDTO crdm;
    
    //保留字段
    private String reserved;

    public String getProordernum() {
        return proordernum;
    }

    public String getTxnstat() {
        return txnstat;
    }

    public void setTxnstat(String txnstat) {
        this.txnstat = txnstat;
    }

    public void setProordernum(String proordernum) {
        this.proordernum = proordernum;
    }

    public String getProcode() {
        return procode;
    }

    public void setProcode(String procode) {
        this.procode = procode;
    }

    public String getMercode() {
        return mercode;
    }

    public void setMercode(String mercode) {
        this.mercode = mercode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getMertype() {
        return mertype;
    }

    public void setMertype(String mertype) {
        this.mertype = mertype;
    }

    public String getPostype() {
        return postype;
    }

    public void setPostype(String postype) {
        this.postype = postype;
    }


    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getSettdate() {
        return settdate;
    }

    public void setSettdate(String settdate) {
        this.settdate = settdate;
    }

    public String getComseq() {
        return comseq;
    }

    public void setComseq(String comseq) {
        this.comseq = comseq;
    }

    public String getIcseq() {
        return icseq;
    }

    public void setIcseq(String icseq) {
        this.icseq = icseq;
    }

    public String getAccseq() {
        return accseq;
    }

    public void setAccseq(String accseq) {
        this.accseq = accseq;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getTxnamt() {
        return txnamt;
    }

    public void setTxnamt(String txnamt) {
        this.txnamt = txnamt;
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno;
    }

    public String getReceivableamt() {
        return receivableamt;
    }

    public void setReceivableamt(String receivableamt) {
        this.receivableamt = receivableamt;
    }

    public String getReceivedamt() {
        return receivedamt;
    }

    public void setReceivedamt(String receivedamt) {
        this.receivedamt = receivedamt;
    }

    public String getPrivimsg() {
        return privimsg;
    }

    public void setPrivimsg(String privimsg) {
        this.privimsg = privimsg;
    }

    public String getAccnum() {
        return accnum;
    }

    public void setAccnum(String accnum) {
        this.accnum = accnum;
    }


    public String getAccinfo() {
        return accinfo;
    }

    public void setAccinfo(String accinfo) {
        this.accinfo = accinfo;
    }



    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getCrdordernum() {
        return crdordernum;
    }

    public void setCrdordernum(String crdordernum) {
        this.crdordernum = crdordernum;
    }

    public String getPoscode() {
        return poscode;
    }

    public void setPoscode(String poscode) {
        this.poscode = poscode;
    }

    public BJAccountConsumeSpecialDTO getCrdm() {
        return crdm;
    }

    public void setCrdm(BJAccountConsumeSpecialDTO crdm) {
        this.crdm = crdm;
    }

    /* 
     * 
     */
    @Override
    public String toString() {
        return super.toString()+"BJAccountConsumeDTO [crdordernum=" + crdordernum + ", proordernum=" + proordernum + ", procode=" + procode + ", mercode=" + mercode + ", citycode=" + citycode + ", mertype=" + mertype + ", postype=" + postype + ", poscode=" + poscode + ", operid=" + operid + ", settdate=" + settdate + ", comseq=" + comseq + ", icseq=" + icseq + ", accseq=" + accseq + ", batchid=" + batchid + ", datetime=" + datetime + ", cardno=" + cardno + ", accountno=" + accountno + ", txnamt=" + txnamt + ", carno="
            + carno + ", receivableamt=" + receivableamt + ", receivedamt=" + receivedamt + ", privimsg=" + privimsg + ", accnum=" + accnum + ", accinfo=" + accinfo + ", txnstat=" + txnstat + ", crdm=" + crdm + ", reserved=" + reserved + "]";
    }




}
