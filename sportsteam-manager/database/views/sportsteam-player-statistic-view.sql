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
