package com.gunnarro.dietmanager.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class HttpSessionListener implements ApplicationListener<ApplicationEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(HttpSessionListener.class);

    // as this is a bean, the listener code executes within the Spring
    // Security Framework
    private int counter = 0;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        LOG.info("start session...");
        // Care only about Http session events
        if (applicationEvent instanceof HttpSessionCreatedEvent) {
            counter++;
            HttpSessionCreatedEvent httpSessionCreatedEvent = (HttpSessionCreatedEvent) applicationEvent;
            Date timestamp = new Date(httpSessionCreatedEvent.getTimestamp());
            if (LOG.isInfoEnabled()) {
                LOG.info("Session created (isNew=" + httpSessionCreatedEvent.getSession().isNew() + "), current number of active sessions: " + counter);
                LOG.info("sessionId: " + httpSessionCreatedEvent.getSession().getId() + ", created: "
                        + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(timestamp));
                LOG.info("Session Attributes:" + getHttpSessionAttributes(httpSessionCreatedEvent.getSession()));
            }
        } else if (applicationEvent instanceof HttpSessionDestroyedEvent) {
            counter--;
            HttpSessionDestroyedEvent httpSessionDestroyedEvent = (HttpSessionDestroyedEvent) applicationEvent;
            Date timestamp = new Date(httpSessionDestroyedEvent.getTimestamp());
            if (LOG.isInfoEnabled()) {
                LOG.info("Session destroyed, current number of active sessions: " + counter);
                LOG.info("sessionId: " + httpSessionDestroyedEvent.getSession().getId() + ", created: "
                        + new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(timestamp));
                LOG.info("Session Attributes:" + getHttpSessionAttributes(httpSessionDestroyedEvent.getSession()));
            }
        }
    }

    private String getHttpSessionAttributes(HttpSession httpSession) {
        StringBuilder attributes = new StringBuilder();
        // FIXME cause java.lang.OutOfMemoryError: Java heap space
        // while (httpSession.getAttributeNames().hasMoreElements()) {
        // String attrName = httpSession.getAttributeNames().nextElement();
        // attributes.append(attrName).append("=").append(httpSession.getAttribute(attrName)).append(",
        // ");
        // }
        return attributes.toString();
    }
}
