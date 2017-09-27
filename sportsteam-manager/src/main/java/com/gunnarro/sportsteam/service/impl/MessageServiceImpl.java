package com.gunnarro.sportsteam.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.gunnarro.sportsteam.connection.SmsGlobalConnection;
import com.gunnarro.sportsteam.domain.message.Sms;
import com.gunnarro.sportsteam.service.MessageService;

public class MessageServiceImpl implements MessageService {

	private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Autowired
	@Qualifier("smsGlobalConnection")
	private SmsGlobalConnection smsConnector;

	@Autowired
	// @Qualifier("gmailMailSender")
	@Qualifier("yahooMailSender")
	private JavaMailSenderImpl mailSender;
	 
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean sendSMS(Sms sms) {
		return smsConnector.sendSMS(sms);
	}
	
	/**
     * {@inheritDoc}
     */
    @Override
    public boolean sendEmail(String[] to, String subject, String msg) {
        try {
            SimpleMailMessage emailMsg = new SimpleMailMessage();
            emailMsg.setTo(to);
            emailMsg.setSubject(subject);
            emailMsg.setText(msg);
            emailMsg.setFrom(mailSender.getUsername());
            if (LOG.isInfoEnabled()) {
                LOG.info(to[0]);
                LOG.info(subject);
                LOG.info(msg);
            }
            mailSender.send(emailMsg);
            if (LOG.isInfoEnabled()) {
                LOG.info("Sent email to:" + emailMsg.getTo()[0] + ", from: " + emailMsg.getFrom());
            }
            return true;
        } catch (Exception e) {
            LOG.error("Failed sending email from account: " + mailSender.getUsername());
            LOG.error(null, e);
            return false;
        }
    }
}
