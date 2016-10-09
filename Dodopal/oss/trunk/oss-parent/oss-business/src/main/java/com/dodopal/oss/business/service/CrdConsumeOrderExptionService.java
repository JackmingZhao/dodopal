package com.dodopal.oss.business.service;

import com.dodopal.oss.business.model.CrdConsumeOrderExption;

public interface CrdConsumeOrderExptionService {

	/**
	 * 更新一卡通消费订单异常处理
	 * @param judge
	 * @param orderNo
	 * @param customerNo
	 * @return
	 */
	public void updateCrdConsumeOrder(CrdConsumeOrderExption consumeOrderExption);
	
}
