package com.gunnarro.sportsteam.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.joda.time.DateTime;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Training;
import com.gunnarro.sportsteam.domain.party.Referee;
import com.gunnarro.sportsteam.domain.task.VolunteerTask;
import com.gunnarro.sportsteam.domain.view.ActivityView;
import com.gunnarro.sportsteam.service.exception.ApplicationException;
import com.gunnarro.sportsteam.utility.Utility;

@Controller
public class ActivityController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
    }

    @RequestMapping(value = "/listactivitiesplain/{teamId}/{season}", method = RequestMethod.GET)
    public ModelAndView listActivitiesPlain(@PathVariable("teamId") int teamId, @PathVariable("season") String seasonPeriod) {
        Team team = sportsTeamService.getTeam(teamId);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + teamId);
        }
        ModelAndView modelView = new ModelAndView("activity/list-activities");
        Season season = sportsTeamService.getSeason(seasonPeriod);
        List<ActivityView> upcomingActivities = sportsTeamService.getActivitiesUpcomingWeek(teamId);
        modelView.addObject("team", team);
        modelView.addObject("season", season);
        modelView.addObject("seasons", sportsTeamService.getSeasons());
        modelView.addObject("activityList", upcomingActivities);
        return modelView;
    }

    @RequestMapping(value = "/listactivities/{teamId}/{season}", method = RequestMethod.GET)
    public ModelAndView listActivities(@PathVariable("teamId") int teamId, @PathVariable("season") String seasonPeriod) {
        Team team = sportsTeamService.getTeam(teamId);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + teamId);
        }
        Season season = sportsTeamService.getSeason(seasonPeriod);
        List<ActivityView> upcomingActivities = sportsTeamService.getActivitiesUpcomingWeek(teamId);
        List<Match> matches = sportsTeamService.getMatches(team.getName(), team.getLeague().getId(), seasonPeriod);
        List<Training> trainings = sportsTeamService.getTrainings(teamId, seasonPeriod);
        List<Cup> cups = sportsTeamService.getCups(teamId, seasonPeriod);
        List<VolunteerTask> volunteerTasks = sportsTeamService.getVolunteerTasksForTeam(teamId, seasonPeriod);
        ModelAndView modelView = new ModelAndView("activity/tab-list-activities");
        modelView.addObject("team", team);
        modelView.addObject("season", season);
        modelView.addObject("seasons", sportsTeamService.getSeasons());
        modelView.addObject("upcomingActivityList", upcomingActivities);
        modelView.addObject("numberOfupcomingActivities", upcomingActivities.size());
        modelView.addObject("matchList", matches);
        modelView.addObject("numberOfMatches", matches.size());
        modelView.addObject("cupList", cups);
        modelView.addObject("numberOfCups", cups.size());
        modelView.addObject("trainingList", trainings);
        modelView.addObject("numberOfTrainings", trainings.size());
        modelView.addObject("volunteerTasks", volunteerTasks);
        modelView.addObject("numberOfVolunteerTasks", volunteerTasks.size());
        return modelView;
    }

    // ---------------------------------------------
    // New and update training
    // ---------------------------------------------

    @RequestMapping(value = "/training/new/{teamId}", method = RequestMethod.GET)
    public String initNewTrainingForm(@PathVariable("teamId") int teamId, Map<String, Object> model) {
        Team team = sportsTeamService.getTeam(teamId);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + teamId);
        }
        Training training = new Training();
        training.setFkTeamId(teamId);
        training.setStartDate(Utility.roundToClosestHour(training.getStartDate()));
        training.setEndDate(Utility.addMinutes(training.getStartDate(), Training.DEFAULT_TRAINING_TIME_MINUTES));
        training.setStartTime(Utility.formatTime(training.getStartDate().getTime(), "HH:mm"));
        training.setEndTime(Utility.formatTime(training.getEndDate().getTime(), "HH:mm"));
        training.setSeason(sportsTeamService.getCurrentSeason());
        training.setFkSeasonId(training.getSeason().getId());
        training.setVenue(team.getClub().getStadiumName());
        model.put("training", training);
        model.put("teamList", sportsTeamService.getTeams(team.getFkClubId()));
        return "activity/edit-training";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/training/new/{teamId}", method = RequestMethod.POST)
    public String processNewTrainingForm(@Valid @ModelAttribute("training") Training training, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "activity/edit-training";
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Status:" + status.toString());
                LOG.debug("Training: " + training);
            }

            this.sportsTeamService.saveTraining(training);
            // Create more trainings if requested
            if (LOG.isDebugEnabled()) {
                LOG.debug("Create number of trainings: " + training.getReiterations());
            }
            if (training.getReiterations() > 1) {
                DateTime dt = new DateTime(training.getStartDate());
                for (int i = 1; i <= training.getReiterations(); i++) {
                    dt = dt.plusDays(7);
                    Training newTraining = new Training();
                    newTraining.setStartDate(dt.toDate());
                    newTraining.setEndDate(dt.toDate());
                    newTraining.setVenue(training.getVenue());
                    newTraining.setFkSeasonId(training.getFkSeasonId());
                    newTraining.setFkTeamId(training.getFkTeamId());
                    sportsTeamService.saveTraining(newTraining);
                }
            }
            status.setComplete();
            return "redirect:/listactivities/" + training.getFkTeamId() + "/" + training.getFkSeasonId();
        }
    }

    /**
     * 
     * @param trainingId
     * @param model
     * @return
     */
    @RequestMapping(value = "/training/edit/{trainingId}", method = RequestMethod.GET)
    public String initUpdateTrainingForm(@PathVariable("trainingId") int trainingId, Model model) {
        Training training = sportsTeamService.getTraining(trainingId);
        if (training == null) {
            throw new ApplicationException("Object Not Found, trainingId=" + trainingId);
        }
        model.addAttribute(training);
        return "activity/edit-training";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/training/edit/{trainingId}", method = RequestMethod.PUT)
    public String processUpdateTrainingForm(@Valid @ModelAttribute("training") Training training, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(training.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "activity/edit-training";
        } else {
            sportsTeamService.saveTraining(training);
            status.setComplete();
            return "redirect:/listactivities/" + training.getFkTeamId() + "/" + training.getFkSeasonId();
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/training/delete/{trainingId}", method = RequestMethod.GET)
    public String deleteTraining(@PathVariable("trainingId") int trainingId) {
        Training training = sportsTeamService.getTraining(trainingId);
        if (training == null) {
            throw new ApplicationException("Object Not Found, trainingId=" + trainingId);
        }
        sportsTeamService.deleteTraining(trainingId);
        return "redirect:/listactivities/" + training.getFkTeamId() + "/" + training.getFkSeasonId();
    }

    // ---------------------------------------------
    // New and update match
    // ---------------------------------------------

    @RequestMapping(value = "/match/new/{teamId}", method = RequestMethod.GET)
    public String initNewMatchForm(@PathVariable("teamId") int teamId, Map<String, Object> model) {
        Team team = sportsTeamService.getTeam(teamId);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + teamId);
        }
        Match match = new Match();
        match.setLeague(team.getLeague());
        match.setFkTeamId(teamId);
        match.setHomeTeam(new Team());
        match.setAwayTeam(new Team());
        match.setReferee(new Referee());
        match.setSeason(sportsTeamService.getCurrentSeason());
        match.setFkSeasonId(match.getSeason().getId());
        model.put("match", match);
        model.put("matchStatusTypes", sportsTeamService.getMatchStatusTypes());
        model.put("teamList", sportsTeamService.getTeams(team.getFkClubId()));
        model.put("refereeList", sportsTeamService.getReferees(1));
        return "activity/edit-match";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/match/new/{teamId}", method = RequestMethod.POST)
    public String processNewMatchForm(@Valid @ModelAttribute("match") Match match, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(match.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "match/edit-match";
        } else {
            this.sportsTeamService.saveMatch(match);
            status.setComplete();
            return "redirect:/listactivities/" + match.getFkTeamId() + "/" + match.getFkSeasonId();
        }
    }

    @RequestMapping(value = "/match/edit/{matchId}", method = RequestMethod.GET)
    public String initUpdateMatchForm(@PathVariable("matchId") int matchId, Model model) {
        Match match = sportsTeamService.getMatch(matchId);
        if (match == null) {
            throw new ApplicationException("Object Not Found, matchId=" + matchId);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(match.toString());
        }
        model.addAttribute("match", match);
        model.addAttribute("matchStatusTypes", sportsTeamService.getMatchStatusTypes());
        return "activity/edit-match";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/match/edit/{matchId}", method = RequestMethod.PUT)
    public String processUpdateMatchForm(@Valid @ModelAttribute("match") Match match, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(match.toString());
        }
        if (result.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(result.toString());
            }
            return "activity/edit-match";
        } else {
            sportsTeamService.saveMatch(match);
            status.setComplete();
            if (match.getFkTeamId() != null && match.getFkTeamId() > 0) {
                return "redirect:/listactivities/" + match.getFkTeamId() + "/" + match.getFkSeasonId();
            }
            return "redirect:/listmatches/league/" + match.getFkLeagueId() + "/" + match.getFkSeasonId();
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/match/delete/{matchId}", method = RequestMethod.GET)
    public String deleteMatch(@PathVariable("matchId") int matchId) {
        Match match = sportsTeamService.getMatch(matchId);
        if (match == null) {
            throw new ApplicationException("Object Not Found, matchId=" + matchId);
        }
        sportsTeamService.deleteMatch(matchId);
        if (match.getFkTeamId() != null && match.getFkTeamId() > 0) {
            return "redirect:/listactivities/" + match.getFkTeamId() + "/" + match.getFkSeasonId();
        }
        return "/listmatches/league/" + match.getFkLeagueId() + "/" + match.getFkSeasonId();
    }

}
