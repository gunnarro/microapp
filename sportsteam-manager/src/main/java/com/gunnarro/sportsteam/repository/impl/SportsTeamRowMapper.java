package com.gunnarro.sportsteam.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.ImageDetail;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.MatchStatus;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.activity.Status;
import com.gunnarro.sportsteam.domain.activity.Training;
import com.gunnarro.sportsteam.domain.activity.Type;
import com.gunnarro.sportsteam.domain.activity.Type.MatchTypesEnum;
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
import com.gunnarro.sportsteam.domain.statistic.MatchStatistic;
import com.gunnarro.sportsteam.domain.statistic.Statistic;
import com.gunnarro.sportsteam.domain.task.ScheduledTask;
import com.gunnarro.sportsteam.domain.task.SubTask;
import com.gunnarro.sportsteam.domain.task.TaskGroup;
import com.gunnarro.sportsteam.domain.task.VolunteerTask;
import com.gunnarro.sportsteam.domain.view.ActivityView;
import com.gunnarro.sportsteam.domain.view.list.Item;
import com.gunnarro.sportsteam.utility.Utility;

/**
 * This call contain RowMapper which is required for converting ResultSet into
 * java domain class
 * 
 */
public class SportsTeamRowMapper {

    private SportsTeamRowMapper() {
    }

