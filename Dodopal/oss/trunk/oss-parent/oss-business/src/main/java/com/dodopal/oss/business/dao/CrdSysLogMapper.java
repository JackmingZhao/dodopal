package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.CrdSysLog;
import com.dodopal.oss.business.model.dto.CrdSysLogQuery;

public interface CrdSysLogMapper {
	public List<CrdSysLog> findCrdSysLogByPage(CrdSysLogQuery crdsyslogQuery);

}
