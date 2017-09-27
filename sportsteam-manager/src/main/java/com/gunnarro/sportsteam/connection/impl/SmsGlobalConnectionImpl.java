package com.gunnarro.sportsteam.connection.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gunnarro.sportsteam.connection.SmsGlobalConnection;
import com.gunnarro.sportsteam.domain.message.Sms;

@Controller
@Scope("session")
public class SmsGlobalConnectionImpl implements SmsGlobalConnection {

    private static final Logger LOG = LoggerFactory.getLogger(SmsGlobalConnectionImpl.class);

    @Override
    public boolean sendSMS(Sms sms) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(sms.toString());
        }
        return false;
    }

    @Override
    public void logOut() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getTickect() {
        // TODO Auto-generated method stub
        return null;
    }

}
