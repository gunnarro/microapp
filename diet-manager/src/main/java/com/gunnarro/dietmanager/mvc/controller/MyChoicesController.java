package com.gunnarro.dietmanager.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.dietmanager.domain.diet.DietMenu;
import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.domain.statistic.KeyValue;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.domain.statistic.MealStatistic;
import com.gunnarro.dietmanager.mvc.dto.UserDto;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.dietmanager.service.impl.DietManagerServiceImpl;
import com.gunnarro.dietmanager.utility.Utility;
import com.gunnarro.useraccount.domain.user.LocalUser;

@Controller
// @RequestMapping("/user")
@Scope("session")
public class MyChoicesController extends BaseController {

    private static final int FOR_LAST_DAYS = 7;

    private static final Logger LOG = LoggerFactory.getLogger(MyChoicesController.class);

    /**
     * 
     * @param binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat(Utility.DATE_PATTERN);
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
    }

    private List<UserDto> getUsersWithControllerRights() {
        List<UserDto> users = new ArrayList<>();
        users.add(new UserDto(5, "pappa"));
        users.add(new UserDto(6, "mamma"));
        users.add(new UserDto(4, "pepilie"));
        return users;
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/diet/mychoices/delete/{id}", method = RequestMethod.GET)
    public String deleteMyChoiceMenuItem(@PathVariable("id") int id) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException(ApplicationException.NOT_LOGGED_IN);
        }
        dietManagerService.deleteSelectedFoodForUser(id);
        return "redirect:/diet/mychoices";
    }

    /**
     * 
     * @param menuId
     * @param forDate must be on date format dd.MM.yyyy. if not set or wrong
     *            format this parameter is ignored, and we use current date
     *            instead
     * @param model
     * @return
     */
    @RequestMapping(value = "/diet/mychoices/new/{menuId}/{forDate}", method = RequestMethod.GET)
    public String initNewDietMyChoicesForm(@PathVariable("menuId") int menuId, @PathVariable("forDate") String forDate, Map<String, Object> model) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException(ApplicationException.NOT_LOGGED_IN);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("logged in user: " + loggedInUser.toString());
        }
        DietMenu dietMenu = dietManagerService.getDietMenu(menuId);
        if (dietMenu == null) {
            throw new ApplicationException("Object Not Found, menuId=" + menuId);
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setFkDietMenuId(menuId);
        menuItem.setCreatedTime(System.currentTimeMillis());
        Date date = Utility.timeToDate(forDate, Utility.DATE_PATTERN);
        if (date != null) {
            menuItem.setCreatedTime(date.getTime());
        }
        menuItem.setControlledByUserId(loggedInUser.getId());
        menuItem.setPreparedByUserId(loggedInUser.getId());
        menuItem.setControlledByUsername(loggedInUser.getUsername());
        // caused no conflict as default
        menuItem.setCausedConflict(0);
        // Do not return meal items for already selected meals
        List<String> mealNames = dietManagerService.getSelectedMealNamesForDate(menuItem.getCreatedDate());
        model.put("menuItem", menuItem);
        model.put("userId", loggedInUser.getId());
        model.put("users", getUsersWithControllerRights());
        model.put("breakfastMenuItems", mealNames.contains("Frokost") ? null : dietMenu.getBreakfastMenuItems());
        model.put("lunchMenuItems", mealNames.contains("Lunsj") ? null : dietMenu.getLunchMenuItems());
        model.put("dinnerMenuItems", mealNames.contains("Middag") ? null : dietMenu.getDinnerMenuItems());
        model.put("dessertMenuItems", mealNames.contains("Dessert") ? null : dietMenu.getDessertMenuItems());
        model.put("eveningMenuItems", mealNames.contains("Kveldsmat") ? null : dietMenu.getEveningMenuItems());
        model.put("mealBetweenMenuItems", mealNames.contains("Mellom måltid") ? null : dietMenu.getMealBetweenMenuItems());
        if (LOG.isDebugEnabled()) {
            LOG.debug("dietrules: " + dietManagerService.getDietRules(1));
        }
        model.put("dietRules", dietManagerService.getDietRules(1));
        return "diet/edit-diet-mychoice";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/diet/mychoices/new/{menuId}/{forDate}", method = RequestMethod.POST)
    public String processNewMyChoicesForm(@Valid @ModelAttribute("menuItem") MenuItem menuItem, BindingResult result, SessionStatus status, Model model) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException(ApplicationException.NOT_LOGGED_IN);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("for date: " + menuItem.getCreatedDate() + ", id: " + menuItem.getId() + ", controlledBy: " + menuItem.getControlledByUserId()
                    + ", hasConflict: " + menuItem.hasConflict());
        }

        // Do not double register meal type for a day
        boolean isMealAlreadyRegistered = dietManagerService.checkIfSelectedMealAlreadyRegistered(loggedInUser.getId(), menuItem.getCreatedDate(),
                menuItem.getId());
        if (isMealAlreadyRegistered) {
            String mealName = dietManagerService.getDietMenuItem(menuItem.getId()).getName();
            String errMsg = mealName + " er allerede registrert for valgt dato: " + Utility.formatTime(menuItem.getCreatedTime(), Utility.DATE_PATTERN);
            result.rejectValue("id", "error.selected.meal", errMsg);
        }

        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            // Have to reset selected meal so it not will be handled as an
            // update request
            menuItem.setId(null);
            menuItem.setControlledByUserId(loggedInUser.getId());
            menuItem.setControlledByUsername(loggedInUser.getUsername());
            menuItem.setPreparedByUserId(loggedInUser.getId());
            DietMenu dietMenu = dietManagerService.getDietMenu(menuItem.getFkDietMenuId());

            // Do not return meal items for already selected meals
            List<String> mealNames = dietManagerService.getSelectedMealNamesForDate(menuItem.getCreatedDate());

            model.addAttribute("userId", loggedInUser.getId());
            model.addAttribute("breakfastMenuItems", mealNames.contains("Frokost") ? null : dietMenu.getBreakfastMenuItems());
            model.addAttribute("lunchMenuItems", mealNames.contains("Lunsj") ? null : dietMenu.getLunchMenuItems());
            model.addAttribute("dinnerMenuItems", mealNames.contains("Middag") ? null : dietMenu.getDinnerMenuItems());
            model.addAttribute("dessertMenuItems", mealNames.contains("Dessert") ? null : dietMenu.getDessertMenuItems());
            model.addAttribute("eveningMenuItems", mealNames.contains("Kveldsmat") ? null : dietMenu.getEveningMenuItems());
            model.addAttribute("mealBetweenMenuItems", mealNames.contains("Mellom måltid") ? null : dietMenu.getMealBetweenMenuItems());
            model.addAttribute("dietRules", dietManagerService.getDietRules(1));
            return "diet/edit-diet-mychoice";
        } else {
            if (menuItem.hasConflict()) {
                MenuItem dietMenuItem = dietManagerService.getDietMenuItem(menuItem.getId());
                LogEntry log = new LogEntry();
                log.setFkUserId(loggedInUser.getId());
                log.setCreatedDate(menuItem.getCreatedDate());
                log.setLevel("CONFLICT");
                log.setTitle("Konflikt i forbindelse med " + dietMenuItem.getName());
                log.setContent(menuItem.getConflictDescription());
                int logId = logEventService.saveLogEvent(log);
                menuItem.setFkLogId(logId);
            }
            this.dietManagerService.saveSelectedFoodForUser(loggedInUser.getId(), menuItem);
            status.setComplete();
            return "redirect:/diet/mychoices";
        }
    }

    /**
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/diet/mychoices", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewMyChoices() {
        int forLastDays = 60;
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException(ApplicationException.NOT_LOGGED_IN);
        }
        List<MenuItem> menuItems = dietManagerService.getSelectedMenuItemsForUser(loggedInUser.getId(), forLastDays);

        Comparator<Long> asendingComparator = new Comparator<Long>() {
            @Override
            public int compare(Long v1, Long v2) {
                return v2.compareTo(v1);
            }
        };
        // sort the map by date
        TreeMap<Long, List<MenuItem>> map = new TreeMap<>(asendingComparator);
        for (MenuItem m : menuItems) {
            Long createdDate = Utility.resetTime(m.getCreatedDate()).getTime();
            if (!map.containsKey(createdDate)) {
                List<MenuItem> list = new ArrayList<>();
                list.add(m);
                map.put(createdDate, list);
            } else {
                map.get(createdDate).add(m);
            }
        }

        Comparator<MenuItem> menuItemComparator = new Comparator<MenuItem>() {
            @Override
            public int compare(MenuItem i1, MenuItem i2) {
                return i1.getSortByValue().compareTo(i2.getSortByValue());
            }
        };

        // Have to run through all list and sort menu items by meal type
        for (Map.Entry<Long, List<MenuItem>> entry : map.entrySet()) {
            Collections.sort(entry.getValue(), menuItemComparator);
        }

        // Get missing meals
        Map<Date, List<String>> missingMealsMap = dietManagerService.getMissingMealsForUser(loggedInUser.getId(), FOR_LAST_DAYS);

        String period = null;
        if (!map.isEmpty()) {
            period = Utility.formatTime(map.lastKey(), Utility.DATE_PATTERN) + " - " + Utility.formatTime(map.firstKey(), Utility.DATE_PATTERN);
        }
        ModelAndView modelView = new ModelAndView("diet/view-diet-mychoices");
        modelView.getModel().put("period", period);
        modelView.getModel().put("menuItemMap", map);
        modelView.getModel().put("missingMealsMap", missingMealsMap.size() == 0 ? null : missingMealsMap);
        return modelView;
    }

    /**
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/diet/mychoices/statistic/graph", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewMyChoicesMealsControlledByGraph() {
        return new ModelAndView("diet/view-diet-mychoices-controlledby-graph");
    }

    // ---------------------------------------------
    // New and delete my choices
    // ---------------------------------------------

    /**
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/diet/mychoices/statistic/log/deprecated", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewMyChoicesMealsControlledByLogDeprecated() {
        Map<String, List<KeyValuePair>> myChoicesStatisticMap = dietManagerService.getMyChoicesStatisticByWeek();
        List<KeyValuePair> list = dietManagerService.getMyChoicesStatisticSummary();
        ModelAndView modelView = new ModelAndView("diet/view-diet-mychoices-statistic");
        modelView.getModel().put("statisticMap", myChoicesStatisticMap);
        modelView.getModel().put("statisticSummaryList", list);
        return modelView;
    }

    /**
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/diet/mychoices/statistic/log", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewMyChoicesStatisticLog() {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException(ApplicationException.NOT_LOGGED_IN);
        }
        int days = 30;
        List<MealStatistic> mealStatsticList = dietManagerService.getMealStatsticForUsers(loggedInUser.getId(), days);
        Map<KeyValue, List<MealStatistic>> mealStatisticByWeekNumberMap = DietManagerServiceImpl.mapMealStatisticByWeekNumber(mealStatsticList);
        List<MealStatistic> sumMealStatisticByUserName = DietManagerServiceImpl.sumMealStatisticByUserName(mealStatsticList);
        String period = Utility.formatTime(sumMealStatisticByUserName.get(0).getFromDate().getTime(), "dd.MM.yyyy") + " to "
                + Utility.formatTime(sumMealStatisticByUserName.get(0).getToDate().getTime(), "dd.MM.yyyy");
        ModelAndView modelView = new ModelAndView("diet/view-diet-mychoices-statistic");
        modelView.getModel().put("period", period);
        modelView.getModel().put("statisticMap", mealStatisticByWeekNumberMap);
        modelView.getModel().put("statisticSummaryList", sumMealStatisticByUserName);
        return modelView;
    }

    /**
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/diet/mychoices/statistic/top10", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewMyChoicesTopTenMeals() {
        List<KeyValuePair> top10List = dietManagerService.getTop10SeletedMeals();
        ModelAndView modelView = new ModelAndView("diet/view-diet-mychoices-top10");
        modelView.getModel().put("top10List", top10List);
        return modelView;
    }

}
