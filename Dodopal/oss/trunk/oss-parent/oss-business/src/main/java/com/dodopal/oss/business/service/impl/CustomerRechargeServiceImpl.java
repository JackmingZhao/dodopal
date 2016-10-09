package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.dao.CustomerRechargeMapper;
import com.dodopal.oss.business.model.CustomerRecharge;
import com.dodopal.oss.business.model.dto.CustomerRechargeQuery;
import com.dodopal.oss.business.service.CustomerRechargeService;

@Service
public class CustomerRechargeServiceImpl implements CustomerRechargeService {

	@Autowired
	private CustomerRechargeMapper customerRechargeMapper;
	
	@Override
	@Transactional(readOnly=true)
	public DodopalDataPage<CustomerRecharge> findCustomerRechargeList(CustomerRechargeQuery customerRechargeQuery) {
		List<CustomerRecharge> records=customerRechargeMapper.findCustomerRechargeByPage(customerRechargeQuery);
		DodopalDataPage<CustomerRecharge> dataPage = new DodopalDataPage<>(customerRechargeQuery.getPage(), records);
		return dataPage;
	}

	@Override
	public int findCustomerRechargeCount(CustomerRechargeQuery customerRechargeQuery) {
		// TODO Auto-generated method stub
		return customerRechargeMapper.findCustomerRechargeCount(customerRechargeQuery);
	}

	@Override
	public List<CustomerRecharge> findCardRechargeExcel(CustomerRechargeQuery customerRechargeQuery) {
		// TODO Auto-generated method stub
		return customerRechargeMapper.findCardRechargeExcel(customerRechargeQuery);
	}

}
