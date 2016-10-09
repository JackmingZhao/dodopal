package com.dodopal.api.card.dto;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.dodopal.common.model.BaseEntity;

/**
 * pos签到签退DTO
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PosSignInOutDTO extends BaseEntity {

    private static final long serialVersionUID = 7966993264749145322L;

    //消息类型
    private String messagetype;

    //版本号
    private String ver;

    //前端发送时间格式如下 YYYYMMDDhhmmss
    private String sysdatetime;

    //响应码
    private String respcode;

    //一卡通代码
    private String yktcode;

    //城市代码
    private String citycode;

    //设备编号
    private String posid;

    //PSAM号
    private String samno;

    //保留域
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

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getSamno() {
        return samno;
    }

    public void setSamno(String samno) {
        this.samno = samno;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

}
