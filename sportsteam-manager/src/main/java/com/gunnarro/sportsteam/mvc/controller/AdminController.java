package com.gunnarro.sportsteam.mvc.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.party.User;
import com.gunnarro.sportsteam.domain.task.ScheduledTask;
import com.gunnarro.sportsteam.mvc.dto.UserDto;
import com.gunnarro.sportsteam.service.exception.ApplicationException;
import com.gunnarro.sportsteam.utility.Utility;

@Controller
@Scope("session")
@RequestMapping("/admin")
public class AdminController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView adminView() {
        ModelAndView modelView = new ModelAndView("secured/admin/admin-view");
        modelView.addObject("infoMsg", null);
        modelView.addObject("statisticList", sportsTeamService.getClubStatistic());
        return modelView;
    }

    @RequestMapping(value = "/deletedata", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView deleteData() {
        int rows = sportsTeamService.deleteAllData(-1);
        ModelAndView modelView = new ModelAndView("secured/admin/admin-view");
        modelView.addObject("infoMsg", "Deleted all data, number of tables: " + rows);
        modelView.addObject("statisticList", sportsTeamService.getClubStatistic());
        return modelView;
    }

    @RequestMapping(value = "/listuseraccounts", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listUserAccounts() {
        List<User> users = sportsTeamService.getUsers();
        ModelAndView modelView = new ModelAndView("secured/admin/account/list-user-accounts");
        modelView.addObject("userList", users);
        return modelView;
    }

    // ---------------------------------------------
    // View, New and update scheduled tasks
    // ---------------------------------------------

    @RequestMapping(value = "/listscheduledtasks", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listScheduledTasks() {
        List<ScheduledTask> tasks = sportsTeamService.getTeamScheduledTasks();
        ModelAndView modelView = new ModelAndView("secured/admin/task/list-scheduled-tasks");
        modelView.addObject("taskList", tasks);
        return modelView;
    }

    // ---------------------------------------------
    // View, New and update user accounts
    // ---------------------------------------------

    @RequestMapping(value = "/useraccount/{userId}", method = RequestMethod.GET)
    public ModelAndView viewUserAccount(@PathVariable("userId") int userId) {
        User user = sportsTeamService.getUser(userId);
        if (user == null) {
            throw new ApplicationException("Object Not Found, userId=" + userId);
        }
        ModelAndView modelView = new ModelAndView("secured/admin/account/view-user-account");
        modelView.addObject("user", new UserDto(user.getUserName(), null, null, user.getEmail()));
        return modelView;
    }

    @RequestMapping(value = "/useraccount/new", method = RequestMethod.GET)
    public String initNewUserAccountForm(Map<String, Object> model) {
        UserDto user = new UserDto();
        model.put("user", user);
        model.put("userRoleOptions", sportsTeamService.getUserRoles());
        return "secured/admin/account/edit-user-account";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/useraccount/new", method = RequestMethod.POST)
    public String processNewUserAccountForm(UserDto userDto, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(userDto.toString());
        }
        if (result.hasErrors()) {
            return "secured/admin/account/edit-user-account";
        } else {
            User user = this.sportsTeamService.getUser(userDto.getUserName());
            if (user != null) {
                return "secured/admin/account/edit-user-account?error=user exist";
            }
            
            this.sportsTeamService.saveUser(new User(userDto.getUserName(), Utility.hashPassword(userDto.getPassword()), userDto.getEmail()));
            status.setComplete();
            return "redirect:/admin/listuseraccounts";
        }
    }

    @RequestMapping(value = "/useraccount/edit/{userId}", method = RequestMethod.GET)
    public String initUpdateUserAccountForm(@PathVariable("userId") int userId, Model model) {
        User user = sportsTeamService.getUser(userId);
        if (user == null) {
            throw new ApplicationException("Object Not Found, userId=" + userId);
        }
        model.addAttribute("user", new UserDto(user.getUserName(), null, null, user.getEmail()));
        return "secured/admin/account/edit-user-account";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/useraccount/edit/{userId}", method = RequestMethod.PUT)
    public String processUpdateUserAccountForm(UserDto userDto, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "secured/admin/account/edit-user-account";
        } else {
            sportsTeamService.saveUser(new User(userDto.getUserName(), Utility.hashPassword(userDto.getPassword()), userDto.getEmail()));
            status.setComplete();
            return "redirect:/admin/listuseraccounts";
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/useraccount/delete/{userId}", method = RequestMethod.GET)
    public String deleteUserAccount(@PathVariable("userId") int userId) {
        User user = sportsTeamService.getUser(userId);
        if (user == null) {
            throw new ApplicationException("Object Not Found, userId=" + userId);
        }
        sportsTeamService.deleteUser(userId);
        return "redirect:/admin/listuseraccounts";
    }

    /**
     * 
     * @param locale
     * @param model
     * @param token
     * @return
     */
    @RequestMapping(value = "/useraccount/new/confirm", method = RequestMethod.GET)
    public String confirmRegistration(Locale locale, Model model, @RequestParam("token") String token) {
//        VerificationToken verificationToken = userService.getVerificationToken(token);  
//        if (verificationToken == null) {
//            String message = messages.getMessage("auth.message.invalidToken", null, locale);
//            model.addAttribute("message", message);
//            return "redirect:/badUser.html?lang=" + locale.getLanguage();
//        }
//     
//        User user = verificationToken.getUser();
//        Calendar cal = Calendar.getInstance();
//        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
//            model.addAttribute("expired", true);
//            model.addAttribute("token", token);
//            return "redirect:/badUser.html?lang=" + locale.getLanguage();
//        }
//     
//        user.setEnabled(true);
//        userService.saveRegisteredUser(user);
//        model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
        return "redirect:/login.html?lang=";// + locale.getLanguage();
    }
    
}
