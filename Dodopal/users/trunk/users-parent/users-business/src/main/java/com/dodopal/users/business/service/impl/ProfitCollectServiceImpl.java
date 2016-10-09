package com.dodopal.users.business.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.users.business.dao.ProfitCollectMapper;
import com.dodopal.users.business.model.ProfitCollect;
import com.dodopal.users.business.model.query.ProfitQuery;
import com.dodopal.users.business.service.ProfitCollectService;

@Service
public class ProfitCollectServiceImpl implements ProfitCollectService{
	private final static Logger logger = LoggerFactory.getLogger(ProfitCollectServiceImpl.class);
    @Autowired
    ProfitCollectMapper mapper;
	@Override
	public DodopalDataPage<ProfitCollect> findProfitCollectByPage(ProfitQuery query) {
		List<ProfitCollect > list = mapper.findProfitCollectByPage(query);
        DodopalDataPage<ProfitCollect> pages = new DodopalDataPage<ProfitCollect>(query.getPage(), list);
        return pages;
	}
	@Override
	public List<ProfitCollect> findProfitCollect(ProfitQuery query) {
		List<ProfitCollect> list = mapper.findProfitCollect(query);
		return list;
	}
}
