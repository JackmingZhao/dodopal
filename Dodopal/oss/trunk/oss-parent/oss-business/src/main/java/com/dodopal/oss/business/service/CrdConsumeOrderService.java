package com.dodopal.oss.business.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.bean.CrdSysConsOrderBean;
import com.dodopal.oss.business.bean.query.CrdSysOrderQuery;

public interface CrdConsumeOrderService {

	/**
     * 查询卡服务消费订单（分页）
     * @param crdSysOrderQuery
     */
	public DodopalResponse<DodopalDataPage<CrdSysConsOrderBean>> findCrdSysConsOrderByPage(CrdSysOrderQuery crdSysOrderQuery);
	
	/**
     * 查询卡服务消费订单
     * @param crdSysOrderQuery
     */
	public DodopalResponse<List<CrdSysConsOrderBean>> excelExport(HttpServletResponse response,CrdSysOrderQuery crdSysOrderQuery);
	/**
	 * 查询卡服务消费订单详情
	 * @param crdOrderNum
	 * @return
	 */
	public DodopalResponse<CrdSysConsOrderBean> findCrdConsumeOrderByCode(String crdOrderNum);
}
