package com.dodopal.api.card.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BJIntegralConsumeDTO extends BJConsumeDiscountHeadDTO{
    private static final long serialVersionUID = -7768447861010998665L;
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
    
    //积分金额(消耗积分分值)  size10
    private String preautheamt;

    //预留
    private String reserved;
    /**
     * 特殊域
     */
    private BJIntegralConsumeSpecialDTO crdm;
    
    //交易状态0-成功，1-失败；3—已经成功但要撤销，仅仅在失败和撤销的情况下特殊中有值
    private String txnstat;
    
    public String getTxnstat() {
        return txnstat;
    }
    public void setTxnstat(String txnstat) {
        this.txnstat = txnstat;
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
    public String getPreautheamt() {
        return preautheamt;
    }
    public void setPreautheamt(String preautheamt) {
        this.preautheamt = preautheamt;
    }
    public String getProordernum() {
        return proordernum;
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
    public BJIntegralConsumeSpecialDTO getCrdm() {
        return crdm;
    }
    public void setCrdm(BJIntegralConsumeSpecialDTO crdm) {
        this.crdm = crdm;
    }
    /* 
     * 
     */
    @Override
    public String toString() {
        return super.toString()+"BJIntegralConsumeDTO [crdordernum=" + crdordernum + ", proordernum=" + proordernum + ", procode=" + procode + ", mercode=" + mercode + ", citycode=" + citycode + ", mertype=" + mertype + ", postype=" + postype + ", poscode=" + poscode + ", operid=" + operid + ", settdate=" + settdate + ", comseq=" + comseq + ", icseq=" + icseq + ", accseq=" + accseq + ", batchid=" + batchid + ", datetime=" + datetime + ", cardno=" + cardno + ", preautheamt=" + preautheamt + ", reserved=" + reserved
            + ", crdm=" + crdm + ", txnstat=" + txnstat + "]";
    }

}
