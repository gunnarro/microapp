package com.gunnarro.sportsteam.mvc.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.gunnarro.sportsteam.domain.message.Email;
import com.gunnarro.sportsteam.domain.party.Person;
import com.gunnarro.sportsteam.service.MessageService;

@Controller
@Scope("session")
public class EmailController extends DefaultController {

	private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

	 @Autowired
	    @Qualifier("messageService")
	    protected MessageService messageService;
	 
	/**
	 */
	@RequestMapping(value = "/email/sent", method = RequestMethod.GET)
	public String sentEmail() {
		return "message/sent-msg";
	}
	
	/**
	 * GET for new Email
	 */
	@RequestMapping(value = "/email/new", method = RequestMethod.GET)
	public String initSendEmailForm(Map<String, Object> model) {
		model.put("email", new Email());
		return "message/edit-email";
	}

	/**
	 * Email POST for new
	 */
	@RequestMapping(value = "/email/new", method = RequestMethod.POST)
	public String processSendEmailForm(Email email, BindingResult result,
			SessionStatus status) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(email.toString());
		}
		if (result.hasErrors()) {
			return "message/edit-email";
		} else {
			this.messageService.sendEmail(email.getEmailAddresses(), email.getSubject(), email.getMsg());
			status.setComplete();
			LOG.info("Sent email:" + email.getTo());
			return "redirect:/email/sent";
		}
	}

//	@RequestMapping(value = "/email/calendarevent/edit/{eventId}", method = RequestMethod.GET)
//	public String initUpdateEmailForm(@PathVariable("eventId") int eventId, Model model) {
//		Email email = new Email();
//		CalendarEvent calendarEvent = sportsTeamService.findCalendarEventById(null, eventId);
//		if (calendarEvent != null) {
//			List<Person> followers = sportsTeamService.getFollowers(eventId);
//			String msg = "Reminder: \n" + calendarEvent.getSummary() + "\n" + calendarEvent.getLocation() + " \nHilsen SporTsTeam.";
//			email = new Email(new String[]{followers.get(0).getEmailAddress()},"sportsteam", msg);
//			email.setSubject("Reminder: " + calendarEvent.getSummary());
//			email.setTo(followers.get(0).getEmailAddress());
//		} else {
//			model.addAttribute("infoMsg", "No calendar event with id: "+ eventId);
//		}
//		model.addAttribute("email", email);
//		return "message/edit-email";
//	}

	/**
	 * Use PUT for updates
	 * 
	 */
	@RequestMapping(value = "/email/calendarevent/edit/{eventId}", method = RequestMethod.PUT)
	public String processUpdateSendEmailForm(Email email, BindingResult result,
			SessionStatus status) {
		if (result.hasErrors()) {
			return "message/edit-email";
		} else {
			boolean isOk = messageService.sendEmail(new String[]{email.getTo()}, email.getSubject(), email.getMsg());
			status.setComplete();
			LOG.info("Sent email:" + email.getTo() + ", status: " + isOk);
			return "redirect:/email/sent";
		}
	}

}
