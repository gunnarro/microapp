package com.gunnarro.sportsteam.service;

import java.util.Date;
import java.util.List;

import org.springframework.security.access.annotation.Secured;

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
import com.gunnarro.sportsteam.domain.party.Contact;
import com.gunnarro.sportsteam.domain.party.Person;
import com.gunnarro.sportsteam.domain.party.Player;
import com.gunnarro.sportsteam.domain.party.Referee;
import com.gunnarro.sportsteam.domain.party.User;
import com.gunnarro.sportsteam.domain.statistic.MatchStatistic;
import com.gunnarro.sportsteam.domain.statistic.Statistic;
import com.gunnarro.sportsteam.domain.task.ScheduledTask;
import com.gunnarro.sportsteam.domain.task.SubTask;
import com.gunnarro.sportsteam.domain.task.TaskGroup;
import com.gunnarro.sportsteam.domain.task.VolunteerTask;
import com.gunnarro.sportsteam.domain.view.ActivityView;
import com.gunnarro.sportsteam.domain.view.list.Item;

/**
 * Method level security should be applied for all save*() and delete*() methods
 * here.
 * 
 * @author admin
 *
 */
public interface SportsTeamService {

	public Integer assignSubTaskToPerson(Integer taskId, Integer contactId);

	public Integer assignTaskToPerson(int taskId, int contactId);

	public String composeEmail(Team team);

	public void createMatchForCup(Match match, int cupId);

	public int createPlayer(Player player);

	public int createSeason(Season newSeason);

	public Integer cupRegistrerTeam(Integer cupId, Integer teamId);

	public Integer cupUnRegistrerTeam(Integer cupId, Integer teamId);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteAllCups();

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteAllData(int clubId);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteClub(int id);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteContact(int id);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteCup(int id);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteLeague(int leagueId);

	// @Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteMatch(int matchId);

	// @Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deletePlayer(int id);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteReferee(int id);

	// @Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteSeason(int seasonId);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteSubTask(int id);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteTaskGroup(int taskGroupId);

	// @Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteTeam(int id);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteTraining(int trainingId);

	@Secured({ "ROLE_ADMIN" })
	public int deleteUser(int userId);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int deleteVolunteerTask(int id);

	@Secured({ "ROLE_ADMIN" })
	public Integer enableScheduledTask(Integer taskId, boolean b);

	public List<ActivityView> getActivitiesUpcomingWeek(int teamId);

	public Club getClub(Integer id);

	public Club getClub(String name, String departmentName);

	public List<Club> getClubs();

	public Object getClubStatistic();

	public Contact getCoach(Integer teamId);

	public Contact getContact(int id);

	public String[] getContactEmails(int teamId);

	public List<Item> getContactItemList(Integer clubId);

	public List<Contact> getContacts(int clubId);

	public List<Contact> getContactsForClub(int clubId);

	public List<Type> getContactStatusTypes();

	public Cup getCup(int id);

	public List<Item> getCupRegistreredTeamList(Integer id);

	public List<Cup> getCups(Integer teamId, String seasonPeriod);

	public List<Item> getCupSignedPlayerList(int teamId, int cupId);

	public Season getCurrentSeason();

	public League getLeague(int leagueId);

	public League getLeague(String sportType, String leagueName);

	public List<League> getLeagues();

	public List<MatchStatistic> getLeagueTable(int leagueId, String seasonPeriod);

	public List<Type> getLeagueTypes(Integer seasonId);

	public Match getMatch(Integer teamId);

	public List<Match> getMatches(int teamId, String season);

	public List<Match> getMatches(Integer teamId, String season);

	public List<Match> getMatches(String teamName, Integer leagueId,
			String seasonPeriod);

	public Match[] getMatchesByLeague(League league, String seasonPeriod);

	public List<Match> getMatchesByTeamName(String teamName, League league,
			String seasonPeriod);

	public List<Match> getMatchesForClub(Integer clubId, String seasonPeriod);

	public List<MatchEvent> getMatchEventList(int matchId);

