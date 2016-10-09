package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.dao.LogDLogMapper;
import com.dodopal.oss.business.model.LogDLog;
import com.dodopal.oss.business.model.dto.LogDLogQuery;
import com.dodopal.oss.business.service.LogDLogService;
/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2015年12月23日 下午8:45:08 
  * @version 1.0 
  * @parameter  
  * ocx日志  
  */
@Service
public class LogDLogServiceImpl implements LogDLogService{
	@Autowired
	LogDLogMapper logDLogMapper;
	
	@Override
	public DodopalDataPage<LogDLog> findPayConfigByPage(LogDLogQuery dLogQuery) {
		List<LogDLog> result = logDLogMapper.findLogDLogByPage(dLogQuery);
	    DodopalDataPage<LogDLog> pages = new DodopalDataPage<LogDLog>(dLogQuery.getPage(), result);
	    return pages;
	}

}
