package com.dodopal.oss.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.oss.business.dao.CrdSysLogMapper;
import com.dodopal.oss.business.model.CrdSysLog;
import com.dodopal.oss.business.model.dto.CrdSysLogQuery;
import com.dodopal.oss.business.service.CrdSysLogService;
/** 
  * @author  Dingkuiyuan@dodopal.com 
  * @date 创建时间：2015年12月23日 下午8:45:17 
  * @version 1.0 
  * @parameter    
  * 卡服务日志
  */
@Service
public class CrdSysLogServiceImpl implements CrdSysLogService {
	@Autowired
	CrdSysLogMapper crdSysLogMapper;
	
	@Override
	public DodopalDataPage<CrdSysLog> findPayConfigByPage(
			CrdSysLogQuery crdsyslogquery) {
		List<CrdSysLog> result = crdSysLogMapper.findCrdSysLogByPage(crdsyslogquery);
	    DodopalDataPage<CrdSysLog> pages = new DodopalDataPage<CrdSysLog>(crdsyslogquery.getPage(), result);
	    return pages;
	}
}
