package com.dodopal.common.log;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.dodopal.common.model.SysLog;

public class ActivemqLogPublisher {

	public static void publishLog2Queue(SysLog log,String queueName, String queueUrl){
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(queueUrl);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			((ActiveMQConnection)connection).setUseAsyncSend(true);
			
			Destination destination = session.createQueue(queueName);
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject(log);
			producer.send(objectMessage);
			session.commit();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
