package com.dodopal.oss.business.service;

import java.util.List;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.CustomerRecharge;
import com.dodopal.oss.business.model.dto.CustomerRechargeQuery;

public interface CustomerRechargeService {

	/**
	 * 查询客户充值明细统计List
	 * @param customerRechargeQuery
	 * @return
	 */
	public DodopalDataPage<CustomerRecharge> findCustomerRechargeList(CustomerRechargeQuery customerRechargeQuery);
	
	/**
	 * 获取导出总记录数
	 * @param customerRechargeQuery
	 * @return
	 */
	public int findCustomerRechargeCount(CustomerRechargeQuery customerRechargeQuery);
	
	/**
	 * 获取导出记录的集合
	 * @param customerRechargeQuery
	 * @return
	 */
	public List<CustomerRecharge> findCardRechargeExcel(CustomerRechargeQuery customerRechargeQuery);
}
