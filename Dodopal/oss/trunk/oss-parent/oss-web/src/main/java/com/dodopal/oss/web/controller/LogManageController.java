package com.dodopal.oss.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dodopal.common.constant.ResponseCode;
import com.dodopal.common.model.DodopalDataPage;
import com.dodopal.common.model.DodopalResponse;
import com.dodopal.oss.business.model.CrdSysLog;
import com.dodopal.oss.business.model.LogDLog;
import com.dodopal.oss.business.model.LogInfo;
import com.dodopal.oss.business.model.dto.CrdSysLogQuery;
import com.dodopal.oss.business.model.dto.LogDLogQuery;
import com.dodopal.oss.business.model.dto.LogInfoQuery;
import com.dodopal.oss.business.service.CrdSysLogService;
import com.dodopal.oss.business.service.LogDLogService;
import com.dodopal.oss.business.service.LogInfoService;

@Controller
@RequestMapping("/logManage")
public class LogManageController {

	private Logger logger = LoggerFactory.getLogger(LogManageController.class);
	@Autowired
    private CrdSysLogService crdSysLogService;
	@Autowired
	private LogDLogService logDLogService;
	@Autowired
	private LogInfoService logInfoService;

	@RequestMapping("logDLog/toLogDLog")
	public ModelAndView toLogDLog(HttpServletRequest request) {
		return new ModelAndView("basic/logMgmt/logDLog");
	}
	
	@RequestMapping("logDLog/findLogDLogByPage")
	public @ResponseBody DodopalResponse<DodopalDataPage<LogDLog>> findLogDLogByPage(HttpServletRequest request,@RequestBody LogDLogQuery logDLogQuery) {
		DodopalResponse<DodopalDataPage<LogDLog>> dodopalResponse = new DodopalResponse<DodopalDataPage<LogDLog>>();
		try{
			dodopalResponse.setResponseEntity(logDLogService.findPayConfigByPage(logDLogQuery));
			dodopalResponse.setCode(ResponseCode.SUCCESS);
		}catch(Exception e){
			logger.error("LogManageController findLogDLogByPage call an error", e);
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}
	
	@RequestMapping("crdSysLog/toCrdSysLog")
	public ModelAndView toCrdSysLog(HttpServletRequest request) {
		return new ModelAndView("basic/logMgmt/crdSysLog");
	}
	@RequestMapping("crdSysLog/findCrdSysLogByPage")
	public @ResponseBody DodopalResponse<DodopalDataPage<CrdSysLog>> findCrdSysLogByPage(HttpServletRequest request,@RequestBody CrdSysLogQuery crdSysLogQuery) {
		DodopalResponse<DodopalDataPage<CrdSysLog>> dodopalResponse = new DodopalResponse<DodopalDataPage<CrdSysLog>>();
		try{
			dodopalResponse.setResponseEntity(crdSysLogService.findPayConfigByPage(crdSysLogQuery));
			dodopalResponse.setCode(ResponseCode.SUCCESS);
		}catch(Exception e){
			logger.error("LogManageController findCrdSysLogByPage call an error", e);
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}
	
	@RequestMapping("collectLog/toCollectLog")
	public ModelAndView toCollectLog(HttpServletRequest request) {
		return new ModelAndView("basic/logMgmt/collectLog");
	}
	
	@RequestMapping("collectLog/findLogInfoByPage")
	public @ResponseBody DodopalResponse<DodopalDataPage<LogInfo>> findLogInfoByPage(HttpServletRequest request,@RequestBody LogInfoQuery crdSysLogQuery) {
		DodopalResponse<DodopalDataPage<LogInfo>> dodopalResponse = new DodopalResponse<DodopalDataPage<LogInfo>>();
		try{
			dodopalResponse.setResponseEntity(logInfoService.findLogInfoByPage(crdSysLogQuery));
			dodopalResponse.setCode(ResponseCode.SUCCESS);
		}catch(Exception e){
			logger.error("LogManageController findCrdSysLogByPage call an error", e);
			dodopalResponse.setCode(ResponseCode.SYSTEM_ERROR);
		}
		return dodopalResponse;
	}
}
