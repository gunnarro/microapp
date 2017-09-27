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
