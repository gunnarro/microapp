package com.gunnarro.dietmanager.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.gunnarro.dietmanager.domain.log.LogComment;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.mvc.controller.AuthenticationFacade;
import com.gunnarro.dietmanager.repository.LogEventRepository;
import com.gunnarro.dietmanager.service.FileUploadService;
import com.gunnarro.dietmanager.service.LogEventService;

/**
 * 
 * @author mentos
 *
 */
@Service
public class LogEventServiceImpl implements LogEventService {

	private static final Logger LOG = LoggerFactory.getLogger(LogEventServiceImpl.class);

	@Autowired
	protected AuthenticationFacade authenticationFacade;

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private LogEventRepository logEventRepository;

	private boolean checkPermission(Integer logEventId) {
		boolean hasPermission = logEventRepository.hasPermission(logEventId,
				authenticationFacade.getAuthentication().getName());
		if (!hasPermission) {
			throw new SecurityException("Access denied");
		}
		return hasPermission;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LogEntry> getLogEvents(Integer userId) {
		return logEventRepository.getLogEvents(userId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogEntry getLogEvent(Integer userId, Integer logEntryId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("userId = {}, logEntryId = {}", +userId, logEntryId);
		}
		LogEntry logEvent = logEventRepository.getLogEvent(userId, logEntryId);
		logEvent.setResources(fileUploadService.getImages(logEntryId.toString()));
		return logEvent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<LogEntry> getAllLogEvents(Integer userId, int pageNumber, int pageSize) {
		return logEventRepository.getAllLogEvents(userId, pageNumber, pageSize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteLogEvent(Integer userId, Integer id) {
		// check if user have permission to delete
		checkPermission(id);
		return logEventRepository.deleteLogEvent(userId, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteLogEventImage(Integer logId, String fileName) {
		checkPermission(logId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int saveLogEvent(LogEntry log) {
		if (log.isNew()) {
			return logEventRepository.createLogEvent(log);
		} else {
			// check if user have permission to update
			checkPermission(log.getId());
			return logEventRepository.updateLogEvent(log);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int saveLogEventComment(LogComment logComment) {
		if (logComment.isNew()) {
			logEventRepository.createLogComment(logComment);
			// update last modified date for main log entry
			return logEventRepository.updateLogEventLastModifiedDate(logComment.getFkLogId());
		} else {
			// update is not supported for comments
			return 0;
		}
	}

	/**
	 * For unit testing only, inject mock
	 * 
	 * @param logEventRepository
	 */
	public void setLogEventRepository(LogEventRepository logEventRepository) {
		this.logEventRepository = logEventRepository;
	}

	/**
	 * For unit testing only, inject mock
	 * 
	 */
	public void setAuthenticationFacade(AuthenticationFacade authenticationFacade) {
		this.authenticationFacade = authenticationFacade;
	}

}
