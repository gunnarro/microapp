package com.gunnarro.calendar.endpoint.rest;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.mvc.controller.SpringTestSetup;
import com.gunnarro.calendar.service.CalendarService;

public class CalendarRestEndpointTest extends SpringTestSetup {

    private CalendarRestEndpoint restEndpoint;

    @Mock
    private CalendarService calendarServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        restEndpoint = new CalendarRestEndpoint(calendarServiceMock);
    }

//    @Test
//    public void sendSMS() {
//    	Sms sms = new Sms(new String[]{"45465500","45465501"}, "SporTsTeaM", "Only testing from unit test");
//    	CalendarEvent event = new CalendarEvent();
//    	event.setSummary("lyn - skeid");
//    	event.setDescription("match");
//    	when(calendarServiceMock.getCalendarEvent(23)).thenReturn(event);
//        when(calendarServiceMock.sendSMS(sms)).thenReturn(true);
//        SendSMSResponse sendSMS = restEndpoint.sendSMS(23);
//        Assert.assertEquals("false",sendSMS.getResult());
//    }

}

