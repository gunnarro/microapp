package com.gunnarro.sportsteam.mvc.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.view.SearchActivity;
import com.gunnarro.sportsteam.domain.view.list.Item;
import com.gunnarro.sportsteam.service.exception.ApplicationException;
import com.gunnarro.sportsteam.utility.Utility;

@Controller
@SessionAttributes(types = SearchActivity.class)
public class MatchController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(MatchController.class);

    @RequestMapping(value = "/deletematch/{matchId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView deleteMatch(@PathVariable("matchId") int matchId) {
        ModelAndView modelView = new ModelAndView("match/delete-match");
        sportsTeamService.deleteMatch(matchId);
        return modelView;
    }

    @RequestMapping(value = "/listmatches/league/{leagueId}/{season}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView listMatchesByLeague(@PathVariable("leagueId") Integer leagueId, @PathVariable("season") String seasonPeriod) {
        Season season = sportsTeamService.getSeason(seasonPeriod);
        if (season == null) {
            season = sportsTeamService.getCurrentSeason();
        }
        List<Type> leagueTypes = sportsTeamService.getLeagueTypes(season.getId());
        Match[] matches = null;
        String selectedLeagueName = "All Leagues";
        if (leagueId == 0) {
            matches = sportsTeamService.searchMatches(season.getPeriod(), season.getStartTimeDate(), season.getEndTimeDate());
        } else {
            League league = sportsTeamService.getLeague(leagueId);
            selectedLeagueName = league.getName();
            matches = sportsTeamService.getMatchesByLeague(league, seasonPeriod);
        }
        ModelAndView modelView = new ModelAndView("match/list-matches-view");
        modelView.getModel().put("season", season);
        modelView.getModel().put("leagueTypes", leagueTypes);
        modelView.getModel().put("selectedLeagueName", selectedLeagueName);
        modelView.getModel().put("selectedLeagueId", leagueId);
        modelView.getModel().put("matchList", matches);
        return modelView;
    }

    @RequestMapping(value = "/listmatches/team/{team}/{league}/{season}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView listMatchesByTeamName(@PathVariable("team") String teamName, @PathVariable("league") String leagueName,
            @PathVariable("season") String seasonPeriod) {
        Season season = sportsTeamService.getSeason(seasonPeriod);
        if (season == null) {
            season = sportsTeamService.getCurrentSeason();
        }
        League league = sportsTeamService.getLeague("BANDY", leagueName);
        List<Match> matches = sportsTeamService.getMatchesByTeamName(teamName, league, seasonPeriod);
        ModelAndView modelView = new ModelAndView("match/list-matches-view");
        modelView.getModel().put("season", season);
        modelView.getModel().put("league", league);
        modelView.getModel().put("matchList", matches);
        return modelView;
    }

    @RequestMapping(value = "/listmatches/{clubId}/{season}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView listMatchesForClub(@PathVariable("clubId") Integer clubId, @PathVariable("season") String seasonPeriod) {
        Season season = sportsTeamService.getSeason(seasonPeriod);
        if (season == null) {
            season = sportsTeamService.getCurrentSeason();
        }
        List<Match> matches = sportsTeamService.getMatchesForClub(clubId, seasonPeriod);
        ModelAndView modelView = new ModelAndView("match/list-matches-view");
        modelView.getModel().put("season", season);
        modelView.getModel().put("leagueTypes", sportsTeamService.getLeagueTypes(season.getId()));
        modelView.getModel().put("selectedLeagueName", "All");
        modelView.getModel().put("selectedLeagueId", 0);
        modelView.getModel().put("matchList", matches);
        return modelView;
    }

    @RequestMapping(value = "/listmatches/{team}/{league}/{season}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView listMatchesForTeam(@PathVariable("team") String teamName, @PathVariable("league") String leagueDepartmentName,
            @PathVariable("season") String seasonPeriod) {
        Season season = sportsTeamService.getSeason(seasonPeriod);
        if (season == null) {
            season = sportsTeamService.getCurrentSeason();
        }
        League league = sportsTeamService.getLeague("BANDY", leagueDepartmentName);
        List<Match> matches = sportsTeamService.getMatchesByTeamName(teamName, league, seasonPeriod);
        ModelAndView modelView = new ModelAndView("match/list-matches-view");
        modelView.getModel().put("selectedfilterBy", "All");
        modelView.getModel().put("season", season);
        modelView.getModel().put("league", league);
        modelView.getModel().put("matchList", matches);
        return modelView;
    }

    @RequestMapping(value = "/listmatches/search/{leagueId}/{season}/{teamId}/{filterBy}/{fromdate}/{todate}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView searchMatches(@PathVariable("leagueId") Integer leagueId, @PathVariable("season") String seasonPeriod,
            @PathVariable("teamId") Integer teamId, @PathVariable("filterBy") String filterBy, @PathVariable("fromdate") String fromDate, @PathVariable("todate") String toDate) {

        String selectedFromDate = fromDate;
        String selectedToDate = toDate;

        if (LOG.isDebugEnabled()) {
            LOG.debug(leagueId + ", " + seasonPeriod + ", " + teamId + ", " + filterBy + ", " + fromDate + ", " + toDate);
        }

        Match[] matches = null;
        String selectedLeagueName = "All Leagues";
        if (filterBy != null && "todays".equalsIgnoreCase(filterBy)) {
            LOG.debug("....todays");
            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("GMT"));
            cal.setTime(new Date());
            matches = sportsTeamService.searchMatches(seasonPeriod, cal.getTime(), cal.getTime());
            selectedFromDate = Utility.formatTime(cal.getTimeInMillis(), "yyyy-MM-dd");
            selectedToDate = selectedFromDate;
        } else if (filterBy != null && "upcoming".equalsIgnoreCase(filterBy)) {
            Date today = new Date();
            Calendar calFrom = Calendar.getInstance();
            calFrom.setTimeZone(TimeZone.getTimeZone("GMT"));
            calFrom.setTime(today);
            Calendar calTo = Calendar.getInstance();
            calTo.setTimeZone(TimeZone.getTimeZone("GMT"));
            calTo.setTime(today);
            calTo.add(Calendar.DATE, 7);
            matches = sportsTeamService.searchMatches(seasonPeriod, calFrom.getTime(), calTo.getTime());
            LOG.debug(matches + "....upcoming");
            selectedFromDate = Utility.formatTime(calFrom.getTimeInMillis(), "yyyy-MM-dd");
            selectedToDate = Utility.formatTime(calTo.getTimeInMillis(), "yyyy-MM-dd");
        } else if (leagueId == 0) {
            matches = sportsTeamService.searchMatches(seasonPeriod, Utility.timeToDate(fromDate, "yyyy-MM-dd"), Utility.timeToDate(toDate, "yyyy-MM-dd"));
        } else {
            League league = sportsTeamService.getLeague(leagueId);
            selectedLeagueName = league.getName();
            matches = sportsTeamService.getMatchesByLeague(league, seasonPeriod);
        }

        Season season = sportsTeamService.getSeason(seasonPeriod);
        if (season == null) {
            season = sportsTeamService.getCurrentSeason();
        }
        List<Type> leagueTypes = sportsTeamService.getLeagueTypes(season.getId());
        ModelAndView modelView = new ModelAndView("match/list-matches-view");
        modelView.getModel().put("season", season);
        modelView.getModel().put("leagueTypes", leagueTypes);
        modelView.getModel().put("selectedLeagueName", selectedLeagueName);
        modelView.getModel().put("selectedLeagueId", leagueId);
        modelView.getModel().put("selectedfilterBy", filterBy);
        modelView.getModel().put("selectedFromDate", selectedFromDate);
        modelView.getModel().put("selectedToDate", selectedToDate);
        modelView.getModel().put("matchList", matches);
        return modelView;
    }

    @RequestMapping("/match/{matchId}")
    public ModelAndView viewMatch(@PathVariable("matchId") int matchId, Map<String, Object> model) {
        Match match = sportsTeamService.getMatch(matchId);
        if (match == null) {
            throw new ApplicationException("BUG: No match found for Id:" + matchId);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(match.toString());
        }
        List<Item> playerList = new ArrayList<Item>();
        if (match.getTeam() != null) {
            playerList = sportsTeamService.getMatchSignedPlayerList(match.getTeam().getId(), match.getId());
        }
        ModelAndView modelView = new ModelAndView("match/view-match");
        modelView.getModel().put("match", match);
        modelView.getModel().put("playerList", playerList);
        return modelView;
    }

}
