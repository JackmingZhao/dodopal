package com.dodopal.api.card.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dodopal.common.model.BaseEntity;

/**
 * 类说明 ：北京优惠信息查询DTO
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BJDiscountDTO extends BaseEntity {

    private static final long serialVersionUID = 5920322412120681862L;
    //消息类型
    private String messagetype;
    //版本号
    private String ver;
    //发送时间YYYYMMDDhhmmss
    private String sysdatetime;
    //应答码
    private String respcode;
    //城市代码
    private String citycode;
    //商户类型 0:网点;1:集团;2:外接
    private String mertype;
    //商户号
    private String mercode;
    //设备类型 0：NFC手机;1：都都宝家用机;2：都都宝商用机
    private String postype;
    //设备编号
    private String posid;
    //操作员号：前12位填写SAM号
    private String operid;
    //结算日期
    private String settdate;
    //通讯流水号
    private String comseq;
    //终端IC交易流水号
    private String icseq;
    //终端账户交易流水号
    private String accseq;
    //批次号
    private String batchid;
    //卡号
    private String cardno;
    //交易前金额
    private String befbal;
    //交易金额
    private String amout;
    //系统时间
    private String datetime;
    //特殊域
    private String crdm;
    //保留字段
    private String reserved;

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

    public String getMercode() {
        return mercode;
    }

    public void setMercode(String mercode) {
        this.mercode = mercode;
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

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getBefbal() {
        return befbal;
    }

    public void setBefbal(String befbal) {
        this.befbal = befbal;
    }

    public String getAmout() {
        return amout;
    }

    public void setAmout(String amout) {
        this.amout = amout;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCrdm() {
        return crdm;
    }

    public void setCrdm(String crdm) {
        this.crdm = crdm;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

}
