package com.gunnarro.sportsteam.service.impl;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.MatchEvent;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.SeasonStatistic;
import com.gunnarro.sportsteam.domain.activity.Status;
import com.gunnarro.sportsteam.domain.activity.Training;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.league.LeagueCategory;
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.domain.party.Contact.StatusEnum;
import com.gunnarro.sportsteam.domain.party.Person;
import com.gunnarro.sportsteam.domain.party.Player;
import com.gunnarro.sportsteam.domain.party.Referee;
import com.gunnarro.sportsteam.domain.party.Role;
import com.gunnarro.sportsteam.domain.party.Role.RoleTypesEnum;
import com.gunnarro.sportsteam.domain.party.User;
import com.gunnarro.sportsteam.domain.statistic.KeyValuePair;
import com.gunnarro.sportsteam.domain.statistic.MatchStatistic;
import com.gunnarro.sportsteam.domain.statistic.Statistic;
import com.gunnarro.sportsteam.domain.task.ScheduledTask;
import com.gunnarro.sportsteam.domain.task.SubTask;
import com.gunnarro.sportsteam.domain.task.TaskGroup;
import com.gunnarro.sportsteam.domain.task.VolunteerTask;
import com.gunnarro.sportsteam.domain.view.ActivityView;
import com.gunnarro.sportsteam.domain.view.list.Item;
import com.gunnarro.sportsteam.parser.XmlDocumentParser;
import com.gunnarro.sportsteam.repository.SportsTeamRepository;
import com.gunnarro.sportsteam.repository.table.TableHelper.PlayerLinkTableTypeEnum;
import com.gunnarro.sportsteam.service.SportsTeamService;
import com.gunnarro.sportsteam.service.exception.ApplicationException;
import com.gunnarro.sportsteam.service.exception.ValidationException;
import com.gunnarro.sportsteam.utility.ExcelReader;
import com.gunnarro.sportsteam.utility.Utility;

@Service
public class SportsTeamServiceImpl implements SportsTeamService {

    private static final Logger LOG = LoggerFactory.getLogger(SportsTeamServiceImpl.class);

    @Autowired
    @Qualifier("sportsTeamRepository")
    private SportsTeamRepository sportsTeamRepository;


