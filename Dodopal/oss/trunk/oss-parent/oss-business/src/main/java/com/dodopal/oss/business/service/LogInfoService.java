package com.dodopal.oss.business.service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.model.LogInfo;
import com.dodopal.oss.business.model.dto.LogInfoQuery;

public interface LogInfoService {
	public  DodopalDataPage<LogInfo> findLogInfoByPage (LogInfoQuery dLogQuery);

}
