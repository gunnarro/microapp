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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.task.SubTask;
import com.gunnarro.sportsteam.domain.task.TaskGroup;
import com.gunnarro.sportsteam.domain.task.VolunteerTask;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

@Controller
public class VolunteerController extends DefaultController {

    private static final Logger LOG = LoggerFactory.getLogger(VolunteerController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
    }

    @RequestMapping(value = "/listvolunteertasks/{clubId}/{season}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listVolunteerTasks(@PathVariable("clubId") int clubId, @PathVariable("season") String seasonPeriod) {
        Club club = sportsTeamService.getClub(clubId);
        if (club == null) {
            throw new ApplicationException("Object Not Found, clubId=" + clubId);
        }
        ModelAndView modelView = new ModelAndView("volunteertask/list-volunteer-tasks-view");
        Season season = sportsTeamService.getSeason(seasonPeriod);
        List<VolunteerTask> volunteerTasks = sportsTeamService.getVolunteerTasks(clubId, season.getPeriod());
        modelView.addObject("clubName", club.getFullName());
        modelView.addObject("season", season);
        modelView.addObject("volunteerTasks", volunteerTasks);
        return modelView;
    }

    @RequestMapping(value = "/volunteertask/{volunteerTaskId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewVolunteerTask(@PathVariable("volunteerTaskId") int volunteerTaskId) {
        VolunteerTask task = sportsTeamService.getVolunteerTask(volunteerTaskId);
        if (task == null) {
            throw new ApplicationException("Object Not found! volunteer task id: " + volunteerTaskId);
        }
        ModelAndView modelView = new ModelAndView("volunteertask/view-volunteer-task");
        modelView.addObject("volunteerTask", task);
        return modelView;
    }

    @RequestMapping(value = "/subtask/{subTaskId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewSubTask(@PathVariable("subTaskId") int subTaskId) {
        SubTask subTask = sportsTeamService.getSubTask(subTaskId);
        if (subTask == null) {
            throw new ApplicationException("Object Not found! sub task id: " + subTaskId);
        }
        ModelAndView modelView = new ModelAndView("volunteertask/view-sub-task");
        modelView.addObject("subTask", subTask);
        return modelView;
    }

    @RequestMapping(value = "/taskgroup/{taskGroupId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewTaskGroup(@PathVariable("taskGroupId") int taskGroupId) {
        TaskGroup taskGroup = sportsTeamService.getTaskGroup(taskGroupId);
        if (taskGroup == null) {
            throw new ApplicationException("Object Not found! task group id: " + taskGroupId);
        }
        ModelAndView modelView = new ModelAndView("volunteertask/view-task-group");
        modelView.addObject("taskGroup", taskGroup);
        return modelView;
    }

    // ---------------------------------------------
    // New, update and delete volunteer task
    // ---------------------------------------------

