package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.LogDLog;
import com.dodopal.oss.business.model.dto.LogDLogQuery;

public interface LogDLogMapper {
	public List<LogDLog> findLogDLogByPage(LogDLogQuery logdlogQuery);
}
