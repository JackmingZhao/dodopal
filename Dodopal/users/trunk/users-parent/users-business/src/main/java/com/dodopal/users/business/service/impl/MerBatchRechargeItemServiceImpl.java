package com.dodopal.users.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.users.business.dao.MerBatchRechargeItemMapper;
import com.dodopal.users.business.model.MerBatchRechargeItem;
import com.dodopal.users.business.service.MerBatchRechargeItemService;

/**
 * @author lifeng@dodopal.com
 */
@Service
public class MerBatchRechargeItemServiceImpl implements MerBatchRechargeItemService {
	@Autowired
	MerBatchRechargeItemMapper merBatchRechargeItemMapper;

	@Override
	@Transactional
	public int batchAddRcgItem(List<MerBatchRechargeItem> list) {
		return merBatchRechargeItemMapper.batchAddRcgItem(list);
	}

}
