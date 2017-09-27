package com.gunnarro.sportsteam.task;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.task.ScheduledTask;
import com.gunnarro.sportsteam.endpoint.rest.RestApplicationException;
import com.gunnarro.sportsteam.service.MessageService;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;


public class UpcomingActivityNotificationTask {

    private static final Logger LOG = LoggerFactory.getLogger(UpcomingActivityNotificationTask.class);

    @Autowired
    private SportsTeamService sportsTeamService;

    
    @Autowired
    private MessageService messageService;

    
    public void run() {
        // FIXME: Have to be called as admin!
        List<ScheduledTask> teamScheduledTasks = sportsTeamService.getTeamScheduledTasks();
        if (LOG.isInfoEnabled()) {
            LOG.info("Number of scheduled tasks: " + teamScheduledTasks.size());
        }
        for (ScheduledTask task : teamScheduledTasks) {
            if (task.isEnabled()) {
                String[] to = new String[] { "gunnar_ronneberg@yahoo.no" };// sportsTeamService.getContactEmails(teamId);
                Team team = sportsTeamService.getTeam(task.getTeamId());
                String subject = team.getClub().getClubNameAbbreviation() + " " + team.getName() + " Agenda for uke "
                        + Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
                String msg = sportsTeamService.composeEmail(team);
                if (LOG.isInfoEnabled()) {
                    LOG.info(msg.toString());
                }
                try {
                    boolean isSent = messageService.sendEmail(to, subject, msg.toString());
                    if (isSent) {
                        LOG.info("submitted: " + subject);
                    } else {
                        LOG.info("Failed sending email: " + subject);
                    }
                } catch (ApplicationException ae) {
                    LOG.error(null, ae);
                    throw new RestApplicationException("Error sending email! teamId:" + task.getTeamId());
                }
            }
        }
    }

    /**
     * For mocking unit test only
     * 
     * @param sportsTeamService
     */
    public void setSportsTeamService(SportsTeamService sportsTeamService) {
        this.sportsTeamService = sportsTeamService;
    }

    /**
     * For mocking unit test only
     * 
     * @param messageService
     */
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
    
    

}
