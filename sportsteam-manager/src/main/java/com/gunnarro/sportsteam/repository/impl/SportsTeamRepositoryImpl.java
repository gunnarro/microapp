package com.gunnarro.sportsteam.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.ImageDetail;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Cup;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.MatchComparator;
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
import com.gunnarro.sportsteam.domain.party.Role;
import com.gunnarro.sportsteam.domain.party.User;
import com.gunnarro.sportsteam.domain.statistic.KeyValuePair;
import com.gunnarro.sportsteam.domain.statistic.MatchStatistic;
import com.gunnarro.sportsteam.domain.statistic.Statistic;
import com.gunnarro.sportsteam.domain.task.ScheduledTask;
import com.gunnarro.sportsteam.domain.task.SubTask;
import com.gunnarro.sportsteam.domain.task.TaskGroup;
import com.gunnarro.sportsteam.domain.task.VolunteerTask;
import com.gunnarro.sportsteam.domain.view.ActivityView;
import com.gunnarro.sportsteam.domain.view.KeyValuePairList;
import com.gunnarro.sportsteam.domain.view.list.Item;
import com.gunnarro.sportsteam.repository.SportsTeamRepository;
import com.gunnarro.sportsteam.repository.table.ClubsTable;
import com.gunnarro.sportsteam.repository.table.ImageDetailsTable;
import com.gunnarro.sportsteam.repository.table.LeaguesTable;
import com.gunnarro.sportsteam.repository.table.TableHelper;
import com.gunnarro.sportsteam.repository.table.TableHelper.PlayerLinkTableTypeEnum;
import com.gunnarro.sportsteam.repository.table.TeamsTable;
import com.gunnarro.sportsteam.repository.table.activity.CupsTable;
import com.gunnarro.sportsteam.repository.table.activity.MatchEventsTable;
import com.gunnarro.sportsteam.repository.table.activity.MatchesTable;
import com.gunnarro.sportsteam.repository.table.activity.SeasonsTable;
import com.gunnarro.sportsteam.repository.table.activity.TrainingsTable;
import com.gunnarro.sportsteam.repository.table.link.ContactRoleTypeLnkTable;
import com.gunnarro.sportsteam.repository.table.link.CupMatchLnkTable;
import com.gunnarro.sportsteam.repository.table.link.PlayerContactLnkTable;
import com.gunnarro.sportsteam.repository.table.link.PlayerCupLnkTable;
import com.gunnarro.sportsteam.repository.table.link.PlayerMatchLnkTable;
import com.gunnarro.sportsteam.repository.table.link.PlayerTrainingLnkTable;
import com.gunnarro.sportsteam.repository.table.link.SubTaskTaskGroupLnkTable;
import com.gunnarro.sportsteam.repository.table.link.TeamContactLnkTable;
import com.gunnarro.sportsteam.repository.table.link.TeamCupLnkTable;
import com.gunnarro.sportsteam.repository.table.party.AddressTable;
import com.gunnarro.sportsteam.repository.table.party.ContactsTable;
import com.gunnarro.sportsteam.repository.table.party.PersonsTable;
import com.gunnarro.sportsteam.repository.table.party.PlayersTable;
import com.gunnarro.sportsteam.repository.table.party.RefereesTable;
import com.gunnarro.sportsteam.repository.table.party.RolesTable;
import com.gunnarro.sportsteam.repository.table.party.UsersTable;
import com.gunnarro.sportsteam.repository.table.task.SubTasksTable;
import com.gunnarro.sportsteam.repository.table.task.TaskGroupsTable;
import com.gunnarro.sportsteam.repository.table.task.VolunteerTasksTable;
import com.gunnarro.sportsteam.service.exception.ApplicationException;
import com.gunnarro.sportsteam.utility.Utility;

/**
 * Database: jbossews User: admincnVhNH8 Password: suSNhqkXILV-
 * 
 * @author admin
 * 
 */
