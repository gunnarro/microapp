package com.gunnarro.sportsteam.connection;

import org.springframework.stereotype.Component;

import com.gunnarro.sportsteam.domain.message.Sms;

/**
 * Interface against external SMS service
 * @author admin
 *
 */
@Component
public interface SmsGlobalConnection {

    public void logOut();
    
    public String getTickect();
    
    public boolean sendSMS(Sms sms);

}
