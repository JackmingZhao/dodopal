package com.dodopal.product.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.product.business.model.PersonalHisOrder;
import com.dodopal.product.business.model.query.PersonalHisOrderQuery;

/**
 * @author lifeng@dodopal.com
 */

public interface PersonalHisOrderService {

	/**
	 * 个人充值订单查询(分页)
	 * 
	 * @param personalHisOrderQuery
	 * @return
	 */
	public DodopalDataPage<PersonalHisOrder> findRechargeOrderByPage(PersonalHisOrderQuery personalHisOrderQuery);

	/**
	 * 个人消费订单查询(分页)
	 * 
	 * @param personalHisOrderQuery
	 * @return
	 */
	public DodopalDataPage<PersonalHisOrder> findConsumeOrderByPage(PersonalHisOrderQuery personalHisOrderQuery);
}
