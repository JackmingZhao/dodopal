package com.dodopal.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dodopal.common.dao.SysLogMapper;
import com.dodopal.common.model.SysLog;
import com.dodopal.common.service.SysLogService;

@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogMapper syslogMapper;

	@Override
	public void persistSysLog(SysLog log) {
		syslogMapper.insertSysLog(log);
	}

	@Transactional
	@Override
	public void persistBatchSysLog(List<SysLog> logs) {
		List<String> keys = syslogMapper.selectMultipleKeys(logs.size());
		for (int i = 0; i < logs.size(); i++) {
			logs.get(i).setId(keys.get(i));
		}
		syslogMapper.insertBatchSysLog(logs);
	}

}
