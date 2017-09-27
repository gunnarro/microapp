package com.gunnarro.calendar.endpoint.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gunnarro.calendar.service.CalendarService;
import com.gunnarro.calendar.endpoint.rest.RestApplicationException;
import com.gunnarro.calendar.endpoint.rest.RestError;

@RestController
@RequestMapping("/rest/calendarmanager")
public class CalendarRestEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(CalendarRestEndpoint.class);

    @Autowired
    @Qualifier("calendarService")
    private CalendarService calendarService;

    /**
     * default constructor, used by spring
     */
    public CalendarRestEndpoint() {
    }

    /**
     * For unit testing only
     * 
     * @param calendarService - inject as mock
     */
    public CalendarRestEndpoint(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    /**
     * 
     * @param eventId
     * @return
     */
    @RequestMapping(value = "/calendar/event/delete/{eventId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer calendarEventDelete(@PathVariable("eventId") Integer eventId) {
        try {
            return calendarService.deleteCalendarEvent(eventId);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }
    
//    @RequestMapping(value = "/calendar/sendsms/{eventId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
//    @ResponseBody
//    public SendSMSResponse sendSMS(@PathVariable("eventId") Integer eventId) {
//        try {
//            CalendarEvent calendarEvent = calendarService.getCalendarEvent(eventId);
//            if (calendarEvent != null) {
//                List<String> phoneNumbers = Arrays.asList("12345678");//calendarService.getFollowersPhoneNumbers(eventId);
//                String smsMsg = calendarEvent.getSummary() + "\nHilsen SporTsTeam";
//                Sms sms = new Sms(phoneNumbers.toArray(new String[phoneNumbers.size()]), "sportsteam", smsMsg);
//                boolean isOk = messageService.sendSMS(sms);
//                return new SendSMSResponse(Boolean.toString(isOk));
//            } else {
//                return new SendSMSResponse("ERROR: Event not found, id: " + eventId);
//            }
//        } catch (AccessDeniedException e) {
//            LOG.error("", e);
//            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
//        }
//    }
}
