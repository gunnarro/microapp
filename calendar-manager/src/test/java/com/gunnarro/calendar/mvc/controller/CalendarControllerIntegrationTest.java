package com.gunnarro.calendar.mvc.controller;

import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.calendar.domain.calendar.CalendarEvent;
import com.gunnarro.calendar.domain.view.SearchActivity;
import com.gunnarro.calendar.domain.view.list.Item;

public class CalendarControllerIntegrationTest extends SpringTestSetup {

		@Autowired
		private CalendarController controller;
	    
	    @Before
	    public void init() {
	    }

	    @Ignore
	    @Test
	    public void listAllCalendarEventsManual() throws Exception {
	        ModelAndView modelAndView = controller.listCalendarEvents(false, "year", 0, "all", "all");
	        Assert.assertEquals("calendar/list-events-view", modelAndView.getViewName());
	        Assert.assertNotNull(modelAndView.getModel());
	        SearchActivity searchActivity = (SearchActivity) modelAndView.getModel().get("searchActivity");

	        Assert.assertEquals("all", searchActivity.getName());
	        Assert.assertEquals("all", searchActivity.getType());
	        Assert.assertEquals("year", searchActivity.getPeriod());
	        Assert.assertEquals(0, searchActivity.getAmount());
	        Assert.assertFalse(searchActivity.isReload());
	        Assert.assertNotNull(modelAndView.getModel().get("lastReloaded"));
	        List<Item> items = (List<Item>) modelAndView.getModel().get("calendarUrls");
	        Assert.assertNotNull(items);
	        Assert.assertEquals(1, items.size());
	        List<Entry<String, List<CalendarEvent>>> events = (List<Entry<String, List<CalendarEvent>>>) modelAndView.getModel().get("entryEventList");
	        Assert.assertNotNull(events);
//	        Assert.assertEquals(3, events.size());
//	        Assert.assertEquals(3, modelAndView.getModel().get("numberOfMatches"));
//	        Assert.assertEquals(3, modelAndView.getModel().get("numberOfMatchesTotal"));
//	        Assert.assertNotNull(modelAndView.getModel().get("selectedPeriodTxt"));
	    }

	}
  

