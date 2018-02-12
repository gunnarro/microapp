package com.gunnarro.dietmanager.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.useraccount.domain.user.LocalUser;

@Controller
@Scope("session")
public class MenuController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(MenuController.class);

    /**
     * Use GET for delete
     * 
     */
    @RequestMapping(value = "/diet/menu/delete/{menuId}", method = RequestMethod.GET)
    public String deleteDietMenuItem(@PathVariable("menuId") int menuId) {
        DietMenu menu = dietManagerService.getDietMenu(menuId);
        if (menu == null) {
            throw new ApplicationException("Object Not Found, menuId=" + menuId);
        }
        dietManagerService.deleteDietMenu(menuId);
        return "redirect:/diet/menu/" + menu.getId();
    }

    /**
     * 
     * @param menuId
     * @param model
     * @return
     */
    @RequestMapping(value = "/diet/menu/new", method = RequestMethod.GET)
    public String initNewDietMenuItemForm(Map<String, Object> model) {
        model.put("menu", new DietMenu());
        return "diet/edit-diet-menu";
    }

    /**
     * 
     * @param menuItemId
     * @param model
     * @return
     */
    @RequestMapping(value = "/diet/menu/edit/{menuId}", method = RequestMethod.GET)
    public String initUpdateDietMenuItemForm(@PathVariable("menuId") int menuId, Model model) {
        DietMenu menu = dietManagerService.getDietMenu(menuId);
        if (menu == null) {
            throw new ApplicationException("Object Not Found, menuId=" + menuId);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(menu.toString());
        }
        model.addAttribute("menu", menu);
        return "diet/edit-diet-menu";
    }

    // ---------------------------------------------
    // New and update menu
    // ---------------------------------------------

    /**
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/diet/listmenus", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listMenus() {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }
        List<DietMenu> menus = dietManagerService.getMenus(loggedInUser.getId());
        if (menus == null) {
            throw new ApplicationException("No menus found for userId:" + loggedInUser.getId());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Number of menus: " + menus.size());
        }
        ModelAndView modelView = new ModelAndView("diet/list-menus-view");
        modelView.getModel().put("menus", menus);
        return modelView;
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/diet/menu/new", method = RequestMethod.POST)
    public String processNewDietMenuItemForm(@Valid @ModelAttribute("dietMenu") DietMenu menu, BindingResult result, SessionStatus status, Model model) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(menu.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "diet/edit-diet-menu";
        } else {
            int menuId = this.dietManagerService.saveDietMenu(menu);
            status.setComplete();
            return "redirect:/diet/menu/" + menuId;
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/diet/menu/edit/{menuId}", method = RequestMethod.PUT)
    public String processUpdateDietMenuItemForm(@Valid @ModelAttribute("menu") DietMenu menu, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(menu.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "diet/edit-diet-menu";
        } else {
            dietManagerService.saveDietMenu(menu);
            status.setComplete();
            return "redirect:/diet/menu/" + menu.getId();
        }
    }

    /**
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/diet/menu/gallery/{menuId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewMenuGallery(@PathVariable("menuId") String menuId) {
        ModelAndView view = viewMenu(menuId);
        view.setViewName("diet/view-diet-menu-gallery");
        return view;
    }

    /**
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "/diet/menu/{menuId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewMenu(@PathVariable("menuId") String menuId) {
        LocalUser loggedInUser = authenticationFacade.getLoggedInUser();
        if (loggedInUser == null) {
            throw new ApplicationException("Not logged in!");
        }

        Integer id = null;
        try {
            id = Integer.parseInt(menuId);
        } catch (Exception e) {
            // // ignore exception, set default menuId,
            // if (LOG.isWarnEnabled() ){
            // LOG.warn("Invalid id: " + id, e);
            // }
            // id = 1;
            throw new ApplicationException(e.getMessage());
        }
        DietMenu dietMenu = dietManagerService.getDietMenu(id);
        ModelAndView modelView = new ModelAndView("diet/view-diet-menu");
        modelView.getModel().put("userId", loggedInUser.getId());
        modelView.getModel().put("dietMenu", dietMenu);
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
