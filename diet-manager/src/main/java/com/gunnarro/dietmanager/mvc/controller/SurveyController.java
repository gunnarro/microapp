package com.gunnarro.dietmanager.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
// @RequestMapping("/user")
@Scope("session")
public class SurveyController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(SurveyController.class);

    /**
     * 
     * @return
     */
    @RequestMapping(value = "/survey/satisfaction", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewMyChoices() {
        ModelAndView modelView = new ModelAndView("diet/view-diet-my-choices");
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