    /**
     * For unit test only
     * 
     * @param sportsTeamRepository
     */
    public void setSportsTeamRepository(SportsTeamRepository sportsTeamRepository) {
        this.sportsTeamRepository = sportsTeamRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ScheduledTask> getTeamScheduledTasks() {
        return sportsTeamRepository.getTeamScheduledTasks();
    }


    

    /**
     * {@inheritDoc}
     */
    @Override
    public String composeEmail(Team team) {
        List<ActivityView> upcomingActivities = getActivitiesUpcomingWeek(team.getId());
        StringBuilder msg = new StringBuilder();
        msg.append("Hei,\n");
        msg.append("Her kommer agenda for uke " + Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)).append("\n");
        msg.append("----------------------------------------------------------------\n");
        for (ActivityView a : upcomingActivities) {
            msg.append(MessageFormat.format("{0} {1} {2}", Utility.formatTime(a.getStartDate().getTime(), "EEE dd.MM.yyyy hh:mm"), a.getDescription(), a.getPlace())).append("\n");
        }
        msg.append("----------------------------------------------------------------\n");
        msg.append("\n\n");
        msg.append("Hilsen\n");
        if (team.getTeamLead() != null) {
            msg.append(team.getTeamLead().getFirstName());
        } else {
            msg.append(team.getName());
        }

        msg.append("\n\n");
        msg.append("------------------------------------------------------------------\n");
        msg.append("This is a generated mail by SportsTeam.");
        return msg.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean loadData(String filePath, String fileName) {
        try {
            XmlDocumentParser xmlParser = new XmlDocumentParser();
            if (fileName.startsWith("club")) {
                xmlParser.loadClub(filePath, fileName, this);
            } else if (fileName.startsWith("team")) {
                xmlParser.loadTeam(filePath, fileName, this);
            } else if (fileName.startsWith("cups")) {
                xmlParser.loadCups(filePath, fileName, this);
            } else if (fileName.endsWith("xlsx") || fileName.endsWith("xls")) {
                List<Match> matches = ExcelReader.mapNBFLeagueMatches(filePath + File.separator + fileName, getCurrentSeason());
                return loadMatches(matches);
            } else {
                LOG.error("Not supported file format: " + fileName);
            }
            return true;
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException(e.getMessage());
        }
    }

    private boolean loadMatches(List<Match> matches) {
        int newMatches = 0;
        int totalNumberOfMatches = 0;
        for (Match match : matches) {
            totalNumberOfMatches++;
            Match findMatch = sportsTeamRepository.findMatch(match.getStartDate(), match.getHomeTeam().getName(), match.getAwayTeam().getName());
            if (findMatch == null) {
                try {
                    newMatches++;
                    League league = getLeague(match.getLeague().getLeagueCategory().getSportType(), match.getLeague().getName());
                    String gender = league.getLeagueCategory().getLeagueRule().getGender();
                    Team homeTeam = checkAndCreateClubAndTeam(match.getHomeTeam(), gender, league.getId());
                    Team awayTeam = checkAndCreateClubAndTeam(match.getAwayTeam(), gender, league.getId());
                    // Save match only if both club and team was
                    // successfully registered
                    if (homeTeam != null && awayTeam != null) {
                        match.setFkLeagueId(league.getId());
                        match.setFkTeamId(homeTeam.getId());
                        if (saveMatch(match) == -1) {
                            LOG.warn("Match already registerered! " + match.toString());
                        }
                    } else {
                        LOG.error("match not saved, home or away team not OK: " + match.toString());
                    }
                } catch (ApplicationException ae) {
                    LOG.error(null, ae);
                }
            }
        }
        LOG.info("Total number of matches   : " + totalNumberOfMatches);
        LOG.info("Number of new matches     : " + newMatches);
        LOG.info("Number of existing matches: " + (totalNumberOfMatches - newMatches));
        return true;
    }

    private Team checkAndCreateClubAndTeam(Team team, String gender, Integer leagueId) {
        // Check if club and team are registered, if not do it now!
        if (StringUtils.isEmpty(team.getClub().getName())) {
            LOG.error("Missing clubName for team!" + team.getName());
            return null;
        }
        Club club = null;
        List<Club> clubs = sportsTeamRepository.searchClubs(team.getClub().getName(), "%");
        if (clubs.isEmpty()) {
            Integer clubId = saveClub(team.getClub());
            club = sportsTeamRepository.getClub(clubId);
        } else if (clubs.size() == 1) {
            club = clubs.get(0);
        } else {
            LOG.error("More than one hit for club name: " + team.getClub().getName());
            return null;
        }
        List<Team> teams = sportsTeamRepository.searchTeams(club.getId(), team.getName(), leagueId);
        if (teams.isEmpty()) {
            // No team found for this name, so save it.
            team.setFkClubId(club.getId());
            team.setFkLeagueId(leagueId);
            team.setGender(gender);
            int teamId = saveTeam(team);
            if (LOG.isInfoEnabled()) {
                LOG.info("created new team: " + team.toString());
            }
            return getTeam(teamId);
        } else {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Duplicate! team name already registered! clubName=" + team.getClub().getName() + ", teamName=" + team.getName() + ", leagueId=" + leagueId);
                LOG.warn("Registered number of teams: " + teams.size() + ", " + teams);
            }
        }
        // team not created, but exist, so return true
        return teams.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Club> getClubs() {
        return sportsTeamRepository.getClubs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getPlayers(int teamId) {
        Status playerStatusActive = sportsTeamRepository.getPlayerStatus(1);
        List<Player> players = sportsTeamRepository.getPlayers(teamId, playerStatusActive);
        for (Player player : players) {
            if (player.hasFkSeasonId()) {
                player.setStatus(sportsTeamRepository.getPlayerStatus(player.getFkStatusId()));
            }
        }
        return players;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Team> getTeams(Integer clubId) {
        List<Team> teams = sportsTeamRepository.getTeams(clubId);
        for (Team t : teams) {
            if (t.hasFkLeagueId()) {
                t.setLeague(sportsTeamRepository.getLeague(t.getFkLeagueId()));
            }
            t.setTeamLead(sportsTeamRepository.getTeamContactPerson(t.getId(), Role.RoleTypesEnum.TEAMLEAD.name()));
            t.setCoach(sportsTeamRepository.getTeamContactPerson(t.getId(), Role.RoleTypesEnum.COACH.name()));
        }
        return teams;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Team getTeam(int id) {
        Team team = sportsTeamRepository.getTeam(id);
        if (team == null) {
            throw new ApplicationException("Object Not Found, teamId=" + id);
        }
        return team;
    }

    @Override
    public Team getTeam(Integer clubId, String teamName) {
        if (teamName == null) {
            throw new ApplicationException("Team name not set!");
        }
        return sportsTeamRepository.getTeam(clubId, teamName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatches(String teamName, Integer leagueId, String seasonPeriod) {
        Season season = getSeason(seasonPeriod);
        return sportsTeamRepository.getMatchList(teamName, leagueId, season.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatches(int teamId, String seasonPeriod) {
        Season season = getSeason(seasonPeriod);
        return sportsTeamRepository.getMatchList(teamId, season.getId());
    }

   
    /**
     * {@inheritDoc}
     */
    @Override
    public Club getClub(Integer id) {
        return sportsTeamRepository.getClub(id);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<Item> getContactItemList(Integer clubId) {
        List<Item> items = new ArrayList<Item>();
        List<Contact> contacts = this.sportsTeamRepository.getContacts(clubId, new Status(StatusEnum.ACTIVE.name()));
        for (Contact c : contacts) {
            items.add(new Item(c.getId(), c.getFullName()));
        }
        return items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Referee getReferee(int refereeId) {
        return sportsTeamRepository.getReferee(refereeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Contact getTeamLead(Integer teamId) {
        return this.sportsTeamRepository.getTeamContactPerson(teamId, Role.RoleTypesEnum.TEAMLEAD.name());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Contact getCoach(Integer teamId) {
        return this.sportsTeamRepository.getTeamContactPerson(teamId, Role.RoleTypesEnum.COACH.name());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatches(Integer teamId, String seasonPeriod) {
        Season season = getSeason(seasonPeriod);
        return sportsTeamRepository.getMatchList(teamId, season.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match[] searchMatches(String seasonPeriod, Date fromDate, Date toDate) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("sesaon:" + seasonPeriod + ", fromdate:" + fromDate + ", todate:" + toDate);
        }
        Season season = getSeason(seasonPeriod);
        return sportsTeamRepository.searchMatches(season.getId(), fromDate, toDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match[] getMatchesByLeague(League league, String seasonPeriod) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(league.toString() + ", " + seasonPeriod);
        }
        Season season = getSeason(seasonPeriod);
        return sportsTeamRepository.getMatchListByLeague(league.getId(), season.getId());
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<Match> getMatchesByTeamName(String teamName, League league, String seasonPeriod) {
        Season season = getSeason(seasonPeriod);
        return sportsTeamRepository.getMatchListByTeamName(teamName, league.getId(), season.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cup> getCups(Integer teamId, String seasonPeriod) {
        Season season = getSeason(seasonPeriod);
        List<Cup> cupList = sportsTeamRepository.getCupList(teamId, season.getId());
        for (Cup cup : cupList) {
            cup.setNumberOfRegistreredTeams(sportsTeamRepository.getNumberOfRegistreredTeamsForCup(cup.getId()));
        }
        return cupList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Match getMatch(Integer id) {
        return sportsTeamRepository.getMatch(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteMatch(int id) {
        int deletedRows = sportsTeamRepository.deleteMatch(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted MatchId: " + id + ", deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int saveMatch(Match match) {
        if (match.isNew()) {
            return sportsTeamRepository.createMatch(match);
        } else {
            return sportsTeamRepository.updateMatch(match);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGoalsHomeTeam(int matchId, int goals) {
        this.sportsTeamRepository.updateGoals(matchId, goals, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGoalsAwayTeam(int matchId, int goals) {
        this.sportsTeamRepository.updateGoals(matchId, goals, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchEvent> getMatchEventList(int matchId) {
        return sportsTeamRepository.getMatchEventList(matchId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateMatchStatus(int matchId, int statusId) {
        sportsTeamRepository.updateMatchStatus(matchId, statusId);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int getNumberOfSignedPlayersForMatch(int matchId) {
        return sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.MATCH.name(), matchId);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Player getPlayer(int id) {
        Player player = sportsTeamRepository.getPlayer(id);
        List<Item> parents = sportsTeamRepository.getContactsForPlayerItem(player.getId());
        player.setParentItemList(parents);
        return player;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Contact getContact(int id) {
        Contact contact = sportsTeamRepository.getContact(id);
        // List<Item> list =
        // sportsTeamRepository.getPlayersForContactItem(contact.getId());
        // contact.setRelationItemList(list);
        return contact;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Club getClub(String name, String departmentName) {
        return sportsTeamRepository.getClub(name, departmentName);
    }

    /**
     * Return current season as default
     */
    @Override
    public Season getSeason(String period) {
        Season season = null;
        if (StringUtils.isEmpty(period) || "current".equalsIgnoreCase(period)) {
            season = getCurrentSeason();
        } else {
            season = sportsTeamRepository.getSeason(period);
        }
        if (season == null) {
            throw new ApplicationException("Season not found for: " + period + ", Check settings for seasons!");
        }
        return season;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int createSeason(Season season) {
        return sportsTeamRepository.createSeason(season);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Season getSeason(int id) {
        return sportsTeamRepository.getSeason(id);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void createMatchForCup(Match match, int cupId) {
        saveMatch(match);

    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int createPlayer(Player player) {
        return sportsTeamRepository.createPlayer(player);

    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<Contact> getContactsForClub(int clubId) {
        return sportsTeamRepository.getContactsForClub(clubId);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<Contact> getContacts(int teamId) {
        Status contactStatusActive = sportsTeamRepository.getContactStatus(1);
        return sportsTeamRepository.getContacts(teamId, contactStatusActive);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public String[] getContactEmails(int teamId) {
        return sportsTeamRepository.getContactEmails(teamId);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<Training> getTrainings(int teamId, String seasonPeriode) {
        Season season = getSeason(seasonPeriode);
        return sportsTeamRepository.getTrainings(teamId, season.getId());
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int deleteTraining(int id) {
        int deletedRows = sportsTeamRepository.deleteTraining(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted TrainingId: " + id + ", deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Training getTraining(int id) {
        return sportsTeamRepository.getTraining(id);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<ActivityView> getActivitiesUpcomingWeek(int teamId) {
        Season season = getCurrentSeason();
        List<ActivityView> activities = sportsTeamRepository.getActivitiesUpcomingWeek(teamId, season.getId());
        for (ActivityView a : activities) {
            a.setNumberOfSignedPlayers(sportsTeamRepository.getNumberOfSignedPlayers(a.getType(), a.getId()));
        }
        return activities;
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<Item> getSignedPlayersForMatch(int teamId, int matchId) {
        return this.sportsTeamRepository.getMatchPlayerList(teamId, matchId);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public int signupForCup(int playerId, int cupId) {
        sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.CUP, playerId, cupId);
        return sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.CUP.name(), cupId);
    }

    @Override
    public int unsignForCup(int playerId, int cupId) {
        sportsTeamRepository.deletePlayerLink(PlayerLinkTableTypeEnum.CUP, playerId, cupId);
        return sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.CUP.name(), cupId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int signupForMatch(int playerId, int matchId) {
        sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.MATCH, playerId, matchId);
        return sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.MATCH.name(), matchId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int unsignForMatch(int playerId, int matchId) {
        sportsTeamRepository.deletePlayerLink(PlayerLinkTableTypeEnum.MATCH, playerId, matchId);
        return sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.MATCH.name(), matchId);
    }

    @Override
    public int signupForTraining(int playerId, int trainingId) {
        sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.TRAINING, playerId, trainingId);
        return sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.TRAINING.name(), trainingId);
    }

    @Override
    public int unsignForTraining(int playerId, int trainingId) {
        sportsTeamRepository.deletePlayerLink(PlayerLinkTableTypeEnum.TRAINING, playerId, trainingId);
        return sportsTeamRepository.getNumberOfSignedPlayers(PlayerLinkTableTypeEnum.TRAINING.name(), trainingId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Season getCurrentSeason() {
        return sportsTeamRepository.getCurrentSeason();
    }

    @Override
    public List<Referee> getReferees(int clubId) {
        return sportsTeamRepository.getReferees(clubId);
    }

    @Override
    public void saveReferee(Referee referee) {
        if (referee.isNew()) {
            sportsTeamRepository.createReferee(referee);
        } else {
            sportsTeamRepository.updateReferee(referee);
        }
    }

    @Override
    public int deleteReferee(int id) {
        int deletedRows = sportsTeamRepository.deleteReferee(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted RefereeId: " + id + ", deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public void savePlayer(Player player) {
        if (player.isNew()) {
            sportsTeamRepository.createPlayer(player);
        } else {
            sportsTeamRepository.updatePlayer(player);
        }
    }

    @Override
    public int deletePlayer(int id) {
        int deletedRows = sportsTeamRepository.deletePlayer(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted PlayerId: " + id + ", deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public List<Type> getPlayerStatusTypes() {
        return sportsTeamRepository.getPlayerStatusTypes();
    }

    @Override
    public List<Type> getPlayerPositionTypes() {
        return sportsTeamRepository.getPlayerPositionTypes();
    }

    @Override
    public List<Type> getContactStatusTypes() {
        return sportsTeamRepository.getContactStatusTypes();
    }

    @Override
    public Statistic getPlayerStatistic(Integer teamId, Integer playerId, Integer seasonId) {
        return sportsTeamRepository.getPlayerStatistic(teamId, playerId, seasonId);
    }

    @Override
    public List<Type> getLeagueTypes(Integer seasonId) {
        return sportsTeamRepository.getLeagueTypes(seasonId);
    }

    @Override
    public int saveClub(Club club) {
        if (club == null) {
            throw new ApplicationException("Club is null!");
        }
        if (!club.hasName()) {
            throw new ValidationException("Club name not set! " + club.toString());
        }
        if (club.isNew()) {
            return sportsTeamRepository.createClub(club);
        }
        // update existing club
        sportsTeamRepository.updateClub(club);
        return club.getId();
    }

    @Override
    public int saveTeam(Team team) {
        if (team == null) {
            throw new ApplicationException("Team is null!");
        }
        if (!team.hasName()) {
            throw new ValidationException("Team name not set! " + team.getName());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(team.toString());
        }
        if (team.isNew()) {
            return sportsTeamRepository.createTeam(team);
        } else {
            // Update an existing team
            sportsTeamRepository.updateTeam(team);
            // Remove old contact persons, if contact person(s) has
            // changed. Or if contact persons have been removed.
            Contact currentTeamlead = getTeamLead(team.getId());
            if (team.hasTeamleadChanged(currentTeamlead)) {
                // Teamlead was removed or changed, therefore delete the link.
                sportsTeamRepository.deleteContactRoleTypeLnk(currentTeamlead.getId(), RoleTypesEnum.TEAMLEAD.name());
            }
            Contact currentCoach = getCoach(team.getId());
            if (team.hasCoachChanged(currentCoach)) {
                // Coach was removed or changed, therefore delete the link.
                sportsTeamRepository.deleteContactRoleTypeLnk(currentCoach.getId(), RoleTypesEnum.COACH.name());
            }
        }
        // Create team contact persons
        if (team.hasFkTeamleadId()) {
            saveContactRole(team.getFkTeamleadId(), RoleTypesEnum.TEAMLEAD.name(), null);
        }
        if (team.hasFkCoachId()) {
            saveContactRole(team.getFkCoachId(), RoleTypesEnum.COACH.name(), null);
        }
        return team.getId();
    }

    @Override
    public int deleteClub(int id) {
        int deletedRows = sportsTeamRepository.deleteClub(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted ClubId: " + id + ", deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public int deleteTeam(int id) {
        int deletedRows = sportsTeamRepository.deleteTeam(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted TeamId: " + id + ", deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public int saveContact(Contact contact) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(contact.toString());
            }
            Integer contactId = null;
            if (contact.isNew()) {
                contactId = sportsTeamRepository.createContact(contact);
            } else {
                contactId = sportsTeamRepository.updateContact(contact);
            }
            contact.setId(contactId);
            updateContactTeamRoles(contact);
            // For debug only
            sportsTeamRepository.runQuery("SELECT * FROM contact_role_type_lnk", Type.class);
            return contactId;
        } catch (Exception sqle) {
            LOG.error(null, sqle);
            throw new ApplicationException(sqle.toString());
        }
    }

    private void updateContactTeamRoles(Contact contact) {
        // Quick and dirty, first delete all current team roles for this
        // contact, if any
        // FIXME not working as intended!
        sportsTeamRepository.deleteAllContactRoleTypeLnk(contact.getId());
        if (contact.hasTeamRoles()) {
            for (Type role : contact.getTeamRoleList()) {
                saveContactRole(contact.getId(), role.getName(), role.getName());
            }
        }
    }

    @Override
    public int saveContactRole(Integer contactId, String roleTypeName, String newRoleTypeName) {
        Integer id = sportsTeamRepository.getContactRoleTypeLnkId(contactId, roleTypeName);
        // If this contact has no roles assigned, create a new.
        if (id == null) {
            int lnkId = sportsTeamRepository.createContactRoleTypeLnk(contactId, roleTypeName);
            LOG.debug("Created link: contactId: " + contactId + " has " + roleTypeName + ", linkId:" + lnkId);
        } else if (newRoleTypeName != null) {
            int rows = sportsTeamRepository.updateContactRoleTypeLnk(contactId, roleTypeName, newRoleTypeName);
            LOG.debug("Updated link: contactId: " + contactId + " from " + roleTypeName + " to " + newRoleTypeName);
        }
        return 0;
    }

    @Override
    public int deleteContact(int id) {
        int deletedRows = sportsTeamRepository.deleteContact(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted ContactId: " + id + ", deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public int saveTraining(Training training) {
        if (training.isNew()) {
            return sportsTeamRepository.createTraining(training);
        } else {
            return sportsTeamRepository.updateTraining(training);
        }

    }

    @Override
    public int deleteCup(int id) {
        int deletedRows = sportsTeamRepository.deleteCup(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted CupId: " + id + ", deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public int deleteAllCups() {
        int deletedRows = sportsTeamRepository.deleteAllCups();
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted all cups, deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public int deleteAllData(int clubId) {
        int deletedRows = sportsTeamRepository.deleteAllData();
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted all data, deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public int saveCup(Cup cup) {
        if (cup.isNew()) {
            return sportsTeamRepository.createCup(cup);
        } else {
            return sportsTeamRepository.updateCup(cup);
        }
    }

    @Override
    public Cup getCup(int id) {
        return sportsTeamRepository.getCup(id);
    }

    @Override
    public List<Type> getMatchTypes() {
        return sportsTeamRepository.getMatchTypes();
    }

    @Override
    public List<Type> getMatchStatusTypes() {
        return sportsTeamRepository.getMatchStatusTypes();
    }

    @Override
    public List<Type> getTeamRoleTypes() {
        return sportsTeamRepository.getTeamRoleTypes();
    }

    @Override
    public List<Item> getCupSignedPlayerList(int teamId, int cupId) {
        return sportsTeamRepository.getCupPlayerList(teamId, cupId);
    }

    @Override
    public List<Item> getTrainingSignedPlayerList(int teamId, int trainingId) {
        return sportsTeamRepository.getTrainingPlayerList(teamId, trainingId);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<Item> getMatchSignedPlayerList(int teamId, int matchId) {
        return this.sportsTeamRepository.getMatchPlayerList(teamId, matchId);
    }

    /**
     * 
     * @param teamId
     * @return
     */
    @Override
    public List<VolunteerTask> getVolunteerTasks(int clubId, String seasonPeriod) {
        Season season = getSeason(seasonPeriod);
        return this.sportsTeamRepository.getVolunteerTasks(clubId, season.getId());
    }

    @Override
    public VolunteerTask getVolunteerTask(int volunteerTaskId) {
        VolunteerTask volunteerTask = this.sportsTeamRepository.getVolunteerTask(volunteerTaskId);
        if (volunteerTask.isAssigned()) {
            volunteerTask.setAssignee(this.sportsTeamRepository.getContact(volunteerTask.getFkAssignedToPersonId()));
        }
        return volunteerTask;
    }

    @Override
    public int deleteVolunteerTask(int id) {
        int deletedRows = sportsTeamRepository.deleteVolunteerTask(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted VolunteerTaskId: " + id + ", deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public int saveVolunteerTask(VolunteerTask task) {
        if (task.isNew()) {
            return sportsTeamRepository.createVolunteerTask(task);
        } else {
            return sportsTeamRepository.updateVolunteerTask(task);
        }
    }

    // FIXME
    @Override
    public List<VolunteerTask> getVolunteerTasksForTeam(int teamId, String seasonPeriod) {
        return new ArrayList<VolunteerTask>();
    }

    @Override
    public Integer playerAddParent(Integer playerId, Integer parentId) {
        return sportsTeamRepository.createPlayerLink(PlayerLinkTableTypeEnum.CONTACT, playerId, parentId);
    }

    @Override
    public Integer playerRemoveParent(Integer playerId, Integer parentId) {
        return sportsTeamRepository.deletePlayerLink(PlayerLinkTableTypeEnum.CONTACT, playerId, parentId);
    }

    @Override
    public Integer cupRegistrerTeam(Integer cupId, Integer teamId) {
        sportsTeamRepository.createTeamCupLink(teamId, cupId);
        return sportsTeamRepository.getNumberOfRegistreredTeamsForCup(cupId);
    }

    @Override
    public Integer cupUnRegistrerTeam(Integer cupId, Integer teamId) {
        sportsTeamRepository.deleteTeamCupLink(teamId, cupId);
        return sportsTeamRepository.getNumberOfRegistreredTeamsForCup(cupId);
    }

    @Override
    public List<Item> getCupRegistreredTeamList(Integer cupId) {
        return this.sportsTeamRepository.getCupTeamList(cupId);
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = sportsTeamRepository.getSubTask(id);
        if (subTask.isAssigned()) {
            subTask.setAssignee(this.sportsTeamRepository.getContact(subTask.getFkAssignedToPersonId()));
        }
        return subTask;
    }

    @Override
    public int deleteSubTask(int id) {
        int deletedRows = sportsTeamRepository.deleteSubTask(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted SubTaskId: " + id + " deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public int saveSubTask(SubTask subTask) {
        if (!subTask.hasFkParentId()) {
            throw new ApplicationException("Must be assigen to a volunteer task, parent id not set!");
        }
        if (subTask.isNew()) {
            return sportsTeamRepository.createSubTask(subTask);
        } else {
            return sportsTeamRepository.updateSubTask(subTask);
        }
    }

    @Override
    public List<Status> getTaskStatuses() {
        return sportsTeamRepository.getTaskStatuses();
    }

    @Override
    public List<KeyValuePair> getClubStatistic() {
        return sportsTeamRepository.getClubStatistic();
    }

    @Override
    public int saveTaskGroup(TaskGroup taskGroup) {
        if (taskGroup.isNew()) {
            return sportsTeamRepository.createTaskGroup(taskGroup);
        } else {
            return -1;
            // fixme
            // return sportsTeamRepository.updateTaskGroup(taskGroup);
        }
    }

    @Override
    public TaskGroup getTaskGroup(int id) {
        return sportsTeamRepository.getTaskGroup(id);
    }

    @Override
    public int deleteTaskGroup(int id) {
        int deletedRows = sportsTeamRepository.deleteTaskGroup(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted TaskGroupId: " + id + ", deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public Integer assignSubTaskToPerson(Integer subTaskId, Integer contactId) {
        return sportsTeamRepository.assignContactToSubTask(subTaskId, contactId);
    }

    @Override
    public Integer unsignPersonFromSubTask(Integer subTaskId, Integer contactId) {
        return sportsTeamRepository.unsignContactFromSubTask(subTaskId, contactId);
    }

    @Override
    public Integer assignTaskToPerson(int taskId, int contactId) {
        return sportsTeamRepository.assignContactToTask(taskId, contactId);
    }

    @Override
    public Integer unsignPersonFromTask(int taskId, int contactId) {
        return sportsTeamRepository.unsignContactFromTask(taskId, contactId);
    }

    /**
     * Return current season as default
     */
    @Override
    public League getLeague(String sportType, String leagueName) {
        if (StringUtils.isEmpty(leagueName)) {
            throw new ApplicationException("Missing league department name!");
        }
        if (StringUtils.isEmpty(sportType)) {
            throw new ApplicationException("Missing sport type!");
        }
        League league = sportsTeamRepository.getLeague(sportType, leagueName);
        if (league == null) {
            throw new ApplicationException("League not found for: " + leagueName + ", Check settings for leagues!");
        }
        return league;
    }

    @Override
    public League getLeague(int id) {
        return sportsTeamRepository.getLeague(id);
    }

    @Override
    public int deleteLeague(int id) {
        int deletedRows = sportsTeamRepository.deleteLeague(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted LeagueId: " + id + " deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public int saveLeague(League league) {
        if (league.isNew()) {
            return sportsTeamRepository.createLeague(league);
        } else {
            return sportsTeamRepository.updateLeague(league);
        }
    }

    @Override
    public List<League> getLeagues() {
        List<League> leagues = sportsTeamRepository.getLeagues();
        for (League league : leagues) {
            LeagueCategory leagueCategory = sportsTeamRepository.getLeagueCategory(league.getFkLeagueCategoryId());
            league.setLeagueCategory(leagueCategory);
        }
        return leagues;
    }

    @Override
    public List<MatchStatistic> getLeagueTable(int leagueId, String seasonPeriod) {
        Season season = getSeason(seasonPeriod);
        return sportsTeamRepository.getLeagueTable(leagueId, season.getId()).getMatchStatisticList();
    }

    @Override
    public List<User> getUsers() {
        return sportsTeamRepository.getUsers();
    }

    @Override
    public User getUser(String userName) {
        return sportsTeamRepository.getUser(userName);
    }

    @Override
    public User getUser(int userId) {
        return sportsTeamRepository.getUser(userId);
    }

    @Override
    public int saveUser(User user) {
        user.checkPasword();
        if (user.isNew()) {
            return sportsTeamRepository.createUser(user);
        }
        return sportsTeamRepository.updateUser(user);
    }

    @Override
    public int deleteUser(int id) {
        int deletedRows = sportsTeamRepository.deleteUser(id);
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted UserId: " + id + " deleted rows: " + deletedRows);
        }
        return deletedRows;
    }

    @Override
    public List<String> getUserRoles() {
        return sportsTeamRepository.getUserRoles();
    }

    @Override
    public List<Match> getMatchesForClub(Integer clubId, String seasonPeriod) {
        Season season = getSeason(seasonPeriod);
        return sportsTeamRepository.getMatchListForClub(clubId, season.getId());
    }

    @Override
    public Integer enableScheduledTask(Integer taskId, boolean isEnable) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("taskId:" + taskId + " enable:" + isEnable);
        }
        return sportsTeamRepository.enableScheduledTask(taskId, isEnable ? 1 : 0);
    }

    @Override
    public int deleteSeason(int seasonId) {
        return sportsTeamRepository.deleteSeason(seasonId);

    }

    @Override
    public List<Season> getSeasons() {
        return sportsTeamRepository.getSeasons();
    }

    @Override
    public int getTeamIdForUser(Integer id) {
        return sportsTeamRepository.getTeamIdForUser(id);
    }

    @Override
    public List<SeasonStatistic> getSeasonStatistics() {
        return sportsTeamRepository.getSeasonStatistics();
    }

    @Override
    public List<String> getFollowersPhoneNumbers(Integer eventId) {
        return sportsTeamRepository.getFollowersPhoneNumbers(eventId);
    }

    @Override
    public List<Person> getFollowers(Integer eventId) {
        return sportsTeamRepository.getFollowers(eventId);
    }

}
