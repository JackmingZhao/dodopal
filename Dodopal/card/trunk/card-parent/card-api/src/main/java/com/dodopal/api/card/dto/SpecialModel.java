package com.dodopal.api.card.dto;

import java.io.Serializable;
import java.util.List;

public class SpecialModel<T> implements Serializable {
    private static final long serialVersionUID = 8847086890491023926L;

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
    private List<T> listPars;
    //sam卡号
    private String samno;
    
    public String getSamno() {
        return samno;
    }

    public void setSamno(String samno) {
        this.samno = samno;
    }

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

	public List<T> getListPars() {
		return listPars;
	}

	public void setListPars(List<T> listPars) {
		this.listPars = listPars;
	}



}
