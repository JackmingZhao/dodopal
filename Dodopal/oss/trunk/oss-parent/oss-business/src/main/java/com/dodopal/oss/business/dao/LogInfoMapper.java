package com.dodopal.oss.business.dao;

import java.util.List;

import com.dodopal.oss.business.model.LogInfo;
import com.dodopal.oss.business.model.dto.LogInfoQuery;

public interface LogInfoMapper {
	public List<LogInfo> findLogInfoByPage(LogInfoQuery loginfo);
}
