package com.gunnarro.dietmanager.mvc.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.domain.diet.DietPlan;
import com.gunnarro.dietmanager.domain.diet.FoodProduct;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.view.KeyValuePairList;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.useraccount.domain.user.LocalUser;

public class DietControllerTest extends SpringTestSetup {

    @Mock
    private AuthenticationFacade authenticationFacadeMock;

    private DietController controller;

    @Mock
    private DietManagerService dietManagerServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new DietController();
        controller.setDietManagerService(dietManagerServiceMock);
        controller.setAuthenticationFacade(authenticationFacadeMock);
    }

    @Test
    public void viewChangeList() throws Exception {
        when(dietManagerServiceMock.getDietFoodChangeList()).thenReturn(new ArrayList<FoodProduct>());
        ModelAndView modelAndView = controller.viewProductChangeList();
        Assert.assertEquals("diet/view-diet-food-changelist", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        List<FoodProduct> list = (List<FoodProduct>) modelAndView.getModel().get("foodProducts");
        Assert.assertNotNull(list);
    }

    @Test
    public void viewDietPlan() throws Exception {
        when(dietManagerServiceMock.getDietPlan(2)).thenReturn(new DietPlan());
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        ModelAndView modelAndView = controller.viewDietPlan("2");
        Assert.assertEquals("diet/view-diet-plan", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        DietPlan dietPlan = (DietPlan) modelAndView.getModel().get("dietPlan");
        Assert.assertNotNull(dietPlan);
    }

    @Test
    public void viewDietPlanList() throws Exception {
        when(dietManagerServiceMock.getDietPlans(1)).thenReturn(new ArrayList<DietPlan>());
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        ModelAndView modelAndView = controller.listDietPlans();
        Assert.assertEquals("diet/list-dietplans-view", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        List<DietPlan> list = (List<DietPlan>) modelAndView.getModel().get("dietPlans");
        Assert.assertNotNull(list);
    }

    @Test
    public void viewMyStatus() throws Exception {
        int forLastDays = new DateTime(System.currentTimeMillis()).getDayOfWeek();
        List<KeyValuePair> statusList = new ArrayList<KeyValuePair>();
        LogEntry log = new LogEntry();
        log.setContent("- item1. - item2.");
        statusList.add(new KeyValuePair("Fulgt dietplan", null, -1));
        when(dietManagerServiceMock.getMyStatus(1, forLastDays)).thenReturn(statusList);
        when(dietManagerServiceMock.getMyStatusReport(1)).thenReturn(log);
        when(authenticationFacadeMock.getLoggedInUser()).thenReturn(new LocalUser(1));
        ModelAndView modelAndView = controller.viewMyStatus();
        Assert.assertEquals("diet/view-my-status", modelAndView.getViewName());
        List<KeyValuePair> list = (List<KeyValuePair>) modelAndView.getModel().get("myStatusItemList");
        Assert.assertEquals(1, list.size());
        List<String> reportList = (List<String>) modelAndView.getModel().get("myStatusReportItemList");
        System.out.println("- item1. - item2".split("-").length);
        Assert.assertEquals(3, reportList.size());
    }

    @Test
    public void viewRecipeList() throws Exception {
        when(dietManagerServiceMock.getFoodRecipes()).thenReturn(new ArrayList<KeyValuePairList>());
        ModelAndView modelAndView = controller.viewRecipesList();
        Assert.assertEquals("diet/view-diet-recipe-list", modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
        List<KeyValuePairList> list = (List<KeyValuePairList>) modelAndView.getModel().get("recipes");
        Assert.assertNotNull(list);
    }

    @Test
    public void viewRules() throws Exception {
        ModelAndView modelAndView = controller.viewRules();
        Assert.assertEquals("diet/view-rules", modelAndView.getViewName());
    }

}