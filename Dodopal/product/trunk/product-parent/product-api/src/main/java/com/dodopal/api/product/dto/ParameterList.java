/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.api.product.dto;

/**
 * Created by lenovo on 2016-03-14.
 * 参数下载
 */
public class ParameterList {
    //消息类型
    private String messagetype;
    //版本号
    private String ver;
    //发送时间
    private String sysdatetime;
    //特殊域启用标志
    private String istsused;
    //应答码
    private String respcode;
    //城市代码
    private String citycode;
    //商户类型
    private String mertype;
    //商户号
    private String mercode;
    //设备类型
    private String postype;
    //设备编号
    private String posid;
    //操作员号
    private String operid;
    //sam卡号
    private String samno;
    //结算日期
    private String settdate;
    //系统时间
    private String datetime;
    //下载标志
    private String downflag;
    //特殊域
    private SpecialModel crdm;
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

    public String getSamno() {
        return samno;
    }

    public void setSamno(String samno) {
        this.samno = samno;
    }

    public String getSettdate() {
        return settdate;
    }

    public void setSettdate(String settdate) {
        this.settdate = settdate;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDownflag() {
        return downflag;
    }

    public void setDownflag(String downflag) {
        this.downflag = downflag;
    }

    public SpecialModel getCrdm() {
        return crdm;
    }

    public void setCrdm(SpecialModel crdm) {
        this.crdm = crdm;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
}
