package com.gunnarro.sportsteam.repository;

import java.util.Date;
import java.util.List;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.ImageDetail;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.MatchEvent;
import com.gunnarro.sportsteam.domain.activity.MatchStatus;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.SeasonStatistic;
import com.gunnarro.sportsteam.domain.activity.Status;
import com.gunnarro.sportsteam.domain.activity.Training;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.league.LeagueCategory;
import com.gunnarro.sportsteam.domain.league.LeagueRule;
import com.gunnarro.sportsteam.domain.party.Address;
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.domain.party.Person;
import com.gunnarro.sportsteam.domain.party.Player;
import com.gunnarro.sportsteam.domain.party.Referee;
import com.gunnarro.sportsteam.domain.party.User;
import com.gunnarro.sportsteam.domain.statistic.KeyValuePair;
import com.gunnarro.sportsteam.domain.statistic.Statistic;
import com.gunnarro.sportsteam.domain.task.ScheduledTask;
import com.gunnarro.sportsteam.domain.task.SubTask;
import com.gunnarro.sportsteam.domain.task.TaskGroup;
import com.gunnarro.sportsteam.domain.task.VolunteerTask;
import com.gunnarro.sportsteam.domain.view.ActivityView;
import com.gunnarro.sportsteam.domain.view.list.Item;
import com.gunnarro.sportsteam.repository.table.TableHelper.PlayerLinkTableTypeEnum;

public interface SportsTeamRepository {

    public int addSubTaskToGroup(Integer subTaskId, Integer taskGroupId);

    public int assignContactToSubTask(Integer subTaskId, Integer contactId);

    public Integer assignContactToTask(Integer taskId, Integer contactId);

    public int assignTeamToSubTask(Integer subTaskId, Integer teamId);

    public int changeUserPwd(User user);

    /**
     * 
     * @param address
     * @return
     */
    public int createAddress(Address address);

    /**
     * 
     * @param club
     * @return
     */
    public int createClub(Club club);

    public int createContact(Contact contact);

    public int createContactRoleTypeLnk(Integer contactId, String roleTypeName);

    public int createCup(Cup cup);

    public int createCupMatchLnk(Integer cupId, Integer matchId);

    public int createLeague(League league);

    public int createMatch(Match match);

    public int createMatchEvent(MatchEvent matchEvent);

    public int createPerson(Person person);

    public int createPlayer(Player player);

    public int createPlayerLink(PlayerLinkTableTypeEnum type, int playerId, int id);

    public int createReferee(Referee referee);

    public int createSeason(Season season);

    public int createSubTask(SubTask subTask);

    public int createTaskGroup(TaskGroup taskGroup);

    public Integer createTeam(Team team);

    public int createTeamContactlink(Integer teamId, Integer contactId);

    public int createTeamCupLink(Integer teamId, Integer cupId);

    public int createTraining(Training training);

    public int createUser(User user);

    public int createVolunteerTask(VolunteerTask task);

    /**
     * @param addressId
     * @return
     */
    public int deleteAddress(Integer addressId);

    public int deleteAllContactRoleTypeLnk(Integer id);

    public int deleteAllContacts();

    public int deleteAllCups();

    public int deleteAllData();

    public int deleteAllMatches();

    public int deleteAllPlayers();

    public int deleteAllReferees();

    public int deleteAllTrainings();

    public int deleteAllVolunteerTasks();

    /**
     * 
     * @param clubId
     * @return
     */
    public int deleteClub(Integer clubId);

    public int deleteContact(Integer id);

    public int deleteContactRoleTypeLnk(Integer contactId, String roleTypeName);

    public int deleteCup(Integer id);

    public int deleteLeague(int id);

    public int deleteMatch(Integer id);

    public int deleteMatchEvents(Integer matchId);

    public int deletePerson(Integer id);

    public int deletePlayer(Integer id);

    public int deletePlayerLink(PlayerLinkTableTypeEnum type, Integer playerId, Integer id);

