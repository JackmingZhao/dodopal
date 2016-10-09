package com.dodopal.oss.business.model.dto;

import java.util.Date;

import com.dodopal.common.model.QueryBase;

/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2015年12月23日 下午1:19:10 
  * @version 1.0 
  * @parameter   
  * 卡服务日志query类 
  */
public class CrdSysLogQuery extends QueryBase{
	private static final long serialVersionUID = -8515702930750590545L;
	//卡服务订单号
	private String crdOrderNum;
	//产品库订单号
	private String proOrderNum;
    //交易类型（1-充值；2-消费)
    private Integer txnType;
	//服务名称
	private String serverName;
	//创建时间(开始)
    private Date createDateStart;
    //创建时间(结束)
    private Date createDateEnd;
	
	
	public String getCrdOrderNum() {
		return crdOrderNum;
	}
	public void setCrdOrderNum(String crdOrderNum) {
		this.crdOrderNum = crdOrderNum;
	}
	public String getProOrderNum() {
		return proOrderNum;
	}
	public void setProOrderNum(String proOrderNum) {
		this.proOrderNum = proOrderNum;
	}
	
	public Integer getTxnType() {
		return txnType;
	}
	public void setTxnType(Integer txnType) {
		this.txnType = txnType;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public Date getCreateDateStart() {
		return createDateStart;
	}
	public void setCreateDateStart(Date createDateStart) {
		this.createDateStart = createDateStart;
	}
	public Date getCreateDateEnd() {
		return createDateEnd;
	}
	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}
}
