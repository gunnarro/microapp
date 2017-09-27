------------------------------------------------------------------------------------------
-- DB name		 : SportsTeam DB
-- DB Component	 : Views
-- Release date	 : 07.09.2014 
-- Schema Version: 1.0-SNAPSHOT
--
--
-- Modification History
-- Date			Modified by		Task 
------------------------------------------------------------------------------------------
-- 07.09.2014	gunnarro 		Initial version 1.0-SNAPSHOT

------------------------------------------------------------------------------------------
-- DROP VIEW match_result_view
-- View: match_result_view
CREATE VIEW match_result_view AS
SELECT m.id AS matchId, 
       m.fk_season_id AS seasonId,
	   m.fk_league_id AS leagueId,
	   m.fk_match_type_id AS matchTypeId,
	   m.home_team_name AS teamName,
	   sum(ifnull(m.goals_home_team,0) > ifnull(m.goals_away_team,0)) AS numberOfWonMatches,
	   sum(ifnull(m.goals_home_team,0) = ifnull(m.goals_away_team,-1)) AS numberOfDrawMatches,
	   sum(ifnull(m.goals_home_team,0) < ifnull(m.goals_away_team,0)) AS numberOfLossMatches,
	   sum(ifnull(m.goals_home_team,0)) AS numberOfGoalsScored,
	   sum(ifnull(m.goals_away_team,0)) AS numberOfGoalsAgainst
FROM matches m
WHERE m.fk_match_status_id > 0
GROUP BY m.fk_season_id,m.fk_league_id,m.fk_match_type_id,m.home_team_name
UNION
SELECT m.id AS matchId, 
	   m.fk_season_id AS seasonId,
       m.fk_league_id AS leagueId,
       m.fk_match_type_id AS matchTypeId,
       m.away_team_name AS teamName,
       sum(ifnull(m.goals_away_team,0) > ifnull(m.goals_home_team,0)) AS numberOfWonMatches,
       sum(ifnull(m.goals_away_team,0) = ifnull(m.goals_home_team,-1)) AS numberOfDrawMatches,
       sum(ifnull(m.goals_away_team,0) < ifnull(m.goals_home_team,0)) AS numberOfLossMatches,
       sum(ifnull(m.goals_away_team,0)) AS numberOfGoalsScored,
       sum(ifnull(m.goals_home_team,0)) AS numberOfGoalsAgainst
FROM matches m
WHERE m.fk_match_status_id > 0
GROUP BY m.fk_season_id,m.fk_league_id,m.fk_match_type_id,m.away_team_name
	   
-- DROP VIEW team_activity_view
-- View: team_activity_view 	   
CREATE VIEW team_activity_view AS	   
	SELECT 'match' as type, m.id, m.fk_team_id, m.fk_season_id, m.start_date as start_date, CONCAT(m.home_team_name, " vs ", m.away_team_name) as info, m.venue as place FROM matches m
	UNION SELECT 'training', t.id, t.fk_team_id, t.fk_season_id, t.start_time, 'Training', t.place FROM trainings t
	UNION SELECT 'cup', c.id, 0, fk_season_id, c.start_date, c.cup_name, c.venue FROM cups c


-- DROP VIEW player_statistic_view
-- View: player_statistic_view
CREATE VIEW player_statistic_view AS
	SELECT s.period, s.id AS season_id, (SELECT t.team_name FROM teams t WHERE t.id = p.fk_team_id) AS team_name, p.fk_team_id AS team_id, p.id as player_id, p.first_name, p.middle_name, p.last_name,
	(SELECT count(l.fk_player_id) FROM matches m, player_match_lnk l WHERE m.fk_season_id = s.id AND l.fk_player_id = p.id) AS numberOfPlayerMatches,
	(SELECT count(l.fk_player_id) FROM cups c, player_cup_lnk l WHERE c.fk_season_id = s.id AND l.fk_player_id = p.id) AS numberOfPlayerCups,
	(SELECT count(l.fk_player_id) FROM trainings t, player_training_lnk l WHERE t.fk_season_id = s.id AND l.fk_player_id = p.id) AS numberOfPlayerTrainings,
	(SELECT count(m.fk_team_id) FROM matches m WHERE m.fk_season_id = s.id AND m.fk_team_id = p.fk_team_id) AS numberOfTeamMatches,
	(SELECT count(c.id) FROM cups c WHERE c.fk_season_id = s.id AND c.id = p.fk_team_id) AS numberOfTeamCups,
	(SELECT count(t.fk_team_id) FROM trainings t WHERE t.fk_season_id = s.id AND t.fk_team_id = p.fk_team_id) AS numberOfTeamTrainings
FROM players p, seasons s
ORDER BY numberOfPlayerTrainings, numberOfPlayerMatches

-- DROP VIEW league_table_view
-- View: league_table_view
CREATE VIEW league_table_view AS
SELECT seasonId,leagueId,matchTypeId,teamName,
       SUM(numberOfWonMatches + numberOfDrawMatches + numberOfLossMatches) AS numberOfPlayedMatches,
       SUM(numberOfWonMatches) AS numberOfWonMatches,
       SUM(numberOfDrawMatches) AS numberOfDrawMatches,
       SUM(numberOfLossMatches) AS numberOfLossMatches,
       SUM(numberOfGoalsScored) AS numberOfGoalsScored,
       SUM(numberOfGoalsAgainst) AS numberOfGoalsAgainst,
       SUM(numberOfWonMatches*2 + numberOfDrawMatches*1) AS scores
 FROM match_result_view
 WHERE matchTypeId = 1
 GROUP BY teamName
 ORDER BY scores, teamName DESC
 
 -- DROP VIEW season_statistic_view
-- View: season_statistic_view
CREATE VIEW season_statistic_view AS
SELECT
	s.id AS seasonId,
	s.period AS seasonPeriod,
	s.start_date AS seasonStartDate,
	s.end_date AS seasonEndDate,
	(SELECT count(*) FROM matches m WHERE m.fk_season_id = s.id ) AS numberOfMatches,
	(SELECT count(*) FROM cups c WHERE c.fk_season_id = s.id ) AS numberOfCups,
	(SELECT count(*) FROM tournaments t WHERE t.fk_season_id = s.id ) AS numberOfTournaments,
	(SELECT count(DISTINCT m.fk_team_id) FROM matches m WHERE m.fk_season_id = s.id ) AS numberOfClubs,
	(SELECT count(DISTINCT m.fk_team_id) FROM matches m WHERE m.fk_season_id = s.id ) AS numberOfTeams,
	(SELECT count(DISTINCT m.fk_league_id) FROM matches m WHERE m.fk_season_id = s.id ) AS numberOfLeagues
FROM seasons s ORDER BY s.period ASC
         
