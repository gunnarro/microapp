package com.gunnarro.sportsteam.mvc.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.party.User;
import com.gunnarro.sportsteam.mvc.dto.UserDto;

@Controller
@Scope("session")
@RequestMapping("/user")
@SessionAttributes(types = UserDto.class)
public class UserController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

//    @Autowired
//    private CalendarController calendarController;

    @RequestMapping(method = RequestMethod.GET)
    public String viewUser() {
        User user = authenticationFacade.getLoggedInUser();
        int teamId = sportsTeamService.getTeamIdForUser(user.getId());
        return "redirect:/team/" + teamId;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, WebRequest request, Errors errors) {
        User registered = new User();
        if (!result.hasErrors()) {
            registered = createUserAccount(userDto, result);
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", userDto);
        } else {
            return new ModelAndView("successRegister", "user", userDto);
        }
    }

//    @RequestMapping(value = "/calendar/listevents/{reload}/{period}/{amount}/{type}/{name}", method = RequestMethod.GET)
//    @ResponseBody
//    public ModelAndView listCalendarEvents(@PathVariable("reload") boolean reload, @PathVariable("period") String period, @PathVariable("amount") int amount,
//            @PathVariable("type") String type, @PathVariable("name") String name) {
//        User user = authenticationFacade.getLoggedInUser();
//        ModelAndView modelView = calendarController.listCalendarEvents(reload, period, amount, type, name);
//        modelView.setViewName("secured/user/calendar/list-events-view");
//        modelView.getModel().put("user", user);
//        return modelView;
//    }

    private User createUserAccount(UserDto userDto, BindingResult result) {
        // User registered = null;
        // try {
        // registered = service.registerNewUserAccount(accountDto);
        // } catch (EmailExistsException e) {
        // return null;
        // }
        return null;
    }
}
