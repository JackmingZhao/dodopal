package com.dodopal.users.business.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.enums.MerUserFlgEnum;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.dao.MerBatchRechargeOrderMapper;
import com.dodopal.users.business.model.MerBatchRechargeItem;
import com.dodopal.users.business.model.MerBatchRechargeOrder;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.query.MerBatchRcgOrderQuery;
import com.dodopal.users.business.service.MerBatchRechargeItemService;
import com.dodopal.users.business.service.MerBatchRechargeOrderService;
import com.dodopal.users.business.service.MerchantUserService;

/**
 * @author lifeng@dodopal.com
 */
@Service
public class MerBatchRechargeOrderServiceImpl implements MerBatchRechargeOrderService {
	@Autowired
	private MerBatchRechargeOrderMapper merBatchRechargeOrderMapper;
	@Autowired
	private MerchantUserService merchantUserService;
	@Autowired
	private MerBatchRechargeItemService merBatchRechargeItemService;

	@Override
	@Transactional(readOnly = true)
	public DodopalDataPage<MerBatchRechargeOrder> findBatchRcgOrderByPage(MerBatchRcgOrderQuery query) {
		List<MerBatchRechargeOrder> resultList = null;
		MerchantUser user = merchantUserService.findMerchantUserById(query.getUserId());
		if (user != null) {
			if (MerUserFlgEnum.ADMIN.getCode().equals(user.getMerUserFlag())) {
				resultList = merBatchRechargeOrderMapper.findMerOrdersByPage(query);
			} else {
				query.setMerUserName(user.getMerUserName());
				resultList = merBatchRechargeOrderMapper.findCommonUserOrdersByPage(query);
			}
		}
		DodopalDataPage<MerBatchRechargeOrder> pages = new DodopalDataPage<MerBatchRechargeOrder>(query.getPage(), resultList);
		return pages;
	}

	@Override
	@Transactional
	public int saveBatchRcgOrder(MerBatchRechargeOrder order) {
		int num = merBatchRechargeOrderMapper.saveBatchRcgOrder(order);
		if (num > 0) {
			String orderId = order.getId();
			if (StringUtils.isNotBlank(orderId)) {
				List<MerBatchRechargeItem> list = order.getBatchItemList();
				if (CollectionUtils.isNotEmpty(list)) {
					// 充值项设置批次单号
					for (MerBatchRechargeItem item : list) {
						item.setBatchOrderId(orderId);
					}
					merBatchRechargeItemService.batchAddRcgItem(list);
				}
			}
		}
		return num;
	}

}
