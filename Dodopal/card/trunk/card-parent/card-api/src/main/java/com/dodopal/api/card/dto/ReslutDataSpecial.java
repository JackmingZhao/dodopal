package com.dodopal.api.card.dto;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 北京结果上传特殊域数据
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ReslutDataSpecial implements Serializable {

    private static final long serialVersionUID = -2642148975607684803L;

    //总记录数
    private String totalrecnum;
    //本次记录数
    private String recnum;
    //剩余记录数
    private String leftnum;
    //N条记录数据，N<=8
    private List<ReslutData> filerecords;

    public String getTotalrecnum() {
        return totalrecnum;
    }

    public void setTotalrecnum(String totalrecnum) {
        this.totalrecnum = totalrecnum;
    }

    public String getRecnum() {
        return recnum;
    }

    public void setRecnum(String recnum) {
        this.recnum = recnum;
    }

    public String getLeftnum() {
        return leftnum;
    }

    public void setLeftnum(String leftnum) {
        this.leftnum = leftnum;
    }

    public List<ReslutData> getFilerecords() {
        return filerecords;
    }

    public void setFilerecords(List<ReslutData> filerecords) {
        this.filerecords = filerecords;
    }

}