@Repository
// @Transactional
public class SportsTeamRepositoryImpl extends BaseJdbcRepository implements SportsTeamRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SportsTeamRepositoryImpl.class);

    private final static String WILD_CARD = "%";

    public SportsTeamRepositoryImpl() {
        super(null);
    }

    public SportsTeamRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createClub(Club club) {
        Integer addressId = null;
        if (club.getAddress() != null) {
            addressId = createAddress(club.getAddress());
        }
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(ClubsTable.createInsertPreparedStatement(club, addressId), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer createTeam(Team team) {
        try {
            if (!team.hasFkClubId()) {
                throw new ApplicationException("Team missing club Id. " + team.toString());
            }
            if (team.getGender() == null) {
                throw new ApplicationException("Team missing gender. " + team.toString());
            }
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(TeamsTable.createInsertPreparedStatement(team), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException(team + ", Ex: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createAddress(final Address address) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(AddressTable.createInsertQuery(), new String[] { "id" });
                ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                ps.setString(2, address.getStreetName());
                ps.setString(3, address.getStreetNumber());
                ps.setString(4, address.getStreetNumberPostfix());
                ps.setString(5, address.getPostCode());
                ps.setString(6, address.getCity());
                ps.setString(7, address.getCountry());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Person getPerson(Integer id) {
        Person person = null;
        try {
            person = getJdbcTemplate().queryForObject(SportsTeamSql.getByIdQuery("persons"), new Object[] { id }, SportsTeamRowMapper.mapToPersonRM());
            if (person.hasFkAddressId()) {
                person.setAddress(getAddress(person.getFkAddressId()));
            }
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
        }
        return person;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createPerson(Person person) {
        Integer addressId = null;
        if (person.getAddress() != null) {
            addressId = createAddress(person.getAddress());
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(PersonsTable.createInsertPreparedStatement(person, addressId), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public int updatePerson(Person person) {
        if (person.getAddress() != null) {
            updateAddress(person.getAddress());
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(person.toString());
        }
        return getJdbcTemplate().update(PersonsTable.createUpdateQuery(), PersonsTable.createUpdateParam(person));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deletePerson(Integer id) {
        Person person = getPerson(id);
        if (person != null && person.getAddress() != null) {
            deleteAddress(person.getAddress().getId());
        }
        int rows = getJdbcTemplate().update("DELETE FROM persons WHERE id = ?", new Object[] { id });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted person id=" + id + ", deleted rows=" + rows);
        }
        return rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createContact(Contact contact) {
        Integer addressId = null;
        if (contact.getAddress() != null) {
            addressId = createAddress(contact.getAddress());
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            getJdbcTemplate().update(ContactsTable.createInsertPreparedStatement(contact, addressId), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception sqle) {
            throw new ApplicationException(sqle.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createPlayer(Player player) {
        if (!player.hasFkTeamId()) {
            throw new ApplicationException("Player must be asigned to a team. Missing valid team!");
        }
        try {
            Integer addressId = null;
            if (player.getAddress() != null) {
                addressId = createAddress(player.getAddress());
            }

            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(PlayersTable.createInsertPreparedStatement(player, addressId), keyHolder);
            int playerId = keyHolder.getKey().intValue();

            if (player.hasParents()) {
                for (Contact parent : player.getParents()) {
                    Contact contact = getContact(parent.getFirstName(), parent.getLastName());
                    if (contact != null) {
                        createPlayerLink(PlayerLinkTableTypeEnum.CONTACT, Long.valueOf(playerId).intValue(), contact.getId().intValue());
                    } else {
                        LOG.error("No contact found for: " + parent.getFirstName() + " " + parent.getLastName());
                    }
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug(player.toString());
            }
            return playerId;
        } catch (Exception sqle) {
            LOG.error("Error creating: " + player);
            LOG.error(null, sqle);
            throw new ApplicationException(sqle.getMessage());
        }
    }

    private Contact getContact(String firstName, String lastName) {
        Contact contact = null;
        try {
            contact = getJdbcTemplate().queryForObject("SELECT * FROM contacts WHERE first_name = ? AND last_name = ?", new Object[] { firstName, lastName },
                    SportsTeamRowMapper.mapToContactRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
        if (contact.hasFkAddressId()) {
            contact.setAddress(getAddress(contact.getFkAddressId()));
        }

        if (contact.hasFkTeamId()) {
            contact.setTeam(getTeam(contact.getFkTeamId()));
        }

        if (contact.hasFkStatusId()) {
            contact.setStatus(getContactStatus(contact.getFkStatusId()));
        }

        return contact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createContactRoleTypeLnk(Integer contactId, String roleTypeName) {
        Type teamRoleType = getTeamRoleType(roleTypeName);
        return createLink(ContactRoleTypeLnkTable.TABLE_NAME, ContactRoleTypeLnkTable.getFKColumnNames(), new Object[] { contactId, teamRoleType.getId() });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getContactRoleTypeLnkId(Integer contactId, String roleTypeName) {
        Type teamRoleType = getTeamRoleType(roleTypeName);
        return getLinkId(ContactRoleTypeLnkTable.TABLE_NAME, ContactRoleTypeLnkTable.getFKColumnNames(), new Object[] { contactId, teamRoleType.getId() });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateContactRoleTypeLnk(Integer contactId, String roleTypeName, String newRoleTypeName) {
        Integer linkId = getContactRoleTypeLnkId(contactId, roleTypeName);
        Type newRoleType = getTeamRoleType(newRoleTypeName);
        return updateLink(ContactRoleTypeLnkTable.TABLE_NAME, ContactRoleTypeLnkTable.COLUMN_FK_TEAM_ROLE_TYPE_ID, new Object[] { newRoleType.getId(), linkId });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteContactRoleTypeLnk(Integer contactId, String roleTypeName) {
        Type teamRoleType = getTeamRoleType(roleTypeName);
        return deleteLinkAll(ContactRoleTypeLnkTable.TABLE_NAME, ContactRoleTypeLnkTable.getFKColumnNames(), new Object[] { contactId, teamRoleType.getId() });
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public int deleteAllContactRoleTypeLnk(Integer contactId) {
        int rows = deleteAllLink(ContactRoleTypeLnkTable.TABLE_NAME, ContactRoleTypeLnkTable.COLUMN_FK_CONTACT_ID, contactId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Deleted number of links: " + rows + ", for contactId=" + contactId);
        }
        return rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateClub(Club club) {
        Integer addressId = null;
        if (club.isAddressNew()) {
            addressId = createAddress(club.getAddress());
        } else if (club.hasAddress()) {
            updateAddress(club.getAddress());
            addressId = club.getAddress().getId();
        } else if (club.getAddress() == null || club.getAddress().isAddressEmpty()) {
            // The address was deleted, so remove it
            addressId = null;
            // have to reload the club in order to get the address id
            Club c = getClub(club.getId());
            deleteAddress(c.getAddress().getId());
        }
        return getJdbcTemplate().update(ClubsTable.createUpdateQuery(), ClubsTable.createUpdateParam(club, addressId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteClub(Integer id) {
        Club club = getClub(id);
        if (club != null && club.getAddress() != null) {
            deleteAddress(club.getAddress().getId());
        }
        return getJdbcTemplate().update("DELETE FROM clubs WHERE id = ?", new Object[] { id });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteTeam(Integer id) {
        return getJdbcTemplate().update("DELETE FROM teams WHERE id = ?", new Object[] { id });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // @Transactional(readOnly = true)
    public List<Club> getClubs() {
        return getJdbcTemplate().query(SportsTeamSql.getAllClubsQuery(), new Object[] {}, SportsTeamRowMapper.mapToClubRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Club> searchClubs(String clubName, String departmentName) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT c.*");
            query.append(" FROM clubs c");
            query.append(" WHERE upper(c.club_name) LIKE upper(?)");
            query.append(" AND upper(c.club_department_name) LIKE upper(?)");
            return getJdbcTemplate().query(query.toString(), new Object[] { clubName.replace("*", "%"), departmentName.replace("*", "%") }, SportsTeamRowMapper.mapToClubRM());
        } catch (EmptyResultDataAccessException erdae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erdae);
            }
            // No hit return a empty list
            return new ArrayList<Club>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Team> searchTeams(Integer clubId, String teamName, Integer leagueId) {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("clubId=" + clubId + ", temaName=" + teamName + ", leagueId=" + leagueId);
            }
            Object[] params = new Object[] { teamName.replace("*", "%") };
            StringBuilder query = new StringBuilder();
            query.append("SELECT t.*");
            query.append(" FROM teams t");
            query.append(" WHERE upper(t.team_name) LIKE upper(?)");
            if (clubId != null) {
                query.append(" AND t.fk_club_id = ?");
                params = new Object[] { teamName.replace("*", "%"), clubId };
            }
            if (leagueId != null) {
                query.append(" AND t.fk_league_id = ?");
                params = new Object[] { teamName.replace("*", "%"), clubId, leagueId, };
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug(query.toString());
            }
            return getJdbcTemplate().query(query.toString(), params, SportsTeamRowMapper.mapToTeamRM());
        } catch (EmptyResultDataAccessException erdae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erdae);
            }
            // No hits, return a empty list
            return new ArrayList<Team>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Team> getTeams(Integer clubId) {
        return getJdbcTemplate().query(SportsTeamSql.getTeamsForClubQuery(), new Object[] { clubId }, SportsTeamRowMapper.mapToTeamRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // @Transactional(readOnly = true)
    public Club getClub(Integer id) {
        try {
            Club club = getJdbcTemplate().queryForObject(SportsTeamSql.getByIdQuery("clubs"), new Object[] { id }, SportsTeamRowMapper.mapToClubRM());
            if (club.hasFkAddressId()) {
                club.setAddress(getAddress(club.getFkAddressId()));
            }
            return club;
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // @Transactional(readOnly = true)
    public Club getClub(String name, String departmentName) {
        try {
            Club club = getJdbcTemplate().queryForObject("SELECT * from clubs WHERE club_name = ? AND club_department_name = ?", new Object[] { name, departmentName },
                    SportsTeamRowMapper.mapToClubRM());
            if (club.hasFkAddressId()) {
                Address address = getAddress(club.getFkAddressId());
                club.setAddress(address);
            }
            return club;
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Data not found for: " + name + ", " + departmentName);
                LOG.warn(null, erae);
            }
            return null;
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Team getTeam(Integer clubId, String teamName) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT *,");
        query.append(" (SELECT count(p.id) FROM players p WHERE p.fk_team_id = t.id) AS number_of_players,");
        query.append(" (SELECT count(c.id) FROM contacts c WHERE c.fk_team_id = t.id) AS number_of_contacts");
        query.append(" FROM teams t");
        if (clubId != null) {
            query.append(" WHERE t.fk_club_id = ? AND t.team_name = ?");
            return getTeam(query.toString(), new Object[] { clubId, teamName });
        } else {
            query.append(" WHERE t.team_name = ?");
            return getTeam(query.toString(), new Object[] { teamName });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Team getTeam(Integer id) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT t.*,");
        query.append(" (SELECT count(p.id) FROM players p WHERE p.fk_team_id = t.id) AS number_of_players,");
        query.append(" (SELECT count(c.id) FROM contacts c WHERE c.fk_team_id = t.id) AS number_of_contacts");
        query.append(" FROM teams t");
        query.append(" WHERE t.id = ?");
        return getTeam(query.toString(), new Object[] { id });
    }

    private Team getTeam(String query, Object[] selectionArgs) {
        Team team = null;
        try {
            team = getJdbcTemplate().queryForObject(query, selectionArgs, SportsTeamRowMapper.mapToTeamRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            StringBuilder args = new StringBuilder();
            for (Object o : selectionArgs) {
                args.append(o);
            }
            LOG.error("query:" + query + ", args: " + args.toString());
            LOG.error(null, erae);
            return null;
        }

        Contact teamLead = getTeamContactPerson(team.getId(), Role.RoleTypesEnum.TEAMLEAD.name());
        if (teamLead != null) {
            team.setFkTeamleadId(teamLead.getId());
            team.setTeamLead(teamLead);
        }

        Contact coach = getTeamContactPerson(team.getId(), Role.RoleTypesEnum.COACH.name());
        if (coach != null) {
            team.setFkCoachId(coach.getId());
            team.setCoach(coach);
        }

        if (team.hasFkClubId()) {
            team.setClub(getClub(team.getFkClubId()));
        }

        if (team.hasFkLeagueId()) {
            team.setLeague(getLeague(team.getFkLeagueId()));
        }
        return team;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Type> getLeagueTypes(Integer seasonId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT l.*,");
        query.append(" (SELECT count(m.fk_league_id) FROM matches m WHERE m.fk_league_id = l.id AND m.fk_season_id = ?) AS description");
        query.append(" FROM leagues l");
        query.append(" ORDER BY name ASC");
        return getJdbcTemplate().query(query.toString(), new Object[] { seasonId }, SportsTeamRowMapper.mapToTypeRM());
    }

    // TODO FIXME
    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public League getLeague(String sportsType, String leagueName) {
        return getLeague(leagueName);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public League getLeague(Integer id) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT l.*, s.name AS league_status_name");
            query.append(" FROM leagues l, league_statuses s");
            query.append(" WHERE l.fk_league_status_id = s.id");
            query.append(" AND l.id = ?");
            League league = getJdbcTemplate().queryForObject(query.toString(), new Object[] { id }, SportsTeamRowMapper.mapToLeagueRM());
            league.setLeagueCategory(getLeagueCategory(league.getFkLeagueCategoryId()));
            return league;
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("No leagues found for name=" + id);
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public LeagueCategory getLeagueCategory(Integer id) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT l.*, s.name AS league_status_name, t.name AS sport_type");
            query.append(" FROM league_categories l, league_statuses s, sport_types t");
            query.append(" WHERE l.fk_league_status_id = s.id");
            query.append(" AND l.fk_sport_type_id = t.id");
            query.append(" AND l.id = ?");
            LeagueCategory leagueCategory = getJdbcTemplate().queryForObject(query.toString(), new Object[] { id }, SportsTeamRowMapper.mapToLeagueCategoryRM());

            if (leagueCategory.hasFkLeagueRuleId()) {
                leagueCategory.setLeagueRule(getLeagueRule(leagueCategory.getFkLeagueRuleId()));
            }
            return leagueCategory;
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("No league category found for id=" + id);
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public LeagueRule getLeagueRule(Integer id) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT *");
            query.append(" FROM league_rules");
            query.append(" WHERE id = ?");
            return getJdbcTemplate().queryForObject(query.toString(), new Object[] { id }, SportsTeamRowMapper.mapToLeagueRuleRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("No league rule found for id=" + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public League getLeague(String leagueName) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT l.*, s.name AS league_status_name");
            query.append(" FROM leagues l, league_statuses s");
            query.append(" WHERE l.fk_league_status_id = s.id");
            query.append(" AND upper(l.name) = upper(?)");
            League league = getJdbcTemplate().queryForObject(query.toString(), new Object[] { leagueName }, SportsTeamRowMapper.mapToLeagueRM());
            league.setLeagueCategory(getLeagueCategory(league.getFkLeagueCategoryId()));
            return league;
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("No leagues found for name=" + leagueName);
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public int deleteLeague(int id) {
        int rows = getJdbcTemplate().update("DELETE FROM leagues WHERE id = ?", new Object[] { id });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted address id=" + id + ", deleted rows=" + rows);
        }
        return rows;
    }

    // TODO
    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public int createLeague(League league) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(LeaguesTable.createInsertPreparedStatement(league), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException(league.toString() + ", Ex: " + e.getMessage());
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public int updateLeague(League league) {
        return 0;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<League> getLeagues() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT l.*, s.name AS league_status_name");
        query.append(" FROM leagues l, league_statuses s");
        query.append(" WHERE l.fk_league_status_id = s.id");
        query.append(" ORDER BY l.name ASC");
        return getJdbcTemplate().query(query.toString(), new Object[] {}, SportsTeamRowMapper.mapToLeagueRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteAddress(Integer id) {
        int rows = getJdbcTemplate().update("DELETE FROM addresses WHERE id = ?", new Object[] { id });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted address id=" + id + ", deleted rows=" + rows);
        }
        return rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Address getAddress(Integer id) {
        try {
            return getJdbcTemplate().queryForObject(SportsTeamSql.getByIdQuery("addresses"), new Object[] { id }, SportsTeamRowMapper.mapToAddressRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatchListByTeamName(String teamName, Integer leagueId, Integer seasonId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT m.*, s.name AS match_status_name, s.id AS match_status_id");
        query.append(" FROM matches m, match_status_types s");
        query.append(" WHERE m.fk_match_status_id = s.id");
        query.append(" AND fk_league_id = ?");
        query.append(" AND fk_season_id = ?");
        query.append(" AND (home_team_name LIKE ? OR away_team_name LIKE ?)");
        query.append(" ORDER BY start_date ASC");
        List<Match> matches = getJdbcTemplate().query(query.toString(), new Object[] { leagueId, seasonId, teamName, teamName }, SportsTeamRowMapper.mapToMatchRM());
        Collections.sort(matches, new MatchComparator());
        return matches;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatchListForClub(Integer clubId, Integer seasonId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("clubId:" + clubId + ", seasonId: " + seasonId);
        }
        StringBuilder query = new StringBuilder();
        query.append("SELECT m.*, s.name AS match_status_name, s.id AS match_status_id");
        query.append(" FROM matches m, match_status_types s, teams t");
        query.append(" WHERE m.fk_match_status_id = s.id");
        query.append(" AND m.fk_team_id = t.id");
        query.append(" AND t.fk_club_id = ?");
        query.append(" AND m.fk_season_id = ?");
        query.append(" ORDER BY m.start_date ASC");
        if (LOG.isInfoEnabled()) {
            LOG.info("query: " + query.toString());
        }
        List<Match> matches = getJdbcTemplate().query(query.toString(), new Object[] { clubId, seasonId }, SportsTeamRowMapper.mapToMatchRM());
        Collections.sort(matches);
        return matches;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Match[] searchMatches(Integer seasonId, Date fromDate, Date toDate) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT m.*, s.name AS match_status_name, s.id AS match_status_id");
        query.append(" FROM matches m, match_status_types s");
        query.append(" WHERE m.fk_match_status_id = s.id");
        query.append(" AND date(m.start_date) >= ? AND date(m.start_date) <= ?");
        query.append(" AND m.fk_season_id = ?");
        query.append(" ORDER BY start_date ASC");
        List<Match> list = getJdbcTemplate().query(query.toString(),
                new Object[] { Utility.formatTime(fromDate.getTime(), "yyyy-MM-dd"), Utility.formatTime(toDate.getTime(), "yyyy-MM-dd"), seasonId },
                SportsTeamRowMapper.mapToMatchRM());
        Match[] m = new Match[list.size()];
        list.toArray(m);
        Arrays.sort(m);
        return m;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Match[] getMatchListByLeague(Integer leagueId, Integer seasonId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT m.*, s.name AS match_status_name, s.id AS match_status_id");
        query.append(" FROM matches m, match_status_types s");
        query.append(" WHERE m.fk_match_status_id = s.id");
        query.append(" AND fk_league_id = ?");
        query.append(" AND fk_season_id = ?");
        query.append(" ORDER BY start_date ASC");
        List<Match> list = getJdbcTemplate().query(query.toString(), new Object[] { leagueId, seasonId }, SportsTeamRowMapper.mapToMatchRM());
        Match[] m = new Match[list.size()];
        list.toArray(m);
        Arrays.sort(m);
        // Collections.sort(matches, new MatchComparator());
        // return matches;
        return m;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatchListForLeague(Integer leagueId, Integer seasonId, String teamName) {
        if (StringUtils.isEmpty(teamName)) {
            return getJdbcTemplate().query("SELECT * FROM matches WHERE fk_league_id = ? AND fk_season_id = ? ORDER BY start_date ASC", new Object[] { leagueId, seasonId },
                    SportsTeamRowMapper.mapToMatchRM());
        } else {
            return getJdbcTemplate().query(
                    "SELECT * FROM matches WHERE fk_league_id = ? AND fk_season_id = ? AND (home_team_name = ? OR away_team_name = ?) ORDER BY start_date ASC",
                    new Object[] { leagueId, seasonId, teamName, teamName }, SportsTeamRowMapper.mapToMatchRM());
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatchList(String teamName, Integer leagueId, Integer seasonId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT m.*, s.name AS match_status_name, s.id AS match_status_id");
        query.append(" FROM matches m, match_status_types s");
        query.append(" WHERE m.fk_match_status_id = s.id");
        query.append(" AND m.fk_league_id = ?");
        query.append(" AND m.fk_season_id = ?");
        query.append(" AND (home_team_name LIKE ? OR away_team_name LIKE ?)");
        query.append(" ORDER BY start_date ASC");
        return getJdbcTemplate().query(query.toString(), new Object[] { leagueId, seasonId, teamName, teamName }, SportsTeamRowMapper.mapToMatchRM());
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Match> getMatchList(Integer teamId, Integer seasonId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT m.*, s.name AS match_status_name, s.id AS match_status_id");
        query.append(" FROM matches m, match_status_types s");
        query.append(" WHERE m.fk_match_status_id = s.id");
        query.append(" AND m.fk_team_id = ?");
        query.append(" AND m.fk_season_id = ?");
        query.append(" ORDER BY start_date ASC");
        return getJdbcTemplate().query(query.toString(), new Object[] { teamId, seasonId }, SportsTeamRowMapper.mapToMatchRM());
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Match findMatch(Date startDate, String homeTeamName, String awayTeamName) {
        try {
            String date = Utility.formatTime(startDate.getTime(), "yyyy-MM-dd");
            StringBuilder query = new StringBuilder();
            query.append("SELECT m.*, s.name AS match_status_name, s.id AS match_status_id");
            query.append(" FROM matches m, match_status_types s");
            query.append(" WHERE m.fk_match_status_id = s.id");
            query.append(" AND m.start_date = ?");
            query.append(" AND m.home_team_name = ?");
            query.append(" AND m.away_team_name = ?");
            return getJdbcTemplate().queryForObject(query.toString(), new Object[] { date, homeTeamName, awayTeamName }, SportsTeamRowMapper.mapToMatchRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Match getMatch(Integer id) {
        Match match = null;
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT m.*, s.name AS match_status_name, s.id AS match_status_id");
            query.append(" FROM matches m, match_status_types s");
            query.append(" WHERE m.fk_match_status_id = s.id");
            query.append(" AND m.id = ?");
            match = getJdbcTemplate().queryForObject(query.toString(), new Object[] { id }, SportsTeamRowMapper.mapToMatchRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }

        if (match.hasFkLeagueId()) {
            match.setLeague(getLeague(match.getFkLeagueId()));
        }

        if (match.hasFkTeamId()) {
            match.setTeam(getTeam(match.getFkTeamId()));
        }

        if (match.hasFkSeasonId()) {
            match.setSeason(getSeason(match.getFkSeasonId()));
        }

        if (match.hasFkRefereeId()) {
            match.setReferee(getReferee(match.getFkRefereeId()));
        }
        int numberOfSignedPlayers = getNumberOfSignedPlayers(match.getType(), match.getId());
        match.setNumberOfSignedPlayers(numberOfSignedPlayers);
        return match;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Player> getPlayers(Integer teamId, Status status) {
        return getJdbcTemplate().query(SportsTeamSql.getPlayersForTeamQuery(), new Object[] { teamId, status.getId() }, SportsTeamRowMapper.mapToPlayerRM());
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Contact> getContacts(Integer teamId, Status status) {
        List<Contact> contacts = getJdbcTemplate().query(SportsTeamSql.getContactsForTeamQuery(), new Object[] { teamId, status.getId() }, SportsTeamRowMapper.mapToContactRM());
        for (Contact contact : contacts) {
            contact.setTeamRoleList(getRolesForContact(contact.getId()));
        }
        return contacts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer(Integer id) {
        Player player = null;
        try {
            player = getJdbcTemplate().queryForObject(SportsTeamSql.getByIdQuery("players"), new Object[] { id }, SportsTeamRowMapper.mapToPlayerRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
        if (player.hasFkAddressId()) {
            player.setAddress(getAddress(player.getFkAddressId()));
        }

        if (player.hasFkTeamId()) {
            player.setTeam(getTeam(player.getFkTeamId()));
        }

        if (player.hasFkStatusId()) {
            player.setStatus(getPlayerStatus(player.getFkStatusId()));
        }

        if (player.hasFkPlayerPositionId()) {
            player.setPosition(getPlayerPosition(player.getFkPlayerPositionId()));
        }
        return player;
    }

    private Type getMatchType(Integer id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT id, name, description FROM match_types", new Object[] {}, SportsTeamRowMapper.mapToTypeRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("No Season found for id=" + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Season getSeason(Integer id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * from seasons WHERE id = ?", new Object[] { id }, SportsTeamRowMapper.mapToSeasonRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("No Season found for id=" + id);
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Contact getContact(Integer id) {
        Contact contact = null;
        try {
            contact = getJdbcTemplate().queryForObject(SportsTeamSql.getByIdQuery("contacts"), new Object[] { id }, SportsTeamRowMapper.mapToContactRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
        if (contact.hasFkAddressId()) {
            contact.setAddress(getAddress(contact.getFkAddressId()));
        }

        if (contact.hasFkTeamId()) {
            contact.setTeam(getTeam(contact.getFkTeamId()));
        }

        if (contact.hasFkStatusId()) {
            contact.setStatus(getContactStatus(contact.getFkStatusId()));
        }

        contact.setTeamRoleList(getRolesForContact(contact.getId()));
        return contact;
    }

    private List<Type> getRolesForContact(Integer contactId) {
        return getJdbcTemplate()
                .query("SELECT t.id, t.name, t.description FROM team_role_types t, contact_role_type_lnk l WHERE l.fk_contact_id = ? AND l.fk_team_role_type_id = t.id ORDER BY t.name ASC",
                        new Object[] { contactId }, SportsTeamRowMapper.mapToTypeRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Referee getReferee(Integer id) {
        Referee referee = null;
        try {
            referee = getJdbcTemplate().queryForObject(SportsTeamSql.getByIdQuery("referees"), new Object[] { id }, SportsTeamRowMapper.mapToRefereeRM());

            if (referee.hasFkAddressId()) {
                Address address = getAddress(referee.getFkAddressId());
                referee.setAddress(address);
            }

            if (referee.hasFkClubId()) {
                Club club = getClub(referee.getFkClubId());
                referee.setClub(club);
            }
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
        return referee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deletePlayer(Integer id) {
        Player player = getPlayer(id);
        if (player != null && player.getAddress() != null) {
            deleteAddress(player.getAddress().getId());
        }
        return getJdbcTemplate().update("DELETE FROM players WHERE id = ?", new Object[] { id });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteContact(Integer id) {
        Contact contact = getContact(id);
        if (contact != null && contact.getAddress() != null) {
            deleteAddress(contact.getAddress().getId());
        }
        int rows = getJdbcTemplate().update("DELETE FROM contacts WHERE id = ?", new Object[] { id });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted contact id=" + id + ", deleted rows=" + rows);
        }
        return rows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteReferee(Integer id) {
        Referee referee = getReferee(id);
        if (referee != null && referee.getAddress() != null) {
            deleteAddress(referee.getAddress().getId());
        }
        return getJdbcTemplate().update("DELETE FROM referees WHERE id = ?", new Object[] { id });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteMatch(Integer id) {
        return getJdbcTemplate().update("DELETE FROM matches WHERE id = ?", new Object[] { id });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createMatch(Match match) {
        try {
            if (match.getMatchStatus().getId() == null) {
                match.setMatchStatus(getMatchStatus(match.getMatchStatus().getName()));
            }
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(MatchesTable.createInsertPreparedStatement(match), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (DuplicateKeyException de) {
            LOG.error(null, de);
            return -1;
        }
    }

    /**
     * FIXME {@inheritDoc}
     */
    @Override
    public int updateMatch(Match match) {
        return getJdbcTemplate().update(MatchesTable.createUpdateQuery(), MatchesTable.createUpdateParam(match));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteMatchEvents(Integer matchId) {
        return getJdbcTemplate().update("DELETE FROM match_events WHERE fk_match_id = ?", new Object[] { matchId });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteTraining(Integer id) {
        return getJdbcTemplate().update("DELETE FROM trainings WHERE id = ?", new Object[] { id });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteCup(Integer id) {
        return getJdbcTemplate().update("DELETE FROM cups WHERE id = ?", new Object[] { id });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteAllCups() {
        return getJdbcTemplate().update("DELETE FROM cups WHERE id > -1", new Object[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteAllTrainings() {
        return getJdbcTemplate().update("DELETE FROM trainings WHERE id > -1", new Object[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteAllMatches() {
        return getJdbcTemplate().update("DELETE FROM matches WHERE id > -1", new Object[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteAllReferees() {
        return getJdbcTemplate().update("DELETE FROM referees WHERE id > -1", new Object[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteAllContacts() {
        return getJdbcTemplate().update("DELETE FROM contacts WHERE id > -1", new Object[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteAllPlayers() {
        return getJdbcTemplate().update("DELETE FROM players WHERE id > -1", new Object[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteAllVolunteerTasks() {
        return getJdbcTemplate().update("DELETE FROM volunteer_tasks WHERE id > -1", new Object[] {});
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Item> getMatchPlayerList(Integer teamId, Integer matchId) {
        return getPlayerList(SportsTeamSql.getSignedPlayerNamesQuery("match"), new Object[] { matchId, teamId });
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Item> getCupTeamList(Integer cupId) {
        return getJdbcTemplate().query(SportsTeamSql.getRegistreredTeamNamesForCupQuery(), new Object[] { cupId }, SportsTeamRowMapper.mapToItemPlayerSignedRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> getCalendarUrls() {
        return getJdbcTemplate().query("SELECT c.id, c.team_name, c.url AS value, c.name AS type, c.enabled FROM calendar_urls c WHERE c.enabled = 1 ORDER BY c.name ASC",
                new Object[] {}, SportsTeamRowMapper.mapToItemRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCalendarUrl(String name) {
        try {
            return getJdbcTemplate().queryForObject("SELECT c.url FROM calendar_urls c WHERE c.name = ?", new Object[] { name }, String.class);
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("No hit for name=" + name);
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Item> getTrainingPlayerList(Integer teamId, Integer trainingId) {
        return getPlayerList(SportsTeamSql.getSignedPlayerNamesQuery("training"), new Object[] { trainingId, teamId });
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public List<Item> getCupPlayerList(Integer teamId, Integer cupId) {
        return getPlayerList(SportsTeamSql.getSignedPlayerNamesQuery("cup"), new Object[] { cupId, teamId });
    }

    private List<Item> getPlayerList(String query, Object[] values) {
        return getJdbcTemplate().query(query, values, SportsTeamRowMapper.mapToItemPlayerSignedRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createTeamContactlink(Integer teamId, Integer contactId) {
        return createLink(TeamContactLnkTable.TABLE_NAME, TeamContactLnkTable.getFKColumnNames(), new Object[] { teamId, contactId });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteTeamContactlink(Integer teamId, Integer contactId) {
        return deleteLinkAll(TeamContactLnkTable.TABLE_NAME, TeamContactLnkTable.getFKColumnNames(), new Object[] { teamId, contactId });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createTeamCupLink(Integer teamId, Integer cupId) {
        return createLink(TeamCupLnkTable.TABLE_NAME, TeamCupLnkTable.getFKColumnNames(), new Object[] { teamId, cupId });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteTeamCupLink(Integer teamId, Integer cupId) {
        return deleteLinkAll(TeamCupLnkTable.TABLE_NAME, TeamCupLnkTable.getFKColumnNames(), new Object[] { teamId, cupId });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deletePlayerLink(PlayerLinkTableTypeEnum type, Integer playerId, Integer id) {
        String linkToId = WILD_CARD;
        if (id != null) {
            linkToId = id.toString();
        }

        String[] values = new String[] { playerId.toString(), linkToId };
        if (type == PlayerLinkTableTypeEnum.CONTACT) {
            return deleteLinkAll(PlayerContactLnkTable.TABLE_NAME, PlayerContactLnkTable.getFKColumnNames(), values);
        } else if (type == PlayerLinkTableTypeEnum.MATCH) {
            return deleteLinkAll(PlayerMatchLnkTable.TABLE_NAME, PlayerMatchLnkTable.getFKColumnNames(), values);
        } else if (type == PlayerLinkTableTypeEnum.CUP) {
            return deleteLinkAll(PlayerCupLnkTable.TABLE_NAME, PlayerCupLnkTable.getFKColumnNames(), values);
        } else if (type == PlayerLinkTableTypeEnum.TRAINING) {
            return deleteLinkAll(PlayerTrainingLnkTable.TABLE_NAME, PlayerTrainingLnkTable.getFKColumnNames(), values);
        } else {
            throw new ApplicationException("Invalid link table type: " + type);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Contact> getContactsForPlayer(Integer playerId) {
        return getJdbcTemplate().query("SELECT * FROM contacts c, player_contact_lnk l WHERE l.fk_player_id = ? AND c.id = l.fk_contact_id", new Object[] { playerId },
                SportsTeamRowMapper.mapToContactRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> getContactsForPlayerItem(Integer playerId) {
        return getJdbcTemplate()
                .query("SELECT c.id, CONCAT_WS(',',c.first_name,c.middle_name,c.last_name) AS value,'parent' AS type FROM contacts c, player_contact_lnk l WHERE l.fk_player_id = ? AND c.id = l.fk_contact_id",
                        new Object[] { playerId }, SportsTeamRowMapper.mapToItemRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> getPlayersForContactItem(Integer contactId) {
        return getJdbcTemplate()
                .query("SELECT p.id, CONCAT_WS(',',p.first_name,p.middle_name,p.last_name) AS value,'player' AS type FROM players p, player_contact_lnk l WHERE l.fk_contact_id = ? AND p.id = l.fk_player_id",
                        new Object[] { contactId }, SportsTeamRowMapper.mapToItemRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createPlayerLink(PlayerLinkTableTypeEnum type, int playerId, int id) {
        Object[] values = new Object[] { playerId, id };
        if (type == PlayerLinkTableTypeEnum.CONTACT) {
            return createLink(PlayerContactLnkTable.TABLE_NAME, PlayerContactLnkTable.getFKColumnNames(), values);
        } else if (type == PlayerLinkTableTypeEnum.MATCH) {
            return createLink(PlayerMatchLnkTable.TABLE_NAME, PlayerMatchLnkTable.getFKColumnNames(), values);
        } else if (type == PlayerLinkTableTypeEnum.CUP) {
            return createLink(PlayerCupLnkTable.TABLE_NAME, PlayerCupLnkTable.getFKColumnNames(), values);
        } else if (type == PlayerLinkTableTypeEnum.TRAINING) {
            return createLink(PlayerTrainingLnkTable.TABLE_NAME, PlayerTrainingLnkTable.getFKColumnNames(), values);
        } else {
            throw new ApplicationException("Invalid link table type: " + type);
        }
    }

    private int createLink(String tableName, String[] columnNames, Object[] values) {
        // Check for null's
        if (StringUtils.isEmpty(tableName)) {
            throw new ApplicationException("Table name not set!");
        }
        TableHelper.checkInputs(columnNames, values);

        try {
            String query = TableHelper.createInsertQuery(tableName, columnNames);
            if (LOG.isDebugEnabled()) {
                LOG.debug("query=" + query.toString() + ", " + values[0] + ", " + values[1]);
            }
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(TableHelper.createInsertPreparedStatement(query, values), keyHolder);
            LOG.debug("Created new link in table: " + tableName + " whith id " + keyHolder.getKey().intValue() + " for " + values[0] + ", " + values[1]);
            runQuery("SELECT * FROM " + tableName, Type.class);
            return keyHolder.getKey().intValue();
        } catch (DuplicateKeyException e) {
            // Ignore it, the entry already exist
            LOG.warn(null, e);
            LOG.warn("Duplicate entry! table: " + tableName + " " + columnNames[0] + " = " + values[0] + ", " + columnNames[1] + " = " + values[1]);
            return 0;
        } catch (Exception sqle) {
            LOG.error("Error creating link! table: " + tableName + " " + columnNames[0] + " = " + values[0] + ", " + columnNames[1] + " = " + values[1]);
            throw new ApplicationException("Error creating link! table=" + tableName + " " + columnNames[0] + " = " + values[0] + ", " + columnNames[1] + " = " + values[1] + "\n"
                    + sqle);
        }
    }

    private int deleteAllLink(String tableName, String columnName, Integer id) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(tableName);
        query.append(" WHERE ").append(columnName).append(" = ?");
        int rows = getJdbcTemplate().update(query.toString(), new Object[] { id });
        if (LOG.isDebugEnabled()) {
            LOG.debug("deleted number og links: " + rows + " ,query=" + query.toString() + ", id=" + id);
        }
        return rows;
    }

    /**
     * Delete only the last inserted row
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @return
     */
    private int deleteLink(String tableName, String[] columnNames, Object[] values) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(tableName);
        query.append(" WHERE ").append(columnNames[0]).append(" = ?");
        query.append(" AND ").append(columnNames[1]).append(" = ?");
        query.append(" ORDER BY created_date_time DESC");
        query.append(" LIMIT 1");
        if (LOG.isDebugEnabled()) {
            LOG.debug("query=" + query.toString() + ", " + values[0] + ", " + values[1]);
        }
        return getJdbcTemplate().update(query.toString(), values);
    }

    /**
     * Delete all rows.
     * 
     * @param tableName
     * @param columnNames
     * @param values
     * @return
     */
    private int deleteLinkAll(String tableName, String[] columnNames, Object[] values) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ").append(tableName);
        query.append(" WHERE ").append(columnNames[0]).append(" = ?");
        query.append(" AND ").append(columnNames[1]).append(" = ?");
        if (LOG.isDebugEnabled()) {
            LOG.debug("query=" + query.toString() + ", " + values[0] + ", " + values[1]);
        }
        return getJdbcTemplate().update(query.toString(), values);
    }

    private int updateLink(String tableName, String columnName, Object[] values) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(tableName);
        query.append(" SET ").append(columnName).append(" = ?");
        query.append(" WHERE id = ?");
        if (LOG.isDebugEnabled()) {
            LOG.debug("updated=" + query.toString() + ", " + values[0] + ", " + values[1]);
        }
        return getJdbcTemplate().update(query.toString(), values);
    }

    private Integer getLinkId(String tableName, String[] columnNames, Object[] values) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT id FROM ").append(tableName);
            query.append(" WHERE ").append(columnNames[0]).append(" = ?");
            query.append(" AND ").append(columnNames[1]).append(" = ?");
            LOG.debug("updated=" + query.toString() + ", " + values[0] + ", " + values[1]);
            return getJdbcTemplate().queryForObject(query.toString(), values, Integer.class);
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(erae.getMessage());
            }
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createCup(Cup cup) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(CupsTable.createInsertPreparedStatement(cup), keyHolder);
            return keyHolder.getKey().intValue();
        } catch (DuplicateKeyException de) {
            LOG.error(null, de);
            return -1;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createTraining(final Training training) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            getJdbcTemplate().update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(TrainingsTable.createInsertQuery(), new String[] { "id" });
                    ps.setTimestamp(1, new Timestamp(TableHelper.getToDay()));
                    ps.setObject(2, training.getFkSeasonId());
                    ps.setObject(3, training.getFkTeamId());
                    ps.setTimestamp(4, new Timestamp(training.getStartDate().getTime()));
                    ps.setTimestamp(5, new Timestamp(training.getEndDate().getTime()));
                    ps.setString(6, training.getVenue());
                    return ps;
                }
            }, keyHolder);
            return keyHolder.getKey().intValue();
        } catch (DuplicateKeyException de) {
            LOG.error(null, de);
            return -1;
        }
    }

    /**
     * 
     */
    @Override
    public int updateTraining(final Training training) {
        return getJdbcTemplate().update(TrainingsTable.createUpdateQuery(), TrainingsTable.createUpdateParam(training));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createSeason(Season season) {
        return getJdbcTemplate().update(SeasonsTable.createInsertQuery(), SeasonsTable.createInsertParam(season));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Season getSeason(String period) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM seasons WHERE period LIKE ?", new Object[] { period.replace("-", "/") }, SportsTeamRowMapper.mapToSeasonRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
    }

    @Override
    public Status getPlayerStatus(Integer id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM player_statuses WHERE id = ?", new Object[] { id }, SportsTeamRowMapper.mapToStatusRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            LOG.error("No status found for ID: " + id);
            throw new ApplicationException("No status found for id: " + id);
        }
    }

    private String getPlayerPosition(Integer id) {
        try {
            Type type = getJdbcTemplate().queryForObject("SELECT * FROM player_position_types WHERE id = ?", new Object[] { id }, SportsTeamRowMapper.mapToTypeRM());
            return type.getName();
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            LOG.error("No status found for ID: " + id);
            throw new ApplicationException("No status found for id: " + id);
        }
    }

    @Override
    public Status getContactStatus(Integer id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM contact_statuses WHERE id = ?", new Object[] { id }, SportsTeamRowMapper.mapToStatusRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            LOG.error("No status found for ID: " + id);
            throw new ApplicationException("No status found for id: " + id);
        }
    }

    @Override
    public List<Type> getMatchTypes() {
        return getJdbcTemplate().query("SELECT id, name, description FROM match_types", new Object[] {}, SportsTeamRowMapper.mapToTypeRM());
    }

    @Override
    public List<Type> getMatchStatusTypes() {
        return getJdbcTemplate().query("SELECT id, name, description FROM match_status_types", new Object[] {}, SportsTeamRowMapper.mapToTypeRM());
    }

    @Override
    public List<Type> getSportTypes() {
        return getJdbcTemplate().query("SELECT id, name, description FROM sport_types", new Object[] {}, SportsTeamRowMapper.mapToTypeRM());
    }

    @Override
    public List<Type> getPlayerStatusTypes() {
        return getJdbcTemplate().query("SELECT id, name, 'description' AS description FROM player_statuses", new Object[] {}, SportsTeamRowMapper.mapToTypeRM());
    }

    @Override
    public List<Type> getPlayerPositionTypes() {
        return getJdbcTemplate().query("SELECT id, name, 'description' AS description FROM player_position_types", new Object[] {}, SportsTeamRowMapper.mapToTypeRM());
    }

    @Override
    public List<Type> getContactStatusTypes() {
        return getJdbcTemplate().query("SELECT id, name, 'description' AS description FROM contact_statuses", new Object[] {}, SportsTeamRowMapper.mapToTypeRM());
    }

    @Override
    public List<Type> getTeamRoleTypes() {
        return getJdbcTemplate().query("SELECT id, name, description FROM team_role_types", new Object[] {}, SportsTeamRowMapper.mapToTypeRM());
    }

    private Type getTeamRoleType(String name) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM team_role_types WHERE name = ?", new Object[] { name.toUpperCase() }, SportsTeamRowMapper.mapToTypeRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("RoleType not found for: " + name);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Season> getSeasonPeriodes() {
        return getJdbcTemplate().query("SELECT * FROM seasons", new Object[] {}, new BeanPropertyRowMapper<Season>(Season.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SeasonStatistic> getSeasonStatistics() {
        return getJdbcTemplate().query("SELECT * FROM season_statistic_view", new Object[] {}, new BeanPropertyRowMapper<SeasonStatistic>(SeasonStatistic.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createReferee(Referee referee) {
        Integer addressId = null;
        if (referee.getAddress() != null) {
            addressId = createAddress(referee.getAddress());
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(RefereesTable.createInsertPreparedStatement(referee, addressId), keyHolder);
        return keyHolder.getKey().intValue();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateContact(Contact contact) {
        if (contact.getAddress() != null) {
            updateAddress(contact.getAddress());
        }
        String query = ContactsTable.createUpdateQuery();
        Object[] params = ContactsTable.createUpdateParam(contact);
        LOG.debug("Params: " + Arrays.toString(params));
        int rows = getJdbcTemplate().update(query, params);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Updated rows: " + rows + ", " + contact.toString());
        }
        return contact.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updatePlayer(Player player) {
        if (player.getAddress() != null) {
            updateAddress(player.getAddress());
        }
        return getJdbcTemplate().update(PlayersTable.createUpdateQuery(), PlayersTable.createUpdateParam(player));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateReferee(Referee referee) {
        if (referee.getAddress() != null) {
            updateAddress(referee.getAddress());
        }
        return getJdbcTemplate().update(RefereesTable.createUpdateQuery(), RefereesTable.createUpdateParam(referee));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateAddress(Address address) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Address: " + address);
        }
        return getJdbcTemplate().update(AddressTable.createUpdateQuery(), AddressTable.createUpdateParam(address));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Training getTrainingByDate(Integer id, Long startTime) {
        Training training = null;
        try {
            training = getJdbcTemplate().queryForObject("SELECT * FROM trainings WHERE id = ? AND start_time = ?",
                    new Object[] { id, Utility.formatTime(startTime, "yyyy-MM-dd HH:mm:ss") }, new BeanPropertyRowMapper<Training>(Training.class));
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }

        if (training.hasFkTeamId()) {
            training.setTeam(getTeam(training.getFkTeamId()));
        }

        if (training.hasFkSeasonId()) {
            training.setSeason(getSeason(training.getFkSeasonId()));
        }
        Integer numberOfSignedPlayers = getNumberOfSignedPlayers(training.getType(), training.getId());
        training.setNumberOfSignedPlayers(numberOfSignedPlayers);
        return training;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Training getTraining(Integer id) {
        Training training = null;
        try {
            training = getJdbcTemplate().queryForObject(SportsTeamSql.getByIdQuery("trainings"), new Object[] { id }, SportsTeamRowMapper.mapToTrainingRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }

        if (training.hasFkTeamId()) {
            training.setTeam(getTeam(training.getFkTeamId()));
        }

        if (training.hasFkSeasonId()) {
            training.setSeason(getSeason(training.getFkSeasonId()));
        }
        Integer numberOfSignedPlayers = getNumberOfSignedPlayers(training.getType(), training.getId());
        training.setNumberOfSignedPlayers(numberOfSignedPlayers);
        return training;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getNumberOfSignedPlayers(String type, int id) {
        Integer numberOfPlayers = 0;
        StringBuilder sqlQuery = new StringBuilder();
        if (type.equalsIgnoreCase(PlayerLinkTableTypeEnum.MATCH.name())) {
            sqlQuery.append("SELECT count(fk_match_id) AS numberOfPlayers FROM player_match_lnk WHERE fk_match_id = ?");
        } else if (type.equalsIgnoreCase(PlayerLinkTableTypeEnum.CUP.name())) {
            sqlQuery.append("SELECT count(fk_cup_id) AS numberOfPlayers FROM player_cup_lnk WHERE fk_cup_id = ?");
        } else if (type.equalsIgnoreCase(PlayerLinkTableTypeEnum.TRAINING.name())) {
            sqlQuery.append("SELECT count(fk_training_id) AS numberOfPlayers FROM player_training_lnk WHERE fk_training_id = ?");
        } else {
            LOG.error("Invalid link table type: " + type);
            throw new ApplicationException("Invalid link table type: " + type);
        }
        numberOfPlayers = getJdbcTemplate().queryForObject(sqlQuery.toString(), new Object[] { id }, SportsTeamRowMapper.mapToNumberOfOccurencesRM("numberOfPlayers"));
        return numberOfPlayers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int registrerRefereeForMatch(Integer refereeId, Integer matchId) {
        return getJdbcTemplate().update(TableHelper.createUpdateQuery(MatchesTable.TABLE_NAME, new String[] { MatchesTable.COLUMN_FK_REFEREE_ID }),
                new Object[] { refereeId, matchId });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateGoals(Integer matchId, Integer goals, boolean isHomeTeam) {
        String columnName = MatchesTable.COLUMN_NUMBER_OF_GOALS_AWAY_TEAM;
        if (isHomeTeam) {
            columnName = MatchesTable.COLUMN_NUMBER_OF_GOALS_HOME_TEAM;
        }
        return getJdbcTemplate().update(TableHelper.createUpdateQuery(MatchesTable.TABLE_NAME, new String[] { columnName }), new Object[] { goals, matchId });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createMatchEvent(MatchEvent matchEvent) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(MatchEventsTable.createInsertPreparedStatement(matchEvent), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MatchEvent> getMatchEventList(Integer matchId) {
        return getJdbcTemplate().query("SELECT * FROM match_events WHERE fk_match_id = ?", new Object[] { matchId }, new BeanPropertyRowMapper<MatchEvent>(MatchEvent.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchStatus getMatchStatus(Integer id) {
        try {
            return getJdbcTemplate().queryForObject(SportsTeamSql.getByIdQuery("match_status_types"), new Object[] { id }, SportsTeamRowMapper.mapToMatchStatusRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("Application configuration error, no match ststius found for id: " + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MatchStatus getMatchStatus(String name) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM match_status_types WHERE name = ?", new Object[] { name }, SportsTeamRowMapper.mapToMatchStatusRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.error(null, erae);
            throw new ApplicationException("Application configuration error, no match ststius found for name: " + name);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateMatchStatus(Integer matchId, Integer statusId) {
        return getJdbcTemplate().update(TableHelper.createUpdateQuery(MatchesTable.TABLE_NAME, new String[] { MatchesTable.COLUMN_FK_MATCH_STATUS_ID }),
                new Object[] { statusId, matchId });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cup getCup(Integer id) {
        Cup cup = null;
        try {
            cup = getJdbcTemplate().queryForObject(SportsTeamSql.getByIdQuery("cups"), new Object[] { id }, SportsTeamRowMapper.mapToCupRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
        if (cup.hasFkSeasonId()) {
            cup.setSeason(getSeason(cup.getFkSeasonId()));
        }

        int numberOfSignedPlayers = getNumberOfSignedPlayers(cup.getType(), cup.getId());
        cup.setNumberOfSignedPlayers(numberOfSignedPlayers);

        Integer numberOfRegistreredTeams = getNumberOfRegistreredTeamsForCup(cup.getId());
        cup.setNumberOfRegistreredTeams(numberOfRegistreredTeams);
        return cup;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createCupMatchLnk(Integer cupId, Integer matchId) {
        long rows = createLink(CupMatchLnkTable.TABLE_NAME, CupMatchLnkTable.getFKColumnNames(), new Object[] { cupId, matchId });
        return Long.valueOf(rows).intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Statistic getTeamStatistic(Integer teamId, Integer seasonId) {
        List<MatchStatistic> matchStatisticList = getJdbcTemplate().query(SportsTeamSql.createTeamMatchStatisticQuery(), new Object[] { teamId, seasonId },
                SportsTeamRowMapper.mapToMatchStatisticRM());
        return new Statistic(matchStatisticList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Statistic getLeagueTable(Integer leagueId, Integer seasonId) {
        List<MatchStatistic> list = getJdbcTemplate().query(SportsTeamSql.createLeagueTableQuery(), new Object[] { seasonId, leagueId },
                SportsTeamRowMapper.mapToMatchStatisticRM());
        return new Statistic(list);
    }

    @Override
    public Statistic getPlayerStatistic(Integer teamId, Integer playerId, Integer seasonId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT * FROM player_statistic_view WHERE season_id = ? AND player_id = ? AND team_id = ?");
        Object[] selectionArgs = { seasonId, playerId, teamId };
        List<Statistic> statisticList = getJdbcTemplate().query(sqlQuery.toString(), selectionArgs, SportsTeamRowMapper.mapToPlayerStatisticRM());
        if (statisticList != null && !statisticList.isEmpty()) {
            return statisticList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Statistic> getTopPlayerStatisticList(int seasonId, int teamId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT * FROM player_statistic_view WHERE season_id = ? AND team_id = ?");
        Object[] selectionArgs = { seasonId, teamId };
        return getJdbcTemplate().query(sqlQuery.toString(), selectionArgs, SportsTeamRowMapper.mapToPlayerStatisticRM());
    }

    @Override
    public List<Training> getTrainings(Integer teamId, Integer seasonId) {
        return getJdbcTemplate().query("SELECT * FROM trainings WHERE fk_team_id = ? AND fk_season_id = ? ORDER BY start_time ASC", new Object[] { teamId, seasonId },
                SportsTeamRowMapper.mapToTrainingRM());
    }

    @Override
    public List<ActivityView> getActivitiesUpcomingWeek(Integer teamId, Integer seasonId) {
        StringBuilder query = new StringBuilder();
        Calendar now = Calendar.getInstance();
        String today = Utility.formatTime(now.getTimeInMillis(), "yyyy-MM-dd");
        now.add(Calendar.WEEK_OF_YEAR, 1);
        String todayPluss7Days = Utility.formatTime(now.getTimeInMillis(), "yyyy-MM-dd");
        query.append("SELECT *");
        query.append(" FROM team_activity_view");
        query.append(" WHERE start_date >= ? AND start_date < ?");
        query.append(" AND fk_season_id = ?");
        query.append(" AND fk_team_id in(0,?)");
        query.append(" ORDER BY start_date ASC");
        return getJdbcTemplate().query(query.toString(), new Object[] { today, todayPluss7Days, seasonId, teamId }, SportsTeamRowMapper.mapToActivityRM());
    }

    @Override
    public Season getCurrentSeason() {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM seasons WHERE now() >= start_date AND now() <= end_date", new Object[] {}, SportsTeamRowMapper.mapToSeasonRM());
        } catch (EmptyResultDataAccessException eda) {
            LOG.error(null, eda);
            throw new ApplicationException("No season found for " + Utility.formatTime(System.currentTimeMillis(), "dd.MM.yyyy") + ", check configuration!");
        }
    }

    /**
     * 
     SELECT c.* FROM team_contact_lnk l, contacts c WHERE l.fk_team_id = 1 AND
     * c.id = l.fk_contact_id
     */
    @Override
    public Contact getTeamContactPerson(Integer teamId, String type) {
        try {
            Type roleType = getTeamRoleType(type);
            if (LOG.isDebugEnabled()) {
                LOG.debug("teamId=" + teamId + ", type=" + type + "(" + roleType.getName() + ", " + roleType.getId() + ")");
            }
            // for debug only
            runQuery("SELECT * FROM contacts", Contact.class);
            runQuery("SELECT * FROM contact_role_type_lnk", Type.class);

            StringBuilder query = new StringBuilder();
            query.append("SELECT DISTINCT c.*");
            query.append(" FROM contacts c, contact_role_type_lnk l");
            query.append(" WHERE c.fk_team_id = ?");
            query.append(" AND l.fk_team_role_type_id = ?");
            query.append(" AND l.fk_contact_id = c.id");
            List<Contact> contacts = getJdbcTemplate().query(query.toString(), new Object[] { teamId, roleType.getId() }, SportsTeamRowMapper.mapToContactRM());
            if (contacts != null && !contacts.isEmpty()) {
                return contacts.get(0);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("No " + type + " found for teamId:" + teamId);
            }
            return null;
        } catch (EmptyResultDataAccessException eda) {
            LOG.warn(null, eda);
            LOG.warn("Contact not found! teamId=" + teamId + ", type=" + type);
            return null;
        }
    }

    @Override
    public List<Referee> getReferees(Integer clubId) {
        return getJdbcTemplate().query("SELECT * FROM referees WHERE fk_club_id = ?", new Object[] { clubId }, SportsTeamRowMapper.mapToRefereeRM());
    }

    @Override
    public int updateTeam(Team team) {
        return getJdbcTemplate().update(TeamsTable.createUpdateQuery(), TeamsTable.createUpdateParam(team));
    }

    @Override
    public int updateCup(Cup cup) {
        return getJdbcTemplate().update(CupsTable.createUpdateQuery(), CupsTable.createUpdateParam(cup));
    }

    @Override
    public List<Cup> getCupList(Integer teamId, Integer seasonId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.*");
        query.append(" FROM cups c");
        query.append(" WHERE c.fk_season_id = ?");
        query.append(" ORDER BY start_date ASC");
        return getJdbcTemplate().query(query.toString(), new Object[] { seasonId }, SportsTeamRowMapper.mapToCupRM());
    }

    @Override
    public List<VolunteerTask> getVolunteerTasks(Integer clubId, Integer seasonId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT t.*, s.name AS task_status_name");
        query.append(" FROM volunteer_tasks t, task_statuses s");
        query.append(" WHERE t.fk_club_id = ? AND t.fk_season_id = ? AND t.fk_task_status_id = s.id");
        query.append(" ORDER BY start_date ASC ");
        return getJdbcTemplate().query(query.toString(), new Object[] { clubId, seasonId }, SportsTeamRowMapper.mapToVolunteerTaskRM());
    }

    @Override
    public int createSubTask(SubTask subTask) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(SubTasksTable.createInsertPreparedStatement(subTask), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int createVolunteerTask(VolunteerTask task) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(VolunteerTasksTable.createInsertPreparedStatement(task), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateVolunteerTask(VolunteerTask task) {
        Object[] params = VolunteerTasksTable.createUpdateParam(task);
        LOG.debug(Arrays.asList(params).toString());
        return getJdbcTemplate().update(VolunteerTasksTable.createUpdateQuery(), params);
    }

    @Override
    public List<SubTask> getSubTasks(Integer parentTaskId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT t.*, s.name AS task_status_name");
        query.append(" FROM sub_tasks t, task_statuses s");
        query.append(" WHERE t.fk_parent_task_id = ?  AND t.fk_task_status_id = s.id");
        query.append(" ORDER BY t.start_date ASC");
        List<SubTask> subTaskList = getJdbcTemplate().query(query.toString(), new Object[] { parentTaskId }, SportsTeamRowMapper.mapToSubTaskRM());
        for (SubTask subTask : subTaskList) {
            if (subTask.isAssigned()) {
                subTask.setAssignee(getContact(subTask.getFkAssignedToPersonId()));
            }
        }
        return subTaskList;
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = getJdbcTemplate().queryForObject(
                "SELECT t.*, s.name AS task_status_name FROM sub_tasks t, task_statuses s WHERE t.id = ? AND t.fk_task_status_id = s.id", new Object[] { id },
                SportsTeamRowMapper.mapToSubTaskRM());

        subTask.setParentTask(getVolunteerTask(subTask.getFkParentTaskId()));
        if (subTask.isAssigned()) {
            subTask.setAssignee(getContact(subTask.getFkAssignedToPersonId()));
        }
        return subTask;
    }

    @Override
    public int deleteSubTask(int id) {
        return getJdbcTemplate().update("DELETE FROM sub_tasks WHERE id = ?", new Object[] { id });
    }

    @Override
    public VolunteerTask getVolunteerTask(int id) {
        VolunteerTask task = getJdbcTemplate().queryForObject(
                "SELECT t.*, s.name AS task_status_name FROM volunteer_tasks t, task_statuses s WHERE t.id = ? AND t.fk_task_status_id = s.id", new Object[] { id },
                SportsTeamRowMapper.mapToVolunteerTaskRM());

        if (task.hasFkTaskStatusId()) {
            task.setStatus(getTaskStatus(task.getFkTaskStatusId()));
        }

        if (task.hasFkClubId()) {
            task.setClub(getClub(task.getFkClubId()));
        }

        if (task.hasFkSeasonId()) {
            task.setSeason(getSeason(task.getFkSeasonId()));
        }

        if (task.isAssigned()) {
            task.setAssignee(getContact(task.getFkAssignedToPersonId()));
        }

        task.setSubTaskList(getSubTasks(task.getId()));
        return task;
    }

    private List<SubTask> getSubTasksForTaskGroup(Integer taskGroupId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT t.*, '' AS task_status_name");
        query.append(" FROM sub_tasks t, sub_task_task_group_lnk l");
        query.append(" WHERE l.fk_task_group_id = ?");
        query.append(" AND t.id = l.fk_sub_task_id");
        return getJdbcTemplate().query(query.toString(), new Object[] { taskGroupId }, SportsTeamRowMapper.mapToSubTaskRM());
    }

    @Override
    public TaskGroup getTaskGroup(int id) {
        TaskGroup taskGroup = getJdbcTemplate().queryForObject("SELECT * FROM task_groups WHERE id = ?", new Object[] { id }, SportsTeamRowMapper.mapToTaskGroupRM());
        taskGroup.setSubTaskList(getSubTasksForTaskGroup(id));
        return taskGroup;
    }

    @Override
    public int createTaskGroup(TaskGroup taskGroup) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(TaskGroupsTable.createInsertPreparedStatement(taskGroup), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int deleteTaskGroup(Integer id) {
        return getJdbcTemplate().update("DELETE FROM task_groups WHERE id = ?", new Object[] { id });
    }

    @Override
    public int deleteVolunteerTask(int id) {
        return getJdbcTemplate().update("DELETE FROM volunteer_tasks WHERE id = ?", new Object[] { id });
    }

    @Override
    public List<Status> getTaskStatuses() {
        try {
            return getJdbcTemplate().query("SELECT * FROM task_statuses", new Object[] {}, SportsTeamRowMapper.mapToStatusRM());
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException("Error getting task statuses! " + e.getMessage());
        }
    }

    @Override
    public Status getTaskStatus(Integer id) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM task_statuses WHERE id = ?", new Object[] { id }, SportsTeamRowMapper.mapToStatusRM());
        } catch (Exception e) {
            LOG.error(null, e);
            throw new ApplicationException("Error getting task status for " + id + ", " + e.getMessage());
        }
    }

    @Override
    public int addSubTaskToGroup(Integer subTaskId, Integer taskGroupId) {
        return createLink(SubTaskTaskGroupLnkTable.TABLE_NAME, SubTaskTaskGroupLnkTable.getFKColumnNames(), new Object[] { subTaskId, taskGroupId });
    }

    @Override
    public int removeSubTaskFromGroup(Integer subTaskId, Integer taskGroupId) {
        return deleteLinkAll(SubTaskTaskGroupLnkTable.TABLE_NAME, SubTaskTaskGroupLnkTable.getFKColumnNames(), new Object[] { subTaskId, taskGroupId });
    }

    @Override
    public int assignContactToSubTask(Integer subTaskId, Integer contactId) {
        SubTask subTask = getSubTask(subTaskId);
        subTask.setFkAssignedToPersonId(contactId);
        return updateSubTask(subTask);
    }

    @Override
    public int unsignContactFromSubTask(Integer subTaskId, Integer contactId) {
        SubTask subTask = getSubTask(subTaskId);
        subTask.setFkAssignedToPersonId(null);
        return updateSubTask(subTask);
    }

    @Override
    public int assignTeamToSubTask(Integer subTaskId, Integer teamId) {
        SubTask subTask = getSubTask(subTaskId);
        subTask.setAssignedTeamId(teamId);
        return updateSubTask(subTask);
    }

    @Override
    public int unsignTeamFromSubTask(Integer subTaskId, Integer teamId) {
        SubTask subTask = getSubTask(subTaskId);
        subTask.setAssignedTeamId(null);
        return updateSubTask(subTask);
    }

    @Override
    public Integer getNumberOfRegistreredTeamsForCup(Integer cupId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT count(fk_team_id) AS numberOfTeams FROM team_cup_lnk WHERE fk_cup_id = ?");
        return getJdbcTemplate().queryForObject(sqlQuery.toString(), new Object[] { cupId }, SportsTeamRowMapper.mapToNumberOfOccurencesRM("numberOfTeams"));
    }

    @Override
    public List<Team> getRegistreredTeamsForCup(Integer cupId) {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT t.* FROM teams t, team_cup_lnk l WHERE l.fk_cup_id = ? AND t.id = l.fk_team_id");
        return getJdbcTemplate().query(sqlQuery.toString(), new Object[] { cupId }, SportsTeamRowMapper.mapToTeamRM());
    }

    @Override
    public int updateSubTask(SubTask subTask) {
        Object[] params = SubTasksTable.createUpdateParam(subTask);
        LOG.debug(Arrays.asList(params).toString());
        return getJdbcTemplate().update(SubTasksTable.createUpdateQuery(), params);
    }

    @Override
    public List<KeyValuePair> getClubStatistic() {
        return getJdbcTemplate().query(SportsTeamSql.getClubStatisticQuery(), new ResultSetExtractor<List<KeyValuePair>>() {
            @Override
            public List<KeyValuePair> extractData(ResultSet rs) throws SQLException {
                List<KeyValuePair> list = new ArrayList<KeyValuePair>();
                while (rs.next()) {
                    list.add(new KeyValuePair("clubs", rs.getString("clubs")));
                    list.add(new KeyValuePair("teams", rs.getString("teams")));
                    list.add(new KeyValuePair("players", rs.getString("players")));
                    list.add(new KeyValuePair("contacts", rs.getString("contacts")));
                    list.add(new KeyValuePair("referees", rs.getString("referees")));
                    list.add(new KeyValuePair("matches", rs.getString("matches")));
                    list.add(new KeyValuePair("trainings", rs.getString("trainings")));
                    list.add(new KeyValuePair("cups", rs.getString("cups")));
                    list.add(new KeyValuePair("volunteer_tasks", rs.getString("volunteer_tasks")));
                    list.add(new KeyValuePair("sub_tasks", rs.getString("sub_tasks")));
                }
                return list;
            }
        });
    }

    @Override
    public int deleteAllData() {
        int[] result = getJdbcTemplate().batchUpdate(SportsTeamSql.getDeleteAllDataBatchQuery());
        return result.length;
    }

    @Override
    public Integer assignContactToTask(Integer taskId, Integer contactId) {
        VolunteerTask volunteerTask = getVolunteerTask(taskId);
        volunteerTask.setFkAssignedToPersonId(contactId);
        return updateVolunteerTask(volunteerTask);
    }

    @Override
    public Integer unsignContactFromTask(Integer taskId, Integer contactId) {
        VolunteerTask volunteerTask = getVolunteerTask(taskId);
        volunteerTask.setFkAssignedToPersonId(null);
        return updateVolunteerTask(volunteerTask);
    }

    @Override
    public List<Contact> getContactsForClub(int clubId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT c.*");
        query.append(" FROM contacts c, teams t ");
        query.append(" WHERE c.fk_team_id = t.id");
        query.append(" AND t.fk_club_id = ?");
        List<Contact> contacts = getJdbcTemplate().query(query.toString(), new Object[] { clubId }, SportsTeamRowMapper.mapToContactRM());
        for (Contact contact : contacts) {
            contact.setTeamRoleList(getRolesForContact(contact.getId()));
        }
        return contacts;
    }

    @Override
    public String[] getContactEmails(int teamId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM");
        query.append(" contacts WHERE fk_team_id = ?");
        query.append(" ORDER BY email ASC");
        List<String> list = getJdbcTemplate().query(query.toString(), new Object[] { teamId }, SportsTeamRowMapper.singelValueRM("email"));
        return (String[]) list.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUsers() {
        try {
            List<User> users = getJdbcTemplate().query("SELECT * FROM users", new Object[] {}, SportsTeamRowMapper.mapToUserRM());
            for (User user : users) {
                user.setRoles(getUserRoles(user.getUserName()));
            }
            return users;
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return new ArrayList<User>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getUserRoles() {
        try {
            return getJdbcTemplate().query("SELECT DISTINCT * FROM roles", new Object[] {}, SportsTeamRowMapper.mapToRoleRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            LOG.warn(null, erae);
            return new ArrayList<String>();
        }
    }

    private List<String> getUserRoles(String userName) {
        try {
            return getJdbcTemplate().query("SELECT * FROM roles WHERE username = ?", new Object[] { userName }, SportsTeamRowMapper.mapToRoleRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return new ArrayList<String>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(String userName) {
        try {
            User user = getJdbcTemplate().queryForObject("SELECT * FROM users WHERE username = ?", new Object[] { userName }, SportsTeamRowMapper.mapToUserRM());
            user.setRoles(getUserRoles(user.getUserName()));
            return user;
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(Integer userId) {
        try {
            User user = getJdbcTemplate().queryForObject("SELECT * FROM users WHERE id = ?", new Object[] { userId }, SportsTeamRowMapper.mapToUserRM());
            user.setRoles(getUserRoles(user.getUserName()));
            return user;
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(UsersTable.createInsertPreparedStatement(user), keyHolder);
        Integer userId = keyHolder.getKey().intValue();
        for (String role : user.getRoles()) {
            addRoleToUser(user.getUserName(), role);
        }
        return userId;
    }

    private int addRoleToUser(String userName, String role) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(RolesTable.createInsertPreparedStatement(userName, role), keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteUser(Integer id) {
        return getJdbcTemplate().update("DELETE FROM users WHERE id = ?", new Object[] { id });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateUser(User user) {
        return getJdbcTemplate().update(UsersTable.createUpdateQuery(), UsersTable.createUpdateParam(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int changeUserPwd(User user) {
        return getJdbcTemplate().update(UsersTable.createUpdateQuery(), UsersTable.createUpdateParam(user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ScheduledTask> getTeamScheduledTasks() {
        try {
            return getJdbcTemplate().query("SELECT * FROM scheduled_tasks", new Object[] {}, SportsTeamRowMapper.mapToScheduledTaskRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return new ArrayList<ScheduledTask>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer enableScheduledTask(Integer taskId, int i) {
        return getJdbcTemplate().update("UPDATE scheduled_tasks SET task_enabled=? WHERE id = ?", new Object[] { i, taskId, });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteSeason(int id) {
        return getJdbcTemplate().update("DELETE FROM seasons WHERE id = ?", new Object[] { id });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Season> getSeasons() {
        return getJdbcTemplate().query(SportsTeamSql.getAllSeasonsQuery(), new Object[] {}, SportsTeamRowMapper.mapToSeasonRM());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTeamIdForUser(Integer id) {
        // FIXME hard coding for test purpose.
        return 155;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void runQuery(String sqlQuery, Class<?> clazz) {
        List<Object> list = getJdbcTemplate().query(sqlQuery, new BeanPropertyRowMapper(clazz));
        if (LOG.isDebugEnabled()) {
            // LOG.debug("----------------------------");
            LOG.debug("Query: " + sqlQuery);
            // LOG.debug("----------------------------");
            // for (Object obj : list) {
            // LOG.debug("result: " + obj);
            // }
            // LOG.debug("----------------------------");
        }
    }

    

  

    private List<KeyValuePairList> mapToKeyValuePairList(List<KeyValuePair> keyValuePairs) {
        Map<String, KeyValuePairList> map = new HashMap<String, KeyValuePairList>();
        for (KeyValuePair p : keyValuePairs) {
            if (!map.containsKey(p.getKey())) {
                List<String> list = new ArrayList<String>();
                list.add(p.getValue());
                map.put(p.getKey(), new KeyValuePairList(p.getKey(), list));
            } else {
                List<String> list = map.get(p.getKey()).getValue();
                list.add(p.getValue());
            }
        }
        List<KeyValuePairList> list = new ArrayList<KeyValuePairList>(map.values());
        Collections.sort(list, new KeyValuePairComparator());
        return list;
    }

    private class KeyValuePairComparator implements Comparator<KeyValuePairList> {
        public int compare(final KeyValuePairList kv1, final KeyValuePairList kv2) {
            return kv1.getKeyToCompare().compareTo(kv2.getKeyToCompare());
        }
    }

   
    @Override
    public List<ImageDetail> getImages(Integer userId) {
        return getJdbcTemplate().query("SELECT * FROM image_details ORDER BY id", new Object[] {}, SportsTeamRowMapper.mapToImageDetailRM());
    }

    @Override
    public ImageDetail getImage(Integer id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM image_details WHERE id = ?", new Object[] { id }, SportsTeamRowMapper.mapToImageDetailRM());
    }

    @Override
    public int createImage(ImageDetail imageDetail) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(ImageDetailsTable.createInsertPreparedStatement(imageDetail), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public boolean deleteImage(Integer imageId) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM image_details");
        query.append(" WHERE id = ?");
        if (LOG.isDebugEnabled()) {
            LOG.debug("query=" + query.toString());
        }
        return getJdbcTemplate().update(query.toString(), new Object[] { imageId }) != 0;
    }


    // FIXME
    @Override
    public List<String> getFollowersPhoneNumbers(Integer eventId) {
        return Arrays.asList("45465500", "45465502", "45465502");
    }

    // FIXME
    @Override
    public List<Person> getFollowers(Integer eventId) {
        return Arrays.asList(new Person(1, "gunnar", "ronn", "45465500", "gunnar_ronneberg@yahoo.no"), new Person(1, "alte", "hansen", "45465500", "gunnar_ronneberg@yahoo.no"),
                new Person(1, "per", "nilsen", "45465500", "gunnar_ronneberg@yahoo.no"));
    }

}