    public int deleteReferee(Integer id);

    public int deleteSeason(int seasonId);

    public int deleteSubTask(int id);

    public int deleteTaskGroup(Integer id);

    public int deleteTeam(Integer teamId);

    public int deleteTeamContactlink(Integer teamId, Integer contactId);

    public int deleteTeamCupLink(Integer teamId, Integer cupId);

    public int deleteTraining(Integer id);

    public int deleteUser(Integer id);

    public int deleteVolunteerTask(int id);

    public Integer enableScheduledTask(Integer taskId, int i);

    public Match findMatch(Date startDate, String homeTeamName, String awayTeamName);

    public List<ActivityView> getActivitiesUpcomingWeek(Integer teamId, Integer seasonId);

    /**
     * 
     * @param addressId
     * @return
     */
    public Address getAddress(Integer addressId);

    /**
     * 
     * @param id
     * @return
     */
    public Club getClub(Integer id);

    public Club getClub(String name, String departmentName);

    /**
     * 
     * @return
     */
    public List<Club> getClubs();

    public List<KeyValuePair> getClubStatistic();

    /**
     * 
     * @param id
     * @return
     */
    public Contact getContact(Integer id);

    public String[] getContactEmails(int teamId);

    public Integer getContactRoleTypeLnkId(Integer contactId, String roleTypeName);

    public List<Contact> getContacts(Integer teamId, Status status);

    public List<Contact> getContactsForClub(int clubId);

    public List<Contact> getContactsForPlayer(Integer playerId);

    public List<Item> getContactsForPlayerItem(Integer playerId);

    public Status getContactStatus(Integer id);

    public List<Type> getContactStatusTypes();

    public Cup getCup(Integer id);

    public List<Cup> getCupList(Integer teamId, Integer id);

    public List<Item> getCupPlayerList(Integer teamId, Integer cupId);

    public List<Item> getCupTeamList(Integer cupId);

    public Season getCurrentSeason();

    public League getLeague(Integer id);

    public League getLeague(String leagueName);

    public League getLeague(String sportsType, String leagueName);

    public LeagueCategory getLeagueCategory(Integer id);

    public LeagueRule getLeagueRule(Integer id);

    public List<League> getLeagues();

    public Statistic getLeagueTable(Integer leagueId, Integer seasonId);

    public List<Type> getLeagueTypes(Integer seasonId);

    public Match getMatch(Integer id);

    public List<MatchEvent> getMatchEventList(Integer matchId1);

    /**
     * 
     * @param teamId
     * @param period
     * @return
     */
    public List<Match> getMatchList(Integer teamId, Integer period);

    public List<Match> getMatchList(String teamName, Integer leagueId, Integer seasonId);

    public Match[] getMatchListByLeague(Integer id, Integer id2);

    public List<Match> getMatchListByTeamName(String teamName, Integer leagueId, Integer seasonId);

    public List<Match> getMatchListForClub(Integer clubId, Integer seasonId);

    public List<Match> getMatchListForLeague(Integer leagueId, Integer seasonId, String teamName);

    public List<Item> getMatchPlayerList(Integer teamId, Integer matchId);

    public MatchStatus getMatchStatus(Integer statusId);

    public MatchStatus getMatchStatus(String name);

    public List<Type> getMatchStatusTypes();

    public List<Type> getMatchTypes();

    public Integer getNumberOfRegistreredTeamsForCup(Integer cupId);

    public Integer getNumberOfSignedPlayers(String activityType, int id);

    public Person getPerson(Integer id);

    /**
     * 
     * @param id
     * @return
     */
    public Player getPlayer(Integer id);

    public List<Type> getPlayerPositionTypes();

    public List<Player> getPlayers(Integer teamId, Status status);

    public List<Item> getPlayersForContactItem(Integer contactId);

    public Statistic getPlayerStatistic(Integer teamId, Integer playerId, Integer seasonId);

