package com.gunnarro.sportsteam.mvc.controller;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.gunnarro.sportsteam.domain.message.Sms;
import com.gunnarro.sportsteam.service.MessageService;


@Controller
@Scope("session")
public class SmsController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);

    @Autowired
    @Qualifier("messageService")
    protected MessageService messageService;
    /**
	 */
	@RequestMapping(value = "/sms/sent", method = RequestMethod.GET)
	public String sentEmail() {
		return "message/sent-msg";
	}
	
   /**
    * GET for new sms
    */
    @RequestMapping(value = "/sms/new", method = RequestMethod.GET)
    public String initSendSMSForm(Map<String, Object> model) {
        model.put("sms", new Sms());
        return "message/edit-sms";
    }

    /**
     * Sms POST for new
     */
    @RequestMapping(value = "/sms/new", method = RequestMethod.POST)
    public String processSendSMSForm(Sms sms, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(sms.toString());
        }
        if (result.hasErrors()) {
            return "message/edit-sms";
        } else {
            this.messageService.sendSMS(sms);
            status.setComplete();
            LOG.info("Sent sms:" + sms.getTo());
            return "redirect:/sms/sent";
        }
    }

//    @RequestMapping(value = "/sms/calendarevent/edit/{eventId}", method = RequestMethod.GET)
//    public String initUpdateSendSMSForm(@PathVariable("eventId") int eventId, Model model) {
//    	Sms sms = new Sms();
//    	CalendarEvent calendarEvent = sportsTeamService.findCalendarEventById(null,eventId);
//    	if (calendarEvent != null) {
//    		List<String> phoneNumbers = sportsTeamService.getFollowersPhoneNumbers(eventId);
//    		String smsMsg = "Reminder: \n" + calendarEvent.getSummary() + "\n" + calendarEvent.getLocation() + " \nHilsen SporTsTeam.";
//    		sms = new Sms(phoneNumbers.toArray(new String[phoneNumbers.size()]), "sportsteam", smsMsg);
//    		sms.setTo(phoneNumbers.toString());
//    	} else {
//    		model.addAttribute("infoMsg", "No calendar event with id: " + eventId);
//    	}
//    	model.addAttribute("sms", sms);
//        return "message/edit-sms";
//    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/sms/calendarevent/edit/{eventId}", method = RequestMethod.PUT)
    public String processUpdateSendSMSForm(Sms sms, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "message/edit-sms";
        } else {
        	messageService.sendSMS(sms);
            status.setComplete();
            LOG.info("Sent sms:" + sms.getTo());
            return "redirect:/sms/sent";
        }
    }

}
