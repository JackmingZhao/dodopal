package com.dodopal.users.business.service;

import java.util.List;

import com.dodopal.users.business.model.MerBatchRechargeItem;

/** 
 * @author lifeng@dodopal.com
 */

public interface MerBatchRechargeItemService {
	/**
	 * 批量添加批次单充值项
	 * @param list
	 * @return
	 */
	public int batchAddRcgItem(List<MerBatchRechargeItem> list);
}
