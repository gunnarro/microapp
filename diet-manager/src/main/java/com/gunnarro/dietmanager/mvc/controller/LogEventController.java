package com.gunnarro.dietmanager.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gunnarro.dietmanager.domain.log.ImageResource;
import com.gunnarro.dietmanager.domain.log.LogComment;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.dietmanager.utility.Utility;
import com.gunnarro.useraccount.domain.user.LocalUser;

/**
 * 
 * http://microbuilder.io/blog/2016/01/10/plotting-json-data-with-chart-js.html
 * https://github.com/chartjs/Chart.js/tree/master/dist
 * 
 * @author admin
 *
 */
@Controller
@Scope("session")
public class LogEventController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(LogEventController.class);

	private static final String REDIRECT = "redirect";
	private static final String URI_LOG_EVENTS = "/diet/log/events";
	private static final String URI_LOG_EVENT_VIEW = "/diet/log/event/view";

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat(Utility.DATE_TIME_PATTERN);
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
	}

	@GetMapping(URI_LOG_EVENTS)
	@ResponseBody
	public ModelAndView getLogEvents(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer pageSize) {
		LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
		Page<LogEntry> logsPage = logEventService.getAllLogEvents(loggedInUser.getId(),
				pageNumber != null ? pageNumber : 0, pageSize != null ? pageSize : 25);
		LOG.debug("number = {}, logs = {}, total pages = {}", logsPage.getNumber(), logsPage.getNumberOfElements(),
				logsPage.getTotalPages());
		PageWrapper<LogEntry> page = new PageWrapper<LogEntry>(logsPage, URI_LOG_EVENTS);
		ModelAndView modelView = new ModelAndView("log/view-event-logs");
		modelView.getModel().put("page", page);
		modelView.getModel().put("logsFromDate",
				!page.getContent().isEmpty() ? page.getContent().get(page.getContent().size() - 1).getCreatedDate()
						: new Date());
		modelView.getModel().put("logsToDate",
				!page.getContent().isEmpty() ? page.getContent().get(0).getCreatedDate() : new Date());
		return modelView;
	}

	@GetMapping("/diet/log/event/view/{logId}")
	public ModelAndView logEventView(@PathVariable("logId") int logId) {
		LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
		if (loggedInUser == null) {
			throw new ApplicationException("Not logged in!");
		}
		LogEntry logEvent = logEventService.getLogEvent(loggedInUser.getId(), logId);
		if (LOG.isDebugEnabled()) {
			LOG.debug("{}", logEvent);
		}
		ModelAndView modelView = new ModelAndView("log/view-log-event");
		modelView.getModel().put("log", logEvent);
		return modelView;
	}

	@GetMapping("/diet/log/events/txt")
	@ResponseBody
	public ModelAndView viewLogEventsAsPlainText() {
		LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
		Page<LogEntry> page = logEventService.getAllLogEvents(loggedInUser.getId(), 1, 25);
		if (LOG.isDebugEnabled()) {
			LOG.debug("number of log entries: " + page.getNumber());
		}
		ModelAndView modelView = new ModelAndView("log/view-event-logs-txt");
		modelView.getModel().put("page", page);
		return modelView;
	}

	// ---------------------------------------------
	// New and update log event
	// ---------------------------------------------

	@GetMapping("/diet/log/event/new")
	public String initNewLogEventForm(Map<String, Object> model) {
		LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
		if (loggedInUser == null) {
			throw new ApplicationException("Not logged in!");
		}
		LogEntry log = new LogEntry();
		log.setLevel("INFO");
		log.setCreatedDate(new Date());
		log.setLastModifiedTime(System.currentTimeMillis());
		log.setCreatedByUser(loggedInUser.getUsername());
		model.put("log", log);
		return "log/edit-event-log";
	}

	/**
	 * User POST for new
	 * 
	 */
	@PostMapping("/diet/log/event/new")
	public String processNewLogEventForm(@Valid @ModelAttribute("log") LogEntry log, BindingResult result,
			SessionStatus status) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(log.toString());
		}
		if (result.hasErrors()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(result.toString());
			}
			return "log/edit-event-log";
		} else {
			// set created by user id
			log.setFkUserId(authenticationFacade.getLoggedInUser().getId());
			this.logEventService.saveLogEvent(log);
			status.setComplete();
			return String.format("%s:%s", REDIRECT, URI_LOG_EVENTS);
		}
	}

	@GetMapping("/diet/log/event/edit/{logEventId}")
	public String initUpdateLogEventForm(@PathVariable("logEventId") int logEventId, Model model) {
		LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
		LogEntry log = logEventService.getLogEvent(loggedInUser.getId(), logEventId);
		if (log == null) {
			throw new ApplicationException(String.format("Object Not Found, logEventId=%s", logEventId));
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug(log.toString());
		}
		model.addAttribute("log", log);
		return "log/edit-event-log";
	}

	/**
	 * Use PUT for updates
	 * 
	 */
	@PostMapping("/diet/log/event/edit")
	public String processUpdateLogEventForm(@Valid @ModelAttribute("log") LogEntry log, BindingResult result,
			SessionStatus status) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(log.toString());
		}
		if (result.hasErrors()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(result.toString());
			}
			return "log/edit-event-log";
		} else {
			logEventService.saveLogEvent(log);
			status.setComplete();
			return String.format("%s:%s", REDIRECT, URI_LOG_EVENTS);
		}
	}

	/**
	 * Use PUT for updates
	 * 
	 */
	@PostMapping("/diet/log/event/edit/{logEventId}")
	public String processUpdateLogEventIdForm(@Valid @ModelAttribute("log") LogEntry log, BindingResult result,
			SessionStatus status) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(log.toString());
		}
		if (result.hasErrors()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(result.toString());
			}
			return "log/edit-event-log";
		} else {
			logEventService.saveLogEvent(log);
			status.setComplete();
			return String.format("%s:%s", REDIRECT, URI_LOG_EVENTS);
		}
	}

	/**
	 * 
	 */
	@GetMapping("/diet/log/event/delete/{logEntryId}")
	public String deletelogEvent(@PathVariable("logEntryId") int logEntryId) {
		LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
		LogEntry log = logEventService.getLogEvent(loggedInUser.getId(), logEntryId);
		if (log == null) {
			throw new ApplicationException("Object Not Found, logEntryId=" + logEntryId);
		}
		logEventService.deleteLogEvent(loggedInUser.getId(), logEntryId);
		return String.format("%s:%s", REDIRECT, URI_LOG_EVENTS);
	}

	// ---------------------------------------------
	// Add/delete image to log event
	// ---------------------------------------------
	
	/**
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/diet/log/event/img/upload/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("description") String description, @RequestParam("id") String id, RedirectAttributes redirectAttributes) {
		if (file == null) {
			// return error
			return "redirect:/upload/" + id;
		}
		fileUploadService.store(file, id, description);
		LOG.debug("Successfully uploaded: {}/{}", id, file.getName());
		// Add parameters to be viewed on the redirect page
		redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
		return "redirect:/upload/" + id;
	}

	
	/**
	 * Use PUT for updates
	 * 
	 */
	@PostMapping("/diet/log/event/img/delete")
	public String processDeleteLogImageForm(@Valid @ModelAttribute("resource") ImageResource resource, BindingResult result,
			SessionStatus status) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(resource.toString());
		}
		if (result.hasErrors()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(result.toString());
			}
			return String.format("%s:%s/%s", REDIRECT, URI_LOG_EVENT_VIEW, resource.getId());
		} else {
			fileUploadService.deleteImage(resource.getId(), resource.getName());
			logEventService.deleteLogEventImage(Integer.parseInt(resource.getId()), resource.getName());
			return String.format("%s:%s/%s", REDIRECT, URI_LOG_EVENT_VIEW, resource.getId());
		}
	}

	/**
	 * 
	 */
	@GetMapping("/diet/log/event/img/delete/{logEntryId}/{filename}")
	public String deletelogEventImg(@PathVariable("logEntryId") int logEntryId,
			@PathVariable("filename") String filename) {
		fileUploadService.deleteImage(Integer.toString(logEntryId), filename);
		logEventService.deleteLogEventImage(logEntryId, filename);
		return String.format("%s:%s/%s", REDIRECT, URI_LOG_EVENT_VIEW, logEntryId);
	}

	// ---------------------------------------------
	// Add comments to log event
	// ---------------------------------------------

	@PostMapping(value = "/diet/log/event/comment/add", params = { "logId", "comment" })
	public String newCommentLogEventForm(@RequestParam("logId") Integer logId, @RequestParam("comment") String comment,
			Model map) {
		LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
		LogComment logComment = new LogComment();
		logComment.setFkUserId(loggedInUser.getId());
		logComment.setCreatedDate(new Date());
		logComment.setFkLogId(logId);
		logComment.setContent(comment);
		logEventService.saveLogEventComment(logComment);
		return String.format("%s:%s/%s", REDIRECT, URI_LOG_EVENT_VIEW, logId);
	}

	@GetMapping(value = "/diet/log/event/comment/new")
	public String initNewCommentLogEventForm(Map<String, Object> model) {
		LogComment comment = new LogComment();
		comment.setCreatedDate(new Date());
		model.put("logComment", comment);
		return "log/edit-comment-event-log";
	}

	/**
	 * User POST for new
	 * 
	 */
	@PostMapping(value = "/diet/log/event/comment/new")
	public String processNewCommentLogEventForm(@Valid @ModelAttribute("logComment") LogComment logComment,
			BindingResult result, SessionStatus status) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(logComment.toString());
		}
		if (result.hasErrors()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(result.toString());
			}
			return "log/edit-comment-event-log";
		} else {
			// set created by user id
			logComment.setFkUserId(authenticationFacade.getLoggedInUser().getId());
			this.logEventService.saveLogEventComment(logComment);
			status.setComplete();
			return String.format("%s:%s", REDIRECT, URI_LOG_EVENTS);
		}
	}

}
