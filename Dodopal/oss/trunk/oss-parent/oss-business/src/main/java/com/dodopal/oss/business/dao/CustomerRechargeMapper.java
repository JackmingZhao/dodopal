package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.CustomerRecharge;
import com.dodopal.oss.business.model.dto.CustomerRechargeQuery;

public interface CustomerRechargeMapper {

	/**
	 * 查询客户充值明细统计List
	 * @param customerRechargeQuery
	 * @return
	 */
	public List<CustomerRecharge> findCustomerRechargeByPage(CustomerRechargeQuery customerRechargeQuery);
	
	/**
	 * 获取当前总计路数
	 * @param customerRechargeQuery
	 * @return
	 */
	public int findCustomerRechargeCount(CustomerRechargeQuery customerRechargeQuery) ;
	
	/**
	 * 导出数据集合
	 * @param customerRechargeQuery
	 * @return
	 */
	public List<CustomerRecharge> findCardRechargeExcel(CustomerRechargeQuery customerRechargeQuery) ;
}
