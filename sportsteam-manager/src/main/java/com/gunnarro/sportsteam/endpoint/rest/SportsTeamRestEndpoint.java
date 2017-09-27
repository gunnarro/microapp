package com.gunnarro.sportsteam.endpoint.rest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.SendMailResponse;
import com.gunnarro.sportsteam.domain.SendSMSResponse;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.message.Sms;
import com.gunnarro.sportsteam.service.MessageService;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;
import com.gunnarro.sportsteam.utility.Utility;

@RestController
@RequestMapping("/rest/sportsteam")
public class SportsTeamRestEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(SportsTeamRestEndpoint.class);

    @Autowired
    @Qualifier("sportsTeamService")
    private SportsTeamService sportsTeamService;
    
    @Autowired
    @Qualifier("messageService")
    private MessageService messageService;

    /**
     * default constructor, used by spring
     */
    public SportsTeamRestEndpoint() {
    }

    /**
     * For unit testing only
     * 
     * @param sportsTeamService - inject as mock
     */
    public SportsTeamRestEndpoint(SportsTeamService sportsTeamService, MessageService messageSerive) {
        this.sportsTeamService = sportsTeamService;
        this.messageService = messageSerive;
    }

    /**
     * MediaType.APPLICATION_JSON_VALUE
     * 
     * @return
     */
    @RequestMapping(value = "/clubs", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public List<Club> listAllClubs() {
        try {
            return sportsTeamService.getClubs();
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/club/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Club findClub(@PathVariable("id") Integer id) {
        try {
            if (id == null) {
                throw new RestApplicationException(new RestError(HttpStatus.NOT_FOUND.name(), "Missing parameter clubId!"));
            }
            Club club = sportsTeamService.getClub(id);
            if (club == null) {
                throw new RestApplicationException(new RestError(HttpStatus.NOT_FOUND.name(), "Club not found for id: " + id));
            }
            return club;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param teamId
     * @param season
     * @return
     */
    @RequestMapping(value = "/matches/{teamId}/{season}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public List<Match> listAllMatches(@PathVariable("teamId") Integer teamId, @PathVariable("season") String season) {
        try {
            return sportsTeamService.getMatches(teamId, season);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param matchId
     * @return
     */
    @RequestMapping(value = "/match/{matchId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Match findMatch(@PathVariable("matchId") Integer matchId) {
        try {
            if (matchId == null) {
                throw new RestApplicationException("Missing parameter matchId!");
            }
            Match match = sportsTeamService.getMatch(matchId);
            if (match == null) {
                throw new RestApplicationException("Object not found! id=" + matchId);
            }
            return match;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param activityType
     * @param activityId
     * @param playerId
     * @return
     */
    @RequestMapping(value = "/activity/registrer/{activityType}/{activityId}/{playerId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer activityRegistrer(@PathVariable("activityType") String activityType, @PathVariable("activityId") Integer activityId, @PathVariable("playerId") Integer playerId) {
        try {
            Integer numberOfSignedPlayers = null;
            if ("match".equalsIgnoreCase(activityType)) {
                numberOfSignedPlayers = sportsTeamService.signupForMatch(playerId, activityId);
            } else if ("cup".equalsIgnoreCase(activityType)) {
                numberOfSignedPlayers = sportsTeamService.signupForCup(playerId, activityId);
            } else if ("training".equalsIgnoreCase(activityType)) {
                numberOfSignedPlayers = sportsTeamService.signupForTraining(playerId, activityId);
            } else {
                throw new RestApplicationException("Invalid activity type: " + activityType);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("type=" + activityType + ", activityId=" + activityId + ", playerId=" + playerId + ", number of registrered players" + numberOfSignedPlayers);
            }
            return numberOfSignedPlayers;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error! type: " + activityType + ", id:" + activityId + ", playerId:" + playerId);
        }
    }

    /**
     * 
     * @param activityType
     * @param activityId
     * @param playerId
     * @return
     */
    @RequestMapping(value = "/activity/deregistrer/{activityType}/{activityId}/{playerId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer activityDeRegistrer(@PathVariable("activityType") String activityType, @PathVariable("activityId") Integer activityId, @PathVariable("playerId") Integer playerId) {
        try {
            Integer numberOfSignedPlayers = null;
            if ("match".equalsIgnoreCase(activityType)) {
                numberOfSignedPlayers = sportsTeamService.unsignForMatch(playerId, activityId);
            } else if ("cup".equalsIgnoreCase(activityType)) {
                numberOfSignedPlayers = sportsTeamService.unsignForCup(playerId, activityId);
            } else if ("training".equalsIgnoreCase(activityType)) {
                numberOfSignedPlayers = sportsTeamService.unsignForTraining(playerId, activityId);
            } else {
                throw new RestApplicationException("Invalid activity type: " + activityType);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("type=" + activityType + ", activityId=" + activityId + ", playerId=" + playerId + ", number of registrered players" + numberOfSignedPlayers);
            }
            return numberOfSignedPlayers;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error! type: " + activityType + ", id:" + activityId + ", playerId:" + playerId);
        }
    }

    /**
     * 
     * @param cupId
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/cup/registrerteam/{cupId}/{teamId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer cupRegistrerTeam(@PathVariable("cupId") Integer cupId, @PathVariable("teamId") Integer teamId) {
        try {
            Integer numberOfRegisteredTeam = null;
            if (LOG.isDebugEnabled()) {
                LOG.debug("cupId=" + cupId + ", teamId=" + teamId + ", number of registrered teams: " + numberOfRegisteredTeam);
            }
            numberOfRegisteredTeam = sportsTeamService.cupRegistrerTeam(cupId, teamId);
            return numberOfRegisteredTeam;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error!  cupId:" + cupId + ", teamId:" + teamId);
        }
    }

    /**
     * 
     * @param cupId
     * @param teamId
     * @return
     */
    @RequestMapping(value = "/cup/unregistrerteam/{cupId}/{teamId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer cupUnRegistrerTeam(@PathVariable("cupId") Integer cupId, @PathVariable("teamId") Integer teamId) {
        try {
            Integer numberOfRegisteredTeam = null;
            if (LOG.isDebugEnabled()) {
                LOG.debug("cupId=" + cupId + ", teamId=" + teamId + ", number of registrered teams: " + numberOfRegisteredTeam);
            }
            numberOfRegisteredTeam = sportsTeamService.cupUnRegistrerTeam(cupId, teamId);
            return numberOfRegisteredTeam;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Application error!  cupId:" + cupId + ", teamId:" + teamId);
        }
    }

    /**
     * 
     * @param playerId
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/player/addparent/{playerId}/{parentId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer playerAddParent(@PathVariable("playerId") Integer playerId, @PathVariable("parentId") Integer parentId) {
        try {
            return sportsTeamService.playerAddParent(playerId, parentId);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param playerId
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/player/removeparent/{playerId}/{parentId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer playerRemoveParent(@PathVariable("playerId") Integer playerId, @PathVariable("parentId") Integer parentId) {
        try {
            return sportsTeamService.playerRemoveParent(playerId, parentId);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param playerId
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/subtask/assign/{taskId}/{contactId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer assignSubTaskToPerson(@PathVariable("taskId") Integer taskId, @PathVariable("contactId") Integer contactId) {
        try {
            return sportsTeamService.assignSubTaskToPerson(taskId, contactId);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param playerId
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/subtask/unsign/{taskId}/{contactId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer unsignSubTaskToPerson(@PathVariable("taskId") Integer taskId, @PathVariable("contactId") Integer contactId) {
        try {
            return sportsTeamService.unsignPersonFromSubTask(taskId, contactId);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param playerId
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/task/assign/{taskId}/{contactId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer assignTaskToPerson(@PathVariable("taskId") Integer taskId, @PathVariable("contactId") Integer contactId) {
        try {
            return sportsTeamService.assignTaskToPerson(taskId, contactId);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param playerId
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/task/unsign/{taskId}/{contactId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer unsignTaskToPerson(@PathVariable("taskId") Integer taskId, @PathVariable("contactId") Integer contactId) {
        try {
            return sportsTeamService.unsignPersonFromTask(taskId, contactId);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param playerId
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/activity/sendmail/{teamId}/{season}/{activityType}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public SendMailResponse sendMail(@PathVariable("teamId") Integer teamId, @PathVariable("season") String seasonPeriod, @PathVariable("activityType") String activityType) {
        String[] to = new String[] { "gunnar_ronneberg@yahoo.no" };// sportsTeamService.getContactEmails(teamId);
        Team team = sportsTeamService.getTeam(teamId);
        String subject = team.getClub().getClubNameAbbreviation() + " " + team.getName() + " Agenda for uke " + Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        String msg = sportsTeamService.composeEmail(team);
        if (LOG.isInfoEnabled()) {
            LOG.info(msg.toString());
        }

        if (LOG.isInfoEnabled()) {
            LOG.info(msg.toString());
        }
        try {
            boolean isSent = messageService.sendEmail(to, subject, msg.toString());
            if (isSent) {
                LOG.info("submitted: " + subject);
                return new SendMailResponse(Utility.formatTime(System.currentTimeMillis(), "EEE dd.MM.yyyy hh:mm") + " Sent email: " + subject);
            } else {
                LOG.info("Failed sending email: " + subject);
                SendMailResponse resp = new SendMailResponse();
                resp.setError(new RestError("4000", "Failed sending email!"));
                return resp;
            }
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        } catch (ApplicationException ae) {
            LOG.error(null, ae);
            throw new RestApplicationException("Error sending email! type:" + activityType + ", teamId:" + teamId);
        }
    }

    /**
     * 
     * @param playerId
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/scheduledtask/stop/{taskId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer stopScheduledTask(@PathVariable("taskId") Integer taskId) {
        try {
            return sportsTeamService.enableScheduledTask(taskId, false);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param playerId
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/scheduledtask/start/{taskId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer startScheduledTask(@PathVariable("taskId") Integer taskId) {
        try {
            return sportsTeamService.enableScheduledTask(taskId, true);
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    /**
     * 
     * @param playerId
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/scheduledtask/run/{taskId}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
    @ResponseBody
    public Integer runScheduledTask(@PathVariable("taskId") Integer taskId) {
        try {
            return -1;
        } catch (AccessDeniedException e) {
            LOG.error("", e);
            throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
        }
    }

    // FIXME hard coded
  @RequestMapping(value = "/calendar/sendsms/{eventId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json", headers = "content-type=application/json")
  @ResponseBody
  public SendSMSResponse sendSMS(@PathVariable("eventId") Integer eventId) {
      try {
//          CalendarEvent calendarEvent = calendarService.getCalendarEvent(eventId);
//          if (calendarEvent != null) {
              List<String> phoneNumbers = Arrays.asList("12345678");//calendarService.getFollowersPhoneNumbers(eventId);
              String smsMsg = "TEST SMS!";//calendarEvent.getSummary() + "\nHilsen SporTsTeam";
              Sms sms = new Sms(phoneNumbers.toArray(new String[phoneNumbers.size()]), "sportsteam", smsMsg);
              boolean isOk = messageService.sendSMS(sms);
              return new SendSMSResponse(Boolean.toString(isOk));
      //    } else {
        //      return new SendSMSResponse("ERROR: Event not found, id: " + eventId);
        //  }
      } catch (AccessDeniedException e) {
          LOG.error("", e);
          throw new RestApplicationException(new RestError(HttpStatus.FORBIDDEN.name(), e.getMessage()));
      }
  }
}