	public List<Item> getMatchSignedPlayerList(int teamId, int matchId);

	public List<Type> getMatchStatusTypes();

	public List<Type> getMatchTypes();

	public int getNumberOfSignedPlayersForMatch(int matchId);

	public Player getPlayer(int id);

	public List<Type> getPlayerPositionTypes();

	public List<Player> getPlayers(int teamId);

	public Statistic getPlayerStatistic(Integer teamId, Integer playerId,
			Integer seasonId);

	public List<Type> getPlayerStatusTypes();

	public Referee getReferee(int refereeId);

	public List<Referee> getReferees(int clubId);

	public Season getSeason(int seasonId);

	public Season getSeason(String period);

	public List<Season> getSeasons();

	public List<SeasonStatistic> getSeasonStatistics();

	public List<Item> getSignedPlayersForMatch(int teamId, int matchId);

	public SubTask getSubTask(int id);

	public TaskGroup getTaskGroup(int taskGroupId);

	public List<Status> getTaskStatuses();

	public Team getTeam(int teamId);

	public Team getTeam(Integer id, String name);

	public Contact getTeamLead(Integer teamId);

	public List<Type> getTeamRoleTypes();

	public List<Team> getTeams(Integer clubid);

	@Secured({ "ROLE_ADMIN" })
	public List<ScheduledTask> getTeamScheduledTasks();

	public Training getTraining(int traningId);

	public List<Training> getTrainings(int teamId, String season);

	public List<Item> getTrainingSignedPlayerList(int teamId, int trainingId);

	// @Secured({ "ROLE_ADMIN" })
	public User getUser(int userId);

	// @Secured({ "ROLE_ADMIN" })
	public User getUser(String userName);

	@Secured({ "ROLE_ADMIN" })
	public List<String> getUserRoles();

	// -----------------------------------------------------------
	// Actions only allowed by users with admin role
	// -----------------------------------------------------------

	public VolunteerTask getVolunteerTask(int volunteerTaskId);

	public List<VolunteerTask> getVolunteerTasks(int clubId, String seasonPeriod);

	public List<VolunteerTask> getVolunteerTasksForTeam(int teamId,
			String seasonPeriod);

	public boolean loadData(String filePath, String fileName);

	public Integer playerAddParent(Integer playerId, Integer parentId);

	public Integer playerRemoveParent(Integer playerId, Integer parentId);

	// @Secured({ "ROLE_ADMIN" })
	public List<User> getUsers();

	@Secured({ "ROLE_ADMIN" })
	public int saveUser(User user);

	// @Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveClub(Club club);

	// @Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveContact(Contact contact);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveContactRole(Integer contactId, String roleTypeName,
			String newRoleType);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveCup(Cup cup);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveLeague(League league);

	// @Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveMatch(Match match);

	// @Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public void savePlayer(Player player);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public void saveReferee(Referee referee);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveSubTask(SubTask subTask);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveTaskGroup(TaskGroup taskGroup);

	// @Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveTeam(Team team);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveTraining(Training training);

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	public int saveVolunteerTask(VolunteerTask task);

	public Match[] searchMatches(String seasonPeriod, Date fromDate, Date toDate);

	public int signupForCup(int playerId, int cupId);

	public int signupForMatch(int playerId, int matchId);

	public int signupForTraining(int playerId, int trainingId);

	public int unsignForCup(int playerId, int cupId);

	public int unsignForMatch(int playerId, int matchId);

	public int unsignForTraining(int playerId, int trainingId);

	public Integer unsignPersonFromSubTask(Integer subTaskId, Integer contactId);

	public Integer unsignPersonFromTask(int taskId, int contactId);

	public void updateGoalsAwayTeam(int matchId, int goals);

	public void updateGoalsHomeTeam(int matchId, int goals);

	public void updateMatchStatus(int matchId, int statusId);

	public int getTeamIdForUser(Integer id);

	public List<String> getFollowersPhoneNumbers(Integer eventId);

	public List<Person> getFollowers(Integer eventId);

}