    public Status getPlayerStatus(Integer id);

    public List<Type> getPlayerStatusTypes();

    /**
     * 
     * @param id
     * @return
     */
    public Referee getReferee(Integer id);

    public List<Referee> getReferees(Integer clubId);

    public List<Team> getRegistreredTeamsForCup(Integer cupId);

    public Season getSeason(Integer id);

    public Season getSeason(String period);

    public List<Season> getSeasonPeriodes();

    public List<Season> getSeasons();

    public List<Type> getSportTypes();

    public SubTask getSubTask(int id);

    public List<SubTask> getSubTasks(Integer parentTaskId);

    public TaskGroup getTaskGroup(int id);

    public Status getTaskStatus(Integer id);

    public List<Status> getTaskStatuses();

    /**
     * 
     * @param id
     * @return
     */
    public Team getTeam(Integer id);

    public Team getTeam(Integer clubId, String teamName);

    public Contact getTeamContactPerson(Integer teamId, String contactType);

    public List<Type> getTeamRoleTypes();

    public List<Team> getTeams(Integer clubId);

    public List<ScheduledTask> getTeamScheduledTasks();

    public Statistic getTeamStatistic(Integer teamId, Integer seasonId);

    public List<Statistic> getTopPlayerStatisticList(int seasonId, int teamId);

    public Training getTraining(Integer trainingId);

    public Training getTrainingByDate(Integer id, Long startTime);

    public List<Item> getTrainingPlayerList(Integer teamId, Integer trainingId);

    public List<Training> getTrainings(Integer teamId, Integer seasonId);

    public User getUser(Integer userId);

    public User getUser(String userName);

    public List<String> getUserRoles();

    public List<User> getUsers();

    public VolunteerTask getVolunteerTask(int id);

    public List<VolunteerTask> getVolunteerTasks(Integer clubId, Integer seasonId);

    public int registrerRefereeForMatch(Integer refereeId, Integer matchId);

    public int removeSubTaskFromGroup(Integer subTaskId, Integer taskGroupId);

    public List<Club> searchClubs(String clubName, String departmentName);

    public Match[] searchMatches(Integer seasonId, Date fromdate, Date toDate);

    public List<Team> searchTeams(Integer clubId, String teamName, Integer leagueId);

    public int unsignContactFromSubTask(Integer subTaskId, Integer contactId);

    public Integer unsignContactFromTask(Integer taskId, Integer contactId);

    public int unsignTeamFromSubTask(Integer subTaskId, Integer teamId);

    public int updateAddress(Address address);

    /**
     * 
     * @param club
     * @return
     */
    public int updateClub(Club club);

    public int updateContact(Contact contact);

    public int updateContactRoleTypeLnk(Integer contactId, String roleTypeName, String newRoleTypeName);

    public int updateCup(Cup cup);

    public int updateGoals(Integer matchId, Integer goals, boolean isHomeTeam);

    public int updateLeague(League league);

    public int updateMatch(Match match);

    public int updateMatchStatus(Integer matchId, Integer id);

    public int updatePerson(Person person);

    public int updatePlayer(Player player);

    public int updateReferee(Referee referee);

    public int updateSubTask(SubTask subTask);

    public int updateTeam(Team team);

    public int updateTraining(Training training);

    public int updateUser(User user);

    public int updateVolunteerTask(VolunteerTask task);

    public int getTeamIdForUser(Integer id);

    public void runQuery(String sqlQuery, Class<?> clazz);

    public List<SeasonStatistic> getSeasonStatistics();

    public List<Item> getCalendarUrls();

    public String getCalendarUrl(String name);

    public List<String> getFollowersPhoneNumbers(Integer eventId);

    public List<Person> getFollowers(Integer eventId);

    public ImageDetail getImage(Integer id);

    public List<ImageDetail> getImages(Integer userId);
    
    public int createImage(ImageDetail imageDetail);
    
    public boolean deleteImage(Integer imageId);

}
