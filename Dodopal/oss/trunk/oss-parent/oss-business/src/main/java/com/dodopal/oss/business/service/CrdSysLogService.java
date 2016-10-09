package com.dodopal.oss.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.CrdSysLog;
import com.dodopal.oss.business.model.dto.CrdSysLogQuery;

public interface CrdSysLogService {
	public DodopalDataPage<CrdSysLog> findPayConfigByPage(CrdSysLogQuery crdsyslogquery);
}
