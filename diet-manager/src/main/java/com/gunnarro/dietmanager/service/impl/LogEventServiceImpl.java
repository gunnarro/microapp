package com.gunnarro.dietmanager.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gunnarro.dietmanager.domain.log.LogComment;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.mvc.controller.AuthenticationFacade;
import com.gunnarro.dietmanager.repository.LogEventRepository;
import com.gunnarro.dietmanager.service.LogEventService;

@Service
public class LogEventServiceImpl implements LogEventService {

	private static final Logger LOG = LoggerFactory.getLogger(LogEventServiceImpl.class);

	@Autowired
	protected AuthenticationFacade authenticationFacade;
	
	@Autowired
	private LogEventRepository logEventRepository;

	private boolean checkPermission(Integer logEventId) throws SecurityException {
		boolean hasPermission = logEventRepository.hasPermission(logEventId, authenticationFacade.getAuthentication().getName());
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
	public LogEntry getLogEvent(Integer userId, int logEntryId) {
		LOG.debug("userId=" + userId + ", logEntryId=" + logEntryId);
		return logEventRepository.getLogEvent(userId, logEntryId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LogEntry> getLogEvents(Integer userId, String filterBy, String filterValue) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("filterBy: " + filterBy + ", filterValue: " + filterValue);
		}
		if (StringUtils.isEmpty(filterBy) || StringUtils.isEmpty(filterValue)) {
			return logEventRepository.getAllLogEvents(userId);
		}

		if (filterBy.equals("title")) {
			return logEventRepository.getLogEventsFilteredByTitle(userId, filterValue);
		} else if (filterBy.equals("type")) {
			return logEventRepository.getLogEventsFilteredByType(userId, filterValue);
		} else if (filterBy.equals("content")) {
			return logEventRepository.searchLogEventsContent(userId, filterValue);
		} else {
			return logEventRepository.getAllLogEvents(userId);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LogEntry> getAllLogEvents(Integer userId) {
		return logEventRepository.getAllLogEvents(userId);
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
			return logEventRepository.createLogComment(logComment);
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
