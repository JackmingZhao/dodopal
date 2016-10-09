package com.dodopal.users.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.model.MerBatchRechargeOrder;
import com.dodopal.users.business.model.query.MerBatchRcgOrderQuery;

/**
 * @author lifeng@dodopal.com
 */

public interface MerBatchRechargeOrderService {
	/**
	 * 分页查询批次单列表
	 * 
	 * @param userId
	 * @param merCode
	 * @return
	 */
	public DodopalDataPage<MerBatchRechargeOrder> findBatchRcgOrderByPage(MerBatchRcgOrderQuery query);

	/**
	 * 新增批次单
	 * @param order
	 * @return
	 */
	public int saveBatchRcgOrder(MerBatchRechargeOrder order);
}
