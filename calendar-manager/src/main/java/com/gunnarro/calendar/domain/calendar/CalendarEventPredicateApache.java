package com.gunnarro.calendar.domain.calendar;

import java.util.List;

import org.apache.commons.collections4.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author admin
 *
 */
public class CalendarEventPredicateApache {

    private static final Logger LOG = LoggerFactory.getLogger(CalendarEventPredicateApache.class);

    public static Predicate<CalendarEvent> createPredicate(final List<CalendarEvent> filters) {
        if (LOG.isDebugEnabled()) {
        	LOG.debug("Number of filtes: " + (filters != null ? filters.size() : 0));
        	if (filters != null) {
        		for (CalendarEvent filter : filters) {
        			LOG.debug("Applied Filter: " + filter.getType() + ":" + filter.getName());
        		}
        	}
        }
        return new Predicate<CalendarEvent>() {
            @Override
            public boolean evaluate(CalendarEvent event) {
                if (filters == null || filters.isEmpty()) {
                	if (LOG.isDebugEnabled()) {
                		LOG.debug("No filters fond!");
                	}
                    return true;
                }
                boolean hasPassedFilter = false;
                String appliedFilter = null;
                for (CalendarEvent filter : filters) {
                	appliedFilter = filter.getType() + ":" + filter.getName();
                	if (isAll(filter.getType()) && isAll(filter.getName())) {
                		hasPassedFilter = true;
                    } else if (isAll(filter.getType()) && event.summaryContains(filter.getName())) {
                    	hasPassedFilter = true;
                    } else if (event.isTypeEqual(filter.getType()) && isAll(filter.getName())) {
                    	hasPassedFilter = true;
                    } else if (event.isTypeEqual(filter.getType()) && event.summaryContains(filter.getName())) {
                    	hasPassedFilter = true;
                    }
                	// break out of the loop upon first time passing a filter criteria
                	if (hasPassedFilter) {
                		break;
                	}
                }
                
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Applied Filter = " + appliedFilter + ", has passed filter = " + hasPassedFilter + ", event = " + event.toString());
                }
                return hasPassedFilter;
            }
        };
    }

    private static boolean isAll(String value) {
        return value == null || value.isEmpty() || value.equalsIgnoreCase("all");
    }
}