    @RequestMapping(value = "/volunteertask/new", method = RequestMethod.GET)
    public String initNewVolunteerTaskForm(Map<String, Object> model) {
        VolunteerTask task = new VolunteerTask();
        task.setSeason(sportsTeamService.getCurrentSeason());
        task.setFkSeasonId(task.getSeason().getId());
        task.setFkTaskStatusId(1);
        model.put("volunteerTask", task);
        model.put("statusOptions", sportsTeamService.getTaskStatuses());
        // model.put("clubList", sportsTeamService.getClubs());
        model.put("contactList", sportsTeamService.getContacts(1));
        if (LOG.isDebugEnabled()) {
            LOG.debug(task.toString());
        }
        return "volunteertask/edit-volunteer-task";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/volunteertask/new", method = RequestMethod.POST)
    public String processNewVolunteerTaskForm(@Valid @ModelAttribute("volunteerTask") VolunteerTask task, BindingResult validationResult, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(task.toString());
        }
        if (validationResult.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(validationResult.toString());
            }
            return "volunteertask/edit-volunteer-task";
        } else {
            this.sportsTeamService.saveVolunteerTask(task);
            status.setComplete();
            return "redirect:/listvolunteertasks/" + task.getFkClubId() + "/current";
        }
    }

    @RequestMapping(value = "/volunteertask/edit/{volunteerTaskId}", method = RequestMethod.GET)
    public String initUpdateVolunteerTaskForm(@PathVariable("volunteerTaskId") int volunteerTaskId, Model model) {
        VolunteerTask task = sportsTeamService.getVolunteerTask(volunteerTaskId);
        if (task == null) {
            throw new ApplicationException("Object Not Found, volunteerTaskId=" + volunteerTaskId);
        }
        model.addAttribute("volunteerTask", task);
        model.addAttribute("statusOptions", sportsTeamService.getTaskStatuses());
        model.addAttribute("contactList", sportsTeamService.getContacts(task.getFkClubId()));
        return "volunteertask/edit-volunteer-task";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/volunteertask/edit/{volunteerTaskId}", method = RequestMethod.PUT)
    public String processUpdateVolunteerTaskForm(@Valid @ModelAttribute("volunteertask") VolunteerTask task, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(task.toString());
        }
        if (result.hasErrors()) {
            return "volunteertask/edit-volunteer-task";
        } else {
            sportsTeamService.saveVolunteerTask(task);
            VolunteerTask tv = sportsTeamService.getVolunteerTask(task.getId());
            status.setComplete();
            return "redirect:/listvolunteertasks/" + tv.getFkClubId() + "/current";
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/volunteertask/delete/{volunteerTaskId}", method = RequestMethod.GET)
    public String deleteVolunteerTask(@PathVariable("volunteerTaskId") int volunteerTaskId) {
        VolunteerTask task = sportsTeamService.getVolunteerTask(volunteerTaskId);
        if (task == null) {
            throw new ApplicationException("Object Not found! volunteer task id: " + volunteerTaskId);
        }
        sportsTeamService.deleteVolunteerTask(volunteerTaskId);
        return "redirect:/listvolunteertasks/" + task.getFkClubId() + "/current";
    }

    // ---------------------------------------------
    // New, update and delete sub task
    // ---------------------------------------------

    @RequestMapping(value = "/subtask/new/{parentTaskId}", method = RequestMethod.GET)
    public String initNewSubTaskForm(@PathVariable("parentTaskId") int parentTaskId, Map<String, Object> model) {
        VolunteerTask volunteerTask = sportsTeamService.getVolunteerTask(parentTaskId);
        if (volunteerTask == null) {
            throw new ApplicationException("Object Not found! voluenteer task id: " + parentTaskId);
        }
        SubTask subTask = new SubTask();
        subTask.setFkParentTaskId(volunteerTask.getId());
        subTask.setParentTask(volunteerTask);
        subTask.setStartDate(volunteerTask.getStartDate());
        subTask.setFkTaskStatusId(1);
        model.put("subTask", subTask);
        model.put("statusOptions", sportsTeamService.getTaskStatuses());
        // model.put("teamList",
        // sportsTeamService.getTeams(volunteerTask.getId()));
        model.put("contactList", sportsTeamService.getContacts(1));
        if (LOG.isDebugEnabled()) {
            LOG.debug(subTask.toString());
        }
        return "volunteertask/edit-sub-task";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/subtask/new/{parentTaskId}", method = RequestMethod.POST)
    public String processNewSubTaskForm(@PathVariable("parentTaskId") int parentTaskId, @Valid @ModelAttribute("subTask") SubTask subTask,
            BindingResult validationResult, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(subTask.toString());
        }
        if (validationResult.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(validationResult.toString());
            }
            return "volunteertask/edit-sub-task";
        } else {
            this.sportsTeamService.saveSubTask(subTask);
            status.setComplete();
            return "redirect:/volunteertask/" + parentTaskId;
        }
    }

    @RequestMapping(value = "/subtask/edit/{subTaskId}", method = RequestMethod.GET)
    public String initUpdateSubTaskForm(@PathVariable("subTaskId") int subTaskId, Model model) {
        SubTask subTask = sportsTeamService.getSubTask(subTaskId);
        if (subTask == null) {
            throw new ApplicationException("Object Not Found! subTaskId=" + subTaskId);
        }
        model.addAttribute("subTask", subTask);
        model.addAttribute("statusOptions", sportsTeamService.getTaskStatuses());
        model.addAttribute("contactList", sportsTeamService.getContacts(subTask.getParentTask().getFkClubId()));
        if (LOG.isDebugEnabled()) {
            LOG.debug(subTask.toString());
        }
        return "volunteertask/edit-sub-task";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/subtask/edit/{subTaskId}", method = RequestMethod.PUT)
    public String processUpdateSubTaskForm(@Valid @ModelAttribute("subtask") SubTask subTask, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(subTask.toString());
        }
        if (result.hasErrors()) {
            return "volunteertask/edit-sub-task";
        } else {
            sportsTeamService.saveSubTask(subTask);
            status.setComplete();
            return "redirect:/volunteertask/" + subTask.getFkParentTaskId();
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/subtask/delete/{subTaskId}", method = RequestMethod.GET)
    public String deleteSubTask(@PathVariable("subTaskId") int subTaskId) {
        SubTask subTask = sportsTeamService.getSubTask(subTaskId);
        if (subTask == null) {
            throw new ApplicationException("Object Not found! sub task id: " + subTaskId);
        }
        sportsTeamService.deleteSubTask(subTaskId);
        return "redirect:/volunteertask/" + subTask.getFkParentTaskId();
    }

    // ---------------------------------------------
    // New, update and delete task group
    // ---------------------------------------------

    @RequestMapping(value = "/taskgroup/new", method = RequestMethod.GET)
    public String initNewTaskGroupForm(Map<String, Object> model) {
        TaskGroup taskGroup = new TaskGroup();
        model.put("taskGroup", taskGroup);
        if (LOG.isDebugEnabled()) {
            LOG.debug(taskGroup.toString());
        }
        return "volunteertask/edit-task-group";
    }

    /**
     * User POST for new
     * 
     */
    @RequestMapping(value = "/taskgroup/new", method = RequestMethod.POST)
    public String processNewTaskGroupForm(@Valid @ModelAttribute("taskGroup") TaskGroup taskGroup, BindingResult validationResult, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(taskGroup.toString());
        }
        if (validationResult.hasErrors()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(validationResult.toString());
            }
            return "volunteertask/edit-task-group";
        } else {
            this.sportsTeamService.saveTaskGroup(taskGroup);
            status.setComplete();
            return "redirect:/volunteertask/";
        }
    }

    @RequestMapping(value = "/taskgroup/edit/{taskGroupId}", method = RequestMethod.GET)
    public String initUpdateTaskGroupForm(@PathVariable("taskGroupId") int taskGroupId, Model model) {
        TaskGroup taskGroup = sportsTeamService.getTaskGroup(taskGroupId);
        if (taskGroup == null) {
            throw new ApplicationException("Object Not Found! subTaskId=" + taskGroupId);
        }
        model.addAttribute(taskGroup);
        if (LOG.isDebugEnabled()) {
            LOG.debug(taskGroup.toString());
        }
        return "volunteertask/edit-task-group";
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/taskgroup/edit/{taskGroupId}", method = RequestMethod.PUT)
    public String processUpdateTaskGroupForm(@Valid @ModelAttribute("taskgroup") TaskGroup taskGroup, BindingResult result, SessionStatus status) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(taskGroup.toString());
        }
        if (result.hasErrors()) {
            return "volunteertask/edit-task-group";
        } else {
            sportsTeamService.saveTaskGroup(taskGroup);
            status.setComplete();
            return "redirect:volunteertask/view-volunteer-task";
        }
    }

    /**
     * Use PUT for updates
     * 
     */
    @RequestMapping(value = "/taskgroup/delete/{taskGroupId}", method = RequestMethod.GET)
    public String deleteTaskGroup(@PathVariable("taskGroupId") int taskGroupId) {
        TaskGroup taskGroup = sportsTeamService.getTaskGroup(taskGroupId);
        if (taskGroup == null) {
            throw new ApplicationException("Object Not found! task group id: " + taskGroupId);
        }
        sportsTeamService.deleteTaskGroup(taskGroupId);
        return "redirect:/volunteertask/";
    }

}
