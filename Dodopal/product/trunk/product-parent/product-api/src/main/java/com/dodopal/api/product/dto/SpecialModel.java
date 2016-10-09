/**
 * 建亿通北京数据处理有限公司上海分公司
 */
package com.dodopal.api.product.dto;

import java.util.List;

/**
 * Created by lenovo on 2016-03-14.
 * 特殊域格式方法
 */
public class SpecialModel {
    //下载参数编号
    private String parno;
    //一次下载的记录条数
    private String reqtotal;
    //记录下载开始索引
    private String reqindex;
    //该类参数总记录条数
    private String total;
    //剩余记录条数
    private String surplus;
    //本次发送记录数
    private String size;
    //各个参数编号对应的对象集合
    private List listPars;

    public String getParno() {
        return parno;
    }

    public void setParno(String parno) {
        this.parno = parno;
    }

    public String getReqtotal() {
        return reqtotal;
    }

    public void setReqtotal(String reqtotal) {
        this.reqtotal = reqtotal;
    }

    public String getReqindex() {
        return reqindex;
    }

    public void setReqindex(String reqindex) {
        this.reqindex = reqindex;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List getListPars() {
        return listPars;
    }

    public void setListPars(List listPars) {
        this.listPars = listPars;
    }
}
