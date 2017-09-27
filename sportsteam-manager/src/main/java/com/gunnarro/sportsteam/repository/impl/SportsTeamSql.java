package com.gunnarro.sportsteam.repository.impl;

/**
 * This class contains all the query which are fired on SportsTeam
 * 
 */
public class SportsTeamSql {

    private SportsTeamSql() {
    }

    public static String getLeagueQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT l.*, c.name AS category_name, r.*, s.name AS sport_type from leagues l, league_categories c, sport_types s, league_rules r");
        query.append(" WHERE l.id = ?");
        query.append(" AND l.fk_category_id = c.id");
        query.append(" AND c.fk_league_rule_id = r.id");
        query.append(" AND c.fk_sport_type_id = s.id");
        return query.toString();
    }

    public static String getClubAddressByIdQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT a.* FROM Address a, Club c");
        query.append(" WHERE c.id LIKE ?");
        query.append(" AND c.fk_address_id LIKE a.id");
        return query.toString();
    }

    public static String getAllClubsQuery() {
        StringBuilder query = new StringBuilder();
        // query.append("SELECT * FROM clubs");
        query.append("SELECT c.*,");
        query.append(" (SELECT count(t.id) FROM teams t WHERE t.fk_club_id = c.id) AS number_of_teams,");
        query.append(" (SELECT count(r.id) FROM referees r WHERE r.fk_club_id = c.id) AS number_of_referees");
        query.append(" FROM clubs c");
        return query.toString();
    }
    
    public static String getAllSeasonsQuery() {
        StringBuilder query = new StringBuilder();
        // query.append("SELECT * FROM clubs");
        query.append("SELECT s.*");
//        query.append(" (SELECT count(t.id) FROM teams t WHERE t.fk_club_id = c.id) AS number_of_teams,");
//        query.append(" (SELECT count(r.id) FROM referees r WHERE r.fk_club_id = c.id) AS number_of_referees");
        query.append(" FROM seasons s");
        return query.toString();
    }

    /**
     * SELECT t.*, (SELECT count(p.id) FROM players p WHERE p.fk_team_id = t.id)
     * AS number_of_players") FROM teams t WHERE t.fk_club_id = ?
     * 
     * @return
     */
    public static String getTeamsForClubQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT t.*,");
        query.append(" (SELECT count(p.id) FROM players p WHERE p.fk_team_id = t.id) AS number_of_players,");
        query.append(" (SELECT count(c.id) FROM contacts c WHERE c.fk_team_id = t.id) AS number_of_contacts");
        query.append(" FROM teams t");
        query.append(" WHERE t.fk_club_id = ?");
        return query.toString();
    }

    public static String getByIdQuery(String tableName) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(tableName);
        query.append(" WHERE id = ?");
        return query.toString();
    }

    public static String getMatchesForTeamQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT m.*,");
        query.append(" (SELECT count(l.fk_match_id)");
        query.append(" FROM player_match_lnk l");
        query.append(" WHERE l.fk_match_id = m.id)");
        query.append(" AS number_of_signed_players");
        query.append(" FROM matches m");
        query.append(" WHERE m.fk_team_id = ?");
        query.append(" ORDER BY m.start_date ASC;");
        return query.toString();
    }

    public static String getPlayersForTeamQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT *");
        query.append(" FROM players");
        query.append(" WHERE fk_team_id  = ?");
        query.append(" AND fk_status_id  = ?");
        query.append(" ORDER BY first_name, last_name ASC;");
        return query.toString();
    }

    public static String getContactsForTeamQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT *");
        query.append(" FROM contacts");
        query.append(" WHERE fk_team_id  = ?");
        query.append(" AND fk_status_id  = ?");
        query.append(" ORDER BY first_name, last_name ASC;");
        return query.toString();
    }

    public static String getSignedPlayerNamesQuery(String activityType) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT p.id, p.first_name, p.last_name,'").append(activityType).append("' AS type,");
        query.append(" (SELECT l.id FROM player_" + activityType + "_lnk l WHERE p.id = l.fk_player_id AND l.fk_" + activityType + "_id = ?) as signed");
        query.append(" FROM players p");
        query.append(" WHERE p.fk_team_id = ?");
        query.append(" ORDER BY p.first_name ASC");
        return query.toString();
    }

    public static String getRegistreredTeamNamesForCupQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT t.id, t.team_name AS first_name, '' AS last_name, 'cup' AS type,");
        query.append(" (SELECT l.id FROM team_cup_lnk l WHERE t.id = l.fk_team_id AND l.fk_cup_id = ?) as signed");
        query.append(" FROM teams t");
        // query.append(" WHERE t.id = ?");
        query.append(" ORDER BY t.team_name ASC");
        return query.toString();
    }

    public static String getClubStatisticQuery() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT");
        query.append(" (SELECT count(id) FROM clubs) AS 'clubs',");
        query.append(" (SELECT count(id) FROM teams) AS 'teams',");
        query.append(" (SELECT count(id) FROM players) AS 'players',");
        query.append(" (SELECT count(id) FROM contacts) AS 'contacts',");
        query.append(" (SELECT count(id) FROM referees) AS 'referees',");
        query.append(" (SELECT count(id) FROM matches) AS 'matches',");
        query.append(" (SELECT count(id) FROM trainings) AS 'trainings',");
        query.append(" (SELECT count(id) FROM cups) AS 'cups',");
        query.append(" (SELECT count(id) FROM volunteer_tasks) AS 'volunteer_tasks',");
        query.append(" (SELECT count(id) FROM sub_tasks) AS 'sub_tasks'");
        query.append(" FROM dual");
        return query.toString();
    }

    public static String[] getDeleteAllDataBatchQuery() {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM sub_tasks WHERE id > 0");
        query.append(":DELETE FROM volunteer_tasks WHERE id > 0");
        query.append(":DELETE FROM matches WHERE id > 0");
        query.append(":DELETE FROM trainings WHERE id > 0");
        query.append(":DELETE FROM cups WHERE id > 0");
        query.append(":DELETE FROM referees WHERE id > 0");
        query.append(":DELETE FROM contacts WHERE id > 0");
        query.append(":DELETE FROM players WHERE id > 0");
        query.append(":DELETE FROM teams WHERE id > 0");
        query.append(":DELETE FROM clubs WHERE id > 0");
        query.append(":DELETE FROM addresses WHERE id > 0");
        return query.toString().split(":");
    }

    public static String createTeamMatchStatisticQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT *,");
        sqlQuery.append(" sum( numberOfWonMatches ) AS numberOfWonMatches,");
        sqlQuery.append(" sum( numberOfDrawMatches ) AS numberOfDrawMatches,");
        sqlQuery.append(" sum( numberOfLossMatches ) AS numberOfLossMatches,");
        sqlQuery.append(" sum( numberOfGoalsScored ) AS numberOfGoalsScored,");
        sqlQuery.append(" sum( numberOfGoalsAgainst ) AS numberOfGoalsAgainst,");
        sqlQuery.append(" sum( numberOfWonMatches*2 + numberOfDrawMatches*1 ) AS scores");
        sqlQuery.append(" FROM match_result_view");
        sqlQuery.append(" WHERE seasonId = ?");
        // FIXME use team name
        sqlQuery.append(" AND leagueId = ?");
        sqlQuery.append(" GROUP BY matchTypeId;");
        return sqlQuery.toString();
    }

    public static String createLeagueTableQuery() {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("SELECT * FROM league_table_view");
        sqlQuery.append(" WHERE seasonId = ?");
        sqlQuery.append(" AND leagueId = ?");
//        sqlQuery.append("SELECT seasonId,leagueId,matchTypeId,teamName,");
//        sqlQuery.append(" SUM(numberOfWonMatches + numberOfDrawMatches + numberOfLossMatches) AS numberOfPlayedMatches,");
//        sqlQuery.append(" SUM(numberOfWonMatches) AS numberOfWonMatches,");
//        sqlQuery.append(" SUM(numberOfDrawMatches) AS numberOfDrawMatches,");
//        sqlQuery.append(" SUM(numberOfLossMatches) AS numberOfLossMatches,");
//        sqlQuery.append(" SUM(numberOfGoalsScored) AS numberOfGoalsScored,");
//        sqlQuery.append(" SUM(numberOfGoalsAgainst) AS numberOfGoalsAgainst,");
//        sqlQuery.append(" SUM(numberOfWonMatches*2 + numberOfDrawMatches*1) AS scores");
//        sqlQuery.append(" FROM match_result_view");
//        sqlQuery.append(" WHERE matchTypeId = 1");
//        sqlQuery.append(" AND seasonId = ?");
//        sqlQuery.append(" AND leagueId = ?");
//        sqlQuery.append(" GROUP BY teamName");
//        sqlQuery.append(" ORDER BY scores, teamName DESC");
        return sqlQuery.toString();
    }
}
