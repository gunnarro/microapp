package com.gunnarro.dietmanager.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.domain.diet.DietPlan;
import com.gunnarro.dietmanager.domain.diet.FoodProduct;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.view.KeyValuePairList;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.dietmanager.utility.Utility;
import com.gunnarro.useraccount.domain.user.LocalUser;

@Controller
// @RequestMapping("/user")
@Scope("session")
public class DietController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(DietController.class);

    /**
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/diet/listdietplans", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listDietPlans() {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        List<DietPlan> dietPlans = dietManagerService.getDietPlans(loggedInUser.getId());
        if (dietPlans == null) {
            throw new ApplicationException("No diet plan found for userId:" + loggedInUser.getId());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Number of diet plans: " + dietPlans.size());
        }
        ModelAndView modelView = new ModelAndView("diet/list-dietplans-view");
        modelView.getModel().put("dietPlans", dietPlans);
        return modelView;
    }

    /**
     * 
     * @param userId
     * @param dietPlanId
     * @return
     */
    @RequestMapping(value = "/diet/dietplan/{dietPlanId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewDietPlan(@PathVariable("dietPlanId") String dietPlanId) {
        Integer id = null;
        try {
            id = Integer.parseInt(dietPlanId);
        } catch (Exception e) {
            LOG.warn("Invalid diet plan id: " + dietPlanId + ", user default diet plan id: 1", e);
            // ignore exception, select current active diet plan,
            id = -1;
        }

        DietPlan dietPlan = dietManagerService.getDietPlan(id);
        if (dietPlan == null) {
            throw new ApplicationException("No diet plan found for Id:" + dietPlanId);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(dietPlan.toString());
        }
        ModelAndView modelView = new ModelAndView("diet/view-diet-plan");
        modelView.getModel().put("dietPlan", dietPlan);
        return modelView;
    }

    /**
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/diet/mystatus", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewMyStatus() {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        int forLastDays = new DateTime(System.currentTimeMillis()).getDayOfWeek();
        DateTime fromDate = new DateTime().minusDays(forLastDays);
        String period = Utility.getWeekInfo(fromDate.getWeekOfWeekyear());
        List<KeyValuePair> myStatusList = dietManagerService.getMyStatus(loggedInUser.getId(), forLastDays);
        LogEntry reportLog = dietManagerService.getMyStatusReport(loggedInUser.getId());
        LogEntry recentConflictLog = dietManagerService.getRecentLogEvent(loggedInUser.getId(), "CONFLICT", forLastDays);
        ModelAndView modelView = new ModelAndView("diet/view-my-status");
        modelView.getModel().put("period", period);
        modelView.getModel().put("myStatusItemList", myStatusList);
        modelView.getModel().put("myStatusReportItemList", reportLog != null ? Arrays.asList(reportLog.getContent().split("-")) : null);
        modelView.getModel().put("recentConflictLog", recentConflictLog);
        return modelView;
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "/diet/changelist", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewProductChangeList() {
        List<FoodProduct> foodProducts = dietManagerService.getDietFoodChangeList();
        ModelAndView modelView = new ModelAndView("diet/view-diet-food-changelist");
        modelView.getModel().put("foodProducts", foodProducts);
        return modelView;
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "/diet/recipes", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewRecipesList() {
        List<KeyValuePairList> recipes = dietManagerService.getFoodRecipes();
        ModelAndView modelView = new ModelAndView("diet/view-diet-recipe-list");
        modelView.getModel().put("recipes", recipes);
        return modelView;
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "/diet/rules", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewRules() {
        ModelAndView modelView = new ModelAndView("diet/view-rules");
        return modelView;
    }

    /**
     * 
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
    }

}
