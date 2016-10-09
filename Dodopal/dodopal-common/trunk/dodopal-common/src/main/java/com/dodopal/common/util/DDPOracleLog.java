package com.dodopal.common.util;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.common.model.SysLog;

public class DDPOracleLog<T> implements Serializable {
	private static final long serialVersionUID = 567309439385718596L;

	private Logger log;

	@SuppressWarnings("rawtypes")
 private Class clazz;
	
	private Logger logger = LoggerFactory.getLogger(DDPOracleLog.class);
	
	public DDPOracleLog(Class<T> clazz) {

		try {
			this.clazz = clazz;
			log = LoggerFactory.getLogger("ddpOracleLogger");
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean isTraceEnabled() {
		return log.isTraceEnabled();
	}

	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	public boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}

	public boolean isErrorEnabled() {
		return log.isErrorEnabled();
	}

	/**
	 * 日志级别:info
	 * @param logObj
	 */
	public void info(SysLog logObj) {
		try {
			if (isInfoEnabled()) {
				populateMDC(logObj);
				log.info(JSONObject.toJSONString(logObj));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 日志级别:debug
	 * @param logObj
	 */
	public void debug(SysLog logObj) {
		try {
			if (isDebugEnabled()) {
				populateMDC(logObj);
				log.debug(JSONObject.toJSONString(logObj));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}

	}

	/**
	 * 日志级别:warn
	 * @param logObj
	 */
	public void warn(SysLog logObj) {
		try {
			if (isWarnEnabled()) {
				populateMDC(logObj);
				log.warn(JSONObject.toJSONString(logObj));
			}
		}	catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 日志级别:error
	 * @param logObj
	 */
	public void error(SysLog logObj) {
		try {
			if (isErrorEnabled()) {
				populateMDC(logObj);
				log.error(JSONObject.toJSONString(logObj));
			}
		}	catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
	}

	public void error(SysLog logObj,Throwable e) {
		try {
			if (isErrorEnabled()) {
				populateMDC(logObj);
				String stackTrace = ExceptionUtils.getFullStackTrace(e);
				stackTrace = StringUtils.substring(stackTrace, 0, 4000);
				MDC.put("StackTrace", stackTrace);
				log.error(JSONObject.toJSONString(logObj),e);
			}
		}	catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
	}
	
	private void populateMDC(SysLog log){
		MDC.put("Class", this.clazz.getName());
		
		if(StringUtils.isNotBlank(log.getServerName())){
			MDC.put("ServerName", log.getServerName());
		}
		
		if(StringUtils.isNotBlank(log.getOrderNum())){
			MDC.put("OrderNum", log.getOrderNum());
		}
		
		if(StringUtils.isNotBlank(log.getTranNum())){
			MDC.put("TranNum", log.getTranNum());
		}
		
		if(StringUtils.isNotBlank(log.getMethodName())){
			MDC.put("MethodName", log.getMethodName());
		}
		
		if(StringUtils.isNotBlank(log.getDescription())){
			MDC.put("MethodDesc", log.getDescription());
		}

		if(StringUtils.isNotBlank(log.getInParas())){
			MDC.put("InParas", log.getInParas());
		}
		
		if(StringUtils.isNotBlank(log.getOutParas())){
			MDC.put("OutParas", log.getOutParas());
		}
		
		if(StringUtils.isNotBlank(log.getRespExplain())){
			MDC.put("RespMessage", log.getRespExplain());
		}
		
		if(StringUtils.isNotBlank(log.getRespCode())){
			MDC.put("RespCode", log.getRespCode());
		}
		
		if(StringUtils.isNotBlank(log.getIpAddress())){
			MDC.put("IpAddress", log.getIpAddress());
		}
	}
}
