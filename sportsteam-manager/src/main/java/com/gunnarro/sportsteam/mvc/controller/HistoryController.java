package com.gunnarro.sportsteam.mvc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.SeasonStatistic;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@Controller
@SessionAttributes(types = Club.class)
public class HistoryController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(HistoryController.class);

    @RequestMapping(value = "/listseasons", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listSeasons() {
        ModelAndView modelView = new ModelAndView("history/list-seasons");
        List<Season> seasonList = sportsTeamService.getSeasons();
        modelView.addObject("seasonList", seasonList);
        return modelView;
    }

    @RequestMapping(value = "/listseasonstatistic", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listSeasonStatistic() {
        ModelAndView modelView = new ModelAndView("history/list-season-statistic");
        List<SeasonStatistic> list = sportsTeamService.getSeasonStatistics();
        modelView.addObject("seasonStatisticList", list);
        return modelView;
    }

    @ResponseBody
    public String deleteSeason(@PathVariable("seasonId") int seasonId) {
        Season season = sportsTeamService.getSeason(seasonId);
        if (season == null) {
            throw new ApplicationException("Object Not Found, seasonId=" + seasonId);
        }
        sportsTeamService.deleteSeason(seasonId);
        return "redirect:/listseasons";
    }
}
