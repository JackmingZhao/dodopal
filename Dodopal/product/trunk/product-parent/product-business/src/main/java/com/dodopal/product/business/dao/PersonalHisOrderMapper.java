package com.dodopal.product.business.dao;

import java.util.List;

import com.dodopal.product.business.model.PersonalHisOrder;
import com.dodopal.product.business.model.query.PersonalHisOrderQuery;

/**
 * @author lifeng@dodopal.com
 */

public interface PersonalHisOrderMapper {
	public List<PersonalHisOrder> findRechargeOrderByPage(PersonalHisOrderQuery personalHisOrderQuery);

	public List<PersonalHisOrder> findConsumeOrderByPage(PersonalHisOrderQuery personalHisOrderQuery);
}
