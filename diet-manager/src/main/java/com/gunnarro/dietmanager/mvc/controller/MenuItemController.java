package com.gunnarro.dietmanager.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.useraccount.domain.user.LocalUser;

@Controller
// @RequestMapping("/user")
@Scope("session")
public class MenuItemController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(MenuItemController.class);

    /**
     * Use GET for delete
     * 
     */
    @RequestMapping(value = "/diet/menuitem/delete/{menuItemId}", method = RequestMethod.GET)
    public String deleteDietMenuItem(@PathVariable("menuItemId") int menuItemId) {
        MenuItem menuItem = dietManagerService.getDietMenuItem(menuItemId);
        if (menuItem == null) {
            throw new ApplicationException("Object Not Found, menuItemId=" + menuItemId);
        }
        dietManagerService.deleteDietMenuItem(menuItemId);
        return "redirect:/diet/menu/" + menuItem.getFkDietMenuId();
    }

    /**
     * 
     * @param menuId
     * @param model
     * @return
     */
    @RequestMapping(value = "/diet/menuitem/new/{menuId}", method = RequestMethod.GET)
    public String initNewDietMenuItemForm(@PathVariable("menuId") int menuId, Map<String, Object> model) {
        DietMenu dietMenu = dietManagerService.getDietMenu(menuId);
        if (dietMenu == null) {
            throw new ApplicationException("Object Not Found, menuId=" + menuId);
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setFkDietMenuId(menuId);
        model.put("menuItem", menuItem);
        model.put("menuItemTypes", dietManagerService.getDietMenuItemTypes());
        return "diet/edit-diet-menu-item";
    }

    // ---------------------------------------------
    // New and update menu item
    // ---------------------------------------------

    /**
     * 
     * @param menuItemId
     * @param model
     * @return
     */
    @RequestMapping(value = "/diet/menuitem/edit/{menuItemId}", method = RequestMethod.GET)
    public String initUpdateDietMenuItemForm(@PathVariable("menuItemId") int menuItemId, Model model) {
        MenuItem menuItem = dietManagerService.getDietMenuItem(menuItemId);
        if (menuItem == null) {
            throw new ApplicationException("Object Not Found, menuItemId=" + menuItemId);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(menuItem.toString());
        }
        model.addAttribute("menuItem", menuItem);
        model.addAttribute("menuItemTypes", dietManagerService.getDietMenuItemTypes());
        return "diet/edit-diet-menu-item";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/diet/menuitem/new/{menuId}", method = RequestMethod.POST)
    public String processNewDietMenuItemForm(@Valid @ModelAttribute("menuItem") MenuItem menuItem, BindingResult result, SessionStatus status, Model model) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(menuItem.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            model.addAttribute("menuItemTypes", dietManagerService.getDietMenuItemTypes());
            return "diet/edit-diet-menu-item";
        } else {
            this.dietManagerService.saveDietMenuItem(menuItem);
            status.setComplete();
            return "redirect:/diet/menu/" + menuItem.getFkDietMenuId();
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/diet/menuitem/edit/{menuItemId}", method = RequestMethod.PUT)
    public String processUpdateDietMenuItemForm(@Valid @ModelAttribute("menuItem") MenuItem menuItem, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(menuItem.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "diet/edit-diet-menu-item";
        } else {
            dietManagerService.saveDietMenuItem(menuItem);
            status.setComplete();
            return "redirect:/diet/menu/" + menuItem.getFkDietMenuId();
        }
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "/diet/menuitem/view/{menuItemId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewMenuItem(@PathVariable("menuItemId") int menuItemId) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        ModelAndView modelView = new ModelAndView("diet/view-menu-item");
        modelView.getModel().put("menuItem", dietManagerService.getDietMenuItem(menuItemId));
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
