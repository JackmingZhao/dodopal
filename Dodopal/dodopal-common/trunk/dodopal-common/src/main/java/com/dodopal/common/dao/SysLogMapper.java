package com.dodopal.common.dao;

import java.util.List;

import com.dodopal.common.model.SysLog;

public interface SysLogMapper {

	public void insertSysLog(SysLog syslog);
	
	public void insertBatchSysLog(List<SysLog> syslogs);

	public List<String> selectMultipleKeys(int numberOfKeysRequired);
	
}
