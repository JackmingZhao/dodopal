package com.dodopal.users.business.dao;

import java.util.List;

import com.dodopal.users.business.model.MerBatchRechargeItem;

/**
 * @author lifeng@dodopal.com
 */

public interface MerBatchRechargeItemMapper {
	//public List<MerBatchRechargeItem> findRcgItemsByOrderIdByPage(String orderId);

	/**
	 * 批量添加批次单充值项
	 * @param list
	 * @return
	 */
	public int batchAddRcgItem(List<MerBatchRechargeItem> list);
}
