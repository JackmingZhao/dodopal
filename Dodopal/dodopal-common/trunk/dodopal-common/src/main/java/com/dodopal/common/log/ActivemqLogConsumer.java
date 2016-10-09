package com.dodopal.common.log;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.context.ApplicationContext;

import com.dodopal.common.model.SysLog;
import com.dodopal.common.service.SysLogService;

public class ActivemqLogConsumer {

	SysLogService service;

	private List<SysLog> batchLogs;

	private int batchSize = 1000;
	
	private int maxStayTime = 5 * 60 * 1000;
	
	private ApplicationContext context;
	
	public ActivemqLogConsumer(ApplicationContext context){
		this.context = context;
	}

	public void consume(SysLog log) {
		System.out.println(ToStringBuilder.reflectionToString(log));

		log.setLogDate(new Date());
		batchLogs.add(log);
		
		if (batchLogs.size() == batchSize) {
			service.persistBatchSysLog(batchLogs);
			batchLogs.clear();
		}else{
			boolean hasOvertimeLog = false;
			for(SysLog l : batchLogs){
				if(l.getLogDate() != null && System.currentTimeMillis() - l.getLogDate().getTime() > maxStayTime){
					hasOvertimeLog = true;
					break;
				}
			}
			if(hasOvertimeLog){
				service.persistBatchSysLog(batchLogs);
				batchLogs.clear();	
			}
		}
	}
	
	public SysLogService getService() {
		if(this.service == null){
			return (SysLogService)context.getBean("sysLogService");
		}
		
		return service;
	}

	public void setService(SysLogService service) {
		this.service = service;
	}

}
