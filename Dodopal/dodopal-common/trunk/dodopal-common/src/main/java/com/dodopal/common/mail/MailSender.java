package com.dodopal.common.mail;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dodopal.common.model.DodopalMailMessage;

public class MailSender {

	private final static Logger logger = LoggerFactory.getLogger(MailSender.class);

	public static void sendEmailMessage(DodopalMailMessage email, String mailSessionJNDIName) {

		if (logger.isInfoEnabled()) {
			logger.info("MailSender sendEmailMessage :: email : " + ToStringBuilder.reflectionToString(email) + "mailSessionJNDIName: " + mailSessionJNDIName);
		}

		Session session = null;
		try {
			Context initCtx = new InitialContext();
			session = (Session) initCtx.lookup("java:comp/env/" + mailSessionJNDIName);

			MimeMessage message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(email.getFrom()));
				List<InternetAddress> toAddress = new ArrayList<InternetAddress>();
				for (String t : email.getTo()) {
					InternetAddress a = new InternetAddress(t);
					toAddress.add(a);
				}

				message.setRecipients(Message.RecipientType.TO, toAddress.toArray(new InternetAddress[0]));
				message.setSubject(email.getSubject());
				
				if(email.getAttachment() != null && StringUtils.isNotBlank(email.getAttachmentFilename())){
					MimeBodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setText(email.getContent());
					Multipart multipart = new MimeMultipart();
					multipart.addBodyPart(messageBodyPart);
					
					messageBodyPart = new MimeBodyPart();
					DataSource source = new ByteArrayDataSource(email.getAttachment(), null);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(email.getAttachmentFilename());
					multipart.addBodyPart(messageBodyPart);
					message.setContent(multipart);
				}else{
					message.setContent(email.getContent(), "text/html;charset=UTF-8");
				}

				Transport.send(message);
			}	catch (AddressException ex) {
				ex.printStackTrace();
				logger.error("邮件发送中出现错误！",ex);
			}catch (MessagingException ex) {
				ex.printStackTrace();
				logger.error("邮件发送中出现错误！",ex);
			}

		}catch (Exception ex) {
			logger.error("发送邮件出错！", ex);
		}

	}
}
