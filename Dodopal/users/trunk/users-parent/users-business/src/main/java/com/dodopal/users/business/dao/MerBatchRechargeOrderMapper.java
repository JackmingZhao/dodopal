package com.dodopal.users.business.dao;

import java.util.List;

import com.dodopal.users.business.model.MerBatchRechargeOrder;
import com.dodopal.users.business.model.query.MerBatchRcgOrderQuery;

/**
 * @author lifeng@dodopal.com
 */

public interface MerBatchRechargeOrderMapper {
	/**
	 * 分页查询批次单列表（商户管理员）
	 * 
	 * @param merCode
	 * @return
	 */
	public List<MerBatchRechargeOrder> findMerOrdersByPage(MerBatchRcgOrderQuery query);

	/**
	 * 分页查询批次单列表（批充管理员和普通操作员）
	 * 
	 * @param userId
	 * @return
	 */
	public List<MerBatchRechargeOrder> findCommonUserOrdersByPage(MerBatchRcgOrderQuery query);

	/**
	 * 新增批次单
	 * @param list
	 * @return
	 */
	public int saveBatchRcgOrder(MerBatchRechargeOrder order);
}
