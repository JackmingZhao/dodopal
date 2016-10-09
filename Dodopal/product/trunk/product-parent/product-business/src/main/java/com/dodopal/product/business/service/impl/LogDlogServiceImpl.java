package com.dodopal.product.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.product.business.dao.LogDlogMapper;
import com.dodopal.product.business.model.LogDlog;
import com.dodopal.product.business.service.LogDlogService;

/**
 * @author lifeng@dodopal.com
 */
@Service
public class LogDlogServiceImpl implements LogDlogService {
	@Autowired
	private LogDlogMapper mapper;

	@Override
	@Transactional
	public int addLog(LogDlog log) {
		return mapper.addLog(log);
	}

}
