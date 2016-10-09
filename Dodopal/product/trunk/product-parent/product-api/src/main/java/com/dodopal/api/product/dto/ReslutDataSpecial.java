/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.api.product.dto;

import java.util.List;

/**
 * Created by lenovo on 2016-03-15.
 * 结果上传特殊域数据
 */
public class ReslutDataSpecial {
    //总记录数
    private String totalrecnum;
    //本次记录数
    private String recnum;
    //剩余记录数
    private String leftnum;
    //记录数据集合
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
