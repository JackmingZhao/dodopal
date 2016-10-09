package com.dodopal.product.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.product.business.dao.PersonalHisOrderMapper;
import com.dodopal.product.business.model.PersonalHisOrder;
import com.dodopal.product.business.model.query.PersonalHisOrderQuery;
import com.dodopal.product.business.service.PersonalHisOrderService;

/**
 * @author lifeng@dodopal.com
 */
@Service
public class PersonalHisOrderServiceImpl implements PersonalHisOrderService {

	@Autowired
	private PersonalHisOrderMapper personalHisOrderMapper;

	@Override
	@Transactional(readOnly = true)
	public DodopalDataPage<PersonalHisOrder> findRechargeOrderByPage(PersonalHisOrderQuery personalHisOrderQuery) {
		List<PersonalHisOrder> result = personalHisOrderMapper.findRechargeOrderByPage(personalHisOrderQuery);
		DodopalDataPage<PersonalHisOrder> pages = new DodopalDataPage<PersonalHisOrder> (personalHisOrderQuery.getPage(), result);
		return pages;
	}

	@Override
	@Transactional(readOnly = true)
	public DodopalDataPage<PersonalHisOrder> findConsumeOrderByPage(PersonalHisOrderQuery personalHisOrderQuery) {
		List<PersonalHisOrder> result = personalHisOrderMapper.findRechargeOrderByPage(personalHisOrderQuery);
		DodopalDataPage<PersonalHisOrder> pages = new DodopalDataPage<PersonalHisOrder> (personalHisOrderQuery.getPage(), result);
		return pages;
	}

}
