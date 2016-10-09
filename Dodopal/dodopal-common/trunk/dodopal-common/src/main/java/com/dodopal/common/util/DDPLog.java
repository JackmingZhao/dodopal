package com.dodopal.common.util;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.dodopal.common.model.SysLog;


public class DDPLog<T> implements Serializable {
	private static final long serialVersionUID = 567309439385718596L;
	
	private Logger log;
	
    public DDPLog(Class<T> clazz) {    	
		
		try{			 
			log= LoggerFactory.getLogger(clazz.toString());
	    //日志失败,不影响交易
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
	}


	public boolean isTraceEnabled(){
		return log.isTraceEnabled();
	}

	public boolean isDebugEnabled(){
		return log.isDebugEnabled();
	}
	
	public boolean isInfoEnabled(){
		return log.isInfoEnabled();
	}
	
	public boolean isWarnEnabled(){
		return log.isWarnEnabled();
	}
	
	public boolean isErrorEnabled(){
		return log.isErrorEnabled();
	}
    
	 /**
     * 日志级别:info
     * @param logObj
     */
    public void info(SysLog logObj){
    	//String flg = DodopalAppVarPropsUtil.getStringProp("");
    	try{    		
	    	if(isInfoEnabled()){
	    		log.info(JSONObject.toJSONString(logObj));
	    	}
	    //日志失败,不影响交易
    	}catch(Exception ex){
    		ex.printStackTrace();
    		log.error(ex.getMessage());
    	}
    }
    /**
     * 日志级别:debug
     * @param logObj
     */
    public void debug(SysLog logObj){
    	//String flg = DodopalAppVarPropsUtil.getStringProp("");
    	try{
    		if(isDebugEnabled()){
        		log.debug(JSONObject.toJSONString(logObj));   
        	}
	    //日志失败,不影响交易
    	}catch(Exception ex){
    		ex.printStackTrace();
    		log.error(ex.getMessage());
    	}
    	
    }
    /**
     * 日志级别:warn
     * @param logObj
     */
    public void warn(SysLog logObj){
    	//String flg = DodopalAppVarPropsUtil.getStringProp("");    	
    	try{
    		if(isWarnEnabled()){
        		log.warn(JSONObject.toJSONString(logObj));
        	}
	    //日志失败,不影响交易
    	}catch(Exception ex){
    		ex.printStackTrace();
    		log.error(ex.getMessage());
    	}
    }
    
    /**
     * 日志级别:error
     * @param logObj
     */
    public void error(SysLog logObj){
    	//String flg = DodopalAppVarPropsUtil.getStringProp("");    	
    	try{
    		if(isErrorEnabled()){
        		log.error(JSONObject.toJSONString(logObj));
        	}
	    //日志失败,不影响交易
    	}catch(Exception ex){
    		ex.printStackTrace();
    		log.error(ex.getMessage());
    	}
    }
    
    
}
