package com.dodopal.common.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dodopal.common.model.SysLog;


public class SysLogServiceTest {
	
	//@Autowired
	SysLogService service;

	//@Test
	public void testPersistLog(){
		
		SysLog log = new SysLog();
		log.setInParas("merCode");
		log.setOutParas("");
		log.setMethodName("findMerOperatorByUserCode");
		log.setRespCode("000000");
		log.setRespExplain("Rep Message");
		log.setServerName("portal");
		log.setTranNum("TranNum");
		log.setOrderNum("OrderNum");
		
		service.persistSysLog(log);
		
	}
	
	//@Test
	public void testPersistBatchLog(){
		List<SysLog> logs = new ArrayList<SysLog>();
		for(int i = 0; i < 1000; i++){
			SysLog log = new SysLog();
			log.setInParas("merCode" + i);
			log.setOutParas("");
			log.setMethodName("findMerOperatorByUserCode");
			log.setRespCode("000000");
			log.setRespExplain("Rep Message");
			log.setServerName("portal");
			log.setTranNum("TranNum");
			log.setOrderNum("OrderNum");
			logs.add(log);
		}
		service.persistBatchSysLog(logs);
	}
	
}