    public static RowMapper<Club> mapToClubRM() {
        return new RowMapper<Club>() {
            @Override
            public Club mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Club club = new Club();
                club.setId(resultSet.getInt("id"));
                club.setFkAddressId(resultSet.getInt("fk_address_id"));
                club.setName(resultSet.getString("club_name"));
                club.setDepartmentName(resultSet.getString("club_department_name"));
                club.setClubNameAbbreviation(resultSet.getString("club_name_abbreviation"));
                club.setHomePageUrl(resultSet.getString("club_url_home_page"));
                club.setStadiumName(resultSet.getString("club_stadium_name"));
                try {
                    club.setNumberOfTeams(resultSet.getInt("number_of_teams"));
                } catch (SQLException sqle) {
                    // ignore, the column diden't exist
                }
                try {
                    club.setNumberOfReferees(resultSet.getInt("number_of_referees"));
                } catch (SQLException sqle) {
                    // ignore, the column diden't exist
                }
                try {
                    club.setNumberOfPlayers(resultSet.getInt("number_of_players"));
                } catch (SQLException sqle) {
                    // ignore, the column diden't exist
                }
                return club;
            }
        };
    }

    public static RowMapper<Team> mapToTeamRM() {
        return new RowMapper<Team>() {
            @Override
            public Team mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Team team = new Team();
                team.setId(resultSet.getInt("id"));
                team.setFkClubId(resultSet.getInt("fk_club_id"));
                team.setFkLeagueId(resultSet.getInt("fk_league_id"));
                team.setName(resultSet.getString("team_name"));
                team.setGender(resultSet.getString("team_gender"));
                team.setTeamYearOfBirth(resultSet.getInt("team_year_of_birth"));
                try {
                    team.setNumberOfPlayers(resultSet.getInt("number_of_players"));
                } catch (SQLException sqle) {
                    // ignore, the column diden't exist
                }
                try {
                    team.setNumberOfContacts(resultSet.getInt("number_of_contacts"));
                } catch (SQLException sqle) {
                    // ignore, the column diden't exist
                }
                return team;
            }
        };
    }

    public static RowMapper<Item> mapToItemPlayerSignedRM() {
        return new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Item item = new Item();
                item.setId(resultSet.getInt("id"));
                item.setValue(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                item.setType(resultSet.getString("type"));
                if (resultSet.getInt("signed") > 0) {
                    item.setSelected(true);
                } else {
                    item.setSelected(false);
                }
                return item;
            }
        };
    }

    public static RowMapper<Item> mapToItemRM() {
        return new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Item item = new Item();
                try {
                    item.setId(resultSet.getInt("id"));
                } catch (SQLException sqle) {
                }
                try {
                    item.setValue(resultSet.getString("value"));
                } catch (SQLException sqle) {
                }
                try {
                    item.setType(resultSet.getString("type"));
                } catch (SQLException sqle) {
                }
                try {
                    item.setName(resultSet.getString("team_name"));
                } catch (SQLException sqle) {
                }
                try {
                    boolean isEnabled = (resultSet.getInt("enabled") != 0);
                    item.setEnabled(isEnabled);
                } catch (SQLException sqle) {
                }
                try {
                    item.setStartDate(new Date(resultSet.getTimestamp("start_date").getTime()));
                } catch (SQLException sqle) {
                }

                try {
                    if (resultSet.getInt("signed") > 0) {
                        item.setSelected(true);
                    } else {
                        item.setSelected(false);
                    }
                } catch (SQLException sqle) {
                    // Ignore it, the signed did not appear in the result set
                    // which will happen for some query mappings
                }
                return item;
            }
        };
    }

    public static RowMapper<Address> mapToAddressRM() {
        return new RowMapper<Address>() {
            @Override
            public Address mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Address address = new Address(resultSet.getInt("id"));
                address.setCity(resultSet.getString("city"));
                address.setCountry(resultSet.getString("country"));
                address.setPostCode(resultSet.getString("zip_code"));
                address.setStreetName(resultSet.getString("street_name"));
                address.setStreetNumber(resultSet.getString("street_number"));
                address.setStreetNumberPostfix(resultSet.getString("street_number_postfix"));
                return address;
            }
        };
    }

    public static RowMapper<Player> mapToPlayerRM() {
        return new RowMapper<Player>() {
            @Override
            public Player mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Player player = new Player(resultSet.getInt("id"));
                player.setFkTeamId(resultSet.getInt("fk_team_id"));
                player.setFkAddressId(resultSet.getInt("fk_address_id"));
                player.setFkStatusId(resultSet.getInt("fk_status_id"));
                player.setFkPlayerPositionId(resultSet.getInt("fk_position_id"));
                player.setFirstName(resultSet.getString("first_name"));
                player.setMiddleName(resultSet.getString("middle_name"));
                player.setLastName(resultSet.getString("last_name"));
                player.setGender(resultSet.getString("gender"));
                player.setEmailAddress(resultSet.getString("email"));
                player.setMobileNumber(resultSet.getString("mobile"));
                player.setJerseyNumber(resultSet.getInt("jersey_number"));
                player.setSchoolName(resultSet.getString("school_name"));
                player.setDateOfBirth(new Date(resultSet.getTimestamp("date_of_birth").getTime()));
                return player;
            }
        };
    }

    public static RowMapper<Contact> mapToContactRM() {
        return new RowMapper<Contact>() {
            @Override
            public Contact mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Contact contact = new Contact();
                contact.setId(resultSet.getInt("id"));
                contact.setFkTeamId(resultSet.getInt("fk_team_id"));
                contact.setFkAddressId(resultSet.getInt("fk_address_id"));
                contact.setFkStatusId(resultSet.getInt("fk_status_id"));
                contact.setFirstName(resultSet.getString("first_name"));
                contact.setMiddleName(resultSet.getString("middle_name"));
                contact.setLastName(resultSet.getString("last_name"));
                contact.setGender(resultSet.getString("gender"));
                contact.setEmailAddress(resultSet.getString("email"));
                contact.setMobileNumber(resultSet.getString("mobile"));
                return contact;
            }
        };
    }

    public static RowMapper<Person> mapToPersonRM() {
        return new RowMapper<Person>() {
            @Override
            public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setFkAddressId(resultSet.getInt("fk_address_id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setMiddleName(resultSet.getString("middle_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setDateOfBirth(new Date(resultSet.getTimestamp("date_of_birth").getTime()));
                person.setGender(resultSet.getString("gender"));
                person.setEmailAddress(resultSet.getString("email"));
                person.setMobileNumber(resultSet.getString("mobile"));
                return person;
            }
        };
    }

    public static RowMapper<Referee> mapToRefereeRM() {
        return new RowMapper<Referee>() {
            @Override
            public Referee mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Referee referee = new Referee();
                referee.setId(resultSet.getInt("id"));
                referee.setFkClubId(resultSet.getInt("fk_club_id"));
                referee.setFkAddressId(resultSet.getInt("fk_address_id"));
                referee.setFirstName(resultSet.getString("first_name"));
                referee.setMiddleName(resultSet.getString("middle_name"));
                referee.setLastName(resultSet.getString("last_name"));
                referee.setGender(resultSet.getString("gender"));
                referee.setEmailAddress(resultSet.getString("email"));
                referee.setMobileNumber(resultSet.getString("mobile"));
                return referee;
            }
        };
    }

    public static RowMapper<Integer> mapToNumberOfOccurencesRM(final String columnLabel) {
        return new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getInt(columnLabel);
            }
        };
    }

    public static RowMapper<Match> mapToMatchRM() {
        return new RowMapper<Match>() {
            @Override
            public Match mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Match match = new Match();
                match.setId(resultSet.getInt("id"));
                match.setFkLeagueId(resultSet.getInt("fk_league_id"));
                match.setFkTeamId(resultSet.getInt("fk_team_id"));
                match.setFkSeasonId(resultSet.getInt("fk_season_id"));
                match.setFkRefereeId(resultSet.getInt("fk_referee_id"));
                match.setMatchTypeId(resultSet.getInt("fk_match_type_id"));
                match.setMatchStatus(new MatchStatus(resultSet.getInt("match_status_id"), resultSet.getString("match_status_name")));
                match.setStartDate(new Date(resultSet.getTimestamp("start_date").getTime()));
                match.setHomeTeam(new Team(resultSet.getInt("fk_team_id"), resultSet.getString("home_team_name")));
                match.setAwayTeam(new Team(resultSet.getString("away_team_name")));
                match.setVenue(resultSet.getString("venue"));
                match.setNumberOfGoalsHome(resultSet.getInt("goals_home_team"));
                match.setNumberOfGoalsAway(resultSet.getInt("goals_away_team"));
                return match;
            }
        };
    }

    public static RowMapper<Training> mapToTrainingRM() {
        return new RowMapper<Training>() {
            @Override
            public Training mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Training training = new Training();
                training.setId(resultSet.getInt("id"));
                training.setVenue(resultSet.getString("place"));
                training.setStartDate(new Date(resultSet.getTimestamp("start_time").getTime()));
                training.setEndDate(new Date(resultSet.getTimestamp("end_time").getTime()));
                training.setFkTeamId(resultSet.getInt("fk_team_id"));
                training.setFkSeasonId(resultSet.getInt("fk_season_id"));
                // Set start and end time
                training.setStartTime(Utility.formatTime(training.getStartDate().getTime(), "HH:mm"));
                training.setEndTime(Utility.formatTime(training.getEndDate().getTime(), "HH:mm"));
                return training;
            }
        };
    }

    public static RowMapper<Cup> mapToCupRM() {
        return new RowMapper<Cup>() {
            @Override
            public Cup mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Cup cup = new Cup();
                cup.setId(resultSet.getInt("id"));
                cup.setCupName(resultSet.getString("cup_name"));
                cup.setClubName(resultSet.getString("club_name"));
                cup.setVenue(resultSet.getString("venue"));
                cup.setEmail(resultSet.getString("email"));
                cup.setHomePage(resultSet.getString("home_page"));
                cup.setStartDate(new Date(resultSet.getTimestamp("start_date").getTime()));
                cup.setEndDate(new Date(resultSet.getTimestamp("end_date").getTime()));
                cup.setDeadlineDate(new Date(resultSet.getTimestamp("deadline_date").getTime()));
                cup.setFkSeasonId(resultSet.getInt("fk_season_id"));
                return cup;
            }
        };
    }

    public static RowMapper<Status> mapToStatusRM() {
        return new RowMapper<Status>() {
            @Override
            public Status mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Status status = new Status();
                status.setId(resultSet.getInt("id"));
                status.setName(resultSet.getString("name"));
                return status;
            }
        };
    }

    public static RowMapper<MatchStatus> mapToMatchStatusRM() {
        return new RowMapper<MatchStatus>() {
            @Override
            public MatchStatus mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                MatchStatus status = new MatchStatus();
                status.setId(resultSet.getInt("id"));
                status.setName(resultSet.getString("name"));
                return status;
            }
        };
    }

    public static RowMapper<Statistic> mapToPlayerStatisticRM() {
        return new RowMapper<Statistic>() {
            @Override
            public Statistic mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new Statistic(resultSet.getString("period"), null, resultSet.getString("team_name"), resultSet.getInt("player_id"),
                        resultSet.getInt("numberOfPlayerMatches"), resultSet.getInt("numberOfPlayerCups"), resultSet.getInt("numberOfPlayerTrainings"),
                        resultSet.getInt("numberOfTeamMatches"), resultSet.getInt("numberOfTeamCups"), resultSet.getInt("numberOfTeamTrainings"));
            }
        };
    }

    public static RowMapper<MatchStatistic> mapToMatchStatisticRM() {
        return new RowMapper<MatchStatistic>() {
            @Override
            public MatchStatistic mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                MatchStatistic matchStatistic = new MatchStatistic(MatchTypesEnum.LEAGUE);
                matchStatistic.setPosition(rowNum + 1);// because it starts at 0
                matchStatistic.setLeagueId(resultSet.getInt("leagueId"));
                matchStatistic.setSeasonId(resultSet.getInt("seasonId"));
                matchStatistic.setTeamName(resultSet.getString("teamName"));
                matchStatistic.setMatchTypeId(resultSet.getInt("matchTypeId"));
                matchStatistic.setWon(resultSet.getInt("numberOfWonMatches"));
                matchStatistic.setDraw(resultSet.getInt("numberOfDrawMatches"));
                matchStatistic.setLoss(resultSet.getInt("numberOfLossMatches"));
                matchStatistic.setGoalsScored(resultSet.getInt("numberOfGoalsScored"));
                matchStatistic.setGoalsAgainst(resultSet.getInt("numberOfGoalsAgainst"));
                matchStatistic.setScores(resultSet.getInt("scores"));
                return matchStatistic;
            }
        };
    }

    public static RowMapper<ActivityView> mapToActivityRM() {
        return new RowMapper<ActivityView>() {
            @Override
            public ActivityView mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                ActivityView activity = new ActivityView();
                activity.setId(resultSet.getInt("id"));
                activity.setType(resultSet.getString("type"));
                activity.setDescription(resultSet.getString("info"));
                activity.setPlace(resultSet.getString("place"));
                // activity.setStatus(resultSet.getString("status"));
                activity.setStartDate(new Date(resultSet.getTimestamp("start_date").getTime()));
                return activity;
            }
        };
    }

    public static RowMapper<Season> mapToSeasonRM() {
        return new RowMapper<Season>() {
            @Override
            public Season mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Season season = new Season();
                season.setId(resultSet.getInt("id"));
                season.setStartTime(resultSet.getTimestamp("start_date").getTime());
                season.setEndTime(resultSet.getTimestamp("end_date").getTime());
                // try {
                // season.setNumberOfMatches(resultSet.getInt("number_of_matches"));
                // } catch (SQLException sqle) {
                // // ignore, the column diden't exist
                // }
                // try {
                // season.setNumberOfCups(resultSet.getInt("number_of_cups"));
                // } catch (SQLException sqle) {
                // // ignore, the column diden't exist
                // }
                return season;
            }
        };
    }

  

    public static RowMapper<League> mapToLeagueRM() {
        return new RowMapper<League>() {
            @Override
            public League mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                League league = new League();
                league.setId(resultSet.getInt("id"));
                league.setName(resultSet.getString("name"));
                league.setFkLeagueCategoryId(resultSet.getInt("fk_league_category_id"));
                league.setFkLeagueStatusId(resultSet.getInt("fk_league_status_id"));
                league.setStatus(new Status(resultSet.getInt("fk_league_status_id"), resultSet.getString("league_status_name")));
                return league;
            }
        };
    }

    public static RowMapper<LeagueCategory> mapToLeagueCategoryRM() {
        return new RowMapper<LeagueCategory>() {
            @Override
            public LeagueCategory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                LeagueCategory leagueCategory = new LeagueCategory();
                leagueCategory.setId(resultSet.getInt("id"));
                leagueCategory.setName(resultSet.getString("name"));
                leagueCategory.setFkLeagueStatusId(resultSet.getInt("fk_league_status_id"));
                leagueCategory.setStatus(new Status(resultSet.getInt("fk_league_status_id"), resultSet.getString("league_status_name")));
                leagueCategory.setFkLeagueRuleId(resultSet.getInt("fk_league_rule_id"));
                leagueCategory.setSportType(resultSet.getString("sport_type"));
                return leagueCategory;
            }
        };
    }

    public static RowMapper<LeagueRule> mapToLeagueRuleRM() {
        return new RowMapper<LeagueRule>() {
            @Override
            public LeagueRule mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                LeagueRule leagueRule = new LeagueRule();
                leagueRule.setId(resultSet.getInt("id"));
                leagueRule.setName(resultSet.getString("name"));
                leagueRule.setDescription(resultSet.getString("description"));
                leagueRule.setGender(resultSet.getString("gender"));
                leagueRule.setPlayerAgeMin(resultSet.getInt("player_age_min"));
                leagueRule.setPlayerAgeMax(resultSet.getInt("player_age_max"));
                leagueRule.setMatchPeriodTimeMinutes(resultSet.getInt("match_period_time_minutes"));
                leagueRule.setMatchExtraPeriodTimeMinutes(resultSet.getInt("match_extra_period_time_minutes"));
                leagueRule.setNumberOfPlayers(resultSet.getInt("number_of_players"));
                leagueRule.setNumberOfSubstitutes(resultSet.getInt("number_of_substitutes"));
                leagueRule.setPointsWon(resultSet.getInt("points_win"));
                leagueRule.setPointsLoss(resultSet.getInt("points_loss"));
                leagueRule.setPointsDraw(resultSet.getInt("points_draw"));
                return leagueRule;
            }
        };
    }

    public static RowMapper<Type> mapToTypeRM() {
        return new RowMapper<Type>() {
            @Override
            public Type mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                Type type = new Type();
                type.setId(resultSet.getInt("id"));
                type.setName(resultSet.getString("name"));
                try {
                    type.setDescription(resultSet.getString("description"));
                } catch (SQLException sqle) {
                    // ignore, the column diden't exist
                }
                return type;
            }
        };
    }

    public static RowMapper<VolunteerTask> mapToVolunteerTaskRM() {
        return new RowMapper<VolunteerTask>() {
            @Override
            public VolunteerTask mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                VolunteerTask task = new VolunteerTask();
                task.setId(resultSet.getInt("id"));
                task.setFkClubId(resultSet.getInt("fk_club_id"));
                task.setFkSeasonId(resultSet.getInt("fk_season_id"));
                task.setFkTaskStatusId(resultSet.getInt("fk_task_status_id"));
                task.setFkAssignedToPersonId(resultSet.getInt("fk_assigned_to_person_id") == 0 ? null : resultSet.getInt("fk_assigned_to_person_id"));
                task.setStatus(new Status(resultSet.getInt("fk_task_status_id"), resultSet.getString("task_status_name")));
                task.setStartDate(new Date(resultSet.getTimestamp("start_date").getTime()));
                task.setEndDate(new Date(resultSet.getTimestamp("end_date").getTime()));
                task.setDeadlineDate(new Date(resultSet.getTimestamp("deadline_date").getTime()));
                task.setTaskName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                return task;
            }
        };
    }

    public static RowMapper<SubTask> mapToSubTaskRM() {
        return new RowMapper<SubTask>() {
            @Override
            public SubTask mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                SubTask subTask = new SubTask();
                subTask.setId(resultSet.getInt("id"));
                subTask.setFkParentTaskId(resultSet.getInt("fk_parent_task_id"));
                subTask.setFkTaskStatusId(resultSet.getInt("fk_task_status_id"));
                subTask.setFkAssignedToPersonId(resultSet.getInt("fk_assigned_to_person_id") == 0 ? null : resultSet.getInt("fk_assigned_to_person_id"));
                subTask.setStatus(new Status(resultSet.getInt("fk_task_status_id"), resultSet.getString("task_status_name")));
                subTask.setStartDate(new Date(resultSet.getTimestamp("start_date").getTime()));
                subTask.setEndDate(new Date(resultSet.getTimestamp("end_date").getTime()));
                subTask.setEndDate(new Date(resultSet.getTimestamp("deadline_date").getTime()));
                subTask.setTaskName(resultSet.getString("name"));
                subTask.setDescription(resultSet.getString("description"));
                return subTask;
            }
        };
    }

    public static RowMapper<TaskGroup> mapToTaskGroupRM() {
        return new RowMapper<TaskGroup>() {
            @Override
            public TaskGroup mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                TaskGroup taskGroup = new TaskGroup();
                taskGroup.setId(resultSet.getInt("id"));
                taskGroup.setName(resultSet.getString("name"));
                taskGroup.setDescription(resultSet.getString("description"));
                return taskGroup;
            }
        };
    }

    public static RowMapper<KeyValuePair> mapToKeyValueRM() {
        return new RowMapper<KeyValuePair>() {
            @Override
            public KeyValuePair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new KeyValuePair(resultSet.getString("key"), resultSet.getString("value"));
            }
        };
    }

    public static RowMapper<KeyValuePair> mapToKeyValueRM(final String keyColName, final String valueColName) {
        return new RowMapper<KeyValuePair>() {
            @Override
            public KeyValuePair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new KeyValuePair(resultSet.getString(keyColName), resultSet.getString(valueColName));
            }
        };
    }

    public static RowMapper<String> singelValueRM(final String colomnName) {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getString(colomnName);
            }
        };
    }

    public static RowMapper<User> mapToUserRM() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setActivated(resultSet.getInt("enabled") == 1 ? true : false);
                return user;
            }
        };
    }

    public static RowMapper<String> mapToRoleRM() {
        return new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getString("role");
            }
        };
    }

    public static RowMapper<ScheduledTask> mapToScheduledTaskRM() {
        return new RowMapper<ScheduledTask>() {
            @Override
            public ScheduledTask mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                ScheduledTask task = new ScheduledTask();
                task.setId(resultSet.getInt("id"));
                task.setTeamId(resultSet.getInt("team_id"));
                task.setName(resultSet.getString("task_name"));
                task.setType(resultSet.getString("task_type"));
                task.setDescription(resultSet.getString("task_description"));
                task.setCronExpression(resultSet.getString("cron_expression"));
                task.setEnabled(resultSet.getInt("task_enabled") == 1 ? true : false);
                return task;
            }
        };
    }

  

    public static RowMapper<KeyValuePair> mapToDietPlanMealRM() {
        return new RowMapper<KeyValuePair>() {
            @Override
            public KeyValuePair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                String name = resultSet.getString("meal_name") + " " + resultSet.getString("meal_period");
                return new KeyValuePair(name, resultSet.getString("meal_item_description"));
            }
        };
    }

    public static RowMapper<KeyValuePair> mapToDietMenuItemsRM() {
        return new RowMapper<KeyValuePair>() {
            @Override
            public KeyValuePair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                String name = resultSet.getString("menu_item_name");
                return new KeyValuePair(name, resultSet.getString("menu_item_description"));
            }
        };
    }

    public static RowMapper<KeyValuePair> mapToProductItemsRM() {
        return new RowMapper<KeyValuePair>() {
            @Override
            public KeyValuePair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return new KeyValuePair(resultSet.getString("name"), resultSet.getString("name"));
            }
        };
    }

    public static RowMapper<ImageDetail> mapToImageDetailRM() {
        return new RowMapper<ImageDetail>() {
            @Override
            public ImageDetail mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                ImageDetail imageDetail = new ImageDetail();
                imageDetail.setId(resultSet.getInt("id"));
                imageDetail.setName(resultSet.getString("image_name"));
                imageDetail.setFilePath(resultSet.getString("image_path"));
                imageDetail.setMappedAbsoluteFilePath(resultSet.getString("image_mapped_absolute_path"));
                imageDetail.setSize(resultSet.getLong("image_size_byte"));
                imageDetail.setGeoLocation(resultSet.getString("image_geo_location"));
                imageDetail.setCreatedDate(new Date(resultSet.getTimestamp("image_date_time").getTime()));
                imageDetail.setDescription(resultSet.getString("image_description"));
                imageDetail.setType(resultSet.getString("image_type"));
                return imageDetail;
            }
        };
    }

    public static RowMapper<Integer> mapCountRM() {
        return new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                return resultSet.getInt("count");
            }
        };
    }

}