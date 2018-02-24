package com.gunnarro.dietmanager.endpoint.rest;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gunnarro.dietmanager.domain.diet.MenuItem;
import com.gunnarro.dietmanager.mvc.controller.AuthenticationFacade;
import com.gunnarro.dietmanager.service.DietManagerService;
import com.gunnarro.dietmanager.service.exception.ApplicationException;
import com.gunnarro.dietmanager.utility.Utility;

@RestController
@RequestMapping("/rest")
public class DietManagerRestEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(DietManagerRestEndpoint.class);

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @Autowired
    private DietManagerService dietManagerService;

    /**
     * default constructor, used by spring
     */
    public DietManagerRestEndpoint() {
    }

    /**
     * For unit testing only
     * 
     * @param sportsTeamService - inject as mock
     */
    public DietManagerRestEndpoint(DietManagerService sportsTeamService, AuthenticationFacade authenticationFacade) {
        this.dietManagerService = sportsTeamService;
        this.authenticationFacade = authenticationFacade;
    }

    @RequestMapping(value = "/menu/deregistrer/{userId}/{menuItemId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer menuSelectionDeRegistrer(@PathVariable("userId") Integer userId, @PathVariable("menuItemId") Integer menuItemId) {
        try {
            // delete only meal type for current date
            boolean isMealAlreadyRegistered = dietManagerService.checkIfSelectedMealAlreadyRegistered(userId, new Date(), menuItemId);
            if (!isMealAlreadyRegistered) {
                LOG.debug("Valgte menuItem id ({}) er ikke registrert i dag: {}", menuItemId,
                        Utility.formatTime(System.currentTimeMillis(), Utility.DATE_PATTERN));
                return dietManagerService.getSelectedFoodCountForUser(userId, menuItemId);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Delete selection userId=" + userId + ", menuItemId=" + menuItemId);
            }
            dietManagerService.deleteSelectedFoodForUser(userId, menuItemId);
            return dietManagerService.getSelectedFoodCountForUser(userId, menuItemId);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error! userId: " + userId + ", menuItemId:" + menuItemId);
        }
    }

    /**
     * 
     * @param userId
     * @param menuItemId
     * @return
     */
    @RequestMapping(value = "/menu/registrer/{userId}/{menuItemId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer menuSelectionRegistrer(@PathVariable("userId") Integer userId, @PathVariable("menuItemId") Integer menuItemId) {
        try {
            // Add only one meal type per day.
            boolean isMealAlreadyRegistered = dietManagerService.checkIfSelectedMealAlreadyRegistered(userId, new Date(), menuItemId);
            if (isMealAlreadyRegistered) {
                MenuItem tmpMenuItem = dietManagerService.getDietMenuItem(menuItemId);
                LOG.debug("{} er allerede registrert i dag: {} ", tmpMenuItem.getName(),
                        Utility.formatTime(tmpMenuItem.getCreatedTime(), Utility.DATE_PATTERN));
                return dietManagerService.getSelectedFoodCountForUser(userId, menuItemId);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Save selection userId=" + userId + ", menuItemId=" + menuItemId);
            }

            MenuItem menuItem = new MenuItem();
            menuItem.setId(menuItemId);
            menuItem.setCreatedDate(new Date());
            menuItem.setControlledByUserId(userId);
            menuItem.setPreparedByUserId(userId);
            menuItem.setCausedConflict(0);
            dietManagerService.saveSelectedFoodForUser(userId, menuItem);
            return dietManagerService.getSelectedFoodCountForUser(userId, menuItemId);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error! userId: " + userId + ", menuItemId:" + menuItemId);
        }
    }

}