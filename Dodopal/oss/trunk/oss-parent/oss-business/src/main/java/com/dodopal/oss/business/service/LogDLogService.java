package com.dodopal.oss.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.LogDLog;
import com.dodopal.oss.business.model.dto.LogDLogQuery;

public interface LogDLogService {
	 public  DodopalDataPage<LogDLog> findPayConfigByPage (LogDLogQuery dLogQuery);
}
