package com.gunnarro.sportsteam.service;

import org.springframework.security.access.annotation.Secured;

import com.gunnarro.sportsteam.domain.message.Sms;

public interface MessageService {

	@Secured({ "ROLE_ADMIN" })
	public boolean sendSMS(Sms sms);
	
	@Secured({ "ROLE_ADMIN" })
	public boolean sendEmail(String[] to, String subject, String msg);

}
