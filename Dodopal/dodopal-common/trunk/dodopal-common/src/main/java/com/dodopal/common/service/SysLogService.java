package com.dodopal.common.service;

import java.util.List;

import com.dodopal.common.model.SysLog;


public interface SysLogService {

	public void persistSysLog(SysLog log);
	
	public void persistBatchSysLog(List<SysLog> logs);
	
}
