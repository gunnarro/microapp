package com.gunnarro.sportsteam.mvc.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Training;
import com.gunnarro.sportsteam.domain.view.list.Item;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@Controller
public class TrainingController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(TrainingController.class);

    @RequestMapping(value = "/listtrainings/{teamId}/{season}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView listTrainings(@PathVariable("teamId") int teamId, @PathVariable("season") String season) {
        ModelAndView modelView = new ModelAndView("match/list-trainings");
        Team team = sportsTeamService.getTeam(teamId);
        List<Training> trainings = sportsTeamService.getTrainings(teamId, season);
        modelView.addObject("team", team);
        modelView.addObject("season", season);
        modelView.addObject("trainingList", trainings);
        return modelView;
    }

    @RequestMapping("/training/{trainingId}")
    public ModelAndView viewTraning(@PathVariable("trainingId") int trainingId, Map<String, Object> model) {
        Training training = sportsTeamService.getTraining(trainingId);
        if (training == null) {
            throw new ApplicationException("BUG: No training found for Id:" + trainingId);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(training.toString());
        }
        ModelAndView modelView = new ModelAndView("training/view-training");
        modelView.getModel().put("training", training);
        List<Item> playerList = sportsTeamService.getTrainingSignedPlayerList(training.getTeam().getId(), trainingId);
        modelView.getModel().put("playerList", playerList);
        return modelView;
    }
}
