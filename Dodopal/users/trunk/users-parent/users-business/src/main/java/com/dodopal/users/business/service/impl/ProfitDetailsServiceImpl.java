package com.dodopal.users.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.dao.ProfitDetailsMapper;
import com.dodopal.users.business.model.MerchantUser;
import com.dodopal.users.business.model.ProfitDetails;
import com.dodopal.users.business.model.query.ProfitQuery;
import com.dodopal.users.business.service.ProfitDetailsService;

@Service
public class ProfitDetailsServiceImpl implements ProfitDetailsService{
	private final static Logger logger = LoggerFactory.getLogger(ProfitDetailsServiceImpl.class);
    @Autowired
    ProfitDetailsMapper mapper;
    
    @Transactional(readOnly = true)
	public DodopalDataPage<ProfitDetails> findProfitDetailsByPage(ProfitQuery query) {
    	List<ProfitDetails > list = mapper.findProfitDetailsByPage(query);
        DodopalDataPage<ProfitDetails> pages = new DodopalDataPage<ProfitDetails>(query.getPage(), list);
        return pages;
	}


}
