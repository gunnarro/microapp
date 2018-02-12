package com.gunnarro.dietmanager.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.domain.diet.DietMenu;
import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.dietmanager.utility.Utility;
import com.gunnarro.useraccount.domain.user.LocalUser;

public class MyChoicesControllerTest extends SpringTestSetup {

    @Mock
    private AuthenticationFacade authenticationFacadeMock;

    private MyChoicesController controller;

    @Mock
    private DietManagerService dietManagerServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new MyChoicesController();
        controller.setDietManagerService(dietManagerServiceMock);
        controller.setAuthenticationFacade(authenticationFacadeMock);
    }

    @Test
    public void viewMyChoices() throws Exception {
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, +1);
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE, +2);

        List<MenuItem> menuItemList = Arrays.asList(new MenuItem(DietMenu.BREAKFAST, "", cal1.getTime()), new MenuItem(DietMenu.LUNCH, "", cal1.getTime()),
                new MenuItem(DietMenu.DESSERT, "", cal1.getTime()), new MenuItem(DietMenu.MEAL_BETWEEN, "", cal1.getTime()),
                new MenuItem(DietMenu.DINNER, "", cal1.getTime()), new MenuItem(DietMenu.EVENING, "", cal1.getTime()),
                new MenuItem(DietMenu.LUNCH, "", cal2.getTime()), new MenuItem(DietMenu.DINNER, "", new Date()),
                new MenuItem(DietMenu.EVENING, "", new Date()));

        when(dietManagerServiceMock.getSelectedMenuItemsForUser(1, 60)).thenReturn(menuItemList);
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        ModelAndView modelAndView = controller.viewMyChoices();
        Assert.assertEquals("diet/view-diet-mychoices", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        Map<Long, List<MenuItem>> map = (Map<Long, List<MenuItem>>) modelAndView.getModel().get("menuItemMap");
        Assert.assertNotNull(map);
        Assert.assertEquals(3, map.size());
        for (Map.Entry<Long, List<MenuItem>> entry : map.entrySet()) {
            System.out.println("Key : " + Utility.formatTime(entry.getKey(), "EEEE, dd.MM.yyyy"));
            for (MenuItem it : entry.getValue()) {
                System.out.println(it.getName());
            }
        }
    }

    @Test
    public void viewMyChoicesEmptyList() throws Exception {
        HashMap<Date, List<String>> hashMap = new HashMap<Date, List<String>>();
        hashMap.put(new Date(), Arrays.asList("dinner", "breakfast"));
        when(dietManagerServiceMock.getSelectedMenuItemsForUser(1, 60)).thenReturn(new ArrayList<MenuItem>());
        when(dietManagerServiceMock.getMissingMealsForUser(1, 7)).thenReturn(hashMap);
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        ModelAndView modelAndView = controller.viewMyChoices();
        Assert.assertEquals("diet/view-diet-mychoices", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel().get("menuItemMap"));
        // Assert.assertEquals("{13.11.2016=[dinner, breakfast]}",
        // modelAndView.getModel().get("missingMealsMap").toString());
        System.out.println(modelAndView.getModel().get("menuItemMap"));
    }

}