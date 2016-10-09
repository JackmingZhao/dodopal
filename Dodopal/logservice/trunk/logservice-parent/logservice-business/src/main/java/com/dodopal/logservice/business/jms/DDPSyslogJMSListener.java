package com.dodopal.logservice.business.jms;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.dodopal.common.model.SysLog;
import com.dodopal.common.service.SysLogService;

public class DDPSyslogJMSListener implements MessageListener {

	@Autowired
	private SysLogService logservice;

	private int batchSize = 1000;

	private List<SysLog> batchLogs = new ArrayList<SysLog>(0);

	private JmsTemplate jmsTemplate;
	
	private int maxStayTime = 1 * 60 * 1000;

	private Destination errorDestination;

	@Override
	public void onMessage(final Message message) {

		ObjectMessage objMessage = (ObjectMessage) message;
		try {
			SysLog log = (SysLog) objMessage.getObject();
			batchLogs.add(log);
			if (batchLogs.size() == batchSize) {
				logservice.persistBatchSysLog(batchLogs);
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
					logservice.persistBatchSysLog(batchLogs);
					batchLogs.clear();	
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			jmsTemplate.send(errorDestination, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return message;
				}
			});
		}

	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Destination getErrorDestination() {
		return errorDestination;
	}

	public void setErrorDestination(Destination errorDestination) {
		this.errorDestination = errorDestination;
	}

}
