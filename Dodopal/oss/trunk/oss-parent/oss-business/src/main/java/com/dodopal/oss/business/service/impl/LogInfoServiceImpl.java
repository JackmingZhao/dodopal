package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.dao.LogInfoMapper;
import com.dodopal.oss.business.model.CrdSysLog;
import com.dodopal.oss.business.model.LogInfo;
import com.dodopal.oss.business.model.dto.LogInfoQuery;
import com.dodopal.oss.business.service.LogInfoService;
/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2015年12月23日 下午8:45:02 
  * @version 1.0 
  * @parameter 
  * 集中日志   
  */
@Service
public class LogInfoServiceImpl implements LogInfoService {
	@Autowired
	LogInfoMapper logInfoMapper;
	
	@Override
	public DodopalDataPage<LogInfo> findLogInfoByPage(LogInfoQuery dLogQuery) {
		List<LogInfo> result = logInfoMapper.findLogInfoByPage(dLogQuery);
	    DodopalDataPage<LogInfo> pages = new DodopalDataPage<LogInfo>(dLogQuery.getPage(), result);
		return pages;
	}

}
