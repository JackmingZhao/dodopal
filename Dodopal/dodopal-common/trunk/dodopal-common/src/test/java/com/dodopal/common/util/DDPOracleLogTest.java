package com.dodopal.common.util;


import com.dodopal.common.model.SysLog;


public class DDPOracleLogTest{

	@SuppressWarnings({"rawtypes", "unchecked"})
	static DDPOracleLog logger = new DDPOracleLog(DDPOracleLogTest.class);
	
	public static void testLogInfo(){
		
		SysLog log = new SysLog();
		log.setClassName(DDPOracleLogTest.class.getName());
		log.setInParas("InParas");
		log.setOutParas("OutParas");
		log.setMethodName("testLogInfo");
		log.setRespCode("000000");
		log.setRespExplain("Rep Message");
		log.setServerName("Server Name");
		log.setTranNum("TranNum");
		log.setOrderNum("OrderNum");

		logger.info(log);
		
	}
	
public static void testLogError(){
		
		SysLog log = new SysLog();
		log.setClassName(DDPOracleLogTest.class.getName());
		log.setInParas("InParas");
		log.setOutParas("OutParas");
		log.setMethodName("testLogInfo");
		log.setRespCode("000000");
		log.setRespExplain("Rep Message");
		log.setServerName("Server Name");
		log.setTranNum("TranNum");
		log.setOrderNum("OrderNum");
		log.setIpAddress("127.0.0.1");
		log.setDescription("Method Desc");
		try{
			throw new RuntimeException("Error is occured!");
		}catch(Throwable e){
			logger.error(log,e);
		}
		
	}

	public static void main(String[] args) {
		testLogInfo();
		testLogError();
		
		while(true){
			//等待log4j 插入数据，主线程退出，log4j也会停止插入数据库的线程
		}
    }
}
