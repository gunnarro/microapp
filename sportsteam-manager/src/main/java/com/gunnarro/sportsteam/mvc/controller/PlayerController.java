package com.gunnarro.sportsteam.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.party.Contact.GenderEnum;
import com.gunnarro.sportsteam.domain.party.Player;
import com.gunnarro.sportsteam.domain.statistic.Statistic;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@Controller
@SessionAttributes(types = Player.class)
public class PlayerController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
    }

    @RequestMapping(value = "/listplayers/{teamId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listPlayers(@PathVariable("teamId") int teamId) {
        ModelAndView modelView = new ModelAndView("player/list-players");
        List<Player> players = sportsTeamService.getPlayers(teamId);
        Team team = sportsTeamService.getTeam(teamId);
        modelView.addObject("team", team);
        modelView.addObject("playerList", players);
        return modelView;
    }

    @RequestMapping(value = "/player/{playerId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewPlayer(@PathVariable("playerId") int playerId) {
        Player player = sportsTeamService.getPlayer(playerId);
        if (player == null) {
            throw new ApplicationException("Object Not Found, playerId=" + playerId);
        }
        Statistic playerStatistic = sportsTeamService.getPlayerStatistic(player.getFkTeamId(), playerId, sportsTeamService.getCurrentSeason().getId());
        ModelAndView modelView = new ModelAndView("player/view-player");
        modelView.addObject("player", player);
        modelView.addObject("playerStatistic", playerStatistic);
        return modelView;
    }

    // ---------------------------------------------
    // New and update player
    // ---------------------------------------------

    @RequestMapping(value = "/player/new/{teamId}", method = RequestMethod.GET)
    public String initNewPlayerForm(@PathVariable("teamId") int teamId, Map<String, Object> model) {
        Team team = sportsTeamService.getTeam(teamId);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + teamId);
        }
        Player player = new Player();
        player.setFkTeamId(team.getId());
        model.put("player", player);
        model.put("team", team);
        model.put("statusOptions", sportsTeamService.getPlayerStatusTypes());
        model.put("playerPositionOptions", sportsTeamService.getPlayerPositionTypes());
        model.put("genderOptions", GenderEnum.asArray());
        return "player/edit-player";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/player/new/{teamId}", method = RequestMethod.POST)
    public String processNewPlayerForm(@Valid @ModelAttribute("player") Player player, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "player/edit-player";
        } else {
            this.sportsTeamService.savePlayer(player);
            status.setComplete();
            return "redirect:/listplayers/" + player.getFkTeamId();
        }
    }

    @RequestMapping(value = "/player/edit/{playerId}", method = RequestMethod.GET)
    public String initUpdatePlayerForm(@PathVariable("playerId") int playerId, Model model) {
        Player player = sportsTeamService.getPlayer(playerId);
        if (player == null) {
            throw new ApplicationException("Object Not Found, playerId=" + playerId);
        }
        model.addAttribute("player", player);
        model.addAttribute("team", player.getTeam());
        model.addAttribute("statusOptions", sportsTeamService.getPlayerStatusTypes());
        model.addAttribute("genderOptions", GenderEnum.asArray());
        model.addAttribute("playerPositionOptions", sportsTeamService.getPlayerPositionTypes());
        return "player/edit-player";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/player/edit/{playerId}", method = RequestMethod.PUT)
    public String processUpdatePlayerForm(@Valid @ModelAttribute("player") Player player, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            LOG.error(null, player);
            return "player/edit-player";
        } else {
            sportsTeamService.savePlayer(player);
            status.setComplete();
            return "redirect:/listplayers/" + player.getFkTeamId();
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/player/delete/{playerId}", method = RequestMethod.GET)
    public String deletePlayer(@PathVariable("playerId") int playerId) {
        Player player = sportsTeamService.getPlayer(playerId);
        if (player == null) {
            throw new ApplicationException("Object Not Found, playerId=" + playerId);
        }
        sportsTeamService.deletePlayer(playerId);
        return "redirect:/listplayers/" + player.getFkTeamId();
    }
}
